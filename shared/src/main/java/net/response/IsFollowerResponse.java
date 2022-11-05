package net.response;

public class IsFollowerResponse extends Response{
    boolean isFollower;
    public IsFollowerResponse(boolean success, boolean isFollower) {
        super(success);
        this.isFollower = isFollower;
    }

    public IsFollowerResponse(boolean success, String message, boolean isFollower) {
        super(success, message);
        this.isFollower = isFollower;
    }
}
