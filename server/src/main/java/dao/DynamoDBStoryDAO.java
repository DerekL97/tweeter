package dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;

public class DynamoDBStoryDAO implements StoryDAO {
    private static final String TableName = "Story";

    private static final String SenderAliasAttr = "senderAlias";
    private static final String TimeStampAttr = "timeStamp";
    private static final String PostAttr = "post";
    private static final String UserAttr = "user";
    private static final String DateTimeAttr = "datetime";
    private static final String URLsAttr = "urls";
    private static final String MentionsAttr = "mentions";

    // DynamoDB client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-east-1")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    @Override
    public Boolean postStatus(Status status) {
        Table table = dynamoDB.getTable(TableName);

        Set<String> urls = new HashSet<>(status.getUrls());
        Set<String> mentions = new HashSet<>(status.getMentions());
        urls.add("");
        mentions.add("");

        Item item = new Item()
                .withPrimaryKey(SenderAliasAttr, status.getUser().getAlias(), TimeStampAttr, getTimeStampFromDate(status.getDatetime()))
                .withString(PostAttr, status.getPost())
                .withString(DateTimeAttr, status.getDatetime())
                .withStringSet(URLsAttr,urls)
                .withStringSet(MentionsAttr, mentions);

        table.putItem(item);

        return true;
    }

    @Override
    public Pair<List<Status>, Boolean> getStory(User targetUser, int limit, Status lastStatus) {
        assert limit > 0;
        assert targetUser != null;

        boolean hasMorePages = false;

        List<Status> statusList = new ArrayList<>();

        Map<String, String> attrNames = new HashMap<String, String>();
        attrNames.put("#sndr", SenderAliasAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":trgUsr", new AttributeValue().withS(targetUser.getAlias()));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(TableName)
                .withKeyConditionExpression("#sndr = :trgUsr")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(limit)
                .withScanIndexForward(false);

        if (lastStatus != null) {
            long lastStatusTimeStamp = getTimeStampFromDate(lastStatus.getDatetime());
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(SenderAliasAttr, new AttributeValue().withS(lastStatus.getUser().getAlias()));
            startKey.put(TimeStampAttr, new AttributeValue().withN(String.valueOf(lastStatusTimeStamp)));

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        QueryResult queryResult = amazonDynamoDB.query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();
        if (items != null) {
            for (Map<String, AttributeValue> item : items){
                Status newStatus = new Status(item.get(PostAttr).getS(),
                        targetUser,
                        item.get(DateTimeAttr).getS(),
                        item.get(URLsAttr).getSS(),
                        item.get(MentionsAttr).getSS());
                statusList.add(newStatus);
            }
        }

        if (queryResult.getLastEvaluatedKey() != null) {
            hasMorePages = true;
        }

        return new Pair<>(statusList, hasMorePages);
    }

    private long getTimeStampFromDate(String date) {
        long timeStamp = 0;
        try {
            timeStamp = new SimpleDateFormat("MMM d yyyy h:mm aaa").parse(date).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStamp;
    }

}
