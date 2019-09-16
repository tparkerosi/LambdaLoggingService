package logging;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHandler extends StdDeserializer<Date> {

  public DateHandler(){
    super(String.class);
  }

  protected DateHandler(Class vc) {
    super(vc);
  }

  @Override
  public Date deserialize(JsonParser jsonParser, DeserializationContext context)
      throws IOException, JsonProcessingException {
    String rawDate = jsonParser.getText();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    try {
      Date date = formatter.parse(rawDate);
    } catch (ParseException e) {
      throw new IllegalArgumentException("Invalid Date Format");
    }
    return null;
  }
}
