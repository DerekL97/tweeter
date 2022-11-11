package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import net.TweeterRemoteException;
import net.request.GetStoryRequest;
import net.request.Request;
import net.response.GetStoryResponse;
import net.response.Response;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Background task that retrieves a page of statuses from a user's story.
 */
public class GetStoryTask extends PagedStatusTask {
    public static final String STATUSES_KEY = "items";
    public GetStoryTask(AuthToken authToken, User targetUser, int limit, Status lastStatus,
                        Handler messageHandler) {
        super(authToken, targetUser, limit, lastStatus, messageHandler);
    }

    @Override
    protected Request getRequest() {
        return new GetStoryRequest(authToken, getTargetUser().getAlias(), getLimit(), lastItem);//AuthToken authToken, String followerAlias, int limit, Status lastItem
    }

    @Override
    protected Response getResponse(Request request) throws IOException, TweeterRemoteException {
        return getServerFacade().getStory((GetStoryRequest) request, "/getstory");
    }

    @Override
    protected void setVariables(Response response) {
        GetStoryResponse SResponse = (GetStoryResponse) response;
        items = SResponse.getPage();
//        lastItem = items.get(items.size());
        hasMorePages = SResponse.getHasMorePages();
    }

//    @Override
//    protected Pair<List<Status>, Boolean> getItems() {
//        return getFakeData().getPageOfStatus(getLastItem(), getLimit());
//    }
}
