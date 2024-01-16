package com.peeptodo.peeptodo_backend.config;

public class SlackConfig {
    public static final String SLACK_CHANNEL_FEEDBACK = "C06DMHLATST";
    public static final String SLACK_TOKEN = System.getenv("SLACK_FEEDBACK_TOKEN");
}
