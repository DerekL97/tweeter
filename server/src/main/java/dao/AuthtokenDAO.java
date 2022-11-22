package dao;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public interface AuthtokenDAO {
    boolean addAuthtoken(String authToken, String userAlias);
    boolean clearExpiredTokens(String userAlias);
    boolean updateExpireTime(AuthToken authToken);
    String getUserAlias(String authToken);
    boolean isAuthorized(String authtoken, String userAlias);
}
