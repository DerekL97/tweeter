package edu.byu.cs.tweeter.client.model.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.handler.FollowHandler;
import edu.byu.cs.tweeter.client.model.service.handler.GetFollowersCountHandler;
import edu.byu.cs.tweeter.client.model.service.handler.GetFollowersHandler;
import edu.byu.cs.tweeter.client.model.service.handler.GetFollowingCountHandler;
import edu.byu.cs.tweeter.client.model.service.handler.GetFollowingHandler;
import edu.byu.cs.tweeter.client.model.service.handler.IsFollowerHandler;
import edu.byu.cs.tweeter.client.model.service.handler.UnfollowHandler;
import edu.byu.cs.tweeter.client.presenter.MainActivityPresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowService {


    public void startUnfollowTask(AuthToken currUserAuthToken, User selectedUser, startUnfollowTaskObserver observer) {
        UnfollowTask unfollowTask = new UnfollowTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new UnfollowHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(unfollowTask);
    }




    public interface startUnfollowTaskObserver{ //todo remove interface (replace with extended observer class
        void displayErrorMessage(String message);
        void displayException(Exception ex);
        void UnfollowReturn();
    }

    public void startFollowTask(AuthToken currUserAuthToken, User selectedUser, MainActivityPresenter.startFollowTaskObserver observer) {
        FollowTask followTask = new FollowTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new FollowHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(followTask);
    }
    public interface startFollowTaskObserver{
        void displayErrorMessage(String message);
        void displayException(Exception ex);
        void FollowReturn();
    }

    public interface GetFollowingObserver {
        void addFollowees(List<User> followers, boolean hasMorePages);
        void displayErrorMessage(String message);
        void displayException(Exception ex);
    }

    public void loadMoreFollowingItems(AuthToken currUserAuthToken, User user, int pageSize, User lastFollowee, GetFollowingObserver getFollowingObserver) {
        GetFollowingTask getFollowingTask = new GetFollowingTask(currUserAuthToken,
                user, pageSize, lastFollowee, new GetFollowingHandler(getFollowingObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFollowingTask);

    }

    public interface GetFollowersObserver{
        void addFollowers(boolean hasMorePages, User lastFollower, List<User> followers);
        void displayErrorMessage(String message);
        void displayException(Exception ex);
    }

    public void loadMoreFollowerItems(User user, int PAGE_SIZE, User lastFollower, GetFollowersObserver observer){
        GetFollowersTask getFollowersTask = new GetFollowersTask(Cache.getInstance().getCurrUserAuthToken(),
                user, PAGE_SIZE, lastFollower, new GetFollowersHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFollowersTask);
    }

    public interface IsFollowerHandlerObserver{
        void setIsFollower(boolean isFollower);
        void displayErrorMessage(String message);
        void displayException(Exception ex);
    }

    public void startIsFollowerTask(User selectedUser, AuthToken authToken, User currUser, IsFollowerHandlerObserver observer){
        IsFollowerTask isFollowerTask = new IsFollowerTask(authToken,
                currUser, selectedUser, new IsFollowerHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(isFollowerTask);
    }

    public interface GetFollowersCountObserver{
        void returnFollowersCount(int count);
        void displayErrorMessage(String message);
        void displayException(Exception ex);
    }
    public void startGetFollowersCount(AuthToken currUserAuthToken, User selectedUser, MainActivityPresenter.GetFollowersCountObserver observer) {
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(currUserAuthToken,
                selectedUser, new GetFollowersCountHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(followersCountTask);
    }

    public interface GetFollowingCountObserver{
        void returnFollowingCount(int count);
        void displayErrorMessage(String message);
        void displayException(Exception ex);
    }
    public void startGetFollowingCount(AuthToken currUserAuthToken, User selectedUser, MainActivityPresenter.GetFollowingCountObserver observer) {
        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new GetFollowingCountHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(followingCountTask);
    }

}
