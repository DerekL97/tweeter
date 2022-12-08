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

import dao.DAOFactory;
import dao.FollowDAO;
import dao.FollowDummyDAO;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
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
        checkAuthToken(request.getAuthToken());
        //todo put in try catch
        List<User> moreUsers = daoFactory.getFollowDAO().getFollowees(request.getFollowerAlias(), request.getLastItem(), request.getLimit());
        List<User> check = daoFactory.getFollowDAO().getFollowees(request.getFollowerAlias(), moreUsers.get(moreUsers.size()-1), request.getLimit());
        boolean hasMorePages = !(check.size() == 0);
        return new FollowingResponse(true, moreUsers, hasMorePages);


    }


    /**
     * Returns an instance of {@link FollowDummyDAO}. Allows mocking of the FollowDAO class
     * for testing purposes. All usages of FollowDAO should get their FollowDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */


    public Response unFollow(String followee, String user, AuthToken authToken) {
//        return new Response(true, "Successfully unfollowed");
        checkAuthToken(authToken);
        boolean unfollowed = daoFactory.getFollowDAO().unfollow(user, followee);
        String message;
        if(unfollowed) message = "Successfully unfollowed";
        else message = "Failed to unfollow!";
        return new Response(unfollowed, message);
    }

    public Response Follow(User followee, User user, AuthToken authToken) {
        checkAuthToken(authToken);
        boolean followed = daoFactory.getFollowDAO().follow(user, followee);
        String message;
        if(followed) message = "Successfully Followed";
        else message = "Failed to Follow!";
        return new Response(followed, message);
    }

    public FollowersResponse getFollowers(FollowersRequest request) {
//        Pair data = FakeData.getInstance().getPageOfUsers(request.getLastItem(), request.getLimit(), request.getFollowee());
        checkAuthToken(request.getAuthToken());
        FollowDAO followDAO = daoFactory.getFollowDAO();
        List<User> followers = followDAO.getFollowers(request.getFollowerAlias());
        List<User> followers2 = followDAO.getFollowers(request.getFollowerAlias());
        boolean hasMoreFollowers = followers2.size() > 0;
        return new FollowersResponse(true, "Current Followers", followers, hasMoreFollowers);

    }

    public IsFollowerResponse isFollower(IsFollowerRequest input) {
        checkAuthToken(input.getAuthToken());
        boolean isFollower = daoFactory.getFollowDAO().isFollowing(input.getFollower().getAlias(), input.getFollowee().getAlias());
        return new IsFollowerResponse(true, isFollower);
    }

    public GetCountResponse getFollowingCount(Request input) {
        checkAuthToken(input.getAuthToken());
        int following = daoFactory.getFollowDAO().getFollowingCount(input.getRequester().getAlias());

        return new GetCountResponse(true, following);
    }

    public GetCountResponse getFollowersCount(Request input) {
        checkAuthToken(input.getAuthToken());
        int followers = daoFactory.getFollowDAO().getFollowerCount(input.getRequester().getAlias());
        return new GetCountResponse(true, followers);
    }
}
