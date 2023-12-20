package service;

import entities.EnumTripType;
import ejbLocal.TripManagerEJBLocal;
import entities.Trip;
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

/**
 * RESTful web service class exposing CRUD operations for {@link Trip} entities.
 * @author Iñigo
 */
@Path("trip")
public class TripREST {
    /**
     * Logger for class methods.
     */
    private static final Logger LOGGER = Logger.getLogger("javafxserverside");

    /**
     * EJB reference for business logic object.
     */
    @EJB
    private TripManagerEJBLocal ejb;

    /**
     * RESTful POST method for creating {@link Trip} objects from XML representation.
     * @param trip The object containing trip data.
     */
    @POST
    @Consumes({"application/xml"})
    public void create(Trip trip) {
        try {
            LOGGER.log(Level.INFO, "TripRESTful service: create {0}.", trip);
            ejb.createTrip(trip);
        } catch (CreateException ex) {
            LOGGER.log(Level.SEVERE,
                    "TripRESTful service: Exception creating trip, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * RESTful PUT method for updating {@link Trip} objects from XML representation.
     * @param trip The object containing trip data.
     */
    @PUT
    @Consumes({"application/xml"})
    public void update(Trip trip) {
        try {
            LOGGER.log(Level.INFO, "TripRESTful service: update {0}.", trip);
            ejb.updateTrip(trip);
        } catch (UpdateException ex) {
            LOGGER.log(Level.SEVERE,
                    "TripRESTful service: Exception updating trip, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * RESTful DELETE method for deleting {@link Trip} objects from id.
     * @param id The id for the object to be deleted.
     */
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Integer id) {
        try {
            LOGGER.log(Level.INFO, "TripRESTful service: delete Trip by id={0}.", id);
            Trip trip = ejb.findTripById(id);
            ejb.deleteTrip(trip);
        } catch (ReadException | DeleteException ex) {
            LOGGER.log(Level.SEVERE,
                    "TripRESTful service: Exception deleting trip by id, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * RESTful GET method for reading {@link Trip} objects through an XML representation.
     * @param id The id for the object to be read.
     * @return The Trip object containing data.
     */
    @GET
    @Path("{id}")
    @Produces({"application/xml"})
    public Trip find(@PathParam("id") Integer id) {
        Trip trip = null;
        try {
            LOGGER.log(Level.INFO, "TripRESTful service: find Trip by id={0}.", id);
            trip = ejb.findTripById(id);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE,
                    "TripRESTful service: Exception reading trip by id, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return trip;
    }

    /**
     * RESTful GET method for reading all {@link Trip} objects.
     * @return A List of {@link Trip} objects.
     */
    @GET
    @Produces({"application/xml"})
    public List<Trip> findAllTrips() {
        List<Trip> tripList = null;
        try {
            LOGGER.log(Level.INFO, "TripRESTful service: find all Trips.");
            tripList = ejb.findAllTrips();
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE,
                    "TripRESTful service: Exception reading all Trips, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return tripList;
    }

    /**
     * RESTful GET method for reading all {@link Trip} objects with a certain tripType value.
     * @param tripType The tripType value for the trips to be found.
     * @return A List of {@link Trip} objects.
     */
    @GET
    @Path("byTripType/{tripType}")
    @Produces({"application/xml"})
    public List<Trip> findTripsByTripType(@PathParam("tripType") EnumTripType tripType) {
        List<Trip> tripList = null;
        try {
            LOGGER.log(Level.INFO, "TripRESTful service: find Trips by tripType={0}.", tripType);
            tripList = ejb.findTripsByTripType(tripType);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE,
                    "TripRESTful service: Exception reading Trips by tripType, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return tripList;
    }
}

