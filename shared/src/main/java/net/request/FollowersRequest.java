package net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowersRequest extends PagedRequest<User>{

    private User followee;

    public FollowersRequest(AuthToken authToken, String followerAlias, int limit, User lastItem, User followee) {
        super(authToken, followerAlias, limit, lastItem);
        this.followee = followee;
    }

    public User getFollowee() {
        return followee;
    }

    public void setFollowee(User followee) {
        this.followee = followee;
    }




}
