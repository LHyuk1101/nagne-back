package com.nagne.global.logger;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import net.minidev.json.JSONObject;

public class CustomDiscordAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {
  
  private String webhookUri;
  private Layout<ILoggingEvent> layout;
  
  @Override
  protected void append(ILoggingEvent eventObject) {
    if (!isStarted()) {
      return;
    }
    
    String message = layout.doLayout(eventObject);
    sendMessageToDiscord(message, eventObject);
  }
  
  private void sendMessageToDiscord(String message, ILoggingEvent event) {
    try {
      URL url = new URL(webhookUri);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setDoOutput(true);
      
      JSONObject json = new JSONObject();
      json.put("content", formatMessage(message, event));
      
      try (OutputStream os = connection.getOutputStream()) {
        byte[] input = json.toString().getBytes(StandardCharsets.UTF_8);
        os.write(input, 0, input.length);
      }
      
      int responseCode = connection.getResponseCode();
      if (responseCode != HttpURLConnection.HTTP_OK
        && responseCode != HttpURLConnection.HTTP_NO_CONTENT) {
        addError("HTTP error: " + responseCode);
      }
    } catch (IOException e) {
      addError("Error sending log message to Discord", e);
    }
  }
  
  private String formatMessage(String message, ILoggingEvent event) {
    StringBuilder sb = new StringBuilder();
    sb.append("**").append(event.getLevel()).append("** - ");
    sb.append(event.getLoggerName()).append("\n");
    sb.append("Thread: ").append(event.getThreadName()).append("\n");
    sb.append("```\n").append(message).append("\n```");
    
    if (event.getThrowableProxy() != null) {
      sb.append("\nException: ").append(event.getThrowableProxy().getClassName())
        .append(" - ").append(event.getThrowableProxy().getMessage());
    }
    
    return sb.toString();
  }
  
  public void setWebhookUri(String webhookUri) {
    this.webhookUri = webhookUri;
  }
  
  public void setLayout(Layout<ILoggingEvent> layout) {
    this.layout = layout;
  }
  
  @Override
  public void start() {
    if (this.webhookUri == null) {
      addError("No webhookUri set for the appender named [" + name + "].");
      return;
    }
    if (this.layout == null) {
      addError("No layout set for the appender named [" + name + "].");
      return;
    }
    super.start();
  }
}