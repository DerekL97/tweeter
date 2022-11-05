package service;

import net.request.GetStoryRequest;
import net.request.PostStatusRequest;
import net.response.GetStoryResponse;
import net.response.Response;

import edu.byu.cs.tweeter.util.FakeData;

public class StatusService {
    public Response postStatus(PostStatusRequest input) {
        return null; //todo make it
    }

    public GetStoryResponse getStory(GetStoryRequest input) {
        FakeData.getInstance().getPageOfStatus(input.getLastItem(), input.getLimit());
        return null;
    }
}
