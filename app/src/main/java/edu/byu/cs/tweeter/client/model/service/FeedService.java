package edu.byu.cs.tweeter.client.model.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.handler.GetFeedHandler;
import edu.byu.cs.tweeter.client.presenter.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedService {
//    private GetFeedObserver observer;

//    public FeedService(FeedObserver observer){
//        this.observer = observer;
//    }

    public interface GetFeedObserver {
//        void handleMessage(Message msg);
//        void setLoading(boolean loading);
//        void setLoadingFooter(boolean footer);
        void addItems(List<Status> statuses, boolean hasMorePages, Status lastStatus);
        void handleFailure(String message);
        void handleException(Exception ex);
    }

    public void loadMoreItems(User user, int PAGE_SIZE, Status lastStatus, ServiceObserver feedObserver) {
        GetFeedTask getFeedTask = new GetFeedTask(Cache.getInstance().getCurrUserAuthToken(),
                user, PAGE_SIZE, lastStatus, new GetFeedHandler(feedObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFeedTask);
    }

}
