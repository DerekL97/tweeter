package edu.byu.cs.tweeter.client.presenter;

import android.content.Intent;
import android.net.Uri;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter {
    private Status lastStatus;
    private boolean hasMorePages;
    private boolean isLoading = false;

    private static final int PAGE_SIZE = 10;

    private User user;

    private View view;
    private StatusService statusService;
    private UserService userService;

    public StoryPresenter(View view){
        this.view = view;
        statusService = new StatusService();
        userService = new UserService();
    }

    public void mentionClick(String userAlias) {
        if (userAlias.contains("http")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(userAlias));
            view.startActivity(intent);
        } else {
            userService.getUser(userAlias, Cache.getInstance().getCurrUserAuthToken(), new GetUserObserver());
//            GetUserTask getUserTask = new GetUserTask(Cache.getInstance().getCurrUserAuthToken(),
//                    userAlias, new StoryFragment.StoryHolder.GetUserHandler());
//            ExecutorService executor = Executors.newSingleThreadExecutor();
//            executor.execute(getUserTask);
            view.displayMessage("Getting user's profile...");
//            Toast.makeText(getContext(), "Getting user's profile...", Toast.LENGTH_LONG).show();
        }
    }

    public interface View{
        void startActivity(Intent intent);
        void displayMessage(String message);
        void setLoadingFooter(boolean value);
        void addStatuses(List<Status> Statuses);
        void showUser(User user);
    }
    public void getUser(String userAlias) {
        userService.getUser(userAlias, Cache.getInstance().getCurrUserAuthToken(), new GetUserObserver());
//        GetUserTask getUserTask = new GetUserTask(Cache.getInstance().getCurrUserAuthToken(),
//                userAlias.getText().toString(), new StoryFragment.StoryHolder.GetUserHandler());
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        executor.execute(getUserTask);
        view.displayMessage("Getting user's profile...");
//        Toast.makeText(getContext(), "Getting user's profile...", Toast.LENGTH_LONG).show();
    }
    public boolean hasMorePages() {
        return hasMorePages;
    }
    public boolean isLoading() {
        return isLoading;
    }

    public void loadMoreItems() {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(true);
//            addLoadingFooter();
            statusService.getMoreStatuses(user, PAGE_SIZE, lastStatus, new StatusServiceObserver());

        }
    }

    private class StatusServiceObserver implements StatusService.StatusServiceObserver{

        @Override
        public void addStatuses(List<Status> Statuses, boolean hasMorePages, Status lastStatus) {
            isLoading = false;
            view.setLoadingFooter(false);
//            removeLoadingFooter();
            StoryPresenter.this.hasMorePages = hasMorePages;
//            hasMorePages = msg.getData().getBoolean(GetStoryTask.MORE_PAGES_KEY);
            StoryPresenter.this.lastStatus = lastStatus;
//            lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;
            view.addStatuses(Statuses);
        }

        @Override
        public void displayErrorMessage(String message) {
            isLoading = false;
            view.setLoadingFooter(false);
//            removeLoadingFooter();
        }

        @Override
        public void displayException(Exception ex) {
            isLoading = false;
            view.setLoadingFooter(false);
//            removeLoadingFooter();
        }
    }
    private class GetUserObserver implements UserService.GetUserObserver {

        @Override
        public void loadUser(User user) {
            view.showUser(user);
        }

        @Override
        public void displayErrorMessage(String message) {
            view.displayMessage("Failed to get user's profile: " + message);
        }

        @Override
        public void displayException(Exception ex) {
            view.displayMessage("Failed to get user's profile because of exception: " + ex.getMessage());
        }
    }
}
