package presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingPresenter {

    private View view;
    private static final int PAGE_SIZE = 10;
    private FollowService service;
    private User lastFollowee;
    private boolean hasMorePages;
    private boolean isLoading = false;

    public interface View{
        void displayMessage(String message);
        void setLoadingFooter(boolean value);
        void addFollowees(List<User> followees);
    }


    public FollowingPresenter(View view) {
        this.view = view;
        service = new FollowService();
    }

    public boolean hasMorePages() {
        return hasMorePages;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void loadMoreItems(User user) {
        isLoading = true;
        view.setLoadingFooter(true);

        service.loadMoreItems(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastFollowee, new GetFollowingObserver());

    }

    private class GetFollowingObserver implements FollowService.GetFollowingObserver {

        @Override
        public void addFollowees(List<User> followees, boolean hasMorePages) {
            lastFollowee = (followees.size() > 0) ? followees.get(followees.size() - 1) : null;
            view.addFollowees(followees);
            view.setLoadingFooter(false);
            isLoading = false;
        }

        @Override
        public void displayErrorMessage(String message){
            view.displayMessage("Failed to get the following: " + message);
            view.setLoadingFooter(false);
            isLoading = false;
        }

        @Override
        public void displayException(Exception ex) {
            view.displayMessage("Failed to get following because of exception: " + ex.getMessage());
            view.setLoadingFooter(false);
            isLoading = false;

        }
    }
}
