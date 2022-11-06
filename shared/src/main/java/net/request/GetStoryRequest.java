package net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class GetStoryRequest extends PagedRequest<Status> {

    public GetStoryRequest() {
    }

    public GetStoryRequest(AuthToken authToken, String followerAlias, int limit, Status lastItem) {
        super(authToken, followerAlias, limit, lastItem);
    }
}
