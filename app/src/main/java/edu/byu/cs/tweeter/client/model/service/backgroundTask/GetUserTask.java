package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import net.TweeterRemoteException;
import net.request.GetUserRequest;
import net.request.Request;
import net.response.GetUserResponse;
import net.response.Response;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Background task that returns the profile for a specified user.
 */
public class GetUserTask extends AuthenticatedTask {

    public static final String USER_KEY = "user";
    private static final String LOG_TAG = "GetUserTask";

    /**
     * Alias (or handle) for user whose profile is being retrieved.
     */
    private final String alias;

    private User user;

    public GetUserTask(AuthToken authToken, String alias, Handler messageHandler) {
        super(authToken, messageHandler);
        this.alias = alias;
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
        user = ((GetUserResponse) response).getUser();
    }

    private Response getResponse(Request request) throws IOException, TweeterRemoteException {
        GetUserRequest nRequest = (GetUserRequest) request;
        return getServerFacade().getUser(nRequest);
    }

    private Request getRequest() {
        return  new GetUserRequest(alias, authToken.token);
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putSerializable(USER_KEY, user);
    }

//    private User getUser() {
//        return getFakeData().findUserByAlias(alias);
//    }
}
