package net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Contains all the information needed to make a request to have the server return the next page of
 * followees for a specified follower.
 */
public class FollowingRequest extends PagedRequest<User>{

    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    private FollowingRequest() {
        super();
    }

    /**
     * Creates an instance.
     *
     * @param followerAlias the alias of the user whose followees are to be returned.
     * @param limit the maximum number of followees to return.
     * @param lastFolloweeAlias the alias of the last followee that was returned in the previous request (null if
     *                     there was no previous request or if no followees were returned in the
     *                     previous request).
     */
    public FollowingRequest(AuthToken authToken, String followerAlias, int limit, User lastFolloweeAlias) {
        super(authToken, followerAlias, limit, lastFolloweeAlias);
    }
}
