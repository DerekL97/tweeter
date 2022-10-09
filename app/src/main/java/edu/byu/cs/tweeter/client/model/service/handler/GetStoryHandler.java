package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.model.domain.Status;

/**
 * Message handler (i.e., observer) for GetStoryTask.
 */
public class GetStoryHandler extends BackgroundTaskHandler {
    private StatusService.StatusServiceObserver observer;

    public GetStoryHandler(StatusService.StatusServiceObserver observer) {
        super(observer);
        this.observer = observer;
    }
    @Override
    protected void handleSuccessMessage(Bundle data) {
        List<Status> statuses = (List<Status>) data.getSerializable(GetStoryTask.STATUSES_KEY);
        boolean hasMorePages = data.getBoolean(GetStoryTask.MORE_PAGES_KEY);
        Status lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;
        observer.addStatuses(statuses, hasMorePages, lastStatus);   //todo fix never ending feed
    }
//    @Override
//    public void handleMessage(@NonNull Message msg) {
////            isLoading = false;
////            removeLoadingFooter();
//
//        boolean success = msg.getData().getBoolean(GetStoryTask.SUCCESS_KEY);
//        if (success) {
//            List<Status> statuses = (List<Status>) msg.getData().getSerializable(GetStoryTask.STATUSES_KEY);
//            boolean hasMorePages = msg.getData().getBoolean(GetStoryTask.MORE_PAGES_KEY);
//
//            Status lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;
//
//            observer.addStatuses(statuses, hasMorePages, lastStatus);
//        } else if (msg.getData().containsKey(GetStoryTask.MESSAGE_KEY)) {
//            String message = msg.getData().getString(GetStoryTask.MESSAGE_KEY);
//            observer.displayErrorMessage(message);
////                Toast.makeText(getContext(), "Failed to get story: " + message, Toast.LENGTH_LONG).show();
//        } else if (msg.getData().containsKey(GetStoryTask.EXCEPTION_KEY)) {
//            Exception ex = (Exception) msg.getData().getSerializable(GetStoryTask.EXCEPTION_KEY);
//            observer.displayException(ex);
////                Toast.makeText(getContext(), "Failed to get story because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
//        }
//    }


}
