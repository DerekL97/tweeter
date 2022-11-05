package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import net.request.Request;
import net.response.GetCountResponse;

import service.FollowService;

public class GetFollowingCountHandler implements RequestHandler<Request, GetCountResponse> {
    @Override
    public GetCountResponse handleRequest(Request input, Context context) {
        FollowService service = new FollowService();
        return service.getFollowingCount(input);
    }
}
