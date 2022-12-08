package dao;

public class DynamoDBDAOFactory implements DAOFactory {
    @Override
    public FollowDAO getFollowDAO() {
        return new DynamoDBFollowDAO();
    }

    @Override
    public FeedDAO getFeedDAO() {
        return new DynamoDBFeedDAO();
    }

    @Override
    public StoryDAO getStoryDAO() {
        return new DynamoDBStoryDAO();
    }

    @Override
    public AuthTokenDAO getAuthTokenDAO() {
        return new DynamoDBAuthTokenDAO();
    }

    @Override
    public UserDAO getUserDAO() {
        return new DynamoDBUserDAO();
    }

    @Override
    public ImageDAO getImageDAO() {
        return new S3ImageDAO();
    }

    @Override
    public QueueDAO getQueueDAO() {
        return new SQSQueueDAO();
    }
}
