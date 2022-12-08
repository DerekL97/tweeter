package dao.dynamodb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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
        StoryDbDTO storyDbDTO = new StoryDbDTO();
        storyDbDTO.setPost(lastStatus.getPost());
        storyDbDTO.setPosterAlias(lastStatus.getUser().getAlias());
        storyDbDTO.setTimeStamp(getEpochSecond(lastStatus));
        storyDbDTO.setImageURL(lastStatus.getUser().getImageUrl());
        return paginatedQuery(recieverAlias, limit, new StoryDbDTO());
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

    public List<String> parseURLs(String post) {
        List<String> containedUrls = new ArrayList<>();
        for (String word : post.split("\\s")) {
            if (word.startsWith("http://") || word.startsWith("https://")) {

                int index = findUrlEndIndex(word);

                word = word.substring(0, index);

                containedUrls.add(word);
            }
        }

        return containedUrls;
    }
    public int findUrlEndIndex(String word) {
        if (word.contains(".com")) {
            int index = word.indexOf(".com");
            index += 4;
            return index;
        } else if (word.contains(".org")) {
            int index = word.indexOf(".org");
            index += 4;
            return index;
        } else if (word.contains(".edu")) {
            int index = word.indexOf(".edu");
            index += 4;
            return index;
        } else if (word.contains(".net")) {
            int index = word.indexOf(".net");
            index += 4;
            return index;
        } else if (word.contains(".mil")) {
            int index = word.indexOf(".mil");
            index += 4;
            return index;
        } else {
            return word.length();
        }
    }
    public List<String> parseMentions(String post) {//todo move this?
        List<String> containedMentions = new ArrayList<>();

        for (String word : post.split("\\s")) {
            if (word.startsWith("@")) {
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                word = "@".concat(word);

                containedMentions.add(word);
            }
        }

        return containedMentions;
    }
    @Override
    protected Status getModelFromDTO(StoryDbDTO rec) {
        User poster = new User();
        poster.setAlias(rec.getPosterAlias());
        poster.setImageUrl(rec.getImageURL());
        List<String> urls = parseURLs(rec.getPost());
        List<String> mentions = parseMentions(rec.getPost());
        return new Status(rec.getPost(), poster, getDateTime(rec.getTimeStamp()),  urls,  mentions);
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
        Date date = new Date(timeStamp* 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE,MMMM d,yyyy h:mm,a", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate = sdf.format(date);
        return formattedDate;
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
