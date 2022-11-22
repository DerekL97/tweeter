package dao.dynamodb;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import dao.AuthtokenDAO;
import dao.dynamodb.DTO.AuthtokenDbDTO;
import dao.dynamodb.DTO.FollowDbDTO;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class AuthTokenDynamoDBDAO extends DynamoDBDAOwithIndex<AuthtokenDbDTO, AuthToken> implements AuthtokenDAO  {
    private static final String TableName = "authToken";
    private static final String PartitionKey = "authtoken";
    private static final String SortKey = "time_stamp";
    public static final String IndexName = "user_alias-time_stamp-index";
    private static final String IndexParitionKey = "user_alias";
    private static final String IndexSortKey = "time_stamp";
    /*  number of seconds an authtoken will be considered valid */
    private static final int ExpireAfter = 14400;

    @Override
    protected String getPartitionLabel() {
        return null;
    }

    @Override
    protected String getSortLabel() {
        return null;
    }

    @Override
    protected AuthToken getModelFromDTO(AuthtokenDbDTO rec) {
        return new AuthToken(rec.getAuthtoken(), rec.getUserAlias(), rec.getTime_stamp());
    }


    @Override
    protected DynamoDbTable createTable() {
        return enhancedClient.table(TableName, TableSchema.fromBean(AuthtokenDbDTO.class));
    }



    @Override
    public boolean addAuthtoken(String authToken, String userAlias) {
        return addAuthtoken(new AuthtokenDbDTO(authToken, userAlias, getCurrentTimeStamp()));
    }
    public boolean addAuthtoken(AuthtokenDbDTO authtokenDbDTO){
        return addRow(authtokenDbDTO);
    }

    @Override
    public boolean clearExpiredTokens(String userAlias) {
        List<AuthToken> authList = queryWithIndex(userAlias);
        int timestamp = getCurrentTimeStamp();
        AuthToken token = authList.get(0);
        if(token.getDatetime() < timestamp-ExpireAfter){
            deleteRow(token.getToken(), token.getDatetime());
        }
        for(int i = 1; i < authList.size(); i++){
            token = authList.get(i);
            deleteRow(token.getToken(), token.getDatetime());
        }
        return true;
    }

    @Override
    public boolean updateExpireTime(AuthToken authToken) {
        boolean added = addAuthtoken(authToken.getToken(), authToken.getUserAlias());
        return added && clearExpiredTokens(authToken.getUserAlias());
    }

    @Override
    public String getUserAlias(String authToken) {
        List<AuthToken> tokens = query(authToken);
        return tokens.get(0).getUserAlias();
    }

    @Override
    public boolean isAuthorized(String authtoken, String userAlias) {
        clearExpiredTokens(userAlias);
        List<AuthToken> list = query(authtoken);
        return list.size() > 0;
    }


    @Override
    protected AuthToken getModelFromDTOIndex(AuthtokenDbDTO myFollow) {
        return new AuthToken(myFollow.getAuthtoken(), myFollow.getUserAlias(), myFollow.getTime_stamp());
    }

    @Override
    protected DynamoDbIndex<AuthtokenDbDTO> createIndex() {
        return enhancedClient.table(TableName, TableSchema.fromBean(AuthtokenDbDTO.class)) .index(IndexName);
    }

    public static String generateAuthToken(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
    public static int getCurrentTimeStamp(){
        return (int) (new Date().getTime()/1000);
    }
}
