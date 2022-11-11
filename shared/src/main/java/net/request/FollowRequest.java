package net.request;

public class FollowRequest extends Request{
    private String followee;
    private String user;
    private String authToken;

    public FollowRequest(String followee, String user, String authToken) {
        this.followee = followee;
        this.user = user;
        this.authToken = authToken;
    }

    private FollowRequest() {
    }

    public String getFollowee() {
        return followee;
    }

    public void setFollowee(String followee) {
        this.followee = followee;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
