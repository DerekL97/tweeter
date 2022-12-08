package dao;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public interface AuthTokenDAO {
    AuthToken createAuthToken(String userAlias);
    boolean validAuthToken(AuthToken authToken);
    String getCurrUserAlias(AuthToken authToken);
    void deleteAuthToken(String token);
}
