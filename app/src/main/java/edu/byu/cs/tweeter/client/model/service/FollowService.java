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

public class FollowService extends Service {

    public interface startUnfollowTaskObserver extends ServiceObserverInterface {
        void UnfollowReturn();
    }
    public void startUnfollowTask(AuthToken currUserAuthToken, User selectedUser, startUnfollowTaskObserver observer) {
        UnfollowTask unfollowTask = new UnfollowTask(currUserAuthToken,
                selectedUser, new UnfollowHandler(observer));
        startTask(unfollowTask);
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        executor.execute(unfollowTask);
    }





    public interface startFollowTaskObserver extends ServiceObserverInterface {
        void FollowReturn(Boolean removed);
    }
    public void startFollowTask(AuthToken currUserAuthToken, User selectedUser, User loggedUser, MainActivityPresenter.startFollowTaskObserver observer) {
        FollowTask followTask = new FollowTask(currUserAuthToken, selectedUser, loggedUser, new FollowHandler(observer));
        startTask(followTask);
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        executor.execute(followTask);
    }


    public interface GetFollowingObserver extends ServiceObserverInterface {
        void addFollowees(List<User> followers, boolean hasMorePages);
    }

    public void loadMoreFollowingItems(AuthToken currUserAuthToken, User user, int pageSize, User lastFollowee, GetFollowingObserver getFollowingObserver) {
        GetFollowingTask getFollowingTask = new GetFollowingTask(currUserAuthToken,
                user, pageSize, lastFollowee, new GetFollowingHandler(getFollowingObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFollowingTask);
    }

    public interface GetFollowersObserver extends ServiceObserverInterface {
        void addFollowers(boolean hasMorePages, User lastFollower, List<User> followers);
    }

    public void loadMoreFollowerItems(AuthToken authToken, User user, int PAGE_SIZE, User lastFollower, GetFollowersObserver observer){
        GetFollowersTask getFollowersTask = new GetFollowersTask(authToken,
                user, PAGE_SIZE, lastFollower, new GetFollowersHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFollowersTask);
    }

    public interface IsFollowerHandlerObserver extends ServiceObserverInterface {
        void setIsFollower(boolean isFollower);
    }

    public void startIsFollowerTask(AuthToken authToken, User selectedUser, User currUser, IsFollowerHandlerObserver observer){
        IsFollowerTask isFollowerTask = new IsFollowerTask(authToken,
                currUser, selectedUser, new IsFollowerHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(isFollowerTask);
    }

    public interface GetFollowersCountObserver extends ServiceObserverInterface {
        void returnFollowersCount(int count);
    }
    public void startGetFollowersCount(AuthToken currUserAuthToken, User selectedUser, MainActivityPresenter.GetFollowersCountObserver observer) {
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(currUserAuthToken,
                selectedUser, new GetFollowersCountHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(followersCountTask);
    }

    public interface GetFollowingCountObserver extends ServiceObserverInterface {
        void returnFollowingCount(int count);
    }
    public void startGetFollowingCount(AuthToken currUserAuthToken, User selectedUser, MainActivityPresenter.GetFollowingCountObserver observer) {
        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(currUserAuthToken,
                selectedUser, new GetFollowingCountHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(followingCountTask);
    }

}
