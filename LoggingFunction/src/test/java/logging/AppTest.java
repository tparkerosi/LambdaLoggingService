package logging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import web.EventRequest;

public class AppTest {
  App app;
  EventRequest event;

  @Before
  public void setup(){
    app = new App();
    event = new EventRequest();
    event.setRequestor("Fred");
  }

  @Test
  public void successfulResponse() {
    assertTrue(true);
  }

  @Test
  public void testDeserializingJSON() throws IOException {
    String json = "{\n"
        + "  \"transactionId\": \"3\",\n"
        + "  \"timestamp\": \"2019-09-12T09:34:04.001+0800\",\n"
        + "  \"reportingService\": \"Snapshot\",\n"
        + "  \"category\": \"Search\",\n"
        + "  \"requestor\": \"Axxx-4567-asdf432\",\n"
        + "  \"request\": \"name=WILLMA,age=17\",\n"
        + "  \"response\": \"Willma sampson, Willma civilized, Willma obsiden\"\n"
        + "}";
    ObjectMapper mapper = new ObjectMapper();

    EventRequest requestEvent = mapper.readValue(json, EventRequest.class);
    assertEquals(requestEvent.getTransactionId(), 3);
    assertEquals(requestEvent.getCategory(), "Search");
//    assertEquals(requestEvent.getTimestamp(), "Wed Sep 11 18:34:04 PDT 2019");
  }
}
