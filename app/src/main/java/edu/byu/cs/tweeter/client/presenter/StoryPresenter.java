package edu.byu.cs.tweeter.client.presenter;

import android.content.Intent;
import android.net.Uri;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter extends PagedPresenter<Status> {
    private View view;
//    private Status lastStatus;
    private User user;
    private StatusService statusService;

    public StoryPresenter(View view){
        super(view);
        this.view = view;
        statusService = new StatusService();
    }

    public interface View extends PagedPresenter.View{
        void startActivity(Intent intent);
        void addStatuses(List<Status> followees);
    }

    @Override
    public void getItems(AuthToken authToken, User user, int PAGE_SIZE, Status lastItem) {
        statusService.getMoreStatuses(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastItem, new StatusServiceObserver());
    }

    public void mentionClick(String userAlias) {
        if (userAlias.contains("http")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(userAlias));
            view.startActivity(intent);
        } else {
            getUser(userAlias);
//            userService.getUser(userAlias, Cache.getInstance().getCurrUserAuthToken(), new GetUserObserver());
//            view.displayMessage("Getting user's profile...");
        }
    }


//    public void loadMoreItems() {
//        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
//            isLoading = true;
//            view.setLoadingFooter(true);
//            statusService.getMoreStatuses(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastStatus, new StatusServiceObserver());
//        }
//    }

    private class StatusServiceObserver extends Presenter.ServiceObserver implements StatusService.StatusServiceObserver{

        @Override
        public void addStatuses(List<Status> Statuses, boolean hasMorePages, Status lastStatus) {
            isLoading = false;
            view.setLoadingFooter(false);
//            removeLoadingFooter();
            StoryPresenter.this.hasMorePages = hasMorePages;
//            hasMorePages = msg.getData().getBoolean(GetStoryTask.MORE_PAGES_KEY);
            StoryPresenter.this.lastItem = lastStatus;
//            lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;
            view.addStatuses(Statuses);
        }

    }
//    private class GetUserObserver extends ServiceObserver implements UserService.GetUserObserver {
//        @Override
//        public void loadUser(User user) {
//            view.showUser(user);
//        }
//    }
}
