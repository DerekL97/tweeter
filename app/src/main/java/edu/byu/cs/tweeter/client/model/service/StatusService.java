package edu.byu.cs.tweeter.client.model.service;

import net.request.FollowersRequest;
import net.request.FollowingRequest;
import net.request.GetFeedRequest;
import net.request.GetStoryRequest;
import net.request.GetUserRequest;
import net.request.IsFollowerRequest;
import net.request.LoginRequest;
import net.request.RegisterRequest;
import net.request.UnFollowRequest;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.net.JsonSerializer;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.handler.GetStoryHandler;
import edu.byu.cs.tweeter.client.model.service.handler.PostStatusHandler;
import edu.byu.cs.tweeter.client.presenter.MainActivityPresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusService extends Service{

    public interface StatusServiceObserver extends ServiceObserverInterface{
        void addStatuses(List<Status> Statuses, boolean hasMorePages, Status lastStatus);
    }
    public void getMoreStatuses(AuthToken authToken, User user, int PAGE_SIZE, Status lastStatus, StatusServiceObserver observer){
        GetStoryTask getStoryTask = new GetStoryTask(authToken,
                user, PAGE_SIZE, lastStatus, new GetStoryHandler(observer));
        startTask(getStoryTask);
//        new Status("this is a post", new User("Name", "Last", "fakeImage"), "datetime", new ArrayList<String>(), new ArrayList<String>())
//        new AuthToken("authtoken"), "FollowerAlias", 40, new Status("this is a post", new User("Name", "Last", "fakeImage"), "datetime", new ArrayList<String>(), new ArrayList<String>())))
//        System.out.println(JsonSerializer.serialize(new UnFollowRequest("exfollowee", "exUsername", new AuthToken("authtoken"))));

//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        executor.execute(getStoryTask);
    }

    public interface PostStatusObserver extends ServiceObserverInterface{
        void postedStatus();
    }
    public void startStatusTask(AuthToken authToken, Status newStatus, MainActivityPresenter.postStatusObserver observer){
        PostStatusTask statusTask = new PostStatusTask(authToken,
                newStatus, new PostStatusHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(statusTask);
    }

    public static void main(){
        JsonSerializer.serialize(new Status("this is a post", new User("Name", "Last", "fakeImage"), "datetime", new ArrayList<String>(), new ArrayList<String>()));
    }

}
