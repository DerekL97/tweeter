package service;

import com.google.gson.JsonSerializer;

import net.request.GetStoryRequest;
import net.request.PostStatusRequest;
import net.response.GetStoryResponse;
import net.response.Response;

import java.util.List;

import dao.StoryDAO;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.util.FakeData;
import edu.byu.cs.tweeter.util.Pair;

public class StatusService extends Service{
    public Response postStatus(PostStatusRequest input) {
        checkAuthToken(input.getAuthToken());
        StoryDAO storyDAO = daoFactory.getStoryDAO();
        storyDAO.addStatus(input.getRequester(), input.getStatus());
        return new Response(true, "Successfully Posted Status");
    }

    public GetStoryResponse getStory(GetStoryRequest input) {
//        Pair<List<Status>, Boolean> data = FakeData.getInstance().getPageOfStatus(input.getLastItem(), input.getLimit());
        checkAuthToken(input.getAuthToken());
        List<Status> statuses = daoFactory.getStoryDAO().getPage(input.getRequester().getAlias(), input.getLimit(), input.getLastItem());
        List<Status> statuses1 = daoFactory.getStoryDAO().getPage(input.getRequester().getAlias(), input.getLimit(), statuses.get(statuses.size()-1));
        boolean hasMoreStatus = statuses1.size() > 0;
        return new GetStoryResponse(true, statuses, hasMoreStatus);
    }
}
