package dao.dynamodb.DTO;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

public class StoryDbDTO extends DynamoDbDTO {
//    private String RecieverAlias;
    private int TimeStamp;
    private String Post;
    private String PosterAlias;
    private String ImageURL;

    public StoryDbDTO() {
    }
//    String recieverAlias,
    public StoryDbDTO(String posterAlias, int timeStamp, String post, String imageURL) {
//        RecieverAlias = recieverAlias;
        TimeStamp = timeStamp;
        Post = post;
        PosterAlias = posterAlias;
        ImageURL = imageURL;
    }

//    @DynamoDbAttribute("RecieverAlias")
//    @DynamoDbPartitionKey
//    public String getRecieverAlias() {
//        return RecieverAlias;
//    }
//
//    public void setRecieverAlias(String recieverAlias) {
//        RecieverAlias = recieverAlias;
//    }

    @DynamoDbAttribute("TimeStamp")
    @DynamoDbSortKey
    public int getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        TimeStamp = timeStamp;
    }

    @DynamoDbAttribute("post")
    public String getPost() {
        return Post;
    }

    public void setPost(String post) {
        Post = post;
    }

    @DynamoDbAttribute("SenderAlias")
    @DynamoDbPartitionKey
    public String getPosterAlias() {
        return PosterAlias;
    }

    public void setPosterAlias(String posterAlias) {
        PosterAlias = posterAlias;
    }

    @DynamoDbAttribute("poster_image_url")
    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    @Override
    public String getPartitionKey() {
        return getPosterAlias();
    }
}
