package ejb;

import ejbLocal.CityManagerEJBLocal;
import entities.City;
import entities.PopulationType;
import entities.WeatherType;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * CityManagerEJB class for managing City entity CRUD operations.
 *
 * @author Janam
 */
public class CityManagerEJB implements CityManagerEJBLocal {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = Logger.getLogger("javafxserverside");

    /**
     * Entity manager object.
     */
    @PersistenceContext
    private EntityManager em;

    @Override
    public void createCity(City city) throws CreateException {

        LOGGER.info("CityManager: Creating City.");

        try {

            em.persist(city);

            LOGGER.info("CityManager: City created.");

        } catch (Exception e) {

            LOGGER.log(Level.SEVERE, "CityManager: Exception creating City.{0}", e.getMessage());

            throw new CreateException(e.getMessage());
        }
    }

    @Override
    public void updateCity(City city) throws UpdateException {

        LOGGER.info("CityManager: Updating City.");

        try {

            em.merge(city);

            em.flush();

            LOGGER.info("CityManager: City updated.");

        } catch (Exception e) {

            LOGGER.log(Level.SEVERE, "CityManager: Exception updating City.{0}", e.getMessage());

            throw new UpdateException(e.getMessage());
        }
    }

    @Override
    public void deleteCity(City city) throws DeleteException {

        LOGGER.info("CityManager: Deleting City.");

        try {

            em.remove(em.merge(city));

            LOGGER.info("CityManager: City deleted.");

        } catch (Exception e) {

            LOGGER.log(Level.SEVERE, "CityManager: Exception deleting City.{0}", e.getMessage());

            throw new DeleteException(e.getMessage());
        }
    }

    @Override
    public City findCityByCityId(Integer CityId) throws ReadException {

        City city = null;

        try {

            LOGGER.info("CityManager: Finding City by CityId.");

            city = em.find(City.class, CityId);

            if (city != null) {

                LOGGER.log(Level.INFO, "CityManager: City found {0}", city.getCityId());

            }

        } catch (Exception e) {

            LOGGER.log(Level.SEVERE, "CityManager: Exception finding City by CityId:", e.getMessage());

            throw new ReadException(e.getMessage());

        }

        return city;
    }

    @Override
    public List<City> findAllCities() throws ReadException {

        List<City> city = null;

        try {

            LOGGER.info("CityManager: Finding all City.");

            city = em.createNamedQuery("findAllCity").getResultList();

        } catch (Exception e) {

            LOGGER.log(Level.SEVERE, "CityManager: Exception finding all City:", e.getMessage());

            throw new ReadException(e.getMessage());

        }

        return city;
    }

    @Override
    public List<City> findAllCityByCountry(String country) throws ReadException {

        List<City> cities = null;

        try {

            LOGGER.info("CityManager: Finding all City by country.");

            cities = em.createNamedQuery("findAllCityByCountry").setParameter("country", country).getResultList();

        } catch (Exception e) {

            LOGGER.log(Level.SEVERE, "CityManager: Exception finding all City by country:", e.getMessage());

            throw new ReadException(e.getMessage());

        }

        return cities;
    }

    @Override
    public List<City> findAllCitiesBypopulationType(PopulationType populationType) throws ReadException {

        List<City> cities = null;

        try {

            LOGGER.info("CityManager: Finding all City by populationType.");

            cities = em.createNamedQuery("findAllCityBypopulationType").setParameter("populationType", populationType).getResultList();

        } catch (Exception e) {

            LOGGER.log(Level.SEVERE, "CityManager: Exception finding all City by populationType:", e.getMessage());

            throw new ReadException(e.getMessage());

        }

        return cities;
    }

}
