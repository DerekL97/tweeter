package dao.dynamodb;

import java.util.List;

import dao.UserDAO;
import dao.dynamodb.DTO.DynamoDbDTO;
import dao.dynamodb.DTO.StoryDbDTO;
import dao.dynamodb.DTO.UserDbDTO;
import edu.byu.cs.tweeter.model.domain.User;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
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
        List<UserDbDTO> userRow = simpleQuery(userAlias);
        if (userRow.size() < 0){
            return 0;
        }
        else{
            return userRow.get(0).getNumFollowers();
        }
    }


    @Override
    public void incrementFollowerCount(String userAlias, int newFollowers) {
        DynamoDbTable<UserDbDTO> table = getTable();
        Key key = Key.builder()
                .partitionValue(userAlias)
                .build();

        // load it if it exists
        UserDbDTO userDbDTO = table.getItem(key);
        if(userDbDTO != null) {
            userDbDTO.setNumFollowers(userDbDTO.getNumFollowers() + newFollowers);
            table.updateItem(userDbDTO);
        }
    }

    @Override
    public int getFolloweeCount(String userAlias) {
        UserDbDTO searchfor = new UserDbDTO();
        searchfor.setUserAlias(userAlias);
        List<UserDbDTO> userRow = simpleQuery(userAlias);
        if (userRow.size() < 0){
            return 0;
        }
        else{
            return userRow.get(0).getNumFollowees();
        }
    }

    @Override
    public void incrementFolloweeCount(String userAlias, int newFollowees) {
        DynamoDbTable<UserDbDTO> table = getTable();
        Key key = Key.builder()
                .partitionValue(userAlias)
                .build();

        // load it if it exists
        UserDbDTO userDbDTO = table.getItem(key);
        if(userDbDTO != null) {
            userDbDTO.setNumFollowees(userDbDTO.getNumFollowees() + newFollowees);
            table.updateItem(userDbDTO);
        }
    }


}
