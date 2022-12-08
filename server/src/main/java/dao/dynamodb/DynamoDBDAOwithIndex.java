package dao.dynamodb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.dynamodb.DTO.DynamoDbDTO;
import dao.dynamodb.DTO.FollowDbDTO;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

public abstract class DynamoDBDAOwithIndex<T extends DynamoDbDTO, U> extends DynamoDBDAO<T, U> {


    protected List<U> queryWithIndex(String newPartitionKey){
        try {
            //Create a DynamoDbTable object based on Follow.
            DynamoDbTable<T> table = getTable();

            DynamoDbIndex<T> secIndex = createIndex();
            AttributeValue attVal = AttributeValue.builder()
                    .s(newPartitionKey)
                    .build();

            // Create a QueryConditional object that's used in the query operation.
            QueryConditional queryConditional = QueryConditional
                    .keyEqualTo(Key.builder().partitionValue(attVal)
                            .build());

            // Get items in the table.
            SdkIterable<Page<T>> results = secIndex.query(QueryEnhancedRequest.builder()
                    .queryConditional(queryConditional)
                    .limit(300) //todo change limit?
                    .build());

            // Return the results
            List<U> followers = new ArrayList<>();
            results.forEach(page -> {
                List<T> allFollow = page.items();
                for (T myFollow: allFollow) {
                    followers.add(getModelFromDTOIndex(myFollow));
                }
            });
            return followers;

        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return new ArrayList<>();
    }

    private boolean isNonEmptyString(T value) {
        return (value != null);
    }

    protected List<T> queryWithPaginatedIndex(String newPartitionKey, T lastRow, int pageSize){
        DynamoDbIndex<T> index = getIndex();
        Key key = Key.builder()
                .partitionValue(lastRow.getPartitionKey())
                .build();

        QueryEnhancedRequest.Builder requestBuilder = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(key))
                // Unlike Tables, querying from an Index returns a PageIterable, so we want to just ask for
                // 1 page with pageSize items
                .limit(pageSize);

        if(isNonEmptyString(lastRow)) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(getPartitionLabel(), AttributeValue.builder().s(lastRow.getPartitionKey()).build());
            startKey.put(getSortLabel(), AttributeValue.builder().s(lastRow.getSortKey()).build());

            requestBuilder.exclusiveStartKey(startKey);
        }

        QueryEnhancedRequest request = requestBuilder.build();

        List<T> visits = new ArrayList<>();

        SdkIterable<Page<T>> results2 = index.query(request);
        PageIterable<T> pages = PageIterable.create(results2);
        // limit 1 page, with pageSize items
        pages.stream()
                .limit(1)
                .forEach(visitsPage -> visitsPage.items().forEach(v -> visits.add(v)));

        return visits;
    }

    protected abstract DynamoDbIndex<T> getIndex();

    protected abstract String getIndexName();


    protected abstract U getModelFromDTOIndex(T myFollow);
    protected abstract DynamoDbIndex<T> createIndex();

}
