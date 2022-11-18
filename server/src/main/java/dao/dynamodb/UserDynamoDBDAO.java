package dao.dynamodb;

import dao.UserDAO;
import edu.byu.cs.tweeter.model.domain.User;

public class UserDynamoDBDAO extends DynamoDBDAO implements UserDAO {
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
