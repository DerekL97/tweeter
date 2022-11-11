package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import net.TweeterRemoteException;
import net.request.FollowersRequest;
import net.request.Request;
import net.response.FollowersResponse;
import net.response.FollowingResponse;
import net.response.Response;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Background task that retrieves a page of followers.
 */
public class GetFollowersTask extends PagedUserTask {
    public static final String FOLLOWERS_KEY = "items";

    public GetFollowersTask(AuthToken authToken, User targetUser, int limit, User lastFollower,
                            Handler messageHandler) {
        super(authToken, targetUser, limit, lastFollower, messageHandler);
    }

    @Override
    protected Request getRequest() {
        return new FollowersRequest(authToken, getTargetUser().alias, limit, lastItem, getTargetUser());//todo figure out why I need target user twice (I don't think I do)
    }

    @Override
    protected Response getResponse(Request request) throws IOException, TweeterRemoteException {
        return getServerFacade().getFollowers(request);
    }

    @Override
    protected void setVariables(Response response) {
        FollowersResponse myResponse = (FollowersResponse) response;
        hasMorePages = myResponse.getHasMorePages();
        items = myResponse.getPage();
    }

//    @Override
//    protected Pair<List<User>, Boolean> getItems() {
//        return getFakeData().getPageOfUsers(getLastItem(), getLimit(), getTargetUser());
//    }
}
