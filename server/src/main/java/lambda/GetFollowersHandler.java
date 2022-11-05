package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import net.request.FollowersRequest;
import net.response.FollowersResponse;

import service.FollowService;

public class GetFollowersHandler implements RequestHandler<FollowersRequest, FollowersResponse> {
    @Override
    public FollowersResponse handleRequest(FollowersRequest input, Context context) {
        FollowService service = new FollowService();
        return service.getFollowers(input);
    }
}
