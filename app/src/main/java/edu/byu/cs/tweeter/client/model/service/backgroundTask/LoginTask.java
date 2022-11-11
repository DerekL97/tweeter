package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import net.TweeterRemoteException;
import net.request.LoginRequest;
import net.request.Request;
import net.response.LoginResponse;
import net.response.RegisterResponse;
import net.response.Response;

import java.io.IOException;

/**
 * Background task that logs in a user (i.e., starts a session).
 */
public class LoginTask extends AuthenticateTask {

    public LoginTask(String username, String password, Handler messageHandler) {
        super(messageHandler, username, password);
    }

    @Override
    protected void setVariables(Response response) {
        this.authenticatedUser = ((LoginResponse) response).getUser();
        this.authToken = ((LoginResponse) response).getAuthToken();
    }

    @Override
    protected LoginResponse getResponse(Request request) throws IOException, TweeterRemoteException {
        return getServerFacade().login((LoginRequest) request);
    }

    @Override
    protected Request getRequest() {
        return new LoginRequest(username, password);
    }

//    @Override
//    protected Pair<User, AuthToken> runAuthenticationTask() {
//        User loggedInUser = getFakeData().getFirstUser();
//        AuthToken authToken = getFakeData().getAuthToken();
//        return new Pair<>(loggedInUser, authToken);
//    }
}
