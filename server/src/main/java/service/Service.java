package service;

import dao.DAOFactory;
import dao.DynamoDBDAOFactory;
import dao.dynamodb.DynamodbDAOFactory;
import edu.byu.cs.tweeter.model.domain.AuthToken;

public abstract class Service {
    protected DAOFactory daoFactory = new DynamoDBDAOFactory();

    protected boolean checkAuthToken(AuthToken token) throws RuntimeException{
        if (daoFactory.getAuthtokenDAO().isAuthorized(token.getToken(), token.getUserAlias())){
            return true;
        }
        else {
            throw new RuntimeException("[Bad Request] Request AuthToken is invalid]");
        }
    }
}
