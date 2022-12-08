package dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.dto.UserDTO;
import edu.byu.cs.tweeter.model.domain.User;

public class DynamoDBUserDAO implements UserDAO {
    private static final String TableName = "Users";

    private static final String AliasAttr = "alias";
    private static final String FirstNameAttr = "firstName";
    private static final String LastNameAttr = "lastName";
    private static final String PasswordAttr = "password";
    private static final String ImageURLAttr = "imageURL";
    private static final String FollowingCountAttr = "followingCount";
    private static final String FollowersCountAttr = "followersCount";

    // DynamoDB client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-east-1")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    @Override
    public int getFollowingCount(String follower) {
        Table table = dynamoDB.getTable(TableName);

        Item item = table.getItem(AliasAttr, follower);
        if (item == null) {
            return 0;
        }
        return item.getInt(FollowingCountAttr);
    }

    @Override
    public int getFollowersCount(String followee) {
        Table table = dynamoDB.getTable(TableName);

        Item item = table.getItem(AliasAttr, followee);
        if (item == null) {
            return 0;
        }
        return item.getInt(FollowersCountAttr);
    }

    @Override
    public User getUser(String userAlias) {
        Table table = dynamoDB.getTable(TableName);

        Item item = table.getItem(AliasAttr, userAlias);
        if (item == null) {
            return null;
        }
        else {
            return new User(item.getString(FirstNameAttr),
                    item.getString(LastNameAttr),
                    item.getString(AliasAttr),
                    item.getString(ImageURLAttr));
        }
    }

    @Override
    public User createUser(String userAlias, String firstName, String lastName, String imageURL, int password) {
        Table table = dynamoDB.getTable(TableName);

        Item item = new Item()
                .withPrimaryKey(AliasAttr, userAlias)
                .withString(FirstNameAttr, firstName)
                .withString(LastNameAttr, lastName)
                .withString(ImageURLAttr, imageURL)
                .withNumber(PasswordAttr, password)
                .withNumber(FollowingCountAttr, 0)
                .withNumber(FollowersCountAttr, 0);

        table.putItem(item);

        return new User(firstName, lastName, userAlias, imageURL);
    }

    @Override
    public int getPasswordHashCode(String userAlias) {
        Table table = dynamoDB.getTable(TableName);

        Item item = table.getItem(AliasAttr, userAlias);
        if (item == null) {
            return 0;
        }
        else {
            return item.getNumber("password").intValue();
        }
    }

    @Override
    public void updateFollowCounts(String followerAlias, String followeeAlias, Boolean followed) {
        Table table = dynamoDB.getTable(TableName);

        Map<String, String> attrFollowingNames = new HashMap<String, String>();
        attrFollowingNames.put("#flngcnt", FollowingCountAttr);

        Map<String, String> attrFollowersNames = new HashMap<String, String>();
        attrFollowersNames.put("#flrscnt", FollowersCountAttr);

        Map<String, Object> attrValues = new HashMap<String, Object>();
        attrValues.put(":val", 1);

        if (followed) {
            table.updateItem(AliasAttr, followerAlias,
                    "set #flngcnt = #flngcnt + :val", attrFollowingNames, attrValues);
            table.updateItem(AliasAttr, followeeAlias,
                    "set #flrscnt = #flrscnt + :val", attrFollowersNames, attrValues);
        } else {
            table.updateItem(AliasAttr, followerAlias,
                    "set #flngcnt = #flngcnt - :val", attrFollowingNames, attrValues);
            table.updateItem(AliasAttr, followeeAlias,
                    "set #flrscnt = #flrscnt - :val", attrFollowersNames, attrValues);
        }
    }

    @Override
    public ArrayList<User> getUsersFromAliases(List<String> aliases) {
        ArrayList<User> userList = new ArrayList();
        for (String alias : aliases) {
            userList.add(getUser(alias));
        }

        return userList;
    }

    @Override
    public void addUserBatch(List<UserDTO> users) {

        // Constructor for TableWriteItems takes the name of the table, which I have stored in TABLE_USER
        TableWriteItems items = new TableWriteItems(TableName);

        // Add each user into the TableWriteItems object
        for (UserDTO user : users) {
            Item item = new Item()
                    .withPrimaryKey("alias", user.getAlias())
                    .withString("firstName", user.getFirstName())
                    .withString("lastName", user.getLastName())
                    .withString("imageURL", user.getImageURL());
            items.addItemToPut(item);

            // 25 is the maximum number of items allowed in a single batch write.
            // Attempting to write more than 25 items will result in an exception being thrown
            if (items.getItemsToPut() != null && items.getItemsToPut().size() == 25) {
                loopBatchWrite(items);
                items = new TableWriteItems(TableName);
            }
        }

        // Write any leftover items
        if (items.getItemsToPut() != null && items.getItemsToPut().size() > 0) {
            loopBatchWrite(items);
        }
    }

    private void loopBatchWrite(TableWriteItems items) {

        // The 'dynamoDB' object is of type DynamoDB and is declared statically in this example
        BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(items);
        System.out.println("Wrote User Batch");

        // Check the outcome for items that didn't make it onto the table
        // If any were not added to the table, try again to write the batch
        while (outcome.getUnprocessedItems().size() > 0) {
            Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();
            outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);
            System.out.println("Wrote more Users");
        }
    }

}
