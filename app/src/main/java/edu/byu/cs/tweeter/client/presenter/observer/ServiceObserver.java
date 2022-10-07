package edu.byu.cs.tweeter.client.presenter.observer;

public abstract class ServiceObserver {
    public void handleFailure(String message) {
        isLoading = false;
        view.setLoadingFooter(false);
        view.displayMessage("Failed to get feed: " + message);
    }

    public void handleException(Exception exception) {

    }
}
/*    public interface GetFeedObserver {
//        void handleMessage(Message msg);
//        void setLoading(boolean loading);
//        void setLoadingFooter(boolean footer);
        void addItems(List<Status> statuses, boolean hasMorePages, Status lastStatus);
        void displayErrorMessage(String message);
        void displayException(Exception ex);
    }

      public interface startUnfollowTaskObserver{
        void displayErrorMessage(String message);
        void displayException(Exception ex);
        void UnfollowReturn();
    }*/