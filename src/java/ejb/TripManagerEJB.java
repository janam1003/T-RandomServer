import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EJB class for managing Trip entity CRUD operations.
 * @author Iñigo
 */
@Stateless
public class TripManagerEJB implements TripManagerEJBLocal {

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
     * Finds a {@link Trip} by its id.
     * 
     * @param id The id for the trip to be found.
     * @return The {@link Trip} object containing trip data.
     * @throws ReadException If there is any Exception during processing.
     */
    @Override
    public Trip findTripById(Integer id) throws ReadException {
        Trip trip = null;
        try {
            LOGGER.info("TripManager: Finding trip by id.");
            trip = em.find(Trip.class, id);
            if (trip != null) {
                LOGGER.log(Level.INFO, "TripManager: Trip found {0}", trip.getId());
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TripManager: Exception finding trip by id:", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return trip;
    }

	/**
     * Finds a List of {@link Trip} objects containing data for all trips in the
     * application data storage.
     * 
     * @return A List of {@link Trip} objects.
     * @throws ReadException If there is any Exception during processing.
     */
    @Override
    public List<Trip> findAllTrips() throws ReadException {
        List<Trip> trips = null;
        try {
            LOGGER.info("TripManager: Reading all trips.");
            trips = em.createNamedQuery("findAllTrips").getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TripManager: Exception reading all trips:", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return trips;
    }

    /**
     * Finds a List of {@link Trip} objects containing data for all trips with a
     * certain tripType value.
     * 
     * @param tripType The tripType value for the trips to be found.
     * @return A List of {@link Trip} objects.
     * @throws ReadException If there is any Exception during processing.
     */
    @Override
    public List<Trip> findTripsByTripType(EnumTripType tripType) throws ReadException {
        List<Trip> trips = null;
        try {
            LOGGER.info("TripManager: Reading trips by tripType.");
            trips = em.createNamedQuery("findTripsByTripType")
                    .setParameter("tripType", tripType)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TripManager: Exception reading trips by tripType.", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return trips;
    }

	/**
     * Creates a Trip and stores it in the underlying application storage.
     * 
     * @param trip The {@link Trip} object containing the trip data.
     * @throws CreateException If there is any Exception during processing.
     */
    @Override
    public void createTrip(Trip trip) throws CreateException {
        LOGGER.info("TripManager: Creating trip.");
        try {
            em.persist(trip);
            LOGGER.info("TripManager: Trip created.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TripManager: Exception creating trip.{0}", e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }

	/**
     * Updates a trip's data in the underlying application storage.
     * 
     * @param trip The {@link Trip} object containing the trip data.
     * @throws UpdateException If there is any Exception during processing.
     */
    @Override
    public void updateTrip(Trip trip) throws UpdateException {
        LOGGER.info("TripManager: Updating trip.");
        try {
            em.merge(trip);
            em.flush();
            LOGGER.info("TripManager: Trip updated.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TripManager: Exception updating trip.{0}", e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    /**
     * Deletes a trip's data in the underlying application storage.
     * 
     * @param trip The {@link Trip} object containing the trip data.
     * @throws DeleteException If there is any Exception during processing.
     */
    @Override
    public void deleteTrip(Trip trip) throws DeleteException {
        LOGGER.info("TripManager: Deleting trip.");
        try {
            trip = em.merge(trip);
            em.remove(trip);
            LOGGER.info("TripManager: Trip deleted.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TripManager: Exception deleting trip.{0}", e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }
}
