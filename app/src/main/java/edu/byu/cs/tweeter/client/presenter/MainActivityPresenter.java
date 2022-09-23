package edu.byu.cs.tweeter.client.presenter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.client.R;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.model.domain.User;

public class MainActivityPresenter {

    private User selectedUser;//todo initialize
    private View view;
    private FollowService followService;

    public MainActivityPresenter(View view, User selectedUser){
        this.view = view;
        followService = new FollowService();
        this.selectedUser = selectedUser;
    }
    public interface View {
        void displayMessage(String message);
        void updateFollowButton(boolean removed);
        void followingButtonSetEnable(boolean isOn);

        void setFollowerCount(int count);

        void setFollowingCount(int count);
    }
    public void clickFollowButton(String ButtonText) {
        if (ButtonText.equals(R.string.Following)) {
            followService.startUnfollowTask(Cache.getInstance().getCurrUserAuthToken(), selectedUser, new startUnfollowTaskObserver());
//            UnfollowTask unfollowTask = new UnfollowTask(Cache.getInstance().getCurrUserAuthToken(),
//                    selectedUser, new MainActivity.UnfollowHandler());
//            ExecutorService executor = Executors.newSingleThreadExecutor();
//            executor.execute(unfollowTask);

            view.displayMessage("Removing " + selectedUser.getName() + "...");
//            Toast.makeText(MainActivity.this, "Removing " + selectedUser.getName() + "...", Toast.LENGTH_LONG).show();
        } else {
            followService.startFollowTask(Cache.getInstance().getCurrUserAuthToken(), selectedUser, new startFollowTaskObserver());
//            FollowTask followTask = new FollowTask(Cache.getInstance().getCurrUserAuthToken(),
//                    selectedUser, new MainActivity.FollowHandler());
//            ExecutorService executor = Executors.newSingleThreadExecutor();
//            executor.execute(followTask);

            view.displayMessage("Adding " + selectedUser.getName() + "...");
//            Toast.makeText(MainActivity.this, "Adding " + selectedUser.getName() + "...", Toast.LENGTH_LONG).show();
        }
    }
    public class startFollowTaskObserver implements FollowService.startFollowTaskObserver{
        @Override
        public void FollowReturn() {
            updateSelectedUserFollowingAndFollowers();
            view.updateFollowButton(false);
//            updateFollowButton(false);
            view.followingButtonSetEnable(true);
//            followButton.setEnabled(true);
        }
        @Override
        public void displayErrorMessage(String message) {
            view.displayMessage("Failed to follow: " + message);
            view.followingButtonSetEnable(true);
//            followButton.setEnabled(true);
        }

        @Override
        public void displayException(Exception ex) {
            view.displayMessage("Failed to follow because of exception: " + ex.getMessage());
            view.followingButtonSetEnable(true);
//            followButton.setEnabled(true);
        }
    }
    private class startUnfollowTaskObserver implements FollowService.startUnfollowTaskObserver{

        @Override
        public void displayErrorMessage(String message) {
            view.displayMessage("Failed to unfollow: " + message);
            view.followingButtonSetEnable(true);
//            followButton.setEnabled(true);
        }

        @Override
        public void displayException(Exception ex) {
            view.displayMessage("Failed to unfollow because of exception: " + ex.getMessage());
            view.followingButtonSetEnable(true);
//            followButton.setEnabled(true);
        }

        @Override
        public void UnfollowReturn() {
            updateSelectedUserFollowingAndFollowers();
            view.updateFollowButton(false);
//            updateFollowButton(true);
            view.followingButtonSetEnable(true);
//            followButton.setEnabled(true);
        }
    }


    public void startIsFollowerTask(User selectedUser) {
        followService.startIsFollowerTask(selectedUser, Cache.getInstance().getCurrUserAuthToken(),
                Cache.getInstance().getCurrUser(), new IsFollowerHandlerObserver());
    }

    private class IsFollowerHandlerObserver implements FollowService.IsFollowerHandlerObserver{

        @Override
        public void setIsFollower(boolean isFollower) {
            view.updateFollowButton(isFollower);

        }

        @Override
        public void displayErrorMessage(String message) {
            view.displayMessage("Failed to determine following relationship: " + message);
        }

        @Override
        public void displayException(Exception ex) {
            view.displayMessage("Failed to determine following relationship because of exception: " + ex.getMessage());
        }
    }

    public void updateSelectedUserFollowingAndFollowers() {
        ExecutorService executor = Executors.newFixedThreadPool(2);// todo move this

        // Get count of most recently selected user's followers.
        followService.startGetFollowersCount(Cache.getInstance().getCurrUserAuthToken(), selectedUser, new GetFollowersCountObserver());
//        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(Cache.getInstance().getCurrUserAuthToken(),
//                this.selectedUser, new MainActivity.GetFollowersCountHandler());
//        executor.execute(followersCountTask);

        // Get count of most recently selected user's followees (who they are following)
        followService.startGetFollowingCount(Cache.getInstance().getCurrUserAuthToken(), selectedUser, new GetFollowingCountObserver());
//        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(Cache.getInstance().getCurrUserAuthToken(),
//                this.selectedUser, new MainActivity.GetFollowingCountHandler());
//        executor.execute(followingCountTask);
    }

    public class GetFollowersCountObserver implements FollowService.GetFollowersCountObserver {

        @Override
        public void returnFollowersCount(int count) {
            view.setFollowerCount(count);
//            followerCount.setText(getString(R.string.followerCount, String.valueOf(count)));
        }

        @Override
        public void displayErrorMessage(String message) {
            view.displayMessage("Failed to get followers count: " + message);
        }

        @Override
        public void displayException(Exception ex) {
            view.displayMessage("Failed to get followers count because of exception: " + ex.getMessage());
        }
    }
    public class GetFollowingCountObserver implements FollowService.GetFollowingCountObserver{

        @Override
        public void returnFollowingCount(int count) {
            view.setFollowingCount(count);
//            followeeCount.setText(getString(R.string.followeeCount, String.valueOf(count)));
        }

        @Override
        public void displayErrorMessage(String message) {
            view.displayMessage("Failed to get following count: " + message);
        }

        @Override
        public void displayException(Exception ex) {
            view.displayMessage("Failed to get following count because of exception: " + ex.getMessage());
        }
    }


    public User getSelectedUser() {
        return selectedUser;
    }


}
