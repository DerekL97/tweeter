package service;

import net.request.GetFeedRequest;
import net.response.GetFeedResponse;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.util.FakeData;
import edu.byu.cs.tweeter.util.Pair;

public class FeedService extends Service{
    public GetFeedResponse getFeed(GetFeedRequest input) {
        Pair<List<Status>, Boolean> data = FakeData.getInstance().getPageOfStatus(input.getLastItem(), input.getLimit());
        return new GetFeedResponse(true, data.getFirst(), data.getSecond());
    }
}
