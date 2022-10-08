package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Message handler (i.e., observer) for GetUserTask.
 */
public class GetUserHandler extends BackgroundTaskHandler {
    private UserService.GetUserObserver observer;

    public GetUserHandler(UserService.GetUserObserver observer) {
        super(observer);
        this.observer = observer;
    }

    @Override
    protected void handleSuccessMessage(Bundle data) {
        User user = (User) data.getSerializable(GetUserTask.USER_KEY);
        this.observer.loadUser(user);
    }
//    @Override
//    public void handleMessage(@NonNull Message msg) {
//        boolean success = msg.getData().getBoolean(GetUserTask.SUCCESS_KEY);
//        if (success) {
//            User user = (User) msg.getData().getSerializable(GetUserTask.USER_KEY);
//
////                Intent intent = new Intent(getContext(), MainActivity.class);
////                intent.putExtra(MainActivity.CURRENT_USER_KEY, user);
////                startActivity(intent);
//            observer.loadUser(user);
//        } else if (msg.getData().containsKey(GetUserTask.MESSAGE_KEY)) {
//            String message = msg.getData().getString(GetUserTask.MESSAGE_KEY);
//            observer.displayErrorMessage(message);
////                Toast.makeText(getContext(), "Failed to get user's profile: " + message, Toast.LENGTH_LONG).show();
//        } else if (msg.getData().containsKey(GetUserTask.EXCEPTION_KEY)) {
//            Exception ex = (Exception) msg.getData().getSerializable(GetUserTask.EXCEPTION_KEY);
//            observer.displayException(ex);
////                Toast.makeText(getContext(), "Failed to get user's profile because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
//        }
//    }

}
