package ejbLocal;

import entities.City;
import entities.PopulationType;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.List;

/**
 * EJB Local Interface for managing City entity CRUD operations.
 *
 * @author Janam
 */
public interface CityManagerEJBLocal {

    /**
     * Creates a City and stores it in the underlying application storage.
     *
     * @param City The {@link City} object containing the City data.
     * @throws CreateException If there is any Exception during processing.
     */
    public void createCity(City city) throws CreateException;

    /**
     * Updates a City's data in the underlying application storage.
     *
     * @param City The {@link City} object containing the trip data.
     * @throws UpdateException If there is any Exception during processing.
     */
    public void updateCity(City city) throws UpdateException;

    /**
     * Deletes a City's data in the underlying application storage.
     *
     * @param City The {@link City} object containing the trip data.
     * @throws DeleteException If there is any Exception during processing.
     */
    public void deleteCity(City city) throws DeleteException;

    /**
     * Finds a {@link City} by its id.
     *
     * @param CityId The id for the City to be found.
     * @return The {@link City} object containing trip data.
     * @throws ReadException If there is any Exception during processing.
     */
    public City findCityByCityId(Integer CityId) throws ReadException;

    /**
     * Finds a List of {@link City} objects containing data for all Cities in
     * the application data storage.
     *
     * @return A List of {@link City} objects.
     * @throws ReadException If there is any Exception during processing.
     */
    public List<City> findAllCities() throws ReadException;

    /**
     * Finds a List of {@link City} objects containing data for all cities with
     * a certain country value.
     *
     * @param country The id for the City to be found.
     * @return The {@link City} object containing trip data.
     * @throws ReadException If there is any Exception during processing.
     */
    public List<City> findAllCityByCountry(String country) throws ReadException;

    /**
     * Finds a List of {@link City} objects containing data for all cities with
     * a certain populationType value.
     *
     * @param populationType The populationType value for the City to be found.
     * @return A List of {@link City} objects.
     * @throws ReadException If there is any Exception during processing.
     */
    public List<City> findAllCitiesBypopulationType(PopulationType populationType) throws ReadException;

}
