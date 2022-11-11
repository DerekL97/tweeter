package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import net.TweeterRemoteException;
import net.request.FollowingRequest;
import net.request.Request;
import net.response.FollowingResponse;
import net.response.Response;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Background task that retrieves a page of other users being followed by a specified user.
 */
public class GetFollowingTask extends PagedUserTask {
    public static final String FOLLOWEES_KEY = "items";

    public GetFollowingTask(AuthToken authToken, User targetUser, int limit, User lastFollowee,
                            Handler messageHandler) {
        super(authToken, targetUser, limit, lastFollowee, messageHandler);
    }

    @Override
    protected Request getRequest() {
        return new FollowingRequest(authToken, getTargetUser().alias, getLimit(), getLastItem());
    }

    @Override
    protected Response getResponse(Request request) throws IOException, TweeterRemoteException {
        FollowingResponse response = getServerFacade().getFollowees((FollowingRequest) request);
//        System.out.println("response returned in getFollowingTask");
        return response;
    }

    @Override
    protected void setVariables(Response response) {
        FollowingResponse myResponse = (FollowingResponse) response;
        hasMorePages = myResponse.getHasMorePages();
        items = myResponse.getPage();
//        lastItem = items.get(items.size());
    }

//    @Override
//    protected Pair<List<User>, Boolean> getItems() {
//        return getFakeData().getPageOfUsers(getLastItem(), getLimit(), getTargetUser());
//    }
}
