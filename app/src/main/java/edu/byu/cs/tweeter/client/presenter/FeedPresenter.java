package edu.byu.cs.tweeter.client.presenter;

import android.content.Intent;
import android.net.Uri;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FeedService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedPresenter {
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



//    @Override
//    public void handleMessage(Message msg) {// todo move back to service and parse what should actually be done
//        boolean success = msg.getData().getBoolean(GetUserTask.SUCCESS_KEY);
//        if (success) {
//            User user = (User) msg.getData().getSerializable(GetUserTask.USER_KEY);
//            view.startContextActivity(user);
//        } else if (msg.getData().containsKey(GetUserTask.MESSAGE_KEY)) {
//            String message = msg.getData().getString(GetUserTask.MESSAGE_KEY);
//            view.printToast("Failed to get user's profile: " + message);
//        } else if (msg.getData().containsKey(GetUserTask.EXCEPTION_KEY)) {
//            Exception ex = (Exception) msg.getData().getSerializable(GetUserTask.EXCEPTION_KEY);
//            view.printToast("Failed to get user's profile because of exception: " + ex.getMessage());
//        }
//    }
//
//    @Override
//    public void setLoading(boolean loading) {
//        isLoading = loading;
//    }
//
//    @Override
//    public void setLoadingFooter(boolean footer) {
//            view.setLoadingFooter(footer);
//    }



    public interface View{
        void startActivity(Intent intent);
//        void startContextActivity(User user); // Currently only starts one type of activity
        void displayMessage(String message);
        void setLoadingFooter(boolean set);
        void addItems(List<Status> statuses);
        void showUser(User user);
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
    private class GetFeedObserver implements FeedService.GetFeedObserver {

        //methods from FeedService implementation
        @Override
        public void addItems(List<Status> statuses, boolean hasMorePages, Status lastStatus) {
            isLoading = false;
            view.setLoadingFooter(false);
            FeedPresenter.this.hasMorePages = hasMorePages;
            FeedPresenter.this.lastStatus = lastStatus;
            view.addItems(statuses);
//        feedRecyclerViewAdapter.addItems(statuses);
        }

        @Override
        public void displayErrorMessage(String message) {
            isLoading = false;
            view.setLoadingFooter(false);
//        Toast.makeText(getContext(), "Failed to get feed: " + message, Toast.LENGTH_LONG).show();
            view.displayMessage("Failed to get feed: " + message);
        }

        @Override
        public void displayException(Exception ex) {
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayMessage("Failed to get feed because of exception: " + ex.getMessage());
//        Toast.makeText(getContext(), "Failed to get feed because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private class GetUserObserver implements UserService.GetUserObserver{

        @Override
        public void loadUser(User user) {
//                Intent intent = new Intent(getContext(), MainActivity.class);
//                intent.putExtra(MainActivity.CURRENT_USER_KEY, user);
//                startActivity(intent);
            view.showUser(user);
        }

        @Override
        public void displayErrorMessage(String message) {
            view.displayMessage("Failed to get user's profile: " + message);
//            Toast.makeText(getContext(), "Failed to get user's profile: " + message, Toast.LENGTH_LONG).show();
        }

        @Override
        public void displayException(Exception ex) {
            view.displayMessage("Failed to get user's profile because of exception: " + ex.getMessage());
        }
    }

}
