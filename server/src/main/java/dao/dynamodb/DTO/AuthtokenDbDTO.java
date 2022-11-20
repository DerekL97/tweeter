package dao.dynamodb.DTO;

import dao.dynamodb.AuthTokenDynamoDBDAO;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class AuthtokenDbDTO extends DynamoDbDTO{
    private String Authtoken;
    private String UserAlias;
    private int time_stamp;

    public AuthtokenDbDTO(String authtoken, String userAlias, int time_stamp) {
        Authtoken = authtoken;
        UserAlias = userAlias;
        this.time_stamp = time_stamp;
    }

    public AuthtokenDbDTO() {
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("authtoken")
    public String getAuthtoken() {
        return Authtoken;
    }
    @Override
    public String getPartitionKey(){
        return getAuthtoken();
    }

    public void setAuthtoken(String authtoken) {
        Authtoken = authtoken;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = AuthTokenDynamoDBDAO.IndexName)
    @DynamoDbAttribute("user_alias")
    public String getUserAlias() {
        return UserAlias;
    }

    public void setUserAlias(String userAlias) {
        UserAlias = userAlias;
    }

    @DynamoDbSortKey
    @DynamoDbSecondarySortKey(indexNames = AuthTokenDynamoDBDAO.IndexName)
    @DynamoDbAttribute("time_stamp")
    public int getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(int time_stamp) {
        this.time_stamp = time_stamp;
    }
}
