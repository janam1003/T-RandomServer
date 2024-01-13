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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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
@Path("city")
public class CityREST {

    @PersistenceContext
    private EntityManager em;

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

            LOGGER.log(Level.INFO, "CityRESTful service: create {0}.", entity);

            cityEJB.createCity(entity);

        } catch (CreateException ex) {

            LOGGER.log(Level.SEVERE, "CityRESTful service: Exception creating city, {0}", ex.getMessage());

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

            LOGGER.log(Level.INFO, "CityRESTful service: update {0}.", entity);

            cityEJB.updateCity(entity);

        } catch (UpdateException ex) {

            LOGGER.log(Level.SEVERE, "CityRESTful service: Exception updating city, {0}", ex.getMessage());

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
    @Transactional
    //@TransactionAttribute(REQUIRED)
    public void deleteCity(@PathParam("cityId") Long cityId) {

        try {

            LOGGER.log(Level.INFO, "CityRESTful service: delete city by cityId={0}.", cityId);

            // Fetch the managed City entity
            City cityToDelete = cityEJB.findCityByCityId(cityId);

            // Check if the entity is not null
            if (cityToDelete != null) {

                // Check if the entity is managed (attached)
                if (em.contains(cityToDelete)) {

                    // Delete the City
                    cityEJB.deleteCity(cityToDelete);

                } else {

                    // If not managed, merge it to attach it to the persistence context
                    City managedCity = em.merge(cityToDelete);

                    // Delete the City
                    cityEJB.deleteCity(managedCity);
                }

            } else {

                // Handle the case where the entity is null (log a warning, throw an exception, etc.)
                LOGGER.log(Level.WARNING, "CityRESTful service: Trying to delete a null city with id {0}.", cityId);

            }

        } catch (ReadException | DeleteException ex) {

            LOGGER.log(Level.SEVERE, "CityRESTful service: Exception deleting city by cityId, {0}", ex.getMessage());

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

            LOGGER.log(Level.INFO, "CityRESTful service: find City by cityId={0}.", cityId);

            city = cityEJB.findCityByCityId(cityId);

        } catch (ReadException ex) {

            LOGGER.log(Level.SEVERE, "CityRESTful service: Exception reading city by cityId, {0}", ex.getMessage());

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

            LOGGER.log(Level.INFO, "CityRESTful service: find all City.");

            cities = cityEJB.findAllCities();

        } catch (ReadException ex) {

            LOGGER.log(Level.SEVERE, "CityRESTful service: Exception reading all City, {0}", ex.getMessage());

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

            LOGGER.log(Level.INFO, "CityRESTful service: find all City by country={0}.", country);

            cities = cityEJB.findAllCityByCountry(country);

        } catch (ReadException ex) {

            LOGGER.log(Level.SEVERE, "CityRESTful service: Exception reading City by country, {0}", ex.getMessage());

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

            LOGGER.log(Level.INFO, "CityRESTful service: find all City by populationType={0}.", populationType);

            cities = cityEJB.findAllCitiesBypopulationType(populationType);

        } catch (ReadException ex) {

            LOGGER.log(Level.SEVERE, "CityRESTful service: Exception reading City by populationType, {0}", ex.getMessage());

            throw new InternalServerErrorException(ex.getMessage());

        }

        return cities;
    }
}
