package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FeedService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.model.domain.Status;

public class GetFeedHandler extends BackgroundTaskHandler<FeedService.GetFeedObserver> {
    public GetFeedHandler(FeedService.GetFeedObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(Bundle data) {
        List<Status> statuses = (List<Status>) data.getSerializable(GetFeedTask.STATUSES_KEY); //todo fix this
        boolean hasMorePages = data.getBoolean(GetFeedTask.MORE_PAGES_KEY);
        Status lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;
        observer.addItems(statuses, hasMorePages, lastStatus);
    }

//    @Override
//    public void handleMessage(@NonNull Message msg) {
//        boolean success = msg.getData().getBoolean(GetFeedTask.SUCCESS_KEY);
//        if (success) {
//            List<Status> statuses = (List<Status>) msg.getData().getSerializable(GetFeedTask.STATUSES_KEY); //todo fix this
//            boolean hasMorePages = msg.getData().getBoolean(GetFeedTask.MORE_PAGES_KEY);
//            Status lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;
//            observer.addItems(statuses, hasMorePages, lastStatus);
//        } else if (msg.getData().containsKey(GetFeedTask.MESSAGE_KEY)) {
//            String message = msg.getData().getString(GetFeedTask.MESSAGE_KEY);
//            observer.displayErrorMessage(message);
//        } else if (msg.getData().containsKey(GetFeedTask.EXCEPTION_KEY)) {
//            Exception ex = (Exception) msg.getData().getSerializable(GetFeedTask.EXCEPTION_KEY);
//            observer.displayException(ex);
//        }
//    }



}
