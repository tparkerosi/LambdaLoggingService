package repository;


import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class EventRepository {

  public EventEntity save(EventEntity event){
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.save(event);
      session.getTransaction().commit();
      return event;
    }
  }
}
