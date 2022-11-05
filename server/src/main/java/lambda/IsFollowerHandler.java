package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import net.request.IsFollowerRequest;
import net.response.IsFollowerResponse;

import service.FollowService;

public class IsFollowerHandler implements RequestHandler<IsFollowerRequest, IsFollowerResponse> {
    @Override
    public IsFollowerResponse handleRequest(IsFollowerRequest input, Context context) {
        FollowService service = new FollowService();
        return service.isFollower(input);
    }
}
