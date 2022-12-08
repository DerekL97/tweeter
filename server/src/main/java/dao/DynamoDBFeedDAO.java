package dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

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

public class DynamoDBFeedDAO implements FeedDAO {
    private static final String TableName = "Feed";

    private static final String ReceiverAliasAttr = "receiverAlias";
    private static final String TimeStampAttr = "timeStamp";
    private static final String PostAttr = "post";
    private static final String SenderAliasAttr = "senderAlias";
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
    public Boolean postStatus(Status status, List<String> FollowerAliases) {
        Table table = dynamoDB.getTable(TableName);

        TableWriteItems statusWrites = new TableWriteItems(TableName);

        for (String followerAlias : FollowerAliases) {
            List<String> senderUser = new ArrayList<>();
            senderUser.add(status.getUser().getFirstName());
            senderUser.add(status.getUser().getLastName());
            senderUser.add(status.getUser().getAlias());
            senderUser.add(status.getUser().getImageUrl());

            Set<String> urls = new HashSet<>(status.getUrls());
            Set<String> mentions = new HashSet<>(status.getMentions());
            urls.add("");
            mentions.add("");

            Item item = new Item()
                    .withPrimaryKey(ReceiverAliasAttr, followerAlias, TimeStampAttr, getTimeStampFromDate(status.getDatetime()))
                    .withString(PostAttr, status.getPost())
                    .withString(DateTimeAttr, status.getDatetime())
                    .withList(SenderAliasAttr, senderUser)
                    .withStringSet(URLsAttr, urls)
                    .withStringSet(MentionsAttr, mentions);

            statusWrites.addItemToPut(item);

            // 25 is the maximum number of items allowed in a single batch write.
            // Attempting to write more than 25 items will result in an exception being thrown
            if (statusWrites.getItemsToPut() != null && statusWrites.getItemsToPut().size() == 25) {
                loopBatchWrite(statusWrites);
                statusWrites = new TableWriteItems(TableName);
            }
        }
        // Write any leftover items
        if (statusWrites.getItemsToPut() != null && statusWrites.getItemsToPut().size() > 0) {
            loopBatchWrite(statusWrites);
        }

        return true;
    }

    private void loopBatchWrite(TableWriteItems items) {

        // The 'dynamoDB' object is of type DynamoDB and is declared statically in this example
        BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(items);

        // Check the outcome for items that didn't make it onto the table
        // If any were not added to the table, try again to write the batch
        while (outcome.getUnprocessedItems().size() > 0) {
            Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();
            outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);
        }
    }

    @Override
    public Pair<List<Status>, Boolean> getFeed(String targetUserAlias, int limit, Status lastStatus) {
        assert limit > 0;
        assert targetUserAlias != null;

        boolean hasMorePages = false;

        List<Status> statusList = new ArrayList<>();

        Map<String, String> attrNames = new HashMap<String, String>();
        attrNames.put("#rcvr", ReceiverAliasAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":trgUsr", new AttributeValue().withS(targetUserAlias));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(TableName)
                .withKeyConditionExpression("#rcvr = :trgUsr")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(limit)
                .withScanIndexForward(false);

        if (lastStatus != null) {
            long lastStatusTimeStamp = getTimeStampFromDate(lastStatus.getDatetime());
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(ReceiverAliasAttr, new AttributeValue().withS(targetUserAlias));
            startKey.put(TimeStampAttr, new AttributeValue().withN(String.valueOf(lastStatusTimeStamp)));

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        QueryResult queryResult = amazonDynamoDB.query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();
        if (items != null) {
            for (Map<String, AttributeValue> item : items){
                List<String> userVals = new ArrayList<>();
                for (AttributeValue val : item.get(SenderAliasAttr).getL()) {
                    userVals.add(val.getS());
                }
                Status newStatus = new Status(item.get(PostAttr).getS(),
                        new User(userVals.get(0), userVals.get(1), userVals.get(2), userVals.get(3)),
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
