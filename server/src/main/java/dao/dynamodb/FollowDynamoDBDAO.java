package dao.dynamodb;

import java.util.List;

import dao.FollowDAO;
import dao.dynamodb.DTO.FollowDbDTO;
import edu.byu.cs.tweeter.model.domain.User;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class FollowDynamoDBDAO extends DynamoDBDAOwithIndex<FollowDbDTO, User> implements FollowDAO {
    private static final String TableName = "follow";
    private static final String PartitionKey = "followerAlias";
    private static final String SortKey = "followeeAlias";
    private static final String IndexName = "followeeAlias-followerAlias-index";
//    public static final String IndexName = "location-visitor-index2";//todo what is this?
//    private static final String FollowerName = "follower_name";
//    private static final String FolloweeName = "followee_name";

    @Override
    protected String getPartitionLabel() {
        return PartitionKey;
    }

    @Override
    protected String getSortLabel() {
        return SortKey;
    }

    @Override
    protected User getModelFromDTO(FollowDbDTO myFollow) {
        return new User(myFollow.getFollowee_first_name(), myFollow.getFollowee_last_name(), myFollow.getFolloweeAlias(), myFollow.getFollowee_image_url());
    }

    @Override
    protected DynamoDbIndex<FollowDbDTO> getIndex() {
        return enhancedClient.table(getTable().tableName(), TableSchema.fromBean(FollowDbDTO.class)).index(getIndexName());
    }

    @Override
    protected String getIndexName() {
        return IndexName;
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

    }

    @Override
    public boolean unfollow(String exFollowerAlias, String followeeAlias) {
        return deleteRow(exFollowerAlias, followeeAlias);

    }

    @Override
    public List<User> getFollowers(String followeeAlias) {
        return queryWithIndex(followeeAlias);

    }


    @Override
    public List<User> getFollowees(String followerAlias, User lastItem, int limit) {
        FollowDbDTO lastDTO = new FollowDbDTO();

        lastDTO.setFollowee_first_name(lastItem.getFirstName());
        lastDTO.setFollowee_last_name(lastItem.getLastName());
        lastDTO.setFolloweeAlias(lastItem.getAlias());
        lastDTO.setFollowee_image_url(lastItem.getImageUrl());

        return paginatedQuery(followerAlias, limit, lastDTO);

    }

    @Override
    public int getFollowerCount(String followeeAlias) {
        return 0;
    }

    @Override
    public int getFollowingCount(String followerAlias) {
        return 0;
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
