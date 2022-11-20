package com.example.server.DynamoDBDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.dynamodb.AuthTokenDynamoDBDAO;
import dao.dynamodb.DTO.AuthtokenDbDTO;
import edu.byu.cs.tweeter.model.domain.AuthToken;

public class AuthTokenDynamoDbDAO_tests {
    AuthToken authToken01;
    AuthtokenDbDTO authtokenDbDTO1;
    AuthtokenDbDTO expiredToken;
    AuthTokenDynamoDBDAO dao;

    @BeforeEach
    public void setup(){
        int time = dao.getCurrentTimeStamp();
        authtokenDbDTO1 = new AuthtokenDbDTO("FAKEAUTHTOKEN", "bobbilicious", time);
        authToken01 = new AuthToken("FAKEAUTHTOKEN", "bobbilicious", time);
        expiredToken = new AuthtokenDbDTO("FAKEAUTHTOKEN2", "billycool", 500);
        dao = new AuthTokenDynamoDBDAO();
    }

    @Test
    public void addAuthtokenTest_success(){
        assert(dao.addAuthtoken(authtokenDbDTO1.getAuthtoken(), authtokenDbDTO1.getUserAlias()));
    }

    @Test
    public void clearExpiredTokensTest_success(){
        assert(dao.addAuthtoken(expiredToken));
        assert(dao.clearExpiredTokens(expiredToken.getUserAlias()));
    }

    @Test
    public void updateExpireTimeTest(){
        assert(dao.updateExpireTime(authToken01));
    }

    @Test
    public void getUserAliasTest_success(){
        assert(authToken01.getUserAlias().equals(dao.getUserAlias(authToken01.getToken())));
    }
}
