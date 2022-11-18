package dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public interface FollowDAO {
    boolean follow(User follower, User followee);
    boolean unfollow(String exFollowerAlias, String followeeAlias);
    List<User> getFollowers(String followeeAlias);
    List<User> getFollowees(String followerAlias);
    boolean isFollowing(String followerAlias, String followeeAlias);
}
