package dao.dynamodb;

import dao.AuthtokenDAO;
import dao.DAOFactory;
import dao.FeedDAO;
import dao.FollowDAO;
import dao.ImageDAO;
import dao.StoryDAO;
import dao.UserDAO;

public class DynamodbDAOFactory implements DAOFactory {

    @Override
    public AuthtokenDAO getAuthtokenDAO() {
        return new AuthTokenDynamoDBDAO();
    }

    @Override
    public FeedDAO getFeedDAO() {
        return new FeedDynamoDBDAO();
    }

    @Override
    public FollowDAO getFollowDAO() {
        return new FollowDynamoDBDAO();
    }

    @Override
    public ImageDAO getImageDAO() {
        return new ImageS3DAO();
    }

    @Override
    public StoryDAO getStoryDAO() {
        return new StoryDynamoDBDAO();
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDynamoDBDAO();
    }
}
