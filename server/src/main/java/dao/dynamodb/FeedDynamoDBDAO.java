package dao.dynamodb;

import org.w3c.dom.CDATASection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dao.FeedDAO;
import dao.UserDAO;
import dao.dynamodb.DTO.FeedDbDTO;
import edu.byu.cs.tweeter.model.domain.Status;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class FeedDynamoDBDAO extends DynamoDBDAO<FeedDbDTO, Status> implements FeedDAO {
    public static final String TableName = "Feed";
    public static final String PartitionLabel = "RecieverAlias";
    public static final String SortKeyLabel = "TimeStamp";
//    public static final String IndexName = "user_alias-time_stamp-index";

    public int getEpochSecond(Status status){
        SimpleDateFormat df = new SimpleDateFormat("dow mon dd hh:mm:ss zzz yyyy");
        try {
            Date date = df.parse(status.getDate());
            return (int) (date.getTime()/1000);
        } catch (ParseException e) {
            e.printStackTrace();
            return (int) (System.currentTimeMillis() / 1000l); //todo stop just lying about what time
        }

    }
    @Override
    public List<Status> getPage(String receiverAlias, int limit, Status lastStatus) {
        FeedDbDTO startFeedDTO = new FeedDbDTO();
        startFeedDTO.setRecieverAlias(receiverAlias);
        startFeedDTO.setTimeStamp(getEpochSecond(lastStatus));
        startFeedDTO.setPost(lastStatus.getPost());
        startFeedDTO.setPosterAlias(lastStatus.getUser().getAlias());
        return paginatedQuery(receiverAlias, limit, startFeedDTO);
    }
    /*

     */
    @Override
    public void addStatus(String posterAlias, String posterImageURL, Status status) {
        FeedDbDTO feedDbDTO = new FeedDbDTO(status.getUser().getAlias(), getEpochSecond(status), status.getPost(), posterAlias, posterImageURL);
        addRow(feedDbDTO);
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
