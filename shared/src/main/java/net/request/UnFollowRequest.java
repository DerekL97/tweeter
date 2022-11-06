package net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class UnFollowRequest {
    private String followee;
    private String userHandle;
    private AuthToken authToken;
    public UnFollowRequest() {
    }

    public UnFollowRequest(String followee, String userHandle, AuthToken authToken) {
        this.followee = followee;
        this.userHandle = userHandle;
        this.authToken = authToken;
    }

    public String getFollowee() {
        return followee;
    }

    public void setFollowee(String followee) {
        this.followee = followee;
    }

    public String getUserHandle() {
        return userHandle;
    }

    public void setUserHandle(String userHandle) {
        this.userHandle = userHandle;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
