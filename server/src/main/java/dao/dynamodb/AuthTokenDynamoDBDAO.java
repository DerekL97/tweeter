package dao.dynamodb;

import dao.AuthtokenDAO;
import edu.byu.cs.tweeter.model.domain.User;

public class AuthTokenDynamoDBDAO extends DynamoDBDAO implements AuthtokenDAO  {
    @Override
    public void addAuthtoken(String authToken, String userAlias) {

    }

    @Override
    public void clearExpiredTokens() {

    }

    @Override
    public void updateExpireTime(String authToken) {

    }

    @Override
    public User getUser(String authToken) {
        return null;
    }
}
