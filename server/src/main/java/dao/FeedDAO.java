package dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.util.Pair;

public interface FeedDAO {
    Boolean postStatus(Status status, List<String> followerAliases);
    Pair<List<Status>, Boolean> getFeed(String targetUserAlias, int limit, Status lastStatus);
}
