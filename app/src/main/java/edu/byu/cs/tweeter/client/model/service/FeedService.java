package edu.byu.cs.tweeter.client.model.service;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedService {
    private FeedObserver observer;

    public FeedService(FeedObserver observer){
        this.observer = observer;
    }

    public interface FeedObserver{
        public void handleMessage(Message msg);
    }

    public void getUser(String userAlias){
        GetUserTask getUserTask = new GetUserTask(Cache.getInstance().getCurrUserAuthToken(),
                userAlias, new GetUserHandler());
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getUserTask);
    }


    private class GetUserHandler extends Handler { //todo this whole class should move I think maybe to service?
        @Override
        public void handleMessage(@NonNull Message msg) {
            observer.handleMessage(msg);

        }
    }
}
