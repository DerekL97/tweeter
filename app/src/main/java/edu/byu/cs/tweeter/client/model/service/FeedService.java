package edu.byu.cs.tweeter.client.model.service;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.presenter.FeedPresenter;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedService {
    private FeedObserver observer;

    public FeedService(FeedObserver observer){
        this.observer = observer;
    }

    public interface FeedObserver{
        void handleMessage(Message msg);
        void setLoading(boolean loading);
        void setLoadingFooter(boolean footer);
        void addItems(List<Status> statuses, boolean hasMorePages, Status lastStatus);
        void displayErrorMessage(String message);
        void displayException(Exception ex);
    }

    public void getUser(String userAlias){
        GetUserTask getUserTask = new GetUserTask(Cache.getInstance().getCurrUserAuthToken(),
                userAlias, new GetUserHandler());
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getUserTask);
    }


    private class GetUserHandler extends Handler { //todo this whole class should move I think maybe to service?
        @Override
        public void handleMessage(@NonNull Message msg) {
            observer.handleMessage(msg);

        }
    }
    public void loadMoreItems(User user, int PAGE_SIZE, Status lastStatus) {
        GetFeedTask getFeedTask = new GetFeedTask(Cache.getInstance().getCurrUserAuthToken(),
                user, PAGE_SIZE, lastStatus, new GetFeedHandler());
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFeedTask);
    }

    private class GetFeedHandler extends Handler {// todo move to feedService
        @Override
        public void handleMessage(@NonNull Message msg) {
            observer.setLoading(false);
//            isLoading = false;
            observer.setLoadingFooter(false);
//            removeLoadingFooter();

            boolean success = msg.getData().getBoolean(GetFeedTask.SUCCESS_KEY);
            if (success) {
                List<Status> statuses = (List<Status>) msg.getData().getSerializable(GetFeedTask.STATUSES_KEY);
                boolean hasMorePages = msg.getData().getBoolean(GetFeedTask.MORE_PAGES_KEY);
                Status lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;
//                feedRecyclerViewAdapter.addItems(statuses);
                observer.addItems(statuses, hasMorePages, lastStatus);
            } else if (msg.getData().containsKey(GetFeedTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(GetFeedTask.MESSAGE_KEY);
                observer.displayErrorMessage(message);
//                Toast.makeText(getContext(), "Failed to get feed: " + message, Toast.LENGTH_LONG).show();
            } else if (msg.getData().containsKey(GetFeedTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(GetFeedTask.EXCEPTION_KEY);
                observer.displayException(ex);
//                Toast.makeText(getContext(), "Failed to get feed because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
