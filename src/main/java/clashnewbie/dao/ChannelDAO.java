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

/**
 *
 * @author Carlos Soza C <carlos.soza at profondos.com>
 */
public class ChannelDAO {

    private static final Logger LOGGER = Logger.getLogger(ChannelDAO.class);

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

    public static void saveChannel(Channel channel) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            // Saving to the database
            session.save(channel);
            // Committing the change in the database.
            session.flush();
            tx.commit();

        } catch (Exception ex) {
            LOGGER.error("Error saving Channel " + ex.getMessage(), ex);
            tx.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public static void updateChannel(Channel channel) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            // Saving to the database
            session.update(channel);
            // Committing the change in the database.
            session.flush();
            tx.commit();

        } catch (Exception ex) {
            LOGGER.error("Error saving Channel " + ex.getMessage(), ex);
            tx.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public static void removeChannel(String channelId) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session = sessionFactory.openSession();
            Channel channel = (Channel) session.createQuery("from Channel where id=:channelId").setParameter("channelId", channelId).list().get(0);
            session.delete(channel);
            // Committing the change in the database.
            session.flush();
            tx.commit();

        } catch (Exception ex) {
            LOGGER.error("Error saving Channel " + ex.getMessage(), ex);
            tx.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public static Channel getChannel(String channelId) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            Channel channel = (Channel) session.createQuery("from Channel where id=:channelId").setParameter("channelId", channelId).list().get(0);
            return channel;

        } catch (Exception ex) {
            LOGGER.error("Error getting Channel " + ex.getMessage(), ex);
            tx.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    public static List<Channel> getChannels() {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            @SuppressWarnings("unchecked")
            List<Channel> channels = session.createQuery("from Channel").list();
            return channels;
        } catch (Exception ex) {
            LOGGER.error("Error getting Channel " + ex.getMessage(), ex);
            tx.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }
}
