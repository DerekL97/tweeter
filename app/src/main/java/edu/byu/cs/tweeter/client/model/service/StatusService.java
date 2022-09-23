package edu.byu.cs.tweeter.client.model.service;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.presenter.MainActivityPresenter;
import edu.byu.cs.tweeter.client.view.main.MainActivity;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusService {

    public interface StatusServiceObserver{
        void addStatuses(List<Status> Statuses, boolean hasMorePages, Status lastStatus);
        void displayErrorMessage(String message);
        void displayException(Exception ex);
    }


    public void getMoreStatuses(User user, int PAGE_SIZE, Status lastStatus, StatusServiceObserver observer){
        GetStoryTask getStoryTask = new GetStoryTask(Cache.getInstance().getCurrUserAuthToken(),
                user, PAGE_SIZE, lastStatus, new GetStoryHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getStoryTask);
    }
    /**
     * Message handler (i.e., observer) for GetStoryTask.
     */
    private class GetStoryHandler extends Handler {
        private StatusServiceObserver observer;

        public GetStoryHandler (StatusServiceObserver observer){
            this.observer = observer;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
//            isLoading = false;
//            removeLoadingFooter();

            boolean success = msg.getData().getBoolean(GetStoryTask.SUCCESS_KEY);
            if (success) {
                List<Status> statuses = (List<Status>) msg.getData().getSerializable(GetStoryTask.STATUSES_KEY);
                boolean hasMorePages = msg.getData().getBoolean(GetStoryTask.MORE_PAGES_KEY);

                Status lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;

                observer.addStatuses(statuses, hasMorePages, lastStatus);
            } else if (msg.getData().containsKey(GetStoryTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(GetStoryTask.MESSAGE_KEY);
                observer.displayErrorMessage(message);
//                Toast.makeText(getContext(), "Failed to get story: " + message, Toast.LENGTH_LONG).show();
            } else if (msg.getData().containsKey(GetStoryTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(GetStoryTask.EXCEPTION_KEY);
                observer.displayException(ex);
//                Toast.makeText(getContext(), "Failed to get story because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public interface PostStatusObserver{
        public void postedStatus();
        public void displayErrorMessage(String message);
        public void displayException(Exception ex);
    }
    public void startStatusTask(AuthToken authToken, Status newStatus, MainActivityPresenter.postStatusObserver observer){
        PostStatusTask statusTask = new PostStatusTask(Cache.getInstance().getCurrUserAuthToken(),
                newStatus, new PostStatusHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(statusTask);
    }
    private class PostStatusHandler extends Handler {//todo move this
        private PostStatusObserver observer;
        public PostStatusHandler(PostStatusObserver observer){
            this.observer = observer;
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(PostStatusTask.SUCCESS_KEY);
            if (success) {
                observer.postedStatus();
//                postingToast.cancel();
//                Toast.makeText(MainActivity.this, "Successfully Posted!", Toast.LENGTH_LONG).show();
            } else if (msg.getData().containsKey(PostStatusTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(PostStatusTask.MESSAGE_KEY);
                observer.displayErrorMessage(message);
//                Toast.makeText(MainActivity.this, "Failed to post status: " + message, Toast.LENGTH_LONG).show();
            } else if (msg.getData().containsKey(PostStatusTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(PostStatusTask.EXCEPTION_KEY);
                observer.displayException(ex);
//                Toast.makeText(MainActivity.this, "Failed to post status because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

}
