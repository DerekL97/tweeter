package dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;

public interface StoryDAO {
    Boolean postStatus(Status status);
    Pair<List<Status>, Boolean> getStory(User targetUser, int limit, Status lastStatus);
}
