package dao.dynamodb;

import java.util.List;

import dao.StoryDAO;
import dao.dynamodb.DTO.DynamoDbDTO;
import edu.byu.cs.tweeter.model.domain.Status;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

public class StoryDynamoDBDAO extends DynamoDBDAO implements StoryDAO {
    @Override
    public List<Status> getPage(String recieverAlias, int limit, Status lastStatus) {
        return null;
    }

    @Override
    public void addStatus(String posterAlias, Status status) {

    }

    @Override
    protected Object getModelFromDTO(DynamoDbDTO rec) {
        return null;
    }

    @Override
    protected DynamoDbTable createTable() {
        return null;
    }
}
