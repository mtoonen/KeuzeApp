package meine.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Meine Toonen
 */
public class MyDb {

    public MyDb(){
        init();
    }

    private static final Log log = LogFactory.getLog(MyDb.class);
    private static final Map<String, EntityManagerFactory> entityManagerFactories = new HashMap();
    private static final ThreadLocal<Map<String, EntityManager>> entityManagers = new ThreadLocal();
    private static String defaultPersistenceUnit;

    public void init() {

        String defaultPu = "beroepenkiezerPU";
        if (defaultPu != null && defaultPu.trim().length() > 0) {
            defaultPersistenceUnit = defaultPu;
            log.info("Default persistence unit set to " + defaultPu + " - returning this unit for JpaUtilServlet.getEntityManager()");
        }


        long startTime = System.currentTimeMillis();
        try {
            log.info("Initializing entity manager factory for persistence unit " + defaultPu);

            EntityManagerFactory emf = Persistence.createEntityManagerFactory(defaultPu);
            entityManagerFactories.put(defaultPu, emf);

        } catch (Exception e) {
            String msg = "Exception initializing entity manager factory for persistence unit " + defaultPu;
            log.error(msg, e);
        } finally {
            if (log.isDebugEnabled()) {
                long timeMs = System.currentTimeMillis() - startTime;
                String time = java.text.NumberFormat.getInstance(java.util.Locale.ENGLISH).format(timeMs / 1000.0);
                log.debug("Initializing entity manager factory for persistence unit " + defaultPu + " took " + time + " s");
            }
        }

    }

    /**
     * Returns an EntityManager for the default persistence unit. The default
     * persistence unit name is configured using the "default-persistence-unit"
     * init parameter or automatically if there is only one persistence unit
     * configured to initialize to that unit.
     *
     * If there is no default persistence unit configured, this method throws
     * an IllegalArgumentException.
     *
     * It's important that when the unit-of-work of the current thread is
     * completed, the entity manager is closed using closeThreadEntityManager()!
     *
     * @see getThreadEntityManager(String)
     *
     * @return an EntityManager for the default persistence unit
     */
    public static EntityManager getThreadEntityManager() throws IllegalArgumentException {
        if (defaultPersistenceUnit == null) {
            throw new IllegalArgumentException("No default persistence unit configured");
        }

        return getThreadEntityManager(defaultPersistenceUnit);
    }

    /**
     * Returns an EntityManager unique for the current thread for the specified
     * persistence unit.
     *
     * If the EntityManagerFactory for the persistence unit hasn't been created,
     * it is created first. This may cause an exception only at the stage the
     * EntityManager is first requested. To see exceptions at application start,
     * use the "initialize-persistence-units" init parameter.
     *
     * If the method is called for the first time in the current thread (or a
     * previously created EntityManager was closed), a new EntityManger will be
     * created. If an EntityManager was already created, that instance is
     * returned.
     *
     * It's important that when the unit-of-work of the current thread is
     * completed, the entity manager is closed using closeThreadEntityManager()!
     *
     * @return an EntityManager for the specified persistence unit
     */
    public static EntityManager getThreadEntityManager(String persistenceUnit) {
        Map<String, EntityManager> ems = entityManagers.get();
        if (ems == null) {
            ems = new HashMap();
            entityManagers.set(ems);
        }

        EntityManager em = ems.get(persistenceUnit);
        if (em == null) {
            EntityManagerFactory emf = getEntityManagerFactory(persistenceUnit);

            em = emf.createEntityManager();
            ems.put(persistenceUnit, em);
        }

        return em;
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        if (defaultPersistenceUnit == null) {
            throw new IllegalArgumentException("No default persistence unit configured");
        }

        return getEntityManagerFactory(defaultPersistenceUnit);
    }

    public static EntityManagerFactory getEntityManagerFactory(String persistenceUnit) {
        EntityManagerFactory emf = null;
        synchronized (entityManagerFactories) {
            emf = entityManagerFactories.get(persistenceUnit);
            if (emf == null) {
                log.info("Initializing entity manager factory for persistance unit " + persistenceUnit);
                emf = Persistence.createEntityManagerFactory(persistenceUnit);
                entityManagerFactories.put(persistenceUnit, emf);
            }
        }
        return emf;
    }

    public static void closeThreadEntityManager() {
        if (defaultPersistenceUnit == null) {
            throw new IllegalArgumentException("No default persistence unit configured");
        }

        closeThreadEntityManager(defaultPersistenceUnit);
    }

    public static void closeThreadEntityManager(String persistenceUnit) {
        Map<String, EntityManager> ems = entityManagers.get();
        if (ems != null) {
            EntityManager em = ems.get(persistenceUnit);
            if (em != null) {
                ems.remove(persistenceUnit);
                em.close();
            }
        }
    }

    public void destroy() {
        Collection<EntityManagerFactory> factories = entityManagerFactories.values();
        if (!factories.isEmpty()) {
            for (Iterator<EntityManagerFactory> it = factories.iterator(); it.hasNext();) {
                EntityManagerFactory emf = it.next();
                emf.close();
            }
        }
    }
}
