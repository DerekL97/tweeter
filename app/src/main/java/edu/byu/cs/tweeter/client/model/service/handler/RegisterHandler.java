package edu.byu.cs.tweeter.client.model.service.handler;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.LogInOutService;
import edu.byu.cs.tweeter.client.model.service.Service;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.view.main.MainActivity;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterHandler extends BackgroundTaskHandler {
    private LogInOutService.RegisterHandlerObserver observer;
    public RegisterHandler(LogInOutService.RegisterHandlerObserver observer) {
        super(observer);
        this.observer = observer;
    }

    @Override
    protected void handleSuccessMessage(Bundle data) {
        observer.registerSuccess(data);
//        User registeredUser = (User) msg.getData().getSerializable(RegisterTask.USER_KEY);
//        AuthToken authToken = (AuthToken) msg.getData().getSerializable(RegisterTask.AUTH_TOKEN_KEY);
//
//        Intent intent = new Intent(getContext(), MainActivity.class);
//
//        Cache.getInstance().setCurrUser(registeredUser);
//        Cache.getInstance().setCurrUserAuthToken(authToken);
//
//        intent.putExtra(MainActivity.CURRENT_USER_KEY, registeredUser);
//
//        registeringToast.cancel();
//
//        Toast.makeText(getContext(), "Hello " + Cache.getInstance().getCurrUser().getName(), Toast.LENGTH_LONG).show();
//        try {
//            startActivity(intent);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
    }
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            boolean success = msg.getData().getBoolean(RegisterTask.SUCCESS_KEY);
//            if (success) {
//                User registeredUser = (User) msg.getData().getSerializable(RegisterTask.USER_KEY);
//                AuthToken authToken = (AuthToken) msg.getData().getSerializable(RegisterTask.AUTH_TOKEN_KEY);
//
//                Intent intent = new Intent(getContext(), MainActivity.class);
//
//                Cache.getInstance().setCurrUser(registeredUser);
//                Cache.getInstance().setCurrUserAuthToken(authToken);
//
//                intent.putExtra(MainActivity.CURRENT_USER_KEY, registeredUser);
//
//                registeringToast.cancel();
//
//                Toast.makeText(getContext(), "Hello " + Cache.getInstance().getCurrUser().getName(), Toast.LENGTH_LONG).show();
//                try {
//                    startActivity(intent);
//                } catch (Exception e) {
//                    System.out.println(e.getMessage());
//                }
//            } else if (msg.getData().containsKey(RegisterTask.MESSAGE_KEY)) {
//                String message = msg.getData().getString(RegisterTask.MESSAGE_KEY);
//                Toast.makeText(getContext(), "Failed to register: " + message, Toast.LENGTH_LONG).show();
//            } else if (msg.getData().containsKey(RegisterTask.EXCEPTION_KEY)) {
//                Exception ex = (Exception) msg.getData().getSerializable(RegisterTask.EXCEPTION_KEY);
//                Toast.makeText(getContext(), "Failed to register because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        }


}
