package net.response;

public class IsFollowerResponse extends Response{
    private boolean isFollower;
    public IsFollowerResponse(boolean success, boolean isFollower) {
        super(success);
        this.isFollower = isFollower;
    }

    public IsFollowerResponse(boolean success, String message, boolean isFollower) {
        super(success, message);
        this.isFollower = isFollower;
    }

    public boolean isFollower() {
        return isFollower;
    }

    public void setFollower(boolean follower) {
        isFollower = follower;
    }
}
