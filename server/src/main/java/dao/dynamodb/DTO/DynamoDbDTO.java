package dao.dynamodb.DTO;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
public abstract class DynamoDbDTO {
    public abstract String getPartitionKey();
    public String getSortKey(){
        return null;
    }
}
