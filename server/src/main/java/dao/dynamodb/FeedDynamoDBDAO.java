package dao.dynamodb;

import android.provider.Telephony;

import java.util.List;

import dao.FeedDAO;
import dao.ImageDAO;
import dao.dynamodb.DTO.AuthtokenDbDTO;
import dao.dynamodb.DTO.DynamoDbDTO;
import dao.dynamodb.DTO.FeedDbDTO;
import edu.byu.cs.tweeter.model.domain.Status;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class FeedDynamoDBDAO extends DynamoDBDAO<FeedDbDTO, Status> implements FeedDAO {
    public static final String TableName = "Feed";
    public static final String PartitionLabel = "RecieverAlias";
    public static final String SortKeyLabel = "TimeStamp";
//    public static final String IndexName = "user_alias-time_stamp-index";

    @Override
    public List<Status> getPage(String receiverAlias, int limit, Status lastStatus, String imageUrl) {
        return paginatedQuery(receiverAlias, limit, new FeedDbDTO(receiverAlias, lastStatus.getDate(), lastStatus.getPost(), lastStatus.getUser().getAlias(), imageUrl));//todo pagify
    }
    /*

     */
    @Override
    public void addStatus(String posterAlias, Status status) {

    }

    @Override
    protected String getPartitionLabel() {
        return PartitionLabel;
    }

    @Override
    protected String getSortLabel() {
        return SortKeyLabel;
    }

    @Override
    protected Status getModelFromDTO(FeedDbDTO rec) {
        return null;
    }

    @Override
    protected DynamoDbTable createTable() {
        return enhancedClient.table(TableName, TableSchema.fromBean(FeedDbDTO.class));
    }
}
