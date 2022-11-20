package com.example.server.DynamoDBDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import dao.dynamodb.FollowDynamoDBDAO;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowDbDTODynamoDBDAO_test {
    FollowDynamoDBDAO followDAO;
    User follower;
    User followee;


    @BeforeEach
    public void setup(){
        followDAO = new FollowDynamoDBDAO();
        follower = new User("Bob", "Jones", "bobbilicious",  "fakeImageURL");
        followee = new User("Billy", "Carter", "billycool", "fakeImageURL2");
    }

    @Test
    public void unfollow_success(){
        assert (followDAO.unfollow(follower.getAlias(), followee.getAlias()));
    }

    @Test
    public void follow_success(){
        assert (followDAO.follow(follower, followee));
    }

    @Test
    public void getFollowersTest_success(){
        List<User> followers = followDAO.getFollowers(followee.getAlias());
        assert(followers != null);
        assert (followers.size() != 0);
        System.out.println(followers);
    }

    @Test
    public void getFolloweesTest_success(){
        List<User> followees = followDAO.getFollowees(follower.getAlias());
        assert (followees != null);
        assert(followees.size() != 0);
        System.out.println(followees);
    }

    @Test
    public void isFollowerTest_success(){
        assert (followDAO.isFollowing(follower.getAlias(), followee.getAlias()));
    }
}
