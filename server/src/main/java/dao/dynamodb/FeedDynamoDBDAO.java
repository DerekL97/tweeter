package dao.dynamodb;

import java.util.List;

import dao.FeedDAO;
import dao.dynamodb.DTO.DynamoDbDTO;
import edu.byu.cs.tweeter.model.domain.Status;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

public class FeedDynamoDBDAO extends DynamoDBDAO implements FeedDAO {
    @Override
    public List<Status> getPage(String receiverAlias, int limit, Status lastStatus) {
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
