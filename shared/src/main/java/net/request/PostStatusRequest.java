package net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class PostStatusRequest extends Request{
    private Status status;

    public PostStatusRequest(Status status) {
        this.status = status;
    }

    public PostStatusRequest(AuthToken authToken, User requester, Status status) {
        super(authToken, requester);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
