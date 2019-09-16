package logging;

import java.util.Date;

public interface Event {

  int getTransactionId();

  void setTransactionId(int transactionId);

  Date getTimestamp();

  void setTimestamp(Date timestamp);

  String getReportingService();

  void setReportingService(String reportingService);

  String getCategory();

  void setCategory(String category);

  String getRequestor();

  void setRequestor(String requestor);

  String getRequest();

  void setRequest(String request);

  String getResponse();

  void setResponse(String response);
}
