import java.util.List;
import javax.ejb.Local;

/**
 * EJB Local Interface for managing Trip entity CRUD operations.
 * @author Iñigo
 */
@Local
public interface TripManagerEJBLocal {

    /**
     * Finds a {@link Trip} by its id. 
     * @param id The id for the trip to be found.
     * @return The {@link Trip} object containing trip data. 
     * @throws ReadException If there is any Exception during processing.
     */
    public Trip findTripById(Integer id) throws ReadException;

    /**
     * Finds a List of {@link Trip} objects containing data for all trips in the
     * application data storage.
     * @return A List of {@link Trip} objects.
     * @throws ReadException If there is any Exception during processing.
     */
    public List<Trip> findAllTrips() throws ReadException;

    /**
     * Finds a List of {@link Trip} objects containing data for all trips with a certain
     * tripType value.
     * @param tripType The tripType value for the trips to be found.
     * @return A List of {@link Trip} objects.
     * @throws ReadException If there is any Exception during processing.
     */
    public List<Trip> findTripsByTripType(EnumTripType tripType) throws ReadException;

    /**
     * Creates a Trip and stores it in the underlying application storage. 
     * @param trip The {@link Trip} object containing the trip data. 
     * @throws CreateException If there is any Exception during processing.
     */
    public void createTrip(Trip trip) throws CreateException;

    /**
     * Updates a trip's data in the underlying application storage. 
     * @param trip The {@link Trip} object containing the trip data. 
     * @throws UpdateException If there is any Exception during processing.
     */
    public void updateTrip(Trip trip) throws UpdateException;

    /**
     * Deletes a trip's data in the underlying application storage. 
     * @param trip The {@link Trip} object containing the trip data. 
     * @throws DeleteException If there is any Exception during processing.
     */
    public void deleteTrip(Trip trip) throws DeleteException;
}

