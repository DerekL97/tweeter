package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;
import android.util.Log;

import net.TweeterRemoteException;
import net.request.PostStatusRequest;
import net.request.Request;
import net.response.Response;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

/**
 * Background task that posts a new status sent by a user.
 */
public class PostStatusTask extends AuthenticatedTask {

    private static final String LOG_TAG = "PostStatusTask";
    /**
     * The new status being sent. Contains all properties of the status,
     * including the identity of the user sending the status.
     */
    private final Status status;

    public PostStatusTask(AuthToken authToken, Status status, Handler messageHandler) {
        super(authToken, messageHandler);
        this.status = status;
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
        //do nothing
    }

    private Response getResponse(Request request) throws IOException, TweeterRemoteException {
        return getServerFacade().postStatus((PostStatusRequest) request);
    }

    private Request getRequest() {
        return new PostStatusRequest();
    }

}
