package edu.byu.cs.tweeter.client.presenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.client.R;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.LogInOutService;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.presenter.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainActivityPresenter {

    private User selectedUser;//todo initialize
    private View view;
    private FollowService followService;
    private LogInOutService logInOutService;
    private StatusService statusService;

    public MainActivityPresenter(View view, User selectedUser){
        this.view = view;
        followService = new FollowService();
        this.selectedUser = selectedUser;
        this.logInOutService = new LogInOutService();
        this.statusService = new StatusService();
    }



    public interface View {
        void displayMessage(String message);
        void updateFollowButton(boolean removed);
        void followingButtonSetEnable(boolean isOn);

        void setFollowerCount(int count);

        void setFollowingCount(int count);

        void logout();

        void postedStatus();
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
    public class startFollowTaskObserver extends ServiceObserver implements FollowService.startFollowTaskObserver { //todo get rid of startFollowTaskObserver
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

    public void onOptionsItemSelected() {
        logInOutService.StartLogoutTask(Cache.getInstance().getCurrUserAuthToken(), new LogoutHandlerObserver());
//        LogoutTask logoutTask = new LogoutTask(Cache.getInstance().getCurrUserAuthToken(), new MainActivity.LogoutHandler());
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        executor.execute(logoutTask);
    }
    public class LogoutHandlerObserver implements LogInOutService.LogoutHandlerObserver{

        @Override
        public void logOutSuccess() {
            view.logout();
            Cache.getInstance().clearCache(); //todo move to presenter
//            logOutToast.cancel();
//            logoutUser();
        }

        @Override
        public void displayErrorMessage(String message) {
            view.displayMessage("Failed to logout: " + message);
        }

        @Override
        public void displayException(Exception ex) {
            view.displayMessage("Failed to logout because of exception: " + ex.getMessage());
        }
    }
    public void postStatus(String post) throws ParseException {
        Status newStatus = new Status(post, Cache.getInstance().getCurrUser(), getFormattedDateTime(), parseURLs(post), parseMentions(post));

        statusService.startStatusTask(Cache.getInstance().getCurrUserAuthToken(), newStatus, new postStatusObserver());
//        PostStatusTask statusTask = new PostStatusTask(Cache.getInstance().getCurrUserAuthToken(),
//                newStatus, new MainActivity.PostStatusHandler());
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        executor.execute(statusTask);
    }
    public class postStatusObserver implements StatusService.PostStatusObserver{

        @Override
        public void postedStatus() {
            view.postedStatus();
//            postingToast.cancel();
//            Toast.makeText(MainActivity.this, "Successfully Posted!", Toast.LENGTH_LONG).show();
        }

        @Override
        public void displayErrorMessage(String message) {
            view.displayMessage("Failed to post status: " + message);
        }

        @Override
        public void displayException(Exception ex) {
            view.displayMessage("Failed to post status because of exception: " + ex.getMessage());
        }
    }


    public User getSelectedUser() {
        return selectedUser;
    }

    public String getFormattedDateTime() throws ParseException {
        SimpleDateFormat userFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

        return statusFormat.format(userFormat.parse(LocalDate.now().toString() + " " + LocalTime.now().toString().substring(0, 8)));
    }

    public List<String> parseURLs(String post) {//todo move this?
        List<String> containedUrls = new ArrayList<>();
        for (String word : post.split("\\s")) {
            if (word.startsWith("http://") || word.startsWith("https://")) {

                int index = findUrlEndIndex(word);

                word = word.substring(0, index);

                containedUrls.add(word);
            }
        }

        return containedUrls;
    }
    public int findUrlEndIndex(String word) {
        if (word.contains(".com")) {
            int index = word.indexOf(".com");
            index += 4;
            return index;
        } else if (word.contains(".org")) {
            int index = word.indexOf(".org");
            index += 4;
            return index;
        } else if (word.contains(".edu")) {
            int index = word.indexOf(".edu");
            index += 4;
            return index;
        } else if (word.contains(".net")) {
            int index = word.indexOf(".net");
            index += 4;
            return index;
        } else if (word.contains(".mil")) {
            int index = word.indexOf(".mil");
            index += 4;
            return index;
        } else {
            return word.length();
        }
    }

    public List<String> parseMentions(String post) {//todo move this?
        List<String> containedMentions = new ArrayList<>();

        for (String word : post.split("\\s")) {
            if (word.startsWith("@")) {
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                word = "@".concat(word);

                containedMentions.add(word);
            }
        }

        return containedMentions;
    }
}
