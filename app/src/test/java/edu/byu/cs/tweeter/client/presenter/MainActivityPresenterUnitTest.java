package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.concurrent.ExecutionException;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainActivityPresenterUnitTest {
    private MainActivityPresenter.View mockView;
    private Cache mockCache;
//    private UserService mockUserService;
    private StatusService mockStatusService;
    private User mockUser;
    private MainActivityPresenter mainActivityPresenterSpy;

    @BeforeEach
    public void setup(){
        mockView = Mockito.mock(MainActivityPresenter.View.class);
        mockCache = Mockito.mock(Cache.class);
        mockStatusService = Mockito.mock(StatusService.class);
//        mockUserService =  Mockito.mock(UserService.class);
        mockUser = Mockito.mock(User.class);
        Cache.setInstance(mockCache);
        mainActivityPresenterSpy = Mockito.spy(new MainActivityPresenter(mockView, mockUser));
//        Mockito.doReturn(mockStatusService).when(mainActivityPresenterSpy).getStatusService();
        Mockito.when(mainActivityPresenterSpy.getStatusService()).thenReturn(mockStatusService);

    }
//    Instruct the View to display a "Posting Status..." message.
//    Create a Status object and call a Service to send it to the server.
//    Instruct the View to display one of the following messages telling the user the outcome of the post operation:
//            "Successfully Posted!"
//            "Failed to post status: <ERROR MESSAGE>"
//            "Failed to post status because of exception: <EXCEPTION MESSAGE>"
    public void testPostStatusSetup(){
//    Instruct the View to display a "Posting Status..." message.
//    Create a Status object and call a Service to send it to the server.
//    Instruct the View to display one of the following messages telling the user the outcome of the post operation:
    }
    @Test
    public void testPostStatus_success(){
        Answer<Void> answer = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                MainActivityPresenter.postStatusObserver observer = invocation.getArgument(2, MainActivityPresenter.postStatusObserver.class);
                observer.postedStatus();
                return null;
            }
        };
        Mockito.doAnswer(answer).when(mockStatusService).startStatusTask(Mockito.any(),
                Mockito.any(), Mockito.any());
        String post = "This is a Status";
        try {
            mainActivityPresenterSpy.postStatus(post);
            //Mockito.verify(mockView).PostInfoMessage("Posting Status. . . ");
            Status newStatus = new Status(post, mockCache.getCurrUser(),
                    mainActivityPresenterSpy.getFormattedDateTime(), mainActivityPresenterSpy.parseURLs(post),
                    mainActivityPresenterSpy.parseMentions(post));

            Mockito.verify(mockStatusService).startStatusTask(mockCache.getCurrUserAuthToken(),
                    newStatus, mainActivityPresenterSpy.getPostStatusObserver());
            Mockito.verify(mockView).postedStatus();
        }catch(Exception e){
            assert(false);
        }
    }
    @Test
    public void testPostStatus_failed(){
//        testPostStatusSetup();
        Answer<Void> answer = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                MainActivityPresenter.postStatusObserver observer = invocation.getArgument(2, MainActivityPresenter.postStatusObserver.class);
                observer.handleFailure("<ERROR MESSAGE>");
                return null;
            }
        };
        Mockito.doAnswer(answer).when(mockStatusService).startStatusTask(Mockito.any(),
                Mockito.any(), Mockito.any());
        String post = "This is a Status";
        try {
            mainActivityPresenterSpy.postStatus(post);
            //Mockito.verify(mockView).PostInfoMessage("Posting Status. . . ");
            Status newStatus = new Status(post, mockCache.getCurrUser(),
                    mainActivityPresenterSpy.getFormattedDateTime(), mainActivityPresenterSpy.parseURLs(post),
                    mainActivityPresenterSpy.parseMentions(post));

            Mockito.verify(mockStatusService).startStatusTask(mockCache.getCurrUserAuthToken(),
                    newStatus, mainActivityPresenterSpy.getPostStatusObserver());
            Mockito.verify(mockView).displayMessage("Failed to post status: <ERROR MESSAGE>");
        }catch(Exception e){
            assert(false);
        }
    }
    @Test
    public void testPostStatus_failedWithException(){
//        testPostStatusSetup();
        Answer<Void> answer = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                MainActivityPresenter.postStatusObserver observer = invocation.getArgument(2, MainActivityPresenter.postStatusObserver.class);
                try {
                    throw new Exception("<EXCEPTION MESSAGE>");
                }
                catch (Exception e) {
                    observer.handleException(e);
                }
                return null;
            }
        };
        Mockito.doAnswer(answer).when(mockStatusService).startStatusTask(Mockito.any(),
                Mockito.any(), Mockito.any());
        String post = "This is a Status";
        try {
            mainActivityPresenterSpy.postStatus(post);
            //Mockito.verify(mockView).PostInfoMessage("Posting Status. . . ");
            Status newStatus = new Status(post, mockCache.getCurrUser(),
                    mainActivityPresenterSpy.getFormattedDateTime(), mainActivityPresenterSpy.parseURLs(post),
                    mainActivityPresenterSpy.parseMentions(post));

            Mockito.verify(mockStatusService).startStatusTask(mockCache.getCurrUserAuthToken(),
                    newStatus, mainActivityPresenterSpy.getPostStatusObserver());
            Mockito.verify(mockView).displayMessage("Failed to post status because of exception: <EXCEPTION MESSAGE>");
        }catch(Exception e){
            assert(false);
        }
    }
}
