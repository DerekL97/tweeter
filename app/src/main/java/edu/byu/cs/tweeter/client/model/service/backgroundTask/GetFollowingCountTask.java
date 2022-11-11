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
 * Background task that queries how many other users a specified user is following.
 */
public class GetFollowingCountTask extends GetCountTask {

    public GetFollowingCountTask(AuthToken authToken, User targetUser, Handler messageHandler) {
        super(authToken, targetUser, messageHandler);
    }

    @Override
    protected void setVariables(Response response) {
        count = ((GetCountResponse) response).getCount();
    }

    @Override
    protected Response getResponse(Request request) throws IOException, TweeterRemoteException {
        return getServerFacade().getFollowingCount(request);
    }

    @Override
    protected Request getRequest() {
        return new GetCountRequest();
    }


}
