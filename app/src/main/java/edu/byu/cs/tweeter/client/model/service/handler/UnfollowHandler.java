package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.UnfollowTask;

public class UnfollowHandler extends BackgroundTaskHandler {//todo move this
    private FollowService.startUnfollowTaskObserver observer;

    public UnfollowHandler(FollowService.startUnfollowTaskObserver observer) {
        super(observer);
        this.observer = observer;
    }

    @Override
    protected void handleSuccessMessage(Bundle data) {
        observer.UnfollowReturn();
//        updateSelectedUserFollowingAndFollowers();
//        updateFollowButton(true);
    }
//    @Override
//    public void handleMessage(@NonNull Message msg) {
//        boolean success = msg.getData().getBoolean(UnfollowTask.SUCCESS_KEY);
//        if (success) {
//            observer.UnfollowReturn();
////                updateSelectedUserFollowingAndFollowers();
////                updateFollowButton(true);
//        } else if (msg.getData().containsKey(UnfollowTask.MESSAGE_KEY)) {
//            String message = msg.getData().getString(UnfollowTask.MESSAGE_KEY);
//            observer.displayErrorMessage(message);
////                Toast.makeText(MainActivity.this, "Failed to unfollow: " + message, Toast.LENGTH_LONG).show();
//        } else if (msg.getData().containsKey(UnfollowTask.EXCEPTION_KEY)) {
//            Exception ex = (Exception) msg.getData().getSerializable(UnfollowTask.EXCEPTION_KEY);
//            observer.displayException(ex);
////                Toast.makeText(MainActivity.this, "Failed to unfollow because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
//        }
//
////            followButton.setEnabled(true);
//    }

}
