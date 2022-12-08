package dao.dynamodb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dao.dynamodb.DTO.DynamoDbDTO;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
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

    private static boolean isNonEmptyString(String value) {
        return (value != null && value.length() > 0);
    }

    protected List<U> paginatedQuery(String partitionKey, int pageSize, T lastRow){
        DynamoDbTable<T> table = getTable();
        Key key = Key.builder()
                .partitionValue(partitionKey)
                .build();

        QueryEnhancedRequest.Builder requestBuilder = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(key));
        // If you use iterators, it auto-fetches next page always, so instead limit the stream below
        //.limit(5);

        if(lastRow != null) {
            // Build up the Exclusive Start Key (telling DynamoDB where you left off reading items)
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(getPartitionLabel(), AttributeValue.builder().s(lastRow.getPartitionKey()).build());
            startKey.put(getSortLabel(), AttributeValue.builder().n(lastRow.getSortKeyInt() + "").build());

            requestBuilder.exclusiveStartKey(startKey);
        }

        QueryEnhancedRequest request = requestBuilder.build();

        List<T> list = table.query(request)
                .items()
                .stream()
                .limit(pageSize)
                .collect(Collectors.toList());
        List<U> returnlist = new ArrayList<>();
        for(T item : list){
            returnlist.add(getModelFromDTO(item));
        }
        return returnlist;
        /*
         * Alternative Implementation 1 of the line above, using forEach
         */
        //List<Visit> visits = new ArrayList<>();
        //table.query(request).items().stream().limit(pageSize).forEach(v -> visits.add(v));
        //return visits;

        /*
         * Alternative Implementation 2 of the line above, using a for loop
         */
        //List<Visit> visits = new ArrayList<>();
        //for(Visit visit : table.query(request).items()) {
        //    // stop iteration if we've reached the number of items asked for
        //    if(visits.size() >= pageSize)
        //        break;
        //    visits.add(visit);
        //}
        //return visits;

        /*
         * Alternative Implementation 3 of the line above, using a while loop
         */
        //List<Visit> visits = new ArrayList<>();
        //Iterator<Visit> it = table.query(request).items().iterator();
        //// while there are elements to iterate over, and we haven't reached the number of items asked for
        //while(it.hasNext() && visits.size() < pageSize){
        //    Visit visit = it.next();
        //    visits.add(visit);
        //}
        //return visits;

    }

    protected abstract String getPartitionLabel();
    protected abstract String getSortLabel();

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
//    protected abstract T getDTOFromModel(U Model);

    protected DynamoDbTable<T> getTable(){
        if (table == null){
            table = createTable();
        }
        return table;
    }
    protected abstract DynamoDbTable<T> createTable();
}
