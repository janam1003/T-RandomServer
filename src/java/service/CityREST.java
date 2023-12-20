package service;

import ejbLocal.CityManagerEJBLocal;
import entities.City;
import entities.PopulationType;
import exception.CreateException;
import exception.DeleteException;
import exception.ReadException;
import exception.UpdateException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * The CityREST class provides RESTful web services for managing City entities.
 * It exposes CRUD operations (Create, Read, Update, Delete) and additional
 * methods for retrieving cities based on various criteria.
 *
 * This class utilizes the CityManagerEJBLocal EJB to interact with the
 * underlying business logic for city management.
 *
 * @author Janam
 */
@Stateless
@Path("city")
public class CityREST {

    @EJB
    private CityManagerEJBLocal cityEJB;

    // Logger
    private static final Logger LOGGER = Logger.getLogger(CityREST.class.getName());

    /**
     * Creates a new City using the provided entity.
     *
     * @param entity The City entity to be created.
     * @throws InternalServerErrorException If an internal server error occurs
     * during the creation process.
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createCity(City entity) {

        try {

            LOGGER.log(Level.INFO, "Creating a City");

            cityEJB.createCity(entity);

        } catch (CreateException ex) {

            LOGGER.severe(ex.getMessage());

            throw new InternalServerErrorException(ex.getMessage());

        }
    }

    /**
     * Updates an existing City with the provided entity.
     *
     * @param entity The City entity containing updated information.
     * @throws InternalServerErrorException If an internal server error occurs
     * during the update process.
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateCity(City entity) {

        try {

            LOGGER.log(Level.INFO, "updating a City");

            cityEJB.updateCity(entity);

        } catch (UpdateException ex) {

            LOGGER.severe(ex.getMessage());

            throw new InternalServerErrorException(ex.getMessage());

        }
    }

    /**
     * Deletes a City with the specified cityId.
     *
     * @param cityId The unique identifier of the City to be deleted.
     * @throws InternalServerErrorException If an internal server error occurs
     * during the deletion process.
     */
    @DELETE
    @Path("{cityId}")
    public void deleteCity(@PathParam("cityId") Long cityId) {

        try {

            LOGGER.log(Level.INFO, "Deleting City {0}", cityId);

            cityEJB.deleteCity(cityEJB.findCityByCityId(cityId));

        } catch (ReadException | DeleteException ex) {

            LOGGER.severe(ex.getMessage());

            throw new InternalServerErrorException(ex.getMessage());

        }
    }

    /**
     * Retrieves a City by its unique identifier (cityId).
     *
     * @param cityId The unique identifier of the City to be retrieved.
     * @return The City object corresponding to the provided cityId.
     * @throws InternalServerErrorException If an internal server error occurs
     * during the retrieval process.
     */
    @GET
    @Path("{cityId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public City findCityById(@PathParam("cityId") Long cityId) {

        City city = null;

        try {

            LOGGER.log(Level.INFO, "getting city by cityId");

            city = cityEJB.findCityByCityId(cityId);

        } catch (ReadException ex) {

            LOGGER.severe(ex.getMessage());

            throw new InternalServerErrorException(ex.getMessage());

        }

        return city;
    }

    /**
     * Retrieves a list of all City entities.
     *
     * @return A list containing all City entities.
     * @throws InternalServerErrorException If an internal server error occurs
     * during the retrieval process.
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<City> findAllCity() {

        List<City> cities = null;

        try {

            LOGGER.log(Level.INFO, "getting all city");

            cities = cityEJB.findAllCities();

        } catch (ReadException ex) {

            LOGGER.severe(ex.getMessage());

            throw new InternalServerErrorException(ex.getMessage());

        }

        return cities;
    }

    /**
     * Retrieves a list of City entities based on the specified country.
     *
     * @param country The name of the country for which cities are to be
     * retrieved.
     * @return A list of City entities belonging to the specified country.
     * @throws InternalServerErrorException If an internal server error occurs
     * during the retrieval process.
     */
    @GET
    @Path("findAllCityByCountry/{country}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<City> findAllCityByCountry(@PathParam("country") String country) {

        List<City> cities = null;

        try {

            LOGGER.log(Level.INFO, "getting city by country");

            cities = cityEJB.findAllCityByCountry(country);

        } catch (ReadException ex) {

            LOGGER.severe(ex.getMessage());

            throw new InternalServerErrorException(ex.getMessage());

        }

        return cities;
    }

    /**
     * Retrieves a list of City entities based on the specified population type.
     *
     * @param populationType The type of population for which cities are to be
     * retrieved.
     * @return A list of City entities belonging to the specified population
     * type.
     * @throws InternalServerErrorException If an internal server error occurs
     * during the retrieval process.
     */
    @GET
    @Path("findAllCityBypopulationType/{populationType}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<City> findAllCityBypopulationType(@PathParam("populationType") PopulationType populationType) {

        List<City> cities = null;

        try {

            LOGGER.log(Level.INFO, "getting city by populationType");

            cities = cityEJB.findAllCitiesBypopulationType(populationType);

        } catch (ReadException ex) {

            LOGGER.severe(ex.getMessage());

            throw new InternalServerErrorException(ex.getMessage());

        }

        return cities;
    }
}
