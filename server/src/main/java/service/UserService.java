package service;

import net.request.GetUserRequest;
import net.request.LoginRequest;
import net.request.RegisterRequest;
import net.request.Request;
import net.response.GetUserResponse;
import net.response.LoginResponse;
import net.response.RegisterResponse;
import net.response.Response;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Random;

import dao.AuthtokenDAO;
import dao.UserDAO;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;

public class UserService extends Service {
    public RegisterResponse registerNewUser(RegisterRequest input) {
        String userURL = daoFactory.getImageDAO().putImage(input.getUsername(), input.getImage());
        User user = new User(input.getFirstName(), input.getLastName(), input.getUsername(), userURL);

        UserDAO userDAO = daoFactory.getUserDAO();
        userDAO.addUser(user);

        AuthtokenDAO authTokendao = daoFactory.getAuthtokenDAO();
        AuthToken authToken = generateAuthToken(input.getUsername());
        authTokendao.addAuthtoken(authToken.getToken(), user.getAlias());
        return new RegisterResponse(true, user, authToken);
    }

    public Response logout(Request input) {
        checkAuthToken(input.getAuthToken());
        boolean success = daoFactory.getAuthtokenDAO().clearExpiredTokens(input.getRequester().getAlias());
        return new Response(true, "Successfully Logged out");
    }

    public LoginResponse login(LoginRequest request) {
        if(request.getUsername() == null){
            throw new RuntimeException("[Bad Request] Missing a username");
        } else if(request.getPassword() == null) {
            throw new RuntimeException("[Bad Request] Missing a password");
        }
        AuthToken authToken =generateAuthToken(request.getUsername());
        daoFactory.getAuthtokenDAO().addAuthtoken(authToken.getToken(), request.getUsername());
        User user = daoFactory.getUserDAO().getUser(request.getUsername());
//        AuthToken authToken = getDummyAuthToken();
        return new LoginResponse(user, authToken);
    }

    /**
     * Returns the dummy user to be returned by the login operation.
     * This is written as a separate method to allow mocking of the dummy user.
     *
     * @return a dummy user.
     */
//    User getDummyUser() {
//        return getFakeData().getFirstUser();
//    }

    /**
     * Returns the dummy auth token to be returned by the login operation.
     * This is written as a separate method to allow mocking of the dummy auth token.
     *
     * @return a dummy auth token.
     */
//    AuthToken getDummyAuthToken() {
//        return getFakeData().getAuthToken();
//    }

    /**
     * Returns the {@link FakeData} object used to generate dummy users and auth tokens.
     * This is written as a separate method to allow mocking of the {@link FakeData}.
     *
     * @return a {@link FakeData} instance.
     */
//    FakeData getFakeData() {
//        return FakeData.getInstance();
//    }

    public GetUserResponse getUser(GetUserRequest input) {
        checkAuthToken(input.getAuthToken());
        User user = daoFactory.getUserDAO().getUser(input.getUserAlias());
//        User user = getFakeData().findUserByAlias(input.getUserAlias());
        if(user == null) {
            return new GetUserResponse(false, "Failed to findUser", null);
        }
        else{
            return new GetUserResponse(true, "Found User", user);
        }

    }

    public AuthToken generateAuthToken(String userAlias){
        byte[] array = new byte[14]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return new AuthToken(generatedString, userAlias, (int) (new Date().getTime()/1000));
    }
}
