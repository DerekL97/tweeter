package edu.byu.cs.tweeter.client.model.net;

import net.TweeterRemoteException;

import java.util.List;

public class TweeterServerException extends TweeterRemoteException {


    public TweeterServerException(String message, String remoteExceptionType, List<String> remoteStakeTrace) {
        super(message, remoteExceptionType, remoteStakeTrace);
    }
}
