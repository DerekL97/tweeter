package dao;

import java.util.List;

public interface FollowDAO {
    void follow(String followerAlias, String followeeAlias);
    void unfollow(String exFollowerAlias, String followeeAlias);
    List<String> fetFollowers(String followeeAlias);
    List<String> getFollowees(String followerAlias);
    boolean isFollowing(String followerAlias, String followeeAlias);
}
