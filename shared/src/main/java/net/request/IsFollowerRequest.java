package net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class IsFollowerRequest extends Request {
    private User follower;
    private User followee;

    public IsFollowerRequest() {
    }

    public IsFollowerRequest(User follower, User followee) {
        this.follower = follower;
        this.followee = followee;
    }

    public IsFollowerRequest(AuthToken authToken, User follower, User followee) {
        super(authToken, followee);
        this.follower = follower;
        this.followee = followee;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowee() {
        return followee;
    }

    public void setFollowee(User followee) {
        this.followee = followee;
    }
}
