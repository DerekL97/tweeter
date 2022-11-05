package net.response;

import net.request.FollowingRequest;

import java.util.List;
import java.util.Objects;

import edu.byu.cs.tweeter.model.domain.User;

/**
 * A paged response for a {@link FollowingRequest}.
 */
public class FollowingResponse extends PagedResponse<User> {


    public FollowingResponse(boolean success, List<User> page, boolean hasMorePages) {
        super(success, page, hasMorePages);
    }

    public FollowingResponse(boolean success, String message, List<User> page, boolean hasMorePages) {
        super(success, message, page, hasMorePages);
    }
}
