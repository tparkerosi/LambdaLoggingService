package logging;

import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import repository.EventRepository;
import web.EventRequest;
import web.GatewayResponse;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestStreamHandler{

    public void handleRequest(InputStream inputStream, OutputStream outputStream, final Context context) throws IOException {
      EventRequest requestEvent = deserializeRequest(inputStream);
      System.out.println("Processing event: " + requestEvent);

      Logger eventLogger = new Logger(new EventRepository());
      eventLogger.log(requestEvent);

      JSONObject responseJson = buildResponse(requestEvent);
      sendResponse(outputStream, responseJson);
    }

  private void sendResponse(OutputStream outputStream, JSONObject responseJson) throws IOException {
    OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
    writer.write(responseJson.toString());
    writer.close();
  }

  private JSONObject buildResponse(EventRequest requestEvent ) {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");
    headers.put("X-Custom-Header", "application/json");

    final String pageContents = requestEvent.toString();
    String output = String.format("{ \"message\": \"%s\" }", pageContents);

    GatewayResponse response = new GatewayResponse(output, headers, 200);

    JSONObject responseJson = new JSONObject();
    responseJson.put("statusCode", response.getStatusCode());
    responseJson.put("headers", response.getHeaders());
    responseJson.put("body", response.getBody());
    return responseJson;
  }

  private EventRequest deserializeRequest(InputStream inputStream) {
    JSONParser parser = new JSONParser();
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    EventRequest requestEvent = new EventRequest();
    try {
        JSONObject event = (JSONObject) parser.parse(reader);
        if (event.get("body") != null) {
          ObjectMapper mapper = new ObjectMapper();
          System.out.println("Received request" + event.get("body"));
          requestEvent = mapper.readValue((String)event.get("body"), EventRequest.class);
        }
    } catch (IOException e) {
        e.printStackTrace();
    } catch (ParseException e) {
        e.printStackTrace();
    }
    return requestEvent;
  }
}
