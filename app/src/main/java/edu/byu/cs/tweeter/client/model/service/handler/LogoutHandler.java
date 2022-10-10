package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.LogInOutService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LogoutTask;

public class LogoutHandler extends BackgroundTaskHandler {
    private LogInOutService.LogoutHandlerObserver observer;

    public LogoutHandler(LogInOutService.LogoutHandlerObserver observer) {
        super(observer);
        this.observer = observer;
    }

//    @Override
//    public void handleMessage(@NonNull Message msg) {
//        boolean success = msg.getData().getBoolean(LogoutTask.SUCCESS_KEY);
//        if (success) {
//            observer.logOutSuccess();
////                logOutToast.cancel();
////                logoutUser();
//        } else if (msg.getData().containsKey(LogoutTask.MESSAGE_KEY)) {
//            String message = msg.getData().getString(LogoutTask.MESSAGE_KEY);
//            observer.displayErrorMessage(message);
////                Toast.makeText(MainActivity.this, "Failed to logout: " + message, Toast.LENGTH_LONG).show();
//        } else if (msg.getData().containsKey(LogoutTask.EXCEPTION_KEY)) {
//            Exception ex = (Exception) msg.getData().getSerializable(LogoutTask.EXCEPTION_KEY);
//            observer.displayException(ex);
////                Toast.makeText(MainActivity.this, "Failed to logout because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
//        }
//    }

    @Override
    protected void handleSuccessMessage(Bundle data) {
        observer.logOutSuccess();
//        logOutToast.cancel();
//        logoutUser();
    }
}
