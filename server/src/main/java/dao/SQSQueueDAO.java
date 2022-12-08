package dao;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.databind.JsonSerializable;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.JsonSerializer;
import edu.byu.cs.tweeter.server.dto.StringListAndStatus;
import edu.byu.cs.tweeter.util.Pair;


public class SQSQueueDAO implements QueueDAO {

    private final String postStatusQueueURL = "https://sqs.us-east-1.amazonaws.com/970353619413/PostStatusQueue";
    private final String updateFeedsQueueURL = "https://sqs.us-east-1.amazonaws.com/970353619413/UpdateFeedsQueue";

    @Override
    public void putStatusInQueue(Status status) {
        String messageBody = JsonSerializer.serialize(status);

        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(postStatusQueueURL)
                .withMessageBody(messageBody);

        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);
        // I don't think we techincally need anything below
        String msgId = send_msg_result.getMessageId();
        System.out.println("Message ID: " + msgId);
    }

    @Override
    public void putFeedBatchInQueue(List<String> followerAliases, Status status) {
        StringListAndStatus pair = new StringListAndStatus(followerAliases, status);
        String messageBody = JsonSerializer.serialize(pair);

        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(updateFeedsQueueURL)
                .withMessageBody(messageBody);

        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);
        // Again, I don't think we need these two lines
        String msgId = send_msg_result.getMessageId();
        System.out.println("Message ID: " + msgId);
    }
}
