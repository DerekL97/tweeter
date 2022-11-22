package service;

import dao.DAOFactory;
import dao.dynamodb.DynamodbDAOFactory;
import edu.byu.cs.tweeter.model.domain.AuthToken;

public abstract class Service {
    protected DAOFactory daoFactory = new DynamodbDAOFactory();

    protected boolean checkAuthToken(AuthToken token){
        daoFactory.getAuthtokenDAO().
    }
}
