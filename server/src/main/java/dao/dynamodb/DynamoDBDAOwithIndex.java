package dao.dynamodb;

import java.util.ArrayList;
import java.util.List;

import dao.dynamodb.DTO.DynamoDbDTO;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
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

    protected abstract U getModelFromDTOIndex(T myFollow);
    protected abstract DynamoDbIndex<T> createIndex();

}
