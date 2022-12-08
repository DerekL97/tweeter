package dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public interface StoryDAO {
    List<Status> getPage(String recieverAlias, int limit, Status lastStatus);
    void addStatus(User posterAlias, Status status);
}
