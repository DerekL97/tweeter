package edu.byu.cs.tweeter.client.presenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.FeedService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedPresenter implements FeedService.FeedObserver {
    private View view;
    private FeedService service;
    //from feedRecyclerviewAdapter
    private Status lastStatus;



    private boolean hasMorePages;

    public boolean hasMorePages() {
        return hasMorePages;
    }

    public boolean isLoading() {
        return isLoading;
    }

    private boolean isLoading = false;//
    //from feedFragment
    private static final int PAGE_SIZE = 10;

    public FeedPresenter(View view){
        this.view = view;
        service = new FeedService(this);
    }

    //methods from Service implementation
    @Override
    public void handleMessage(Message msg) {// todo move back to service and parse what should actually be done
        boolean success = msg.getData().getBoolean(GetUserTask.SUCCESS_KEY);
        if (success) {
            User user = (User) msg.getData().getSerializable(GetUserTask.USER_KEY);
            view.startContextActivity(user);
        } else if (msg.getData().containsKey(GetUserTask.MESSAGE_KEY)) {
            String message = msg.getData().getString(GetUserTask.MESSAGE_KEY);
            view.printToast("Failed to get user's profile: " + message);
        } else if (msg.getData().containsKey(GetUserTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(GetUserTask.EXCEPTION_KEY);
            view.printToast("Failed to get user's profile because of exception: " + ex.getMessage());
        }
    }

    @Override
    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    @Override
    public void setLoadingFooter(boolean footer) {
            view.setLoadingFooter(footer);
    }


    @Override
    public void addItems(List<Status> statuses, boolean hasMorePages, Status lastStatus) {
        this.hasMorePages = hasMorePages;
        this.lastStatus = lastStatus;
        view.addItems(statuses);
//        feedRecyclerViewAdapter.addItems(statuses);
    }

    @Override
    public void displayErrorMessage(String message) {
//        Toast.makeText(getContext(), "Failed to get feed: " + message, Toast.LENGTH_LONG).show();
        view.printToast("Failed to get feed: " + message);
    }

    @Override
    public void displayException(Exception ex) {
        view.printToast("Failed to get feed because of exception: " + ex.getMessage());
//        Toast.makeText(getContext(), "Failed to get feed because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
    }

    public interface View{
        void startActivity(Intent intent);
        void startContextActivity(User user); // Currently only starts one type of activity
        void printToast(String message);
        void setLoadingFooter(boolean set);
        void addItems(List<Status> statuses);
    }

    //Methods called by the view
    public void mentionClick(String userAlias) {
        if (userAlias.contains("http")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(userAlias));
            view.startActivity(intent);
        } else {
            service.getUser(userAlias);
            view.printToast("Getting user's profile...");
        }
    }
    public void itemViewClick(String userAlias){ //not really sure what this method does . . .
        service.getUser(userAlias);
        view.printToast("Getting user's profile...");
    }

    public void loadMoreItems(User user){
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(true);
            service.loadMoreItems(user, PAGE_SIZE, lastStatus);
        }
    }


}
