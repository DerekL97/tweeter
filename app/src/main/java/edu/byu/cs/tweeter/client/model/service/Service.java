package edu.byu.cs.tweeter.client.model.service;

public class Service {

    public interface ServiceObserverInterface {
        void handleFailure(String message);
        void handleException(Exception ex);
    }
}
