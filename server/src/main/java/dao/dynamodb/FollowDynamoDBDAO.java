package dao.dynamodb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dao.FollowDAO;
import dao.dynamodb.DTO.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

public class FollowDynamoDBDAO extends DynamoDBDAO implements FollowDAO {
    private static final String TableName = "follow";
    private static final String PartitionKey = "followerAlias";
    private static final String SortKey = "followeeAlias";
    private static final String IndexName = "followeeAlias-followerAlias-index";
//    public static final String IndexName = "location-visitor-index2";//todo what is this?
//    private static final String FollowerName = "follower_name";
//    private static final String FolloweeName = "followee_name";

    @Override
    public boolean follow(User follower, User followee) {
        try {
            DynamoDbTable<Follow> followsTable = enhancedClient.table(TableName, TableSchema.fromBean(Follow.class));
            Follow follow = new Follow(follower.getAlias(), followee.getAlias(), follower.getFirstName(),
                    follower.getLastName(), follower.getImageUrl(), followee.getFirstName(), followee.getLastName(),
                    followee.getImageUrl());
            followsTable.putItem(follow);

        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return true;
//        System.out.println("Follow data added to the table with id id101");
    }

    @Override
    public boolean unfollow(String exFollowerAlias, String followeeAlias) {
        try {
            DynamoDbTable<Follow> table = enhancedClient.table(TableName, TableSchema.fromBean(Follow.class));
            Follow follow = new Follow(exFollowerAlias, followeeAlias);
            Key key = Key.builder()
                    .partitionValue(follow.getFollowerAlias()).sortValue(follow.getFolloweeAlias())
                    .build();
            table.deleteItem(key);
        }
        catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return true;
    }

    @Override
    public List<User> getFollowers(String followeeAlias) {
        try {
            //Create a DynamoDbTable object based on Follow.
            DynamoDbTable<Follow> table = enhancedClient.table(TableName, TableSchema.fromBean(Follow.class));

            DynamoDbIndex<Follow> secIndex = enhancedClient.table(TableName, TableSchema.fromBean(Follow.class)) .index(IndexName);
            AttributeValue attVal = AttributeValue.builder()
                    .n(followeeAlias)
                    .build();

            // Create a QueryConditional object that's used in the query operation.
            QueryConditional queryConditional = QueryConditional
                    .keyEqualTo(Key.builder().partitionValue(attVal)
                            .build());

            // Get items in the table.
            SdkIterable<Page<Follow>> results = secIndex.query(QueryEnhancedRequest.builder()
                    .queryConditional(queryConditional)
                    .limit(300) //todo change limit?
                    .build());

            // Display the results.
            results.forEach(page -> {
                List<Follow> allFollow = page.items();
                for (Follow myFollow: allFollow) {
                    System.out.println("The movie title is " + myFollow.getFollowerAlias() + ". The year is " + myFollow.getFollower_first_name());
                }
            });

        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return new ArrayList<User>();
    }

    @Override
    public List<User> getFollowees(String followerAlias) {
        try{
            DynamoDbTable<Follow> mappedTable = enhancedClient.table(TableName, TableSchema.fromBean(Follow.class));
            QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder()
                    .partitionValue(followerAlias)
                    .build());

            // Get items in the table and write out the ID value.
            Iterator<Follow> results = mappedTable.query(queryConditional).items().iterator();
            List<User> result = new ArrayList<>();

            while (results.hasNext()) {
                Follow rec = results.next();
                result.add(new User(rec.getFollowee_first_name(), rec.getFollowee_last_name(), rec.getFolloweeAlias(), rec.getFollowee_image_url()));
//                System.out.println("The record id is "+result);
            }
            return result;

        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return new ArrayList<>();
    }

    @Override
    public boolean isFollowing(String followerAlias, String followeeAlias) {
        return false;
    }
}
