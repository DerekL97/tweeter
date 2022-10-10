package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PostStatusTask;

public class PostStatusHandler extends BackgroundTaskHandler {
    private StatusService.PostStatusObserver observer;

    public PostStatusHandler(StatusService.PostStatusObserver observer) {
        super(observer);
        this.observer = observer;
    }

    @Override
    protected void handleSuccessMessage(Bundle data) {
        observer.postedStatus();
//       postingToast.cancel();
//       Toast.makeText(MainActivity.this, "Successfully Posted!", Toast.LENGTH_LONG).show();
    }
//    @Override
//    public void handleMessage(@NonNull Message msg) {
//        boolean success = msg.getData().getBoolean(PostStatusTask.SUCCESS_KEY);
//        if (success) {
//            observer.postedStatus();
////                postingToast.cancel();
////                Toast.makeText(MainActivity.this, "Successfully Posted!", Toast.LENGTH_LONG).show();
//        } else if (msg.getData().containsKey(PostStatusTask.MESSAGE_KEY)) {
//            String message = msg.getData().getString(PostStatusTask.MESSAGE_KEY);
//            observer.displayErrorMessage(message);
////                Toast.makeText(MainActivity.this, "Failed to post status: " + message, Toast.LENGTH_LONG).show();
//        } else if (msg.getData().containsKey(PostStatusTask.EXCEPTION_KEY)) {
//            Exception ex = (Exception) msg.getData().getSerializable(PostStatusTask.EXCEPTION_KEY);
//            observer.displayException(ex);
////                Toast.makeText(MainActivity.this, "Failed to post status because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
//        }
//    }


}