package edu.byu.cs.tweeter.client.model.net;

import net.TweeterRemoteException;
import net.request.FollowingRequest;
import net.request.GetStoryRequest;
import net.request.GetUserRequest;
import net.request.LoginRequest;
import net.request.PostStatusRequest;
import net.request.RegisterRequest;
import net.request.Request;
import net.request.UnFollowRequest;
import net.response.FollowersResponse;
import net.response.FollowingResponse;
import net.response.GetCountResponse;
import net.response.GetFeedResponse;
import net.response.GetStoryResponse;
import net.response.GetUserResponse;
import net.response.IsFollowerResponse;
import net.response.LoginResponse;
import net.response.RegisterResponse;
import net.response.Response;

import java.io.IOException;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {


    private static final String SERVER_URL = "https://wlvguvpqni.execute-api.us-west-2.amazonaws.com/Dev";

    private final ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);

    public Response getStory(GetStoryRequest request, String urlPath) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost(urlPath, request,null, GetStoryResponse.class);
    }

    /**
     * Performs a login and if successful, returns the logged in user and an auth token.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginResponse login(LoginRequest request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/login", request, null, LoginResponse.class);
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the followees.
     */
    public FollowingResponse getFollowees(FollowingRequest request)//, String urlPath(former parameter
            throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/getfollowers", request, null, FollowingResponse.class);
    }

    public GetCountResponse getFollowingCount(Request request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/getfollowingcount", request, null, GetCountResponse.class);
    }

    public FollowersResponse getFollowers(Request request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/getfollowers", request,null, FollowersResponse.class);
    }

    public Response setFollow(Request request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/followhandler", request, null, Response.class);
    }

    public Response getFollowersCount(Request request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/getfollowercount", request,null, GetCountResponse.class);
    }

    public Response getUser(GetUserRequest request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/getuser", request,null, GetUserResponse.class);
    }

    public Response isFollower(Request request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/isfollower", request,null, IsFollowerResponse.class);
    }

    public RegisterResponse register(RegisterRequest request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/register", request, null, RegisterResponse.class);
    }

    public Response logout(Request request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/logouthandler", request, null, Response.class);
    }

    public Response postStatus(PostStatusRequest request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/poststatus", request, null, Response.class);
    }

    public Response unfollow(UnFollowRequest request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/unfollow", request, null, Response.class);
    }

    public Response getFeed(Request request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/getfeedhandler", request, null, GetFeedResponse.class);
    }
}
