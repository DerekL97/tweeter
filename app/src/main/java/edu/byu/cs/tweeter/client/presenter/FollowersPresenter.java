package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowersPresenter {
    private View view;
    private UserService userService;
    private FollowService followService;

    private User lastFollower;

    public boolean HasMorePages() {
        return hasMorePages;
    }

    private boolean hasMorePages;



    private boolean isLoading = false;
    private static final int PAGE_SIZE = 10;


    public FollowersPresenter(View view) {
        this.view = view;
        userService = new UserService();
        followService = new FollowService();
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void getUser(String userAlias) {
        userService.getUser(userAlias, Cache.getInstance().getCurrUserAuthToken(), new GetUserObserver());
        view.displayMessage("Getting user's profile...");
    }

    public void loadMoreItems(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(true);
//            addLoadingFooter();

            followService.loadMoreFollowerItems(user, PAGE_SIZE, lastFollower, new GetFollowersObserver());
//            GetFollowersTask getFollowersTask = new GetFollowersTask(Cache.getInstance().getCurrUserAuthToken(),
//                    user, PAGE_SIZE, lastFollower, new FollowersFragment.FollowersRecyclerViewAdapter.GetFollowersHandler());
//            ExecutorService executor = Executors.newSingleThreadExecutor();
//            executor.execute(getFollowersTask);
        }
    }

    public interface View {
        void displayMessage(String message);
        void setLoadingFooter(boolean value);
        void showUser(User user);
        void addFollowers(List<User> followers);
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
    private class GetFollowersObserver implements FollowService.GetFollowersObserver{
        @Override
        public void addFollowers(boolean hasMorePages, User lastFollower, List<User> followers) {
            FollowersPresenter.this.hasMorePages = hasMorePages;
            FollowersPresenter.this.lastFollower = lastFollower;
            isLoading = false;
            view.setLoadingFooter(false);
            view.addFollowers(followers);
        }

        @Override
        public void displayErrorMessage(String message) {

            view.displayMessage("Failed to get followers: " + message);
        }

        @Override
        public void displayException(Exception ex) {
            view.displayMessage("Failed to get followers because of exception: " + ex.getMessage());
        }
    }
}
