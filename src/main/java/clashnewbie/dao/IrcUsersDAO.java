package cl.curso.java.clashnewbie.dao;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;


public class IrcUsersDAO {

    private static final Logger LOGGER = Logger.getLogger(IrcUsersDAO.class);

    private static SessionFactory sessionFactory = null;
    private static ServiceRegistry serviceRegistry = null;

    static {
        configureSessionFactory();
    }

    private static void configureSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        configuration.configure();

        Properties properties = configuration.getProperties();

        serviceRegistry = new ServiceRegistryBuilder().applySettings(properties).buildServiceRegistry();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public static void saveIrcUser(IrcUser ircUser) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            // Saving to the database
            session.save(ircUser);
            // Committing the change in the database.
            session.flush();
            tx.commit();

        } catch (Exception ex) {
            LOGGER.error("Error saving IrcUser " + ex.getMessage(), ex);
            tx.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public static void updateIrcUser(IrcUser ircUser) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            // Saving to the database
            session.update(ircUser);
            // Committing the change in the database.
            session.flush();
            tx.commit();

        } catch (Exception ex) {
            LOGGER.error("Error saving IrcUser " + ex.getMessage(), ex);
            tx.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public static IrcUser getIrcUser(String ircuserId) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            IrcUser ircUSer = (IrcUser) session.createQuery("from IrcUser where id=:ircuserId").setParameter("ircuserId", ircuserId).list().get(0);
            return ircUSer;

        } catch (Exception ex) {
            LOGGER.error("Error getting IrcUser " + ex.getMessage(), ex);
            tx.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    public static IrcUser getIrcUserByNickname(String nickname) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            IrcUser ircUSer = (IrcUser) session.createQuery("from IrcUser where nick=:nickname").setParameter("nickname", nickname).list().get(0);
            return ircUSer;

        } catch (Exception ex) {
            LOGGER.error("Error getting IrcUser " + ex.getMessage(), ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    public static List<IrcUser> getIrcUsers() {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            @SuppressWarnings("unchecked")
            List<IrcUser> ircUsers = session.createQuery("from IrcUser").list();
            return ircUsers;
        } catch (Exception ex) {
            LOGGER.error("Error getting IrcUsers " + ex.getMessage(), ex);
            tx.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }
}
