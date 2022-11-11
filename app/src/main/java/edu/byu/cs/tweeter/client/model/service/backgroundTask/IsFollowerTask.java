package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import net.TweeterRemoteException;
import net.request.IsFollowerRequest;
import net.request.Request;
import net.response.IsFollowerResponse;
import net.response.Response;

import java.io.IOException;
import java.util.Random;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Background task that determines if one user is following another.
 */
public class IsFollowerTask extends AuthenticatedTask {

    public static final String IS_FOLLOWER_KEY = "is-follower";
    private static final String LOG_TAG = "IsFollowerTask";

    /**
     * The alleged follower.
     */
    private final User follower;

    /**
     * The alleged followee.
     */
    private final User followee;

    private boolean isFollower;

    public IsFollowerTask(AuthToken authToken, User follower, User followee, Handler messageHandler) {
        super(authToken, messageHandler);
        this.follower = follower;
        this.followee = followee;
    }

    @Override
    protected void runTask() {
        try {
            Request request = getRequest();
            Response response = getResponse(request);

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
        isFollower = ((IsFollowerResponse) response).isFollower();
    }

    private Response getResponse(Request request) throws IOException, TweeterRemoteException {
        IsFollowerRequest nRequest = (IsFollowerRequest) request;
        return getServerFacade().isFollower(nRequest);
    }

    private Request getRequest() {
        return new IsFollowerRequest();
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putBoolean(IS_FOLLOWER_KEY, isFollower);
    }
}
