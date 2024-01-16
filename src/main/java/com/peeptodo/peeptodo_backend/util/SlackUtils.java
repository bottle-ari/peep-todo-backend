package com.peeptodo.peeptodo_backend.util;

import com.peeptodo.peeptodo_backend.config.SlackConfig;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

import java.io.IOException;

public class SlackUtils {
//    public static void main(String[] args){ //메인 메서드에서 호출위해 예외 처리
//        try {
//            messageToSlack();
//        } catch (IOException | SlackApiException e) {
//            e.printStackTrace();
//        }
//    }
    //	private static Slack slack = Slack.getInstance();
    //	private static String token = "";
    //	private static MethodsClient methods = slack.methods(token);

    public static void messageToSlack(String message) throws IOException, SlackApiException {
        Slack slack = Slack.getInstance();
        MethodsClient methods = slack.methods(SlackConfig.SLACK_TOKEN);

        // Build a request object
        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel(SlackConfig.SLACK_CHANNEL_FEEDBACK) // 채널명 or 채널 ID
                .text(message)
                .build();

        // Get a response as a Java object
        ChatPostMessageResponse response = methods.chatPostMessage(request);
        if (!response.isOk()) {
            System.err.println(response.getError());
            System.out.println("피드백 전송 실패");
        }
        //        System.out.println(response.getMessage());
    }

}
