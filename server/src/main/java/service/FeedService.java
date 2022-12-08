package service;

import net.request.GetFeedRequest;
import net.response.GetFeedResponse;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.util.FakeData;
import edu.byu.cs.tweeter.util.Pair;

public class FeedService extends Service{
    public GetFeedResponse getFeed(GetFeedRequest input) {
        checkAuthToken(input.getAuthToken());
        List<Status> status = daoFactory.getFeedDAO().getPage(input.getFollowerAlias(), input.getLimit(), input.getLastItem());
        List<Status> check = daoFactory.getFeedDAO().getPage(input.getFollowerAlias(), input.getLimit(), status.get(status.size()-1));
        boolean moreData = !(check.size() == 0);
        return new GetFeedResponse(true, status, moreData);
    }
}
