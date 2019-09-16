package web;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;
import java.util.Objects;
import logging.DateHandler;
import logging.Event;

public class EventRequest implements Event {
  int transactionId;
  @JsonDeserialize(using = DateHandler.class)
  Date timestamp;
  String reportingService;
  String category;
  String requestor;
  String request;
  String response;

  @Override
  public int getTransactionId() {
    return transactionId;
  }

  @Override
  public void setTransactionId(int transactionId) {
    this.transactionId = transactionId;
  }

  @Override
  public Date getTimestamp() {
    return timestamp;
  }

  @Override
  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String getReportingService() {
    return reportingService;
  }

  @Override
  public void setReportingService(String reportingService) {
    this.reportingService = reportingService;
  }

  @Override
  public String getCategory() {
    return category;
  }

  @Override
  public void setCategory(String category) {
    this.category = category;
  }

  @Override
  public String getRequestor() {
    return requestor;
  }

  @Override
  public void setRequestor(String requestor) {
    this.requestor = requestor;
  }

  @Override
  public String getRequest() {
    return request;
  }

  @Override
  public void setRequest(String request) {
    this.request = request;
  }

  @Override
  public String getResponse() {
    return response;
  }

  @Override
  public void setResponse(String response) {
    this.response = response;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventRequest that = (EventRequest) o;
    return transactionId == that.transactionId &&
        Objects.equals(timestamp, that.timestamp) &&
        Objects.equals(reportingService, that.reportingService) &&
        Objects.equals(category, that.category) &&
        Objects.equals(requestor, that.requestor) &&
        Objects.equals(request, that.request) &&
        Objects.equals(response, that.response);
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(transactionId, timestamp, reportingService, category, requestor, request, response);
  }

  @Override
  public String toString() {
    return "EventRequest{" +
        "transactionId=" + transactionId +
        ", timestamp=" + timestamp +
        ", reportingService='" + reportingService + '\'' +
        ", category='" + category + '\'' +
        ", requestor='" + requestor + '\'' +
        ", request='" + request + '\'' +
        ", response='" + response + '\'' +
        '}';
  }
}
