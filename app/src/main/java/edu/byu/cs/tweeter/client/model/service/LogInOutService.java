package edu.byu.cs.tweeter.client.model.service;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.presenter.MainActivityPresenter;
import edu.byu.cs.tweeter.client.view.main.MainActivity;
import edu.byu.cs.tweeter.model.domain.AuthToken;

public class LogInOutService {

    public interface LogoutHandlerObserver{
        void logOutSuccess();
        void displayErrorMessage(String message);
        void displayException(Exception ex);
    }
    public void StartLogoutTask(AuthToken currUserAuthToken, MainActivityPresenter.LogoutHandlerObserver observer) {
        LogoutTask logoutTask = new LogoutTask(Cache.getInstance().getCurrUserAuthToken(), new LogoutHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(logoutTask);
    }
    private class LogoutHandler extends Handler {//todo move this
        private LogoutHandlerObserver observer;

        public LogoutHandler(LogoutHandlerObserver observer){
            this.observer = observer;
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(LogoutTask.SUCCESS_KEY);
            if (success) {
                observer.logOutSuccess();
//                logOutToast.cancel();
//                logoutUser();
            } else if (msg.getData().containsKey(LogoutTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(LogoutTask.MESSAGE_KEY);
                observer.displayErrorMessage(message);
//                Toast.makeText(MainActivity.this, "Failed to logout: " + message, Toast.LENGTH_LONG).show();
            } else if (msg.getData().containsKey(LogoutTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(LogoutTask.EXCEPTION_KEY);
                observer.displayException(ex);
//                Toast.makeText(MainActivity.this, "Failed to logout because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
