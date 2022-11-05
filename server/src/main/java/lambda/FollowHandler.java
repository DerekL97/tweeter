package lambda;

// these are the imports for SDK v1

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import net.TweeterRemoteException;
import net.request.FollowRequest;
import net.request.FollowingRequest;
import net.response.FollowingResponse;
import net.response.Response;

import java.util.ArrayList;

import service.FollowService;

//import com.amazonaws.regions.Regions;

public class FollowHandler implements RequestHandler<FollowRequest, Response> {
    @Override
    public Response handleRequest(FollowRequest input, Context context) {
        FollowService service = new FollowService();
        return service.Follow(input.getFollowee(), input.getUser(), input.getAuthToken());
    }

//    public Response handleRequest(FollowRequest followRequest, Context context) throws TweeterRemoteException {
//        try{
//            FollowService followService = new FollowService();
//            boolean success = followService.Follow(followRequest.getFollowee(), followRequest.getUser(), followRequest.getAuthToken());
//            return new Response(success);
//        }
//        catch (Exception e){
//            throw new TweeterRemoteException(e.getMessage(), e.getClass().toString(), new ArrayList<>());
//        }
//
//
//    }
}
