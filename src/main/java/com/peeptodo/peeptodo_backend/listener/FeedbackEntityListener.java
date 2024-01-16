package com.peeptodo.peeptodo_backend.listener;

import com.peeptodo.peeptodo_backend.domain.FeedBack;
import com.peeptodo.peeptodo_backend.repository.FeedBackRepository;
import com.peeptodo.peeptodo_backend.util.SlackUtils;
import jakarta.persistence.PrePersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;


public class FeedbackEntityListener {
    /**
     * 피드백 생성 -> Slack 알림
     */

    @Lazy
    @Autowired
    private FeedBackRepository feedbackRepository;

    @PrePersist
    public void prePersist(FeedBack feedBack) {
        try {
            String slackMessage = "";
            if (feedBack.getDateTime() != null) {
                slackMessage += feedBack.getDateTime()+"\n";
            }
            slackMessage += feedBack.getContents();
            if (slackMessage.isBlank()) {
                slackMessage = "Empty Feedback Send";
            }
            SlackUtils.messageToSlack(slackMessage);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("피드백 슬랙 전송 예외");
        }
    }
}