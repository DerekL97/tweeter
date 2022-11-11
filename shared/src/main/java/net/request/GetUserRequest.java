package net.request;

public class GetUserRequest extends Request{
    String userAlias;
    String authToken;

    public GetUserRequest(String userAlias, String authToken) {
        this.userAlias = userAlias;
        this.authToken = authToken;
    }

    public GetUserRequest() {
    }

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }
}
