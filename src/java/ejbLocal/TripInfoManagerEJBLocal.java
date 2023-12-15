package ejbLocal;

import entities.Customer;
import entities.Trip;
import entities.TripInfo;
import exception.CreateException;
import exception.DeleteException;
import exception.ReadException;
import exception.UpdateException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 * EJB Local Interface for managing TripInfo entity CRUD operations.
 * @author Iñigo
 */
@Local
public interface TripInfoManagerEJBLocal {
    /**
     * Finds a {@link TripInfo} by its Id.
     * @param id The Id for the TripInfo to be found.
     * @return The {@link TripInfo} object containing tripInfo data
     * @throws ReadException If there is any Exception during processing.
     */
    public TripInfo findTripInfoById(Integer id) throws ReadException;

    /**
     * Finds all {@link TripInfo} objects associated with a given Customer.
     * @param customer The Customer for which to retrieve TripInfo objects.
     * @return A List of {@link TripInfo} objects.
     * @throws ReadException If there is any Exception during processing.
     */
    public List<TripInfo> findAllTripInfoByCustomer(Customer customer) throws ReadException;

    /**
     * Finds all {@link TripInfo} objects associated with a given Trip.
     * @param trip The Trip for which to retrieve TripInfo objects.
     * @return A List of {@link TripInfo} objects.
     * @throws ReadException If there is any Exception during processing.
     */
    public List<TripInfo> findAllTripInfoByTrip(Trip trip) throws ReadException;

    /**
     * Updates the information of a TripInfo in the underlying application storage.
     * @param tripInfo The {@link TripInfo} object containing the updated data.
     * @throws UpdateException If there is any Exception during processing.
     */
    public void updateTripInfo(TripInfo tripInfo) throws UpdateException;

    /**
     * Deletes a TripInfo from the underlying application storage.
     * @param tripInfo The {@link TripInfo} object to be deleted.
     * @throws DeleteException If there is any Exception during processing.
     */
    public void deleteTripInfo(TripInfo tripInfo) throws DeleteException;

    /**
     * Creates a new TripInfo and stores it in the underlying application storage.
     * @param tripInfo The {@link TripInfo} object containing the new data.
     * @throws CreateException If there is any Exception during processing.
     */
    public void createTripInfo(TripInfo tripInfo) throws CreateException;

    /**
     * Finds all active {@link TripInfo} objects associated with a given Customer.
     * @param customer The Customer for which to retrieve active TripInfo objects.
     * @param date The current date for comparison.
     * @return A List of active {@link TripInfo} objects.
     * @throws ReadException If there is any Exception during processing.
     */
    public List<TripInfo> findActiveTripInfoByCustomer(Customer customer, Date date) throws ReadException;

    /**
     * Finds all inactive {@link TripInfo} objects associated with a given Customer.
     * @param customer The Customer for which to retrieve inactive TripInfo objects.
     * @param date The current date for comparison.
     * @return A List of inactive {@link TripInfo} objects.
     * @throws ReadException If there is any Exception during processing.
     */
    public List<TripInfo> findInactiveTripInfoByCustomer(Customer customer, Date date) throws ReadException;
}
