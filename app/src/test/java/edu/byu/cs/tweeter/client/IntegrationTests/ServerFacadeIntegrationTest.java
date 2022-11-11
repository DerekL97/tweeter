package edu.byu.cs.tweeter.client.IntegrationTests;
import net.TweeterRemoteException;
import net.request.FollowersRequest;
import net.request.GetCountRequest;
import net.request.RegisterRequest;
import net.response.FollowersResponse;
import net.response.GetCountResponse;
import net.response.RegisterResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.JsonSerializer;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;


public class ServerFacadeIntegrationTest {
    ServerFacade serverFacade;
    AuthToken authToken;

    @BeforeEach
    public void setup(){
        serverFacade = Mockito.spy(ServerFacade.class);
        authToken = Mockito.spy(AuthToken.class);
    }
    @Test
    public void testRegister_success(){
        RegisterRequest request = new RegisterRequest("Allen", "Anderson", "FakeImage", "@allen", "exPassword");
        request = Mockito.spy(request);
        RegisterResponse response = null;
        try{
            response = serverFacade.register(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            assert(false);
        }
        assert(response != null);
        assert(JsonSerializer.serialize(response).equals("{\"user\":{\"firstName\":\"Allen\",\"lastName\":\"Anderson\",\"alias\":\"@allen\",\"imageUrl\":\"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"},\"authToken\":{},\"success\":false}"));
    }

    @Test
    public void testGetFollowers_success(){
        User user = new User("Name", "Last", "fakeImage");
        FollowersRequest request = new FollowersRequest(authToken, "@allen", 10, null, user);
        FollowersResponse response = null;
        try{
            response = serverFacade.getFollowers(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            assert(false);
        }
        assert(response != null);
        assert(JsonSerializer.serialize(response).equals("{\"page\":[{\"firstName\":\"Allen\",\"lastName\":\"Anderson\",\"alias\":\"@allen\",\"imageUrl\":\"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"},{\"firstName\":\"Amy\",\"lastName\":\"Ames\",\"alias\":\"@amy\",\"imageUrl\":\"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png\"},{\"firstName\":\"Bob\",\"lastName\":\"Bobson\",\"alias\":\"@bob\",\"imageUrl\":\"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"},{\"firstName\":\"Bonnie\",\"lastName\":\"Beatty\",\"alias\":\"@bonnie\",\"imageUrl\":\"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png\"},{\"firstName\":\"Chris\",\"lastName\":\"Colston\",\"alias\":\"@chris\",\"imageUrl\":\"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"},{\"firstName\":\"Cindy\",\"lastName\":\"Coats\",\"alias\":\"@cindy\",\"imageUrl\":\"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png\"},{\"firstName\":\"Dan\",\"lastName\":\"Donaldson\",\"alias\":\"@dan\",\"imageUrl\":\"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"},{\"firstName\":\"Dee\",\"lastName\":\"Dempsey\",\"alias\":\"@dee\",\"imageUrl\":\"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png\"},{\"firstName\":\"Elliott\",\"lastName\":\"Enderson\",\"alias\":\"@elliott\",\"imageUrl\":\"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"},{\"firstName\":\"Elizabeth\",\"lastName\":\"Engle\",\"alias\":\"@elizabeth\",\"imageUrl\":\"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png\"}],\"hasMorePages\":true,\"success\":true,\"message\":\"Current Followers\"}"));
    }

    @Test
    public void testGetFollowingCount_success(){
        GetCountRequest request = new GetCountRequest();
        GetCountResponse response = null;
        try{
            response = serverFacade.getFollowingCount(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            assert (false);
        }
        assert(response != null);
        assert (JsonSerializer.serialize(response).equals("{\"count\":20,\"success\":true}"));
    }
}
