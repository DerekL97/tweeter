package dao;

import edu.byu.cs.tweeter.model.domain.User;

public interface UserDAO {
    User getUser(String userAlias);
    void addUser(User newUser, String salt, String hashedPassword);
    int getFollowCount(String userAlias);
    void incrementFollowerCount(int newFollowers);
    int getFolloweeCount(String userAlias);
    void incrementFolloweeCount(int newFollowees);
}
