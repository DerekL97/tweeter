package service;

import net.request.FollowersRequest;
import net.request.FollowingRequest;
import net.request.IsFollowerRequest;
import net.request.Request;
import net.response.FollowersResponse;
import net.response.FollowingResponse;
import net.response.GetCountResponse;
import net.response.IsFollowerResponse;
import net.response.Response;

import java.util.List;
import java.util.Random;

import dao.FollowDummyDAO;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;
import edu.byu.cs.tweeter.util.Pair;


/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowService extends Service {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {@link FollowDummyDAO} to
     * get the followees.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public FollowingResponse getFollowees(FollowingRequest request) {
        if(request.getFollowerAlias() == null) {
            throw new RuntimeException("[Bad Request] Request needs to have a follower alias");
        } else if(request.getLimit() <= 0) {
            throw new RuntimeException("[Bad Request] Request needs to have a positive limit");
        }
        return getFollowingDAO().getFollowees(request);
    }


    /**
     * Returns an instance of {@link FollowDummyDAO}. Allows mocking of the FollowDAO class
     * for testing purposes. All usages of FollowDAO should get their FollowDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    private FollowDummyDAO getFollowingDAO() {
        return new FollowDummyDAO();
    }

    public Response unFollow(String followee, String user, String authToken) {
        return new Response(true, "Successfully unfollowed");//todo make this actually do stuff with DAO and throw an exception if necessary
    }

    public Response Follow(String followee, String user, String authToken) {
        return new Response(true, "Successfully followed");//todo make this actually access database
    }

    public FollowersResponse getFollowers(FollowersRequest request) {
        Pair data = FakeData.getInstance().getPageOfUsers(request.getLastItem(), request.getLimit(), request.getFollowee());
        return new FollowersResponse(true, "Current Followers", (List<User>)data.getFirst(), (boolean) data.getSecond());
    }

    public IsFollowerResponse isFollower(IsFollowerRequest input) {
        return new IsFollowerResponse(true, new Random().nextInt() > 0);
    }

    public GetCountResponse getFollowingCount(Request input) {
        return new GetCountResponse(true, 20);
    }

    public GetCountResponse getFollowersCount(Request input) {
        return new GetCountResponse(true, 20);
    }
}
