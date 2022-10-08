package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;

public class GetFollowersCountHandler extends BackgroundTaskHandler {
    private FollowService.GetFollowersCountObserver observer;

    public GetFollowersCountHandler(FollowService.GetFollowersCountObserver observer) {
        super(observer);
        this.observer = observer;
    }

    @Override
    protected void handleSuccessMessage(Bundle data) {
        int count = data.getInt(GetFollowersCountTask.COUNT_KEY);
        observer.returnFollowersCount(count);
//        followerCount.setText(getString(R.string.followerCount, String.valueOf(count)));

    }


//    @Override
//    public void handleMessage(@NonNull Message msg) {
//        boolean success = msg.getData().getBoolean(GetFollowersCountTask.SUCCESS_KEY);
//        if (success) {
//            int count = msg.getData().getInt(GetFollowersCountTask.COUNT_KEY);
//            observer.returnFollowersCount(count);
////                followerCount.setText(getString(R.string.followerCount, String.valueOf(count)));
//        } else if (msg.getData().containsKey(GetFollowersCountTask.MESSAGE_KEY)) {
//            String message = msg.getData().getString(GetFollowersCountTask.MESSAGE_KEY);
//            observer.displayErrorMessage(message);
////                Toast.makeText(MainActivity.this, "Failed to get followers count: " + message, Toast.LENGTH_LONG).show();
//        } else if (msg.getData().containsKey(GetFollowersCountTask.EXCEPTION_KEY)) {
//            Exception ex = (Exception) msg.getData().getSerializable(GetFollowersCountTask.EXCEPTION_KEY);
//            observer.displayException(ex);
////                Toast.makeText(MainActivity.this, "Failed to get followers count because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
//        }
//    }
}
