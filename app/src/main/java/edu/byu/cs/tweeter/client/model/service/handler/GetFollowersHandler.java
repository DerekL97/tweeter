package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowersHandler extends Handler {
    private FollowService.GetFollowersObserver observer;

    public GetFollowersHandler(FollowService.GetFollowersObserver observer) {
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
//            isLoading = false;
//            removeLoadingFooter();

        boolean success = msg.getData().getBoolean(GetFollowersTask.SUCCESS_KEY);
        if (success) {
            List<User> followers = (List<User>) msg.getData().getSerializable(GetFollowersTask.FOLLOWERS_KEY);
            boolean hasMorePages = msg.getData().getBoolean(GetFollowersTask.MORE_PAGES_KEY);
            User lastFollower = (followers.size() > 0) ? followers.get(followers.size() - 1) : null;
            observer.addFollowers(hasMorePages, lastFollower, followers);
//                followersRecyclerViewAdapter.addItems(followers);
        } else if (msg.getData().containsKey(GetFollowersTask.MESSAGE_KEY)) {
            String message = msg.getData().getString(GetFollowersTask.MESSAGE_KEY);
            observer.displayErrorMessage(message);
//                Toast.makeText(getContext(), "Failed to get followers: " + message, Toast.LENGTH_LONG).show();
        } else if (msg.getData().containsKey(GetFollowersTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(GetFollowersTask.EXCEPTION_KEY);
            observer.displayException(ex);
//                Toast.makeText(getContext(), "Failed to get followers because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
