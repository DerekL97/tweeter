package dao.dynamodb;

import java.util.List;

import dao.FeedDAO;
import edu.byu.cs.tweeter.model.domain.Status;

public class FeedDynamoDBDAO extends DynamoDBDAO implements FeedDAO {
    @Override
    public List<Status> getPage(String receiverAlias, int limit, Status lastStatus) {
        return null;
    }

    @Override
    public void addStatus(String posterAlias, Status status) {

    }
}
