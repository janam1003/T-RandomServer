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
 *
 * @author Janam
 */
@Stateless
@Path("city")
public class CityREST {

    @EJB
    private CityManagerEJBLocal cityEJB;

    private static final Logger LOGGER = Logger.getLogger(CityREST.class.getName());

    @POST
    @Consumes({MediaType.APPLICATION_XML})
    public void createCity(City entity) {

        try {

            LOGGER.log(Level.INFO, "Creating a City");

            cityEJB.createCity(entity);

        } catch (CreateException ex) {

            LOGGER.severe(ex.getMessage());

            throw new InternalServerErrorException(ex.getMessage());

        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML})
    public void updateCity(City entity) {

        try {

            LOGGER.log(Level.INFO, "updating a City");

            cityEJB.updateCity(entity);

        } catch (UpdateException ex) {

            LOGGER.severe(ex.getMessage());

            throw new InternalServerErrorException(ex.getMessage());

        }
    }

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

    @GET
    @Path("{cityId}")
    @Produces({MediaType.APPLICATION_XML})
    public City findCityByCId(@PathParam("cityId") Long cityId) {

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

    @GET
    @Produces({MediaType.APPLICATION_XML})
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

    @GET
    @Path("findAllCityByCountry/{country}")
    @Produces({MediaType.APPLICATION_XML})
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

    @GET
    @Path("findAllCityBypopulationType/{populationType}")
    @Produces({MediaType.APPLICATION_XML})
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
