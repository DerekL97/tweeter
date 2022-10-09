package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowersPresenter extends PagedPresenter {
    private View view;
    private FollowService followService;
    private User lastFollower;


    public FollowersPresenter(View view) {
        super(view);
        this.view = view;
        userService = new UserService();
        followService = new FollowService();
    }


    public void getUser(String userAlias) {
        userService.getUser(userAlias, Cache.getInstance().getCurrUserAuthToken(), new GetUserObserver());
        view.displayMessage("Getting user's profile...");
    }

    public void loadMoreItems(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(true);
//            addLoadingFooter();

            followService.loadMoreFollowerItems(user, PAGE_SIZE, lastFollower, new GetFollowersObserver());
//            GetFollowersTask getFollowersTask = new GetFollowersTask(Cache.getInstance().getCurrUserAuthToken(),
//                    user, PAGE_SIZE, lastFollower, new FollowersFragment.FollowersRecyclerViewAdapter.GetFollowersHandler());
//            ExecutorService executor = Executors.newSingleThreadExecutor();
//            executor.execute(getFollowersTask);
        }
    }

    public boolean HasMorePages() {
        return hasMorePages;
    }


    public interface View extends PagedPresenter.View{
        void addFollowers(List<User> followers);
    }

    private class GetUserObserver extends Presenter.ServiceObserver implements UserService.GetUserObserver {
        @Override
        public void loadUser(User user) {
            view.showUser(user);
        }
    }
    private class GetFollowersObserver extends Presenter.ServiceObserver implements FollowService.GetFollowersObserver{
        @Override
        public void addFollowers(boolean hasMorePages, User lastFollower, List<User> followers) {
            FollowersPresenter.this.hasMorePages = hasMorePages;
            FollowersPresenter.this.lastFollower = lastFollower;
            isLoading = false;
            view.setLoadingFooter(false);
            view.addFollowers(followers);
        }

    }
}
