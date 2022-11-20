package dao.dynamodb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dao.FollowDAO;
import dao.dynamodb.DTO.FollowDbDTO;
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

public class FollowDynamoDBDAO extends DynamoDBDAOwithIndex<FollowDbDTO, User> implements FollowDAO {
    private static final String TableName = "follow";
    private static final String PartitionKey = "followerAlias";
    private static final String SortKey = "followeeAlias";
    private static final String IndexName = "followeeAlias-followerAlias-index";
//    public static final String IndexName = "location-visitor-index2";//todo what is this?
//    private static final String FollowerName = "follower_name";
//    private static final String FolloweeName = "followee_name";

    @Override
    protected User getModelFromDTO(FollowDbDTO rec) {
        return new User(rec.getFollowee_first_name(), rec.getFollowee_last_name(), rec.getFolloweeAlias(), rec.getFollowee_image_url());
    }

    @Override
    protected User getModelFromDTOIndex(FollowDbDTO myFollow) {
        return new User(myFollow.getFollower_first_name(), myFollow.getFollowee_last_name(), myFollow.getFollowerAlias(), myFollow.getFollowee_image_url());
    }

    @Override
    protected DynamoDbTable createTable() {
        return enhancedClient.table(TableName, TableSchema.fromBean(FollowDbDTO.class));
    }

    @Override
    protected DynamoDbIndex<FollowDbDTO> createIndex() {
        return enhancedClient.table(TableName, TableSchema.fromBean(FollowDbDTO.class)) .index(IndexName);
    }


    @Override
    public boolean follow(User follower, User followee) {
        return addRow(new FollowDbDTO(follower.getAlias(), followee.getAlias(), follower.getFirstName(),
                    follower.getLastName(), follower.getImageUrl(), followee.getFirstName(), followee.getLastName(),
                    followee.getImageUrl()));
//        try {
//            DynamoDbTable<Follow> followsTable = enhancedClient.table(TableName, TableSchema.fromBean(Follow.class));
//            Follow follow = new Follow(follower.getAlias(), followee.getAlias(), follower.getFirstName(),
//                    follower.getLastName(), follower.getImageUrl(), followee.getFirstName(), followee.getLastName(),
//                    followee.getImageUrl());
//            followsTable.putItem(follow);
//
//        } catch (DynamoDbException e) {
//            System.err.println(e.getMessage());
//            System.exit(1);
//        }
//        return true;
////        System.out.println("Follow data added to the table with id id101");
    }

    @Override
    public boolean unfollow(String exFollowerAlias, String followeeAlias) {
        return deleteRow(exFollowerAlias, followeeAlias);
//        try {
//            DynamoDbTable<Follow> table = enhancedClient.table(TableName, TableSchema.fromBean(Follow.class));
//            Follow follow = new Follow(exFollowerAlias, followeeAlias);
//            Key key = Key.builder()
//                    .partitionValue(follow.getFollowerAlias()).sortValue(follow.getFolloweeAlias())
//                    .build();
//            table.deleteItem(key);
//        }
//        catch (DynamoDbException e) {
//            System.err.println(e.getMessage());
//            System.exit(1);
//        }
//        return true;
    }

    @Override
    public List<User> getFollowers(String followeeAlias) {
        return queryWithIndex(followeeAlias);
//        try {
//            //Create a DynamoDbTable object based on Follow.
//            DynamoDbTable<FollowDbDTO> table = enhancedClient.table(TableName, TableSchema.fromBean(FollowDbDTO.class));
//
//            DynamoDbIndex<FollowDbDTO> secIndex = enhancedClient.table(TableName, TableSchema.fromBean(FollowDbDTO.class)) .index(IndexName);
//            AttributeValue attVal = AttributeValue.builder()
//                    .s(followeeAlias)
//                    .build();
//
//            // Create a QueryConditional object that's used in the query operation.
//            QueryConditional queryConditional = QueryConditional
//                    .keyEqualTo(Key.builder().partitionValue(attVal)
//                            .build());
//
//            // Get items in the table.
//            SdkIterable<Page<FollowDbDTO>> results = secIndex.query(QueryEnhancedRequest.builder()
//                    .queryConditional(queryConditional)
//                    .limit(300) //todo change limit?
//                    .build());
//
//            // Return the results
//            List<User> followers = new ArrayList<>();
//            results.forEach(page -> {
//                List<FollowDbDTO> allFollow = page.items();
//                for (FollowDbDTO myFollow: allFollow) {
//                    followers.add(new User(myFollow.getFollower_first_name(), myFollow.getFollowee_last_name(), myFollow.getFollowerAlias(), myFollow.getFollowee_image_url()));
//                }
//            });
//            return followers;
//
//        } catch (DynamoDbException e) {
//            System.err.println(e.getMessage());
//            System.exit(1);
//        }
//        return new ArrayList<User>();
    }

    @Override
    public List<User> getFollowees(String followerAlias) {
        return query(followerAlias);

//        try{
//            DynamoDbTable<Follow> mappedTable = enhancedClient.table(TableName, TableSchema.fromBean(Follow.class));
//            QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder()
//                    .partitionValue(followerAlias)
//                    .build());
//
//            // Get items in the table and write out the ID value.
//            Iterator<Follow> results = mappedTable.query(queryConditional).items().iterator();
//            List<User> result = new ArrayList<>();
//
//            while (results.hasNext()) {
//                Follow rec = results.next();
//                result.add(new User(rec.getFollowee_first_name(), rec.getFollowee_last_name(), rec.getFolloweeAlias(), rec.getFollowee_image_url()));
////                System.out.println("The record id is "+result);
//            }
//            return result;
//
//        } catch (DynamoDbException e) {
//            System.err.println(e.getMessage());
//            System.exit(1);
//        }
//        return new ArrayList<>();
    }

    @Override
    public boolean isFollowing(String followerAlias, String followeeAlias) {
        List<User> list = query(followerAlias, followeeAlias);
        return !list.isEmpty();
//        try{
//            DynamoDbTable<FollowDbDTO> mappedTable = enhancedClient.table(TableName, TableSchema.fromBean(FollowDbDTO.class));
//            QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder()
//                    .partitionValue(followerAlias)
//                    .sortValue(followeeAlias)
//                    .build());
//
//            // Get items in the table and write out the ID value.
//            Iterator<FollowDbDTO> results = mappedTable.query(queryConditional).items().iterator();
//            List<User> result = new ArrayList<>();
//
//            if (results.hasNext()) {
//                return true;
//            }
//            return false;
//
//        } catch (DynamoDbException e) {
//            System.err.println(e.getMessage());
//            System.exit(1);
//        }
//        return false;
    }


}
