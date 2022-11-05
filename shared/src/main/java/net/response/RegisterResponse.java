package net.response;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterResponse {
    private User user;
    private AuthToken authToken;

    public RegisterResponse(User user, AuthToken authToken) {
        this.user = user;
        this.authToken = authToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
