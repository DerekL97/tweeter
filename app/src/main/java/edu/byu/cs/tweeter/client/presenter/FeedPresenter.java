package edu.byu.cs.tweeter.client.presenter;

import android.content.Intent;
import android.net.Uri;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FeedService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedPresenter extends FragmentPresenter {
    private View view;
    private FeedService feedService;
    private Status lastStatus;

    public FeedPresenter(View view){
        super(view);
        feedService = new FeedService();
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
            feedService.loadMoreItems(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastStatus, new GetFeedObserver());
        }
    }
    //End Methods called by the view

    private class GetFeedObserver extends FragmentPresenter.ServiceObserver implements FeedService.GetFeedObserver {//todo get rid of implements
        //methods from FeedService implementation
        @Override
        public void addItems(List<Status> statuses, boolean hasMorePages, Status lastStatus) {
            isLoading = false;
            view.setLoadingFooter(false);
            FeedPresenter.this.hasMorePages = hasMorePages;
            FeedPresenter.this.lastStatus = lastStatus;
            view.addItems(statuses);
        }


    }

    private class GetUserObserver extends FragmentPresenter.ServiceObserver implements UserService.GetUserObserver{

//        @Override
        public void loadUser(User user) {
            view.showUser(user);
        }


    }

}
