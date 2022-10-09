package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public class PagedPresenter extends Presenter{

    protected UserService userService;
    protected static final int PAGE_SIZE = 10;
    protected boolean hasMorePages;
    protected boolean isLoading = false;

    public PagedPresenter(View view) {
        super(view);
        this.userService = new UserService();

    }

    public interface View extends Presenter.View{
        void displayMessage(String message);
        void setLoadingFooter(boolean value);
        void showUser(User user);
        //

    }

    public boolean hasMorePages() {
        return hasMorePages;
    }
    public boolean isLoading() {
        return isLoading;
    }
}
