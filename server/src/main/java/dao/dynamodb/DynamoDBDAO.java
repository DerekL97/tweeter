package dao.dynamodb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dao.dynamodb.DTO.DynamoDbDTO;
import dao.dynamodb.DTO.FollowDbDTO;
import edu.byu.cs.tweeter.model.domain.User;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

public abstract class DynamoDBDAO<T extends DynamoDbDTO, U> {
    DynamoDbTable<T> table = null;

    protected static DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
            .credentialsProvider(ProfileCredentialsProvider.create())
            .region(Region.US_WEST_1)
            .build();

    protected static DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoDbClient)
            .build();

    public boolean addRow(T row) {
        try {
            DynamoDbTable<T> table = createTable();
            table.putItem(row);

        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return true;
    }

    public boolean deleteRow(String partitionKey, String sortKey) {
        try {
            DynamoDbTable<T> table = getTable();
//            Follow follow = new Follow(exFollowerAlias, followeeAlias);
            Key key = Key.builder()
                    .partitionValue(partitionKey).sortValue(sortKey)
                    .build();
            table.deleteItem(key);
        }
        catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return true;
    }
    public boolean deleteRow(String partitionKey, int sortKey) {
        try {
            DynamoDbTable<T> table = getTable();
//            Follow follow = new Follow(exFollowerAlias, followeeAlias);
            Key key = Key.builder()
                    .partitionValue(partitionKey).sortValue(sortKey)
                    .build();
            table.deleteItem(key);
        }
        catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return true;
    }
    public List<U> query(String partitionKey, String sortKey){
        try{
            DynamoDbTable<T> mappedTable = getTable();
            QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder()
                    .partitionValue(partitionKey)
                    .sortValue(sortKey)
                    .build());

            // Get items in the table and write out the ID value.
            Iterator<T> results = mappedTable.query(queryConditional).items().iterator();
            List<U> result = new ArrayList<>();

            while (results.hasNext()) {
                T rec = results.next();
                result.add(getModelFromDTO(rec));
            }
            return result;

        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return new ArrayList<>();
    }
    public List<U> query(String partitionKey){
        try{
            DynamoDbTable<T> mappedTable = getTable();
            QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder()
                    .partitionValue(partitionKey)
                    .build());

            // Get items in the table and write out the ID value.
            Iterator<T> results = mappedTable.query(queryConditional).items().iterator();
            List<U> result = new ArrayList<>();

            while (results.hasNext()) {
                T rec = results.next();
                result.add(getModelFromDTO(rec));
            }
            return result;

        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return new ArrayList<>();
    }

    protected boolean updateItem(String PartitionKey, int SortKey, T newItem){

        try {

            DynamoDbTable<T> mappedTable = getTable();
            Key key = Key.builder()
                    .partitionValue(PartitionKey)
                    .build();

            // Get the item by using the key and update the email value.
            T Record = mappedTable.getItem(r->r.key(key));
            // update the Record
            Record = newItem;
            mappedTable.updateItem(Record);
            return true;

        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return false;
    }

    protected abstract U getModelFromDTO(T rec);

    protected DynamoDbTable<T> getTable(){
        if (table == null){
            table = createTable();
        }
        return table;
    }
    protected abstract DynamoDbTable<T> createTable();
}
