package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import net.request.GetFeedRequest;
import net.request.Request;
import net.response.GetFeedResponse;

import service.FeedService;

public class GetFeedHandler implements RequestHandler<GetFeedRequest, GetFeedResponse> {
    @Override
    public GetFeedResponse handleRequest(GetFeedRequest input, Context context) {
        FeedService service = new FeedService();
        return service.getFeed(input);
    }
}
