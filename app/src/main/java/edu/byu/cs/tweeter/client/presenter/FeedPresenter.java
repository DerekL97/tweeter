package edu.byu.cs.tweeter.client.presenter;

import android.content.Intent;
import android.net.Uri;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FeedService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.presenter.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedPresenter extends FragmentPresenter {
    private View view;
    private UserService userService;
    private FeedService feedService;

    private Status lastStatus;
    private boolean hasMorePages;
    private boolean isLoading = false;
    private static final int PAGE_SIZE = 10;

    public FeedPresenter(View view){
        this.view = view;
        feedService = new FeedService();
        userService = new UserService();
    }

    public boolean hasMorePages() {
        return hasMorePages;
    }
    public boolean isLoading() {
        return isLoading;
    }


    public interface View extends FragmentPresenter.View{
        void startActivity(Intent intent);
        void addItems(List<Status> statuses);
    }

    //Methods called by the view
    public void mentionClick(String userAlias) {
        if (userAlias.contains("http")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(userAlias));
            view.startActivity(intent);
        } else {
            userService.getUser(userAlias, Cache.getInstance().getCurrUserAuthToken(), new GetUserObserver());
            view.displayMessage("Getting user's profile...");
        }
    }
    public void itemViewClick(String userAlias){ //not really sure what this method does . . .
        userService.getUser(userAlias, Cache.getInstance().getCurrUserAuthToken(), new GetUserObserver());
        view.displayMessage("Getting user's profile...");
    }

    public void loadMoreItems(User user){
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(true);
            feedService.loadMoreItems(user, PAGE_SIZE, lastStatus, new GetFeedObserver());
        }
    }
    //End Methods called by the view

    private class GetFeedObserver extends ServiceObserver implements FeedService.GetFeedObserver {//todo get rid of implements
        //methods from FeedService implementation
        @Override
        public void addItems(List<Status> statuses, boolean hasMorePages, Status lastStatus) {
            isLoading = false;
            view.setLoadingFooter(false);
            FeedPresenter.this.hasMorePages = hasMorePages;
            FeedPresenter.this.lastStatus = lastStatus;
            view.addItems(statuses);
        }

        @Override
        public void handleFailure(String message) {
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayMessage("Failed to get feed: " + message);
        }

        @Override
        public void handleException(Exception ex) {
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayMessage("Failed to get feed because of exception: " + ex.getMessage());
        }
    }

    private class GetUserObserver extends ServiceObserver implements UserService.GetUserObserver{

        @Override
        public void loadUser(User user) {
            view.showUser(user);
        }

        @Override
        public void handleFailure(String message) {
            view.displayMessage("Failed to get user's profile: " + message);
        }

        @Override
        public void handleException(Exception ex) {
            view.displayMessage("Failed to get user's profile because of exception: " + ex.getMessage());
        }
    }

}
