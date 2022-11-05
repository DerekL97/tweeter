package net.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowersResponse extends PagedResponse<User> {


    public FollowersResponse(boolean success, List<User> page, boolean hasMorePages) {
        super(success, page, hasMorePages);
    }

    public FollowersResponse(boolean success, String message, List<User> page, boolean hasMorePages) {
        super(success, message, page, hasMorePages);
    }
}
