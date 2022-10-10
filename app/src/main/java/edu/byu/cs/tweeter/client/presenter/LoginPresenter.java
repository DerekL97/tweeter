package edu.byu.cs.tweeter.client.presenter;

import android.os.Bundle;
import android.text.Editable;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.LogInOutService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginPresenter extends Presenter{
    private static final String LOG_TAG = "LoginFragment";
    private View view;
    private LogInOutService logInOutService;
//    private Toast loginInToast;
//    private EditText alias;
//    private EditText password;
//    private TextView errorView;

    public LoginPresenter(View view) {
        super(view);
        this.view = view;
        logInOutService = new LogInOutService();
    }

    public interface View extends Presenter.View{

        void startMain(User loggedInUser);
    }

    public void login(String alias, String password) throws Exception{
        // Login and move to MainActivity.
//        try {
//            validateLogin();
//            errorView.setText(null);

//            loginInToast = Toast.makeText(getContext(), "Logging In...", Toast.LENGTH_LONG);
//            loginInToast.show();
        view.displayMessage("Logging In...");

        // Send the login request.
        logInOutService.StartLoginTask(alias, password, new LoginTaskObserver());
//        LoginTask loginTask = new LoginTask(alias, password, new LoginHandler());
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        executor.execute(loginTask);
//        } catch (Exception e) {
//            errorView.setText(e.getMessage());
//        }
    }
    public class LoginTaskObserver extends Presenter.ServiceObserver implements LogInOutService.LoginHandlerObserver{
        @Override
        public void logInSuccess(Bundle data) {
            User loggedInUser = (User) data.getSerializable(LoginTask.USER_KEY);
            AuthToken authToken = (AuthToken) data.getSerializable(LoginTask.AUTH_TOKEN_KEY);

            // Cache user session information
            Cache.getInstance().setCurrUser(loggedInUser);
            Cache.getInstance().setCurrUserAuthToken(authToken);

            view.startMain(loggedInUser);
//            Intent intent = new Intent(getContext(), MainActivity.class);
//            intent.putExtra(MainActivity.CURRENT_USER_KEY, loggedInUser);
//
//            loginInToast.cancel();
//
//            Toast.makeText(getContext(), "Hello " + Cache.getInstance().getCurrUser().getName(), Toast.LENGTH_LONG).show();
//            startActivity(intent);
        }
    }

    public void validateLogin(Editable alias, Editable password) {
        if (alias.charAt(0) != '@') {
            throw new IllegalArgumentException("Alias must begin with @.");
        }
        if (alias.length() < 2) {
            throw new IllegalArgumentException("Alias must contain 1 or more characters after the @.");
        }
        if (password.length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
    }

}
