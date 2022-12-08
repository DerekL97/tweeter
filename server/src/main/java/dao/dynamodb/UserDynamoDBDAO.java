package dao.dynamodb;

import dao.UserDAO;
import dao.dynamodb.DTO.DynamoDbDTO;
import edu.byu.cs.tweeter.model.domain.User;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

public class UserDynamoDBDAO extends DynamoDBDAO implements UserDAO {
    private static final String TableName = "User";
    private static final String PartitionKey = "UserAlias";
//    private static final String SortKey = "followeeAlias";
//    private static final String IndexName = "followeeAlias-followerAlias-index";
//    private static final String FollowerName = "follower_name";
//    private static final String FolloweeName = "followee_name";
    @Override
    protected String getPartitionLabel() {
        return null;
    }

    @Override
    protected String getSortLabel() {
        return null;
    }

    @Override
    protected Object getModelFromDTO(DynamoDbDTO rec) {
        return null;
    }

    @Override
    protected DynamoDbTable createTable() {
        return null;
    }

    @Override
    public User getUser(String userAlias) {
        return null;
    }

    @Override
    public void addUser(User newUser) {

    }

    @Override
    public int getFollowCount(String userAlias) {
        return 0;
    }

    @Override
    public void incrementFollowerCount(int newFollowers) {

    }

    @Override
    public int getFolloweeCount(String userAlias) {
        return 0;
    }

    @Override
    public void incrementFolloweeCount(int newFollowees) {

    }


}
