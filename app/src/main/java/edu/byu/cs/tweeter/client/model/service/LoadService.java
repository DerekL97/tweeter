package edu.byu.cs.tweeter.client.model.service;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.handler.GetFeedHandler;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class LoadService extends Service{


    public interface GetFeedObserver extends ServiceObserverInterface{
        void addItems(List<Status> statuses, boolean hasMorePages, Status lastStatus);
        void addFollowees(List<User> followers, boolean hasMorePages);
        void addStatuses(List<Status> Statuses, boolean hasMorePages, Status lastStatus);
//        void loadUser(User user);

    }

    public void loadMoreItems(AuthToken currAuthToken, User user, int PAGE_SIZE, Status lastStatus, FeedService.GetFeedObserver feedObserver) {
        GetFeedTask getFeedTask = new GetFeedTask(currAuthToken,
                user, PAGE_SIZE, lastStatus, new GetFeedHandler(feedObserver));
        startTask(getFeedTask);
    }
}
