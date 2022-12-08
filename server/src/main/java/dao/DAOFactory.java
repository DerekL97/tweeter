package dao;

public interface DAOFactory {
    FollowDAO getFollowDAO();
    FeedDAO getFeedDAO();
    StoryDAO getStoryDAO();
    AuthTokenDAO getAuthTokenDAO();
    UserDAO getUserDAO();
    ImageDAO getImageDAO();
    QueueDAO getQueueDAO();
}
