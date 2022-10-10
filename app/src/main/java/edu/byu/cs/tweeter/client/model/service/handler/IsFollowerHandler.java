package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;

public class IsFollowerHandler extends BackgroundTaskHandler {
    private FollowService.IsFollowerHandlerObserver observer;

    public IsFollowerHandler(FollowService.IsFollowerHandlerObserver observer) {
        super(observer);
        this.observer = observer;
    }

    @Override
    protected void handleSuccessMessage(Bundle data) {
        boolean isFollower = data.getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);

        // If logged in user if a follower of the selected user, display the follow button as "following"
        observer.setIsFollower(isFollower);

    }

//    @Override
//    public void handleMessage(@NonNull Message msg) {
//        boolean success = msg.getData().getBoolean(IsFollowerTask.SUCCESS_KEY);
//        if (success) {
//            boolean isFollower = msg.getData().getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);
//
//            // If logged in user if a follower of the selected user, display the follow button as "following"
//            observer.setIsFollower(isFollower);
//
//        } else if (msg.getData().containsKey(IsFollowerTask.MESSAGE_KEY)) {
//            String message = msg.getData().getString(IsFollowerTask.MESSAGE_KEY);
//            observer.displayErrorMessage(message);
////                Toast.makeText(MainActivity.this, "Failed to determine following relationship: " + message, Toast.LENGTH_LONG).show();
//        } else if (msg.getData().containsKey(IsFollowerTask.EXCEPTION_KEY)) {
//            Exception ex = (Exception) msg.getData().getSerializable(IsFollowerTask.EXCEPTION_KEY);
//            observer.displayException(ex);
////                Toast.makeText(MainActivity.this, "Failed to determine following relationship because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
//        }
//    }
}
