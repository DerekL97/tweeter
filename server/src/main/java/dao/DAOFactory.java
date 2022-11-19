package dao;

public interface DAOFactory {
    AuthtokenDAO getAuthtokenDAO();
    FeedDAO getFeedDAO();
    FollowDAO getFollowDAO();
    ImageDAO getImageDAO();
    StoryDAO getStoryDAO();
    UserDAO getUserDAO();
}
