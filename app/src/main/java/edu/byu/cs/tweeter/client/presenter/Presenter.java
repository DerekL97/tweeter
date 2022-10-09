package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.LogInOutService;
import edu.byu.cs.tweeter.client.model.service.Service;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public class Presenter {

    private View view;
//    protected UserService userService;
//    protected static final int PAGE_SIZE = 10;
//    protected boolean hasMorePages;
//    protected boolean isLoading = false;


    public Presenter(Presenter.View view){
        this.view = view;
    }

    public interface View{
        void displayMessage(String message);
        //

    }

    public abstract class ServiceObserver implements Service.ServiceObserverInterface {
//        @Override
        public void handleFailure(String message) {
//            isLoading = false;
//            view.setLoadingFooter(false);
//            view.displayMessage("Failed to get feed: " + message);
        }

//        @Override
        public void handleException(Exception ex) {
//            isLoading = false;
//            view.setLoadingFooter(false);
//            view.displayMessage("Failed to get feed because of exception: " + ex.getMessage());
        }
    }
}
