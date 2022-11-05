package service;

import net.request.RegisterRequest;
import net.response.RegisterResponse;
import net.response.Response;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;

public class LogInOutService extends Service{
    public static RegisterResponse registerNewUser(RegisterRequest input) {
        User registeredUser = FakeData.getInstance().getFirstUser();
        AuthToken authToken = FakeData.getInstance().getAuthToken();
//        return new Pair<User, AuthToken>(registeredUser, authToken);
        return new RegisterResponse(registeredUser, authToken);
    }

    public Response logout() {
        return new Response(true, "Successfully Logged out");
    }
}
