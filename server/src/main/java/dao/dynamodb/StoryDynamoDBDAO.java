package dao.dynamodb;

import java.util.List;

import dao.StoryDAO;
import edu.byu.cs.tweeter.model.domain.Status;

public class StoryDynamoDBDAO extends DynamoDBDAO implements StoryDAO {
    @Override
    public List<Status> getPage(String recieverAlias, int limit, Status lastStatus) {
        return null;
    }

    @Override
    public void addStatus(String posterAlias, Status status) {

    }
}
