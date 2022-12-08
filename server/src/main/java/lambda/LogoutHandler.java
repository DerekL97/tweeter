package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import net.request.Request;
import net.response.Response;

import service.UserService;

public class LogoutHandler implements RequestHandler<Request, Response>{

    @Override
    public Response handleRequest(Request input, Context context) {
        UserService service = new UserService();
        return service.logout(input);
    }
}
