package dao.dynamodb.DTO;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@DynamoDbBean
public class Follow {
    private String followerAlias;
    private String followeeAlias;

    private String follower_first_name;
    private String follower_last_name;
    private String follower_image_url;

    private String followee_first_name;
    private String followee_last_name;
    private String followee_image_url;

    private static final String IndexName = "followeeAlias-followerAlias-index";

    public Follow(String followerAlias, String followeeAlias, String follower_first_name,
                  String follower_last_name, String follower_image_url, String followee_first_name,
                  String followee_last_name, String followee_image_url) {
        this.followerAlias = followerAlias;
        this.followeeAlias = followeeAlias;
        this.follower_first_name = follower_first_name;
        this.follower_last_name = follower_last_name;
        this.follower_image_url = follower_image_url;
        this.followee_first_name = followee_first_name;
        this.followee_last_name = followee_last_name;
        this.followee_image_url = followee_image_url;
    }

    public Follow(String followerAlias, String followeeAlias) {
        this.followerAlias = followerAlias;
        this.followeeAlias = followeeAlias;
        this.follower_first_name = null;
        this.follower_last_name = null;
        this.follower_image_url = null;
        this.followee_first_name = null;
        this.followee_last_name = null;
        this.followee_image_url = null;
    }

    public Follow() {
    }
//    private int visit_count;

    @DynamoDbPartitionKey
    @DynamoDbSecondarySortKey(indexNames = {IndexName})
    public String getFollowerAlias() {
        return followerAlias;
    }

    public void setFollowerAlias(String follower_alias) {
        this.followerAlias = follower_alias;
    }

    @DynamoDbSortKey
    @DynamoDbSecondaryPartitionKey(indexNames = {IndexName})
    public String getFolloweeAlias() {
        return followeeAlias;
    }

    public void setFolloweeAlias(String followeeAlias) {
        this.followeeAlias = followeeAlias;
    }

    public String getFollower_first_name() {
        return follower_first_name;
    }

    public void setFollower_first_name(String follower_first_name) {
        this.follower_first_name = follower_first_name;
    }

    public String getFollower_last_name() {
        return follower_last_name;
    }

    public void setFollower_last_name(String follower_last_name) {
        this.follower_last_name = follower_last_name;
    }

    public String getFollower_image_url() {
        return follower_image_url;
    }

    public void setFollower_image_url(String follower_image_url) {
        this.follower_image_url = follower_image_url;
    }

    public String getFollowee_first_name() {
        return followee_first_name;
    }

    public void setFollowee_first_name(String followee_first_name) {
        this.followee_first_name = followee_first_name;
    }

    public String getFollowee_last_name() {
        return followee_last_name;
    }

    public void setFollowee_last_name(String followee_last_name) {
        this.followee_last_name = followee_last_name;
    }

    public String getFollowee_image_url() {
        return followee_image_url;
    }

    public void setFollowee_image_url(String followee_image_url) {
        this.followee_image_url = followee_image_url;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "visitor='" + followerAlias + '\'' +
                ", location='" + followeeAlias + '\'' +
                ", follower_name=" + follower_first_name + '\'' +
                ", followee_name=" + followee_last_name +
                '}';
    }


}
