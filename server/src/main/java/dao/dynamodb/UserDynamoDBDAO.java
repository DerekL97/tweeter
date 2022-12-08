package dao.dynamodb;

import dao.UserDAO;
import dao.dynamodb.DTO.DynamoDbDTO;
import dao.dynamodb.DTO.StoryDbDTO;
import dao.dynamodb.DTO.UserDbDTO;
import edu.byu.cs.tweeter.model.domain.User;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class UserDynamoDBDAO extends DynamoDBDAO<UserDbDTO, User> implements UserDAO {
    private static final String TableName = "User";
    private static final String PartitionKey = "UserAlias";
//    private static final String SortKey = "followeeAlias";
//    private static final String IndexName = "followeeAlias-followerAlias-index";
//    private static final String FollowerName = "follower_name";
//    private static final String FolloweeName = "followee_name";
    @Override
    protected String getPartitionLabel() {
        return PartitionKey;
    }

    @Override
    protected String getSortLabel() {
        return null;
    }

    @Override
    protected User getModelFromDTO(UserDbDTO rec) {
        User user = new User();
        user.setImageUrl(rec.getImageLink());
        user.setAlias(rec.getUserAlias());
        user.setFirstName(rec.getFirstName());
        user.setLastName(rec.getLastName());
        return user;
    }


    @Override
    protected DynamoDbTable createTable() {
        return enhancedClient.table(TableName, TableSchema.fromBean(UserDbDTO.class));
    }

    @Override
    public User getUser(String userAlias) {
        return null;
    }

    @Override
    public void addUser(User newUser, String salt, String hashedPassword) {
        UserDbDTO DTOUser = new UserDbDTO();
        DTOUser.setUserAlias(newUser.getAlias());
        DTOUser.setImageLink(newUser.getImageUrl());
        DTOUser.setFirstName(newUser.getFirstName());
        DTOUser.setLastName(newUser.getLastName());

        DTOUser.setNumFollowees(0);
        DTOUser.setNumFollowers(0);

        DTOUser.setHashedPassword(hashedPassword);
        DTOUser.setSalt(salt);
        addRow(DTOUser);

    }


    @Override
    public int getFollowCount(String userAlias) {
        UserDbDTO searchfor = new UserDbDTO();
        searchfor.setUserAlias(userAlias);
        query(userAlias);
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
