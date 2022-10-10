package edu.byu.cs.tweeter.client.presenter;

import android.content.Intent;
import android.net.Uri;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FeedService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedPresenter extends PagedPresenter<Status> {
    private View view;
    private FeedService feedService;
//    private Status lastStatus;

    public FeedPresenter(View view){
        super(view);
        this.view = view;
        feedService = new FeedService();
    }

    public interface View extends PagedPresenter.View{
        void startActivity(Intent intent);
        void addItems(List<Status> statuses);
    }

    @Override
    public void getItems(AuthToken authToken, User user, int PAGE_SIZE, Status lastItem) {
        feedService.loadMoreItems(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastItem, new GetFeedObserver());
    }

    //Methods called by the view
    public void mentionClick(String userAlias) {
        if (userAlias.contains("http")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(userAlias));
            view.startActivity(intent);
        } else {
            getUser(userAlias);
        }
    }

    public void itemViewClick(String userAlias){ //not really sure what this method does . . .
        getUser(userAlias);
    }

//    public void loadMoreItems(User user){
//        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
//            isLoading = true;
//            view.setLoadingFooter(true);
//            feedService.loadMoreItems(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastStatus, new GetFeedObserver());
//        }
//    }
    //End Methods called by the view

    private class GetFeedObserver extends Presenter.ServiceObserver implements FeedService.GetFeedObserver {//todo get rid of implements
        //methods from FeedService implementation
        @Override
        public void addItems(List<Status> statuses, boolean hasMorePages, Status lastStatus) {
            isLoading = false;
            view.setLoadingFooter(false);
            FeedPresenter.this.hasMorePages = hasMorePages;
            FeedPresenter.this.lastItem = lastStatus;
            view.addItems(statuses);
        }
    }

//    private class GetUserObserver extends Presenter.ServiceObserver implements UserService.GetUserObserver{
//        public void loadUser(User user) {
//            view.showUser(user);
//        }
//    }

}
