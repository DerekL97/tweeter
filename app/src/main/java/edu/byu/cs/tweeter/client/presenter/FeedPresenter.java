package edu.byu.cs.tweeter.client.presenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.Spanned;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedPresenter {
    private View view;

    FeedPresenter(View view){
        this.view = view;
    }

    public interface View{
        void startActivity(Intent intent);
        void startContextActivity(User user); // Currently only starts one type of activity
        void printToast(String message);

    }
    public void mentionClick(android.view.View widget) {
        TextView clickedMention = (TextView) widget;// todo should widget be in the presenter?
        Spanned s = (Spanned) clickedMention.getText();
        int start = s.getSpanStart(this);// todo came from feedFragment (shoot, I think it was a subclass), need to change "this"?
        int end = s.getSpanEnd(this);

        String clickable = s.subSequence(start, end).toString();

        if (clickable.contains("http")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(clickable));
            view.startActivity(intent);
            //startActivity(intent);
        } else {
            GetUserTask getUserTask = new GetUserTask(Cache.getInstance().getCurrUserAuthToken(),
                    clickable, new GetUserHandler());
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(getUserTask);
            view.printToast("Getting user's profile...");
        }
    }
    public void itemViewClick(String userAlias){ //not really sure what this method does . . .
        GetUserTask getUserTask = new GetUserTask(Cache.getInstance().getCurrUserAuthToken(),
                userAlias, new GetUserHandler());
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getUserTask);
        view.printToast("Getting user's profile...");
//        Toast.makeText(getContext(), "Getting user's profile...", Toast.LENGTH_LONG).show();
    }

    private class GetUserHandler extends Handler { //todo this whole class should move I think maybe to service?
        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(GetUserTask.SUCCESS_KEY);
            if (success) {
                User user = (User) msg.getData().getSerializable(GetUserTask.USER_KEY);
                view.startContextActivity(user);
//                Intent intent = new Intent(getContext(), MainActivity.class);
//                intent.putExtra(MainActivity.CURRENT_USER_KEY, user);
//                view.startActivity(intent);
            } else if (msg.getData().containsKey(GetUserTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(GetUserTask.MESSAGE_KEY);
                view.printToast("Failed to get user's profile: " + message);
//                Toast.makeText(getContext(), "Failed to get user's profile: " + message, Toast.LENGTH_LONG).show();
            } else if (msg.getData().containsKey(GetUserTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(GetUserTask.EXCEPTION_KEY);
                view.printToast("Failed to get user's profile because of exception: " + ex.getMessage());
//                Toast.makeText(getContext(), "Failed to get user's profile because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
