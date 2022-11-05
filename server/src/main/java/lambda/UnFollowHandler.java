package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import net.request.UnFollowRequest;
import net.response.Response;

import service.FollowService;

public class UnFollowHandler implements RequestHandler<UnFollowRequest, Response> {
    @Override
    public Response handleRequest(UnFollowRequest input, Context context) {
        FollowService service = new FollowService();
        return service.unFollow(input.getFollowee(), input.getUser(), input.getAuthToken());
    }

}
