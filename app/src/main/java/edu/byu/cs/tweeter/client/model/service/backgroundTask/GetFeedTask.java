package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import net.TweeterRemoteException;
import net.request.GetFeedRequest;
import net.request.PagedRequest;
import net.request.Request;
import net.response.GetFeedResponse;
import net.response.Response;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Background task that retrieves a page of statuses from a user's feed.
 */
public class GetFeedTask extends PagedStatusTask {
    public static final String STATUSES_KEY = "items";

    public GetFeedTask(AuthToken authToken, User targetUser, int limit, Status lastStatus,
                       Handler messageHandler) {
        super(authToken, targetUser, limit, lastStatus, messageHandler);
    }

    @Override
    protected Request getRequest() {
        return new GetFeedRequest(authToken, getTargetUser().alias, limit, lastItem);
    }

    @Override
    protected Response getResponse(Request request) throws IOException, TweeterRemoteException {
        return getServerFacade().getFeed(request);
    }

    @Override
    protected void setVariables(Response response) {
        items = ((GetFeedResponse) response).getPage();
        hasMorePages = ((GetFeedResponse) response).getHasMorePages();
    }

//    @Override
//    protected Pair<List<Status>, Boolean> getItems() {
//        return getFakeData().getPageOfStatus(getLastItem(), getLimit());
//    }
}
