package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.handler.GetUserHandler;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class UserService {

    public interface GetUserObserver {

        void loadUser(User user);
        void handleFailure(String message);
        void handleException(Exception ex);
    }

    public void getUser(String userAlias, AuthToken authToken, GetUserObserver getUserObserver){
        GetUserTask getUserTask = new GetUserTask(authToken,
                userAlias, new GetUserHandler(getUserObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getUserTask);
    }
}
