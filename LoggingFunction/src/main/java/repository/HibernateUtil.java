package repository;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
  private static SessionFactory sessionFactory;
  private static Configuration configuration;

  public static SessionFactory getSessionFactory() {
    if (null != sessionFactory)
      return sessionFactory;

     if(configuration == null){
       initConfig();
     }

    configuration.configure();
    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
    try {
      sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    } catch (HibernateException e) {
      System.err.println("Initial SessionFactory creation failed." + e);
      throw new ExceptionInInitializerError(e);
    }
    return sessionFactory;
  }

  private static void initConfig(){
    String jdbcUrl = "jdbc:mysql://"
        + System.getenv("RDS_HOSTNAME")
        + "/"
        + System.getenv("RDS_DB_NAME");

    configuration = new Configuration();
    configuration.setProperty("hibernate.connection.url", jdbcUrl);
    configuration.setProperty("hibernate.connection.username", System.getenv("RDS_USERNAME"));
    configuration.setProperty("hibernate.connection.password", System.getenv("RDS_PASSWORD"));

    configuration.addAnnotatedClass(EventEntity.class);

  }

  protected static Configuration getConfiguration(){
    return configuration;
  }

}
