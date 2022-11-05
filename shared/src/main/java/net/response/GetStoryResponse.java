package net.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;

public class GetStoryResponse extends PagedResponse<Status> {

    public GetStoryResponse(boolean success, List<Status> page, boolean hasMorePages) {
        super(success, page, hasMorePages);
    }

    public GetStoryResponse(boolean success, String message, List<Status> page, boolean hasMorePages) {
        super(success, message, page, hasMorePages);
    }
}
