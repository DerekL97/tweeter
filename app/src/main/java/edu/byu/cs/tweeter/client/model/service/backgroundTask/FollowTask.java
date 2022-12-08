package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;
import android.util.Log;

import net.TweeterRemoteException;
import net.request.FollowRequest;
import net.request.Request;
import net.response.Response;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Background task that establishes a following relationship between two users.
 */
public class FollowTask extends AuthenticatedTask {
    private static final String LOG_TAG = "FollowTask";
    /**
     * The user that is being followed.
     */
    private final User followee;
    private final User follower;


    public FollowTask(AuthToken authToken, User followee, User follower, Handler messageHandler) {
        super(authToken, messageHandler);
        this.followee = followee;
        this.follower = follower;
    }

    @Override
    protected void runTask() {
        try {
            Request request = getRequest();
            Response response = getResponse(request);

//        items = pageOfItems.getFirst();
//        hasMorePages = pageOfItems.getSecond();

            if (response.isSuccess()) {
                setVariables(response);
                sendSuccessMessage();
            } else {
                sendFailedMessage(response.getMessage());
            }
        } catch(IOException | TweeterRemoteException ex){
            Log.e(LOG_TAG, "Failed to get Paged Items", ex);
            sendExceptionMessage(ex);
       }
    }

    private void setVariables(Response response) {
        //do nothing
    }

    private Response getResponse(Request request) throws IOException, TweeterRemoteException {
        FollowRequest Nrequest = (FollowRequest) request;
        return getServerFacade().setFollow(request);
    }

    private FollowRequest getRequest() {
        return new FollowRequest(followee, follower, authToken);
    }

}
