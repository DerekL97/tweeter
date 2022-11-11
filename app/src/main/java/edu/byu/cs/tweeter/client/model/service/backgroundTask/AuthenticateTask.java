package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import net.TweeterRemoteException;
import net.request.Request;
import net.response.RegisterResponse;
import net.response.Response;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class AuthenticateTask extends BackgroundTask {

    public static final String USER_KEY = "user";
    public static final String AUTH_TOKEN_KEY = "auth-token";
    private static final String LOG_TAG = "AuthenticateTask";

    protected User authenticatedUser;

    protected AuthToken authToken;

    /**
     * The user's username (or "alias" or "handle"). E.g., "@susan".
     */
    protected final String username;

    /**
     * The user's password.
     */
    protected final String password;

    protected AuthenticateTask(Handler messageHandler, String username, String password) {
        super(messageHandler);
        this.username = username;
        this.password = password;
    }


    @Override
    protected final void runTask()  throws IOException {
//        Pair<User, AuthToken> loginResult = runAuthenticationTask();
//
//        authenticatedUser = loginResult.getFirst();
//        authToken = loginResult.getSecond();
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

    protected abstract void setVariables(Response response);


    protected abstract Response getResponse(Request request) throws IOException, TweeterRemoteException;

    protected abstract Request getRequest();

//    protected abstract Pair<User, AuthToken> runAuthenticationTask();

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putSerializable(USER_KEY, authenticatedUser);
        msgBundle.putSerializable(AUTH_TOKEN_KEY, authToken);
    }
}
