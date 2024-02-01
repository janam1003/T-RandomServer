package service;

import ejbLocal.CustomerManagerEJBLocal;
import ejbLocal.TripInfoManagerEJBLocal;
import ejbLocal.TripManagerEJBLocal;
import entities.Customer;
import entities.Trip;
import entities.TripInfo;
import exception.CreateException;
import exception.DeleteException;
import exception.ReadException;
import exception.UpdateException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import entities.TripInfoId;
import javax.ws.rs.core.MediaType;

/**
 * RESTful web service class exposing CRUD operations for {@link TripInfo}
 * entities.
 *
 * @author Iñigo
 */
@Path("tripinfo")
public class TripInfoREST {

    /**
     * Logger for class methods.
     */
    private static final Logger LOGGER = Logger.getLogger("restTripInfo");

    /**
     * EJB reference for business logic object.
     */
    @EJB
    private TripInfoManagerEJBLocal tripInfoEjb;

    /**
     * EJB reference for business logic object.
     */
    @EJB
    private CustomerManagerEJBLocal customerEjb;

    /**
     * EJB reference for business logic object.
     */
    @EJB
    private TripManagerEJBLocal tripEjb;

    /**
     * RESTful POST method for creating {@link TripInfo} objects from XML
     * representation.
     *
     * @param tripInfo The object containing tripInfo data.
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(TripInfo tripInfo) {
        try {
            LOGGER.log(Level.INFO, "TripInfoRESTful service: create {0}.", tripInfo);
            tripInfoEjb.createTripInfo(tripInfo);
        } catch (CreateException ex) {
            LOGGER.log(Level.SEVERE,
                    "TripInfoRESTful service: Exception creating tripInfo, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * RESTful PUT method for updating {@link TripInfo} objects from XML
     * representation.
     *
     * @param tripInfo The object containing tripInfo data.
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void update(TripInfo tripInfo) {
        try {
            LOGGER.log(Level.INFO, "TripInfoRESTful service: update {0}.", tripInfo);
            tripInfoEjb.updateTripInfo(tripInfo);
        } catch (UpdateException ex) {
            LOGGER.log(Level.SEVERE,
                    "TripInfoRESTful service: Exception updating tripInfo, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * RESTful DELETE method for deleting {@link TripInfo} objects from id.
     *
     * @param id The id for the object to be deleted.
     */
    @DELETE
    @Path("{customerId}/{tripId}")
    public void delete(@PathParam("customerId") String customerId, @PathParam("tripId") Integer tripId) {
        TripInfoId tripInfoId = new TripInfoId(tripId, customerId);
        try {
            LOGGER.log(Level.INFO, "TripInfoRESTful service: delete TripInfo by id={0}.", tripInfoId);
            TripInfo tripInfo = tripInfoEjb.findTripInfoById(tripInfoId);
            tripInfoEjb.deleteTripInfo(tripInfo);
        } catch (ReadException | DeleteException ex) {
            LOGGER.log(Level.SEVERE,
                    "TripInfoRESTful service: Exception deleting tripInfo by id, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * RESTful GET method for reading {@link TripInfo} objects through an XML
     * representation.
     *
     * @param id The id for the object to be read.
     * @return The TripInfo object containing data.
     */
    @GET
    @Path("{customerId}/{tripId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public TripInfo find(@PathParam("customerId") String customerId, @PathParam("tripId") Integer tripId) {
        TripInfo tripInfo = null;
        TripInfoId tripInfoId = new TripInfoId(tripId, customerId);
        try {
            LOGGER.log(Level.INFO, "TripInfoRESTful service: find TripInfo by id={0}.", tripInfoId);
            tripInfo = tripInfoEjb.findTripInfoById(tripInfoId);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE,
                    "TripInfoRESTful service: Exception reading tripInfo by id, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return tripInfo;
    }

    /**
     * RESTful GET method for reading all {@link TripInfo} objects associated
     * with a given trip.
     *
     * @param tripId The id of the trip for which to retrieve TripInfo objects.
     * @return A List of {@link TripInfo} objects.
     */
    @GET
    @Path("allByTrip/{tripId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<TripInfo> findAllTripInfoByTrip(@PathParam("tripId") Integer tripId) {
        List<TripInfo> tripInfoList = null;
        try {
            Trip trip = tripEjb.findTripById(tripId);
            tripInfoList = tripInfoEjb.findAllTripInfoByTrip(trip);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE,
                    "TripInfoRESTful service: Exception reading all TripInfo by trip, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return tripInfoList;
    }

    /**
     * RESTful GET method for reading all active {@link TripInfo} objects
     * associated with a given Customer.
     *
     * @param mail The mail of the Customer for which to retrieve active
     * TripInfo objects.
     * @return A List of active {@link TripInfo} objects.
     */
    @GET
    @Path("active/{mail}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<TripInfo> findActiveTripInfoByCustomer(@PathParam("mail") String mail) {
        List<TripInfo> tripInfoList = null;
        try {
            Customer customer = customerEjb.findCustomerByMail(mail);
            tripInfoList = tripInfoEjb.findActiveTripInfoByCustomer(customer);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE,
                    "TripInfoRESTful service: Exception reading active TripInfo by customer, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return tripInfoList;
    }

    /**
     * RESTful GET method for reading all inactive {@link TripInfo} objects
     * associated with a given Customer.
     *
     * @param mail The mail of the Customer for which to retrieve inactive
     * TripInfo objects.
     * @return A List of inactive {@link TripInfo} objects.
     */
    @GET
    @Path("inactive/{mail}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<TripInfo> findInactiveTripInfoByCustomer(@PathParam("mail") String mail) {
        List<TripInfo> tripInfoList = null;
        try {
            Customer customer = customerEjb.findCustomerByMail(mail);
            tripInfoList = tripInfoEjb.findInactiveTripInfoByCustomer(customer);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE,
                    "TripInfoRESTful service: Exception reading inactive TripInfo by customer, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return tripInfoList;
    }

    /**
     * RESTful GET method for reading all {@link TripInfo} objects associated
     * with a given Customer.
     *
     * @param mail The mail of the Customer for which to retrieve TripInfo
     * objects.
     * @return A List of {@link TripInfo} objects.
     */
    @GET
    @Path("allByCustomer/{mail}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<TripInfo> findAllTripInfoByCustomer(@PathParam("mail") String mail) {
        List<TripInfo> tripInfoList = null;
        try {
            Customer customer = customerEjb.findCustomerByMail(mail);
            tripInfoList = tripInfoEjb.findAllTripInfoByCustomer(customer);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE,
                    "TripInfoRESTful service: Exception reading all TripInfo by customer, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return tripInfoList;
    }
}
