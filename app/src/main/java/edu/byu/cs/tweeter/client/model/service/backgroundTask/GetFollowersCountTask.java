package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import net.TweeterRemoteException;
import net.request.GetCountRequest;
import net.request.Request;
import net.response.GetCountResponse;
import net.response.Response;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Background task that queries how many followers a user has.
 */
public class GetFollowersCountTask extends GetCountTask {

    public GetFollowersCountTask(AuthToken authToken, User targetUser, Handler messageHandler) {
        super(authToken, targetUser, messageHandler);
    }

    @Override
    protected void setVariables(Response response) {
        count = ((GetCountResponse) response).getCount();
    }

    @Override
    protected Response getResponse(Request request) throws IOException, TweeterRemoteException {
        GetCountRequest nRequest = (GetCountRequest) request;
        return getServerFacade().getFollowersCount(request);
    }

    @Override
    protected Request getRequest() {
        return new GetCountRequest();
    }

}
