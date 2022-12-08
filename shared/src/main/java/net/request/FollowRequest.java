package net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowRequest extends Request{
    private User followee;
    private User user;

    public FollowRequest(User followee, User user, AuthToken authToken) {
        super(authToken, user);
        this.followee = followee;
        this.user = user;
    }

    private FollowRequest() {
    }

    public User getFollowee() {
        return followee;
    }

    public void setFollowee(User followee) {
        this.followee = followee;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
