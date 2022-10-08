package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.Service;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public class FragmentPresenter {

    private View view;
    protected UserService userService;
    protected static final int PAGE_SIZE = 10;
    protected boolean hasMorePages;
    protected boolean isLoading = false;
    public boolean hasMorePages() {
        return hasMorePages;
    }
    public boolean isLoading() {
        return isLoading;
    }

    public FragmentPresenter(FragmentPresenter.View view){
        this.view = view;
        this.userService = new UserService();
    }

    public interface View{
        void displayMessage(String message);
        void setLoadingFooter(boolean value);
        void showUser(User user);
        //

    }

    public abstract class ServiceObserver implements Service.ServiceObserverInterface {
//        @Override
        public void handleFailure(String message) {
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayMessage("Failed to get feed: " + message);
        }

//        @Override
        public void handleException(Exception ex) {
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayMessage("Failed to get feed because of exception: " + ex.getMessage());
        }
    }
}
