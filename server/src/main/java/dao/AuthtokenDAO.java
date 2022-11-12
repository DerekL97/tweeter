package dao;

import edu.byu.cs.tweeter.model.domain.User;

public interface AuthtokenDAO {
    void addAuthtoken(String authToken, String userAlias);
    void clearExpiredTokens();
    void updateExpireTime(String authToken);
    User getUser(String authToken);
}
