package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import net.TweeterRemoteException;
import net.request.RegisterRequest;
import net.request.Request;
import net.response.RegisterResponse;
import net.response.Response;

import java.io.IOException;

/**
 * Background task that creates a new user account and logs in the new user (i.e., starts a session).
 */
public class RegisterTask extends AuthenticateTask {

    /**
     * The user's first name.
     */
    private final String firstName;

    /**
     * The user's last name.
     */
    private final String lastName;

    /**
     * The base-64 encoded bytes of the user's profile image.
     */
    private final String image;

    public RegisterTask(String firstName, String lastName, String username, String password,
                        String image, Handler messageHandler) {
        super(messageHandler, username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
    }

    @Override
    protected void setVariables(Response response) {
        authenticatedUser = ((RegisterResponse) response).getUser();
        authToken = ((RegisterResponse) response).getAuthToken();
    }

    @Override
    protected RegisterResponse getResponse(Request request) throws IOException, TweeterRemoteException {
        return getServerFacade().register((RegisterRequest) request);
    }

    @Override
    protected Request getRequest() {
        return new RegisterRequest(firstName, lastName, image, username, password);
    }

//    @Override
//    protected Pair<User, AuthToken> runAuthenticationTask() {
//        User registeredUser = getFakeData().getFirstUser();
//        AuthToken authToken = getFakeData().getAuthToken();
//        return new Pair<>(registeredUser, authToken);
//    }
}
