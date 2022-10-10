package edu.byu.cs.tweeter.client.model.service;

import android.os.Bundle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.handler.LoginHandler;
import edu.byu.cs.tweeter.client.model.service.handler.LogoutHandler;
import edu.byu.cs.tweeter.client.model.service.handler.RegisterHandler;
import edu.byu.cs.tweeter.client.presenter.LoginPresenter;
import edu.byu.cs.tweeter.client.presenter.MainActivityPresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;

public class LogInOutService extends Service {
    public interface LoginHandlerObserver extends Service.ServiceObserverInterface{
        void logInSuccess(Bundle data);
    }
    public void StartLoginTask(String alias, String password, LoginPresenter.LoginTaskObserver loginTaskObserver) {
        LoginTask loginTask = new LoginTask(alias, password, new LoginHandler(loginTaskObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(loginTask);
    }

    public interface LogoutHandlerObserver extends Service.ServiceObserverInterface{
        void logOutSuccess();
//        void displayErrorMessage(String message);
//        void displayException(Exception ex);
    }
    public void StartLogoutTask(AuthToken currUserAuthToken, MainActivityPresenter.LogoutHandlerObserver observer) {
        LogoutTask logoutTask = new LogoutTask(Cache.getInstance().getCurrUserAuthToken(), new LogoutHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(logoutTask);
    }

    public interface RegisterHandlerObserver extends Service.ServiceObserverInterface{
        void registerSuccess(Bundle data);
    }
    public void StartRegisterTask(String firstName, String lastName, String alias,
                                  String password, String imageBytesBase64,
                                  RegisterHandlerObserver registerHandlerObserver){
        RegisterTask registerTask = new RegisterTask(firstName, lastName,
                alias, password, imageBytesBase64, new RegisterHandler(registerHandlerObserver));

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(registerTask);
    }
}
