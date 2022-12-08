package service;

import net.request.GetUserRequest;
import net.request.LoginRequest;
import net.request.RegisterRequest;
import net.request.Request;
import net.response.GetUserResponse;
import net.response.LoginResponse;
import net.response.RegisterResponse;
import net.response.Response;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

import dao.AuthtokenDAO;
import dao.UserDAO;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;

public class UserService extends Service {
    public RegisterResponse registerNewUser(RegisterRequest request) {
        if(request.getFirstName() == null){
            throw new RuntimeException("[Bad Request] Missing a firstname");
        } else if(request.getLastName() == null) {
            throw new RuntimeException("[Bad Request] Missing a lastname");
        } else if(request.getUsername() == null) {
            throw new RuntimeException("[Bad Request] Missing a username");
        } else if(request.getPassword() == null) {
            throw new RuntimeException("[Bad Request] Missing a password");
        } else if(request.getImage() == null) {
            throw new RuntimeException("[Bad Request] Missing a profile pic");
        }

        if (daoFactory.getUserDAO().getUser(request.getUsername()) != null) {
            return new RegisterResponse(false, "Username is already taken!", null, null);
        }

        String imageURL = daoFactory.getImageDAO().putImage(request.getUsername(), request.getImage());


        User user = new User(request.getFirstName(),
                request.getLastName(),
                request.getUsername(),
                imageURL);

        byte[] salt = generateSalt();
        byte[] hashedPassword;
        try {
            hashedPassword = getHashWithSalt(request.getPassword(), salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("[Internal Error] could not hash password");
        }

        daoFactory.getUserDAO().addUser(user, new String(hashedPassword), new String(salt));

        AuthToken token = generateAuthToken(request.getUsername());

        daoFactory.getAuthtokenDAO().addAuthtoken(token.getToken(), request.getUsername());
        return new RegisterResponse(true, user, token);
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

    public byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        return bytes;
    }


    public byte[] getHashWithSalt(String input, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("sha-256");
        digest.reset();
        digest.update(salt);
        byte[] hashedBytes = digest.digest(stringToByte(input));
        return hashedBytes;
    }
    public byte[] stringToByte(String input) {
        if (Base64.isBase64(input)) {
            return Base64.decodeBase64(input);

        } else {
            return Base64.encodeBase64(input.getBytes());
        }
    }

    public AuthToken generateAuthToken(String userAlias){
        byte[] array = new byte[14]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return new AuthToken(generatedString, userAlias, (int) (new Date().getTime()/1000));
    }
}
