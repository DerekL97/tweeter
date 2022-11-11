package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import net.TweeterRemoteException;
import net.request.Request;
import net.response.Response;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class GetCountTask extends AuthenticatedTask {

    public static final String COUNT_KEY = "count";
    private static final String LOG_TAG = "GetCountTask";

    /**
     * The user whose count is being retrieved.
     * (This can be any user, not just the currently logged-in user.)
     */
    private final User targetUser;

    protected int count;

    protected GetCountTask(AuthToken authToken, User targetUser, Handler messageHandler) {
        super(authToken, messageHandler);
        this.targetUser = targetUser;
    }

    protected User getTargetUser() {
        return targetUser;
    }

    @Override
    protected void runTask() {
        try{
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

    protected abstract void setVariables(Response response);

    protected abstract Response getResponse(Request request) throws IOException, TweeterRemoteException;

    protected abstract Request getRequest();


    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putInt(COUNT_KEY, count);
    }
}
