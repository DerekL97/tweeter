package dao.dynamodb.DTO;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

public class UserDbDTO extends DynamoDbDTO{
    private String userAlias;
    private String firstName;
    private String lastName;
    private String imageLink;
    private int numFollowers;
    private int numFollowees;
    private String salt;
    private String hashedPassword;

    public UserDbDTO() {
    }

    public UserDbDTO(String userAlias, String firstName, String lastName, String imageLink,
                     int numFollowers, int numFollowees, String salt, String hashedPassword) {
        this.userAlias = userAlias;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageLink = imageLink;
        this.numFollowers = numFollowers;
        this.numFollowees = numFollowees;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
    }

    @Override
    public String getPartitionKey() {
        return getUserAlias();
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("UserAlias")
    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    @DynamoDbAttribute("first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @DynamoDbAttribute("last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @DynamoDbAttribute("image_link")
    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    @DynamoDbAttribute("num_followers")
    public int getNumFollowers() {
        return numFollowers;
    }

    public void setNumFollowers(int numFollowers) {
        this.numFollowers = numFollowers;
    }

    @DynamoDbAttribute("num_followees")
    public int getNumFollowees() {
        return numFollowees;
    }

    public void setNumFollowees(int numFollowees) {
        this.numFollowees = numFollowees;
    }

    @DynamoDbAttribute("salt")
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @DynamoDbAttribute("hashed_password")
    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}
