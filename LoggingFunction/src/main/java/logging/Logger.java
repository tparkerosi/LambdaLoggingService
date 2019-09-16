package logging;

import repository.EventEntity;
import repository.EventRepository;

public class Logger {
  EventRepository repo;

  public Logger(EventRepository repo){
    this.repo = repo;
  }

  public void log( Event event){
    EventEntity entity = buildEventEntity(event);
    repo.save(entity);
  }

  private EventEntity buildEventEntity(Event event){
    EventEntity entity = new EventEntity();
    entity.setTransactionId(event.getTransactionId());
    entity.setCategory(event.getCategory());
    entity.setReportingService(event.getReportingService());
    entity.setRequest(event.getRequest());
    entity.setRequestor(event.getRequestor());
    entity.setResponse(event.getResponse());
    entity.setTimestamp(event.getTimestamp());
    return entity;
  }
}
