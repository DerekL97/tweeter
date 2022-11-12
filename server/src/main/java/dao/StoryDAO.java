package dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;

public interface StoryDAO {
    List<Status> getPage(String recieverAlias, int limit, Status lastStatus);
    void addStatus(String posterAlias, Status status);
}
