package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import net.request.GetStoryRequest;
import net.response.GetStoryResponse;

import service.StatusService;

public class GetStoryHandler implements RequestHandler<GetStoryRequest, GetStoryResponse> {
    @Override
    public GetStoryResponse handleRequest(GetStoryRequest input, Context context) {
        StatusService service = new StatusService();
        return service.getStory(input);
    }
}
