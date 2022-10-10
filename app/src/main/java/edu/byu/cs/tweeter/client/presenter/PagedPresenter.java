package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.LoadService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class PagedPresenter<T> extends Presenter{
    protected View view;
    protected UserService userService;
    protected static final int PAGE_SIZE = 10;
    protected boolean hasMorePages;
    protected boolean isLoading = false;
    protected T lastItem;
//    protected boolean isGettingUser;//todo what is this for?
    protected LoadService extraService;

    public PagedPresenter(View view) {
        super(view);
        this.view = view;
        this.userService = new UserService();

    }

    public interface View extends Presenter.View{
//        void displayMessage(String message);
        void setLoadingFooter(boolean value);
        void showUser(User user);
        //
    }
    public void loadMoreItems(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(true);
            getItems(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastItem);
        }
    }
    public abstract void getItems(AuthToken authToken, User user, int PAGE_SIZE, T lastItem);

    public void getUser(String userAlias) {
        userService.getUser(userAlias, Cache.getInstance().getCurrUserAuthToken(), new GetUserObserver());
        view.displayMessage("Getting user's profile...");
    }
    private class GetUserObserver extends Presenter.ServiceObserver implements UserService.GetUserObserver{
        public void loadUser(User user) {
            view.showUser(user);
        }
    }

    public boolean hasMorePages() {
        return hasMorePages;
    }
    public boolean isLoading() {
        return isLoading;
    }
}
