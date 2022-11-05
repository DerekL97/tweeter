package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import net.request.RegisterRequest;
import net.response.RegisterResponse;

import service.LogInOutService;

public class RegisterHandler implements RequestHandler<RegisterRequest, RegisterResponse> {
    @Override
    public RegisterResponse handleRequest(RegisterRequest input, Context context) {
        RegisterResponse response = LogInOutService.registerNewUser(input);
        return null;
    }
}
