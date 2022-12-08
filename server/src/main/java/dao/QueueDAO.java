package dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;

public interface QueueDAO {
    void putStatusInQueue(Status status);
    void putFeedBatchInQueue(List<String> followerAliases, Status status);
}
