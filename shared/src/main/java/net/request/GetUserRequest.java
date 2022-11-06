package net.request;

public class GetUserRequest {
    String userAlias;

    public GetUserRequest() {
    }

    public GetUserRequest(String userAlias) {
        this.userAlias = userAlias;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }
}
