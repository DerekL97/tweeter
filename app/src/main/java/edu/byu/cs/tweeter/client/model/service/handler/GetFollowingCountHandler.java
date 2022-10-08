package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingCountTask;

public class GetFollowingCountHandler extends BackgroundTaskHandler {//todo move this
    private FollowService.GetFollowingCountObserver observer;

    public GetFollowingCountHandler(FollowService.GetFollowingCountObserver observer) {
        super(observer);
        this.observer = observer;
    }

    @Override
    protected void handleSuccessMessage(Bundle data) {
        int count = data.getInt(GetFollowingCountTask.COUNT_KEY);
        observer.returnFollowingCount(count);
//        followeeCount.setText(getString(R.string.followeeCount, String.valueOf(count)));

    }

//    @Override
//    public void handleMessage(@NonNull Message msg) {
//        boolean success = msg.getData().getBoolean(GetFollowingCountTask.SUCCESS_KEY);
//        if (success) {
//            int count = msg.getData().getInt(GetFollowingCountTask.COUNT_KEY);
//            observer.returnFollowingCount(count);
////                followeeCount.setText(getString(R.string.followeeCount, String.valueOf(count)));
//        } else if (msg.getData().containsKey(GetFollowingCountTask.MESSAGE_KEY)) {
//            String message = msg.getData().getString(GetFollowingCountTask.MESSAGE_KEY);
//            observer.displayErrorMessage(message);
////                Toast.makeText(MainActivity.this, "Failed to get following count: " + message, Toast.LENGTH_LONG).show();
//        } else if (msg.getData().containsKey(GetFollowingCountTask.EXCEPTION_KEY)) {
//            Exception ex = (Exception) msg.getData().getSerializable(GetFollowingCountTask.EXCEPTION_KEY);
//            observer.displayException(ex);
////                Toast.makeText(MainActivity.this, "Failed to get following count because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
//        }
//    }
}
