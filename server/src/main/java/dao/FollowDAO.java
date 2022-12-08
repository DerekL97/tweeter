package dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.model.net.response.FollowersCountResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingCountResponse;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.UnfollowResponse;
import edu.byu.cs.tweeter.util.Pair;

public interface FollowDAO {
    boolean isFollower(String followerAlias, String followeeAlias);
    boolean follow(String followerAlias, String followeeAlias);
    boolean unfollow(String followerAlias, String followeeAlias);
    Pair<List<String>, Boolean> getFollowing(String targetUserAlias, int limit, String lastItemAlias);
    Pair<List<String>, Boolean> getFollowers(String targetUserAlias, int limit, String lastItemAlias);
    List<String> getAllFollowers(String targetUserAlias);
    void addFollowersBatch(List<String> followerAliases, String targetAlias);
}
