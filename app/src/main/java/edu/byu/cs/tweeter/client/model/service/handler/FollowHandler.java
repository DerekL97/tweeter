package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.FollowService;

public class FollowHandler extends BackgroundTaskHandler {
    FollowService.startFollowTaskObserver observer;
    public FollowHandler(FollowService.startFollowTaskObserver observer) {
        super(observer);
        this.observer = observer;
    }

    @Override
    protected void handleSuccessMessage(Bundle data) {
        observer.FollowReturn();
//        updateSelectedUserFollowingAndFollowers();
//        updateFollowButton(false);
    }

//    @Override
//    public void handleMessage(@NonNull Message msg) {
//        boolean success = msg.getData().getBoolean(FollowTask.SUCCESS_KEY);
//        if (success) {
//            observer.FollowReturn();
////                updateSelectedUserFollowingAndFollowers();
////                updateFollowButton(false);
//        } else if (msg.getData().containsKey(FollowTask.MESSAGE_KEY)) {
//            String message = msg.getData().getString(FollowTask.MESSAGE_KEY);
//            observer.displayErrorMessage(message);
////                Toast.makeText(MainActivity.this, "Failed to follow: " + message, Toast.LENGTH_LONG).show();
//        } else if (msg.getData().containsKey(FollowTask.EXCEPTION_KEY)) {
//            Exception ex = (Exception) msg.getData().getSerializable(FollowTask.EXCEPTION_KEY);
//            observer.displayException(ex);
////                Toast.makeText(MainActivity.this, "Failed to follow because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
//        }
//
////            followButton.setEnabled(true);
//    }
}
