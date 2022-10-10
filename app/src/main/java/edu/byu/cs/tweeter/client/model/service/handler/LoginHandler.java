package edu.byu.cs.tweeter.client.model.service.handler;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.LogInOutService;
import edu.byu.cs.tweeter.client.model.service.Service;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.presenter.LoginPresenter;
import edu.byu.cs.tweeter.client.view.main.MainActivity;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginHandler extends BackgroundTaskHandler {
    LogInOutService.LoginHandlerObserver observer;

    public LoginHandler(LogInOutService.LoginHandlerObserver observer) {
        super(observer);
        this.observer = observer;
    }

    @Override
    protected void handleSuccessMessage(Bundle data) {
        observer.logInSuccess(data);
//        User loggedInUser = (User) data.getSerializable(LoginTask.USER_KEY);
//        AuthToken authToken = (AuthToken) data.getSerializable(LoginTask.AUTH_TOKEN_KEY);
//
//        // Cache user session information
//        Cache.getInstance().setCurrUser(loggedInUser);
//        Cache.getInstance().setCurrUserAuthToken(authToken);
//
//        Intent intent = new Intent(getContext(), MainActivity.class);
//        intent.putExtra(MainActivity.CURRENT_USER_KEY, loggedInUser);
//
//        loginInToast.cancel();
//
//        Toast.makeText(getContext(), "Hello " + Cache.getInstance().getCurrUser().getName(), Toast.LENGTH_LONG).show();
//        startActivity(intent);
    }
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            boolean success = msg.getData().getBoolean(LoginTask.SUCCESS_KEY);
//            if (success) {
//                User loggedInUser = (User) msg.getData().getSerializable(LoginTask.USER_KEY);
//                AuthToken authToken = (AuthToken) msg.getData().getSerializable(LoginTask.AUTH_TOKEN_KEY);
//
//                // Cache user session information
//                Cache.getInstance().setCurrUser(loggedInUser);
//                Cache.getInstance().setCurrUserAuthToken(authToken);
//
//                Intent intent = new Intent(getContext(), MainActivity.class);
//                intent.putExtra(MainActivity.CURRENT_USER_KEY, loggedInUser);
//
//                loginInToast.cancel();
//
//                Toast.makeText(getContext(), "Hello " + Cache.getInstance().getCurrUser().getName(), Toast.LENGTH_LONG).show();
//                startActivity(intent);
//            } else if (msg.getData().containsKey(LoginTask.MESSAGE_KEY)) {
//                String message = msg.getData().getString(LoginTask.MESSAGE_KEY);
//                Toast.makeText(getContext(), "Failed to login: " + message, Toast.LENGTH_LONG).show();
//            } else if (msg.getData().containsKey(LoginTask.EXCEPTION_KEY)) {
//                Exception ex = (Exception) msg.getData().getSerializable(LoginTask.EXCEPTION_KEY);
//                Toast.makeText(getContext(), "Failed to login because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        }


}
