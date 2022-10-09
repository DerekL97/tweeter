package edu.byu.cs.tweeter.client.model.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.handler.GetFeedHandler;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedService extends Service {

    public interface GetFeedObserver extends ServiceObserverInterface{
        void addItems(List<Status> statuses, boolean hasMorePages, Status lastStatus);
    }

    public void loadMoreItems(AuthToken currAuthToken, User user, int PAGE_SIZE, Status lastStatus, GetFeedObserver feedObserver) {
        GetFeedTask getFeedTask = new GetFeedTask(currAuthToken,
                user, PAGE_SIZE, lastStatus, new GetFeedHandler(feedObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFeedTask);
    }

}
