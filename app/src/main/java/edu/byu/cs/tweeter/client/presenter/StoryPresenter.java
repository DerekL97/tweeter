package edu.byu.cs.tweeter.client.presenter;

import android.content.Intent;
import android.net.Uri;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter extends FragmentPresenter {
    private Status lastStatus;
    private User user;
    private StatusService statusService;

    public StoryPresenter(View view){
        super(view);
        statusService = new StatusService();
        userService = new UserService();
    }

    public void mentionClick(String userAlias) {
        if (userAlias.contains("http")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(userAlias));
            view.startActivity(intent);
        } else {
            userService.getUser(userAlias, Cache.getInstance().getCurrUserAuthToken(), new GetUserObserver());
            view.displayMessage("Getting user's profile...");
        }
    }

    public void getUser(String userAlias) {
        userService.getUser(userAlias, Cache.getInstance().getCurrUserAuthToken(), new GetUserObserver());
        view.displayMessage("Getting user's profile...");
    }


    public void loadMoreItems() {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(true);
            statusService.getMoreStatuses(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastStatus, new StatusServiceObserver());
        }
    }

    private class StatusServiceObserver extends FragmentPresenter.ServiceObserver implements StatusService.StatusServiceObserver{

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
        }

        @Override
        public void displayException(Exception ex) {
            isLoading = false;
            view.setLoadingFooter(false);
        }
    }
    private class GetUserObserver extends ServiceObserver implements UserService.GetUserObserver {
        @Override
        public void loadUser(User user) {
            view.showUser(user);
        }
    }
}
