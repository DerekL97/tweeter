package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.Service;

public class Presenter {

    protected View view;

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
            view.displayMessage("Failed to post status: " + message);
        }

//        @Override
        public void handleException(Exception ex) {
//            isLoading = false;
//            view.setLoadingFooter(false);
            view.displayMessage("Failed to post status because of exception: " + ex.getMessage());
        }
    }
}
