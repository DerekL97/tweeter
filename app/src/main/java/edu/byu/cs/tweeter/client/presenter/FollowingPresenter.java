package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingPresenter extends PagedPresenter<User> {
    private FollowService followService;
//    private User lastFollowee;
    protected View view;

    public FollowingPresenter(View view) {
        super(view);
        this.view = view;
        followService = new FollowService();
    }

    public interface View extends PagedPresenter.View{
        void addFollowees(List<User> followees);
    }

    @Override
    public void getItems(AuthToken authToken, User user, int PAGE_SIZE, User lastItem) {
        followService.loadMoreFollowingItems(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastItem, new GetFollowingObserver());
    }

//    public void loadMoreItems(User user) {
//        isLoading = true;
//        view.setLoadingFooter(true);
//        followService.loadMoreFollowingItems(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastFollowee, new GetFollowingObserver());
//    }

    private class GetFollowingObserver extends Presenter.ServiceObserver implements FollowService.GetFollowingObserver {
        //Methods that FollowService makes you do
        @Override
        public void addFollowees(List<User> followees, boolean hasMorePages) {
            isLoading = false;
            view.setLoadingFooter(false);
            lastItem = (followees.size() > 0) ? followees.get(followees.size() - 1) : null;
            view.addFollowees(followees);
            FollowingPresenter.this.hasMorePages = hasMorePages;
        }
    }

//    private class GetUserObserver extends Presenter.ServiceObserver implements UserService.GetUserObserver{
//        @Override
//        public void loadUser(User user) {
//                view.showUser(user);
//        }
//
////        @Override
////        public void displayErrorMessage(String message) {
////            view.displayMessage("Failed to get user's profile: " + message);
//////            Toast.makeText(getContext(), "Failed to get user's profile: " + message, Toast.LENGTH_LONG).show();
////        }
////
////        @Override
////        public void displayException(Exception ex) {
////            view.displayMessage("Failed to get user's profile because of exception: " + ex.getMessage());
////        }
//    }

}
