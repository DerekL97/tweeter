package dao;

import java.util.List;

import dao.dto.UserDTO;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dto.UserDTO;

public interface UserDAO {
    int getFollowingCount(String follower);
    int getFollowersCount(String followee);
    User getUser(String userAlias);
    User createUser(String userAlias, String firstName, String lastName, String imageURL, int password);
    int getPasswordHashCode(String userAlias);
    void updateFollowCounts(String followerAlias, String followeeAlias, Boolean followed);
    List<User> getUsersFromAliases(List<String> aliases);
    void addUserBatch(List<UserDTO> users);
}
