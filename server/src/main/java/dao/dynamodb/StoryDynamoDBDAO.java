package dao.dynamodb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dao.StoryDAO;
import dao.dynamodb.DTO.DynamoDbDTO;
import dao.dynamodb.DTO.FeedDbDTO;
import dao.dynamodb.DTO.StoryDbDTO;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class StoryDynamoDBDAO extends DynamoDBDAOwithIndex<StoryDbDTO, Status> implements StoryDAO {
    private static final String TableName = "Story";
    private static final String PartitionKey = "SenderAlias";
    private static final String SortKey = "TimeStamp";
//    private static final String IndexName = "followeeAlias-followerAlias-index";
//    private static final String FollowerName = "follower_name";
//    private static final String FolloweeName = "followee_name";
    @Override
    public List<Status> getPage(String recieverAlias, int limit, Status lastStatus) {
        return ;
    }

    @Override
    public void addStatus(User posterAlias, Status status) {
        StoryDbDTO storyDbDTO = new StoryDbDTO(posterAlias.getAlias(), getEpochSecond(status), status.getPost(), posterAlias.getImageUrl());
        addRow(storyDbDTO);
    }

    @Override
    protected String getPartitionLabel() {
        return PartitionKey;
    }

    @Override
    protected String getSortLabel() {
        return SortKey;
    }

    @Override
    protected Status getModelFromDTO(StoryDbDTO rec) {
        User poster = new User();
        poster.setAlias(rec.getPosterAlias());
        poster.setImageUrl(rec.getImageURL());

        return new Status(rec.getPost(), poster, rec.getTimeStamp(), List<String> urls, List<String> mentions)
    }


    @Override
    protected DynamoDbTable createTable() {
        return enhancedClient.table(TableName, TableSchema.fromBean(StoryDbDTO.class));
    }

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
    public String getDateTime(int timeStamp){

    }

    @Override
    protected DynamoDbIndex<StoryDbDTO> getIndex() {
        return null;
    }

    @Override
    protected String getIndexName() {
        return null;
    }

    @Override
    protected Status getModelFromDTOIndex(StoryDbDTO myFollow) {
        return null;
    }

    @Override
    protected DynamoDbIndex<StoryDbDTO> createIndex() {
        return null;
    }
}
