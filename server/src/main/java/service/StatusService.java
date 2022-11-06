package service;

import com.google.gson.JsonSerializer;

import net.request.GetStoryRequest;
import net.request.PostStatusRequest;
import net.response.GetStoryResponse;
import net.response.Response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.util.FakeData;
import edu.byu.cs.tweeter.util.Pair;

public class StatusService {
    public Response postStatus(PostStatusRequest input) {
        return new Response(true, "Successfully Posted Status");
    }

    public GetStoryResponse getStory(GetStoryRequest input) {
        Pair<List<Status>, Boolean> data = FakeData.getInstance().getPageOfStatus(input.getLastItem(), input.getLimit());
        return new GetStoryResponse(true, data.getFirst(), data.getSecond());
    }
}
