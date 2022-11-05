package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import net.request.GetUserRequest;
import net.response.GetUserResponse;

import service.UserService;

public class GetUserHandler implements RequestHandler<GetUserRequest, GetUserResponse> {
    @Override
    public GetUserResponse handleRequest(GetUserRequest input, Context context) {
        UserService service = new UserService();
        return service.getUser(input);
    }
}
