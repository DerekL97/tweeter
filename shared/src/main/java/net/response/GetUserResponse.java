package net.response;

import edu.byu.cs.tweeter.model.domain.User;

public class GetUserResponse extends Response{
    public User user;

    public GetUserResponse(boolean success, User user) {
        super(success);
        this.user = user;
    }

    public GetUserResponse(boolean success, String message, User user) {
        super(success, message);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
