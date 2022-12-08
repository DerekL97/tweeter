package dao;


import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class DynamoDBAuthTokenDAO implements AuthTokenDAO {

    private static final String TableName = "AuthTokens";

    private static final String AuthTokenAttr = "authToken";
    private static final String UserAliasAttr = "userAlias";
    private static final String TimeStampAttr = "timeStamp";

    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    private static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    private static long generateTimeStamp() {
        return Instant.now().getEpochSecond();
    }

    // DynamoDB client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-1")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    private static boolean isNonEmptyString(String value) {
        return (value != null && value.length() > 0);
    }

    @Override
    public AuthToken createAuthToken(String userAlias) {
        Table table = dynamoDB.getTable(TableName);

        String token = generateNewToken();

        Item item = new Item()
                .withPrimaryKey(AuthTokenAttr, token)
                .withString(UserAliasAttr, userAlias)
                .withNumber(TimeStampAttr, generateTimeStamp());

        table.putItem(item);

        return new AuthToken(token);
    }

    @Override
    public boolean validAuthToken(AuthToken authToken) {
        Table table = dynamoDB.getTable(TableName);

        Item item = table.getItem(AuthTokenAttr, authToken.getToken());
        if ((item == null) || (generateTimeStamp() - item.getLong(TimeStampAttr) > 28800)) {
            cleanseAuthTokenTable();
            return false;
        }
        return true;
    }

    @Override
    public String getCurrUserAlias(AuthToken authToken) {
        Table table = dynamoDB.getTable(TableName);

        Item item = table.getItem(AuthTokenAttr, authToken.getToken());
        return item.getString(UserAliasAttr);
    }

    @Override
    public void deleteAuthToken(String token) {
        Table table = dynamoDB.getTable(TableName);
        table.deleteItem(AuthTokenAttr, token);
    }

    private void cleanseAuthTokenTable() {
        Table table = dynamoDB.getTable(TableName);

        Map<String, Object> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":val", (generateTimeStamp() - 28800));


        Map<String, String> expressionAttributeNames = new HashMap<>();
        expressionAttributeNames.put("#tmstmp", "timeStamp");

        ItemCollection<ScanOutcome> items = table.scan("#tmstmp < :val", // FilterExpression
                null, // ProjectionExpression
                expressionAttributeNames, // ExpressionAttributeNames
                expressionAttributeValues);

        TableWriteItems tableWriteItems = new TableWriteItems(TableName);
        int totalItems = 0;

        for (Item item : items) {
            if (totalItems < 25) {
                tableWriteItems.addHashOnlyPrimaryKeyToDelete("authToken", item.getString("authToken"));
            }
            totalItems++;
        }

        if (totalItems > 0) {
            dynamoDB.batchWriteItem(tableWriteItems);
        }
    }
}
