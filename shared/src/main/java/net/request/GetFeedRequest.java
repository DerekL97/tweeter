package net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class GetFeedRequest extends PagedRequest<Status> {

    public GetFeedRequest() {
    }

    public GetFeedRequest(AuthToken authToken, String followerAlias, int limit, Status lastItem) {
        super(authToken, followerAlias, limit, lastItem);
    }
}
