package ejb;

import ejbLocal.TripInfoManagerEJBLocal;
import entities.Customer;
import entities.Trip;
import entities.TripInfo;
import entities.TripInfoId;
import exception.CreateException;
import exception.DeleteException;
import exception.ReadException;
import exception.UpdateException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EJB class for managing TripInfo entity CRUD operations.
 *
 * @author Iñigo
 */
@Stateless
public class TripInfoManagerEJB implements TripInfoManagerEJBLocal {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = Logger.getLogger("javafxserverside");

    /**
     * Entity manager object.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Finds a {@link TripInfo} by its Id.
     *
     * @param tripInfoId The Id for the TripInfo to be found.
     * @return The {@link TripInfo} object containing tripInfo data
     * @throws ReadException If there is any Exception during processing.
     */
    @Override
    public TripInfo findTripInfoById(TripInfoId tripInfoId) throws ReadException {
        TripInfo tripInfo = null;
        try {
            LOGGER.info("TripInfoManager: Finding tripInfo by id.");
            tripInfo = em.find(TripInfo.class, tripInfoId);
            if (tripInfo != null) {
                LOGGER.log(Level.INFO, "TripInfoManager: TripInfo found {0}", tripInfo.getTripInfoId());
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TripInfoManager: Exception finding tripInfo by id:", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return tripInfo;
    }

    /**
     * Finds all {@link TripInfo} objects associated with a given Customer.
     *
     * @param customer The Customer for which to retrieve TripInfo objects.
     * @return A List of {@link TripInfo} objects.
     * @throws ReadException If there is any Exception during processing.
     */
    @Override
    public List<TripInfo> findAllTripInfoByCustomer(Customer customer) throws ReadException {
        List<TripInfo> tripInfos = null;
        try {
            LOGGER.info("TripInfoManager: Finding all tripInfos by customer.");
            tripInfos = em.createNamedQuery("findAllTripInfoByCustomer")
                    .setParameter("customer", customer)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TripInfoManager: Exception finding all tripInfos by customer:", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return tripInfos;
    }

    /**
     * Finds all {@link TripInfo} objects associated with a given Trip.
     *
     * @param trip The Trip for which to retrieve TripInfo objects.
     * @return A List of {@link TripInfo} objects.
     * @throws ReadException If there is any Exception during processing.
     */
    @Override
    public List<TripInfo> findAllTripInfoByTrip(Trip trip) throws ReadException {
        List<TripInfo> tripInfos = null;
        try {
            LOGGER.info("TripInfoManager: Finding all tripInfos by trip.");
            tripInfos = em.createNamedQuery("findAllTripInfoByTrip")
                    .setParameter("trip", trip)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TripInfoManager: Exception finding all tripInfos by trip:", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return tripInfos;
    }

    /**
     * Updates the information of a TripInfo in the underlying application
     * storage.
     *
     * @param tripInfo The {@link TripInfo} object containing the updated data.
     * @throws UpdateException If there is any Exception during processing.
     */
    @Override
    public void updateTripInfo(TripInfo tripInfo) throws UpdateException {
        LOGGER.info("TripInfoManager: Updating tripInfo.");
        try {
            em.merge(tripInfo);
            em.flush();
            LOGGER.info("TripInfoManager: TripInfo updated.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TripInfoManager: Exception updating tripInfo.{0}", e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    /**
     * Deletes a TripInfo from the underlying application storage.
     *
     * @param tripInfo The {@link TripInfo} object to be deleted.
     * @throws DeleteException If there is any Exception during processing.
     */
    @Override
    public void deleteTripInfo(TripInfo tripInfo) throws DeleteException {
        LOGGER.info("TripInfoManager: Deleting tripInfo.");
        try {
            tripInfo = em.merge(tripInfo);
            em.remove(tripInfo);
            LOGGER.info("TripInfoManager: TripInfo deleted.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TripInfoManager: Exception deleting tripInfo.{0}", e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }

    /**
     * Creates a new TripInfo and stores it in the underlying application
     * storage.
     *
     * @param tripInfo The {@link TripInfo} object containing the new data.
     * @throws CreateException If there is any Exception during processing.
     */
    @Override
    public void createTripInfo(TripInfo tripInfo) throws CreateException {
        LOGGER.info("TripInfoManager: Creating tripInfo.");
        try {
            em.persist(tripInfo);
            LOGGER.info("TripInfoManager: TripInfo created.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TripInfoManager: Exception creating tripInfo.{0}", e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }

    /**
     * Finds all active {@link TripInfo} objects associated with a given
     * Customer.
     *
     * @param customer The Customer for which to retrieve active TripInfo
     * objects.
     * @return A List of active {@link TripInfo} objects.
     * @throws ReadException If there is any Exception during processing.
     */
    @Override
    public List<TripInfo> findActiveTripInfoByCustomer(Customer customer) throws ReadException {
        List<TripInfo> tripInfos = null;
        try {
            LOGGER.info("TripInfoManager: Finding active tripInfos by customer.");
            Date date = new Date();
            tripInfos = em.createNamedQuery("findActiveTripInfoByCustomer")
                    .setParameter("customer", customer)
                    .setParameter("date", date)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TripInfoManager: Exception finding active tripInfos by customer:", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return tripInfos;
    }

    /**
     * Finds all inactive {@link TripInfo} objects associated with a given
     * Customer.
     *
     * @param customer The Customer for which to retrieve inactive TripInfo
     * objects.
     * @return A List of inactive {@link TripInfo} objects.
     * @throws ReadException If there is any Exception during processing.
     */
    @Override
    public List<TripInfo> findInactiveTripInfoByCustomer(Customer customer) throws ReadException {
        List<TripInfo> tripInfos = null;
        try {
            LOGGER.info("TripInfoManager: Finding inactive tripInfos by customer.");
            Date date = new Date();
            tripInfos = em.createNamedQuery("findInactiveTripInfoByCustomer")
                    .setParameter("customer", customer)
                    .setParameter("date", date)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TripInfoManager: Exception finding inactive tripInfos by customer:", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return tripInfos;
    }
}
