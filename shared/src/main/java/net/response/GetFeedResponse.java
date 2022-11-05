package net.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;

public class GetFeedResponse extends PagedResponse<Status> {
    public GetFeedResponse(boolean success, List<Status> page, boolean hasMorePages) {
        super(success, page, hasMorePages);
    }

    public GetFeedResponse(boolean success, String message, List<Status> page, boolean hasMorePages) {
        super(success, message, page, hasMorePages);
    }
}
