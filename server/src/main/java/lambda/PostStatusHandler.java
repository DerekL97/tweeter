package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import net.request.PostStatusRequest;
import net.response.RegisterResponse;
import net.response.Response;

import service.StatusService;

public class PostStatusHandler implements RequestHandler<PostStatusRequest, Response>{
    @Override
    public Response handleRequest(PostStatusRequest input, Context context) {
        StatusService service = new StatusService();
        return service.postStatus(input);
    }
}
