package entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * It is an City entitywhich is Serializable. Containing attributes CityId,
 * name, country,populationType and weatherType.
 *
 * @author Janam
 */
@Entity
@Table(name = "city", schema = "g3crud")
@NamedQueries({
    //Query to get all the Cities
    @NamedQuery(name = "findAllCity", query = "SELECT c FROM City c")
    ,
    //Query to get the City by Id.
    @NamedQuery(name = "findCityById", query = "SELECT c FROM City c WHERE c.cityId = :cityId")
    ,
    //Query to get the City by Country.
    @NamedQuery(name = "findAllCityByCountry", query = "SELECT c FROM City c WHERE c.country = :country")
    ,
    //Query to get the City by PopulationType.
    @NamedQuery(name = "findAllCityBypopulationType", query = "SELECT c FROM City c WHERE c.populationType = :populationType")

})
@XmlRootElement
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identification field for the City and is AutoGenetrated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cityId;

    /**
     * Name of the City.
     */
    private String name;

    /**
     * Name of country of that City.
     */
    private String country;

    /**
     * populationType of the City.
     */
    @Enumerated(EnumType.STRING)
    private PopulationType populationType;

    /**
     * weatherType of the City.
     */
    @Enumerated(EnumType.STRING)
    private WeatherType weatherType;

    /**
     * Relational field for cities trips.
     */
    @ManyToMany(mappedBy = "cities", fetch = FetchType.EAGER)
    private List<Trip> trips;

    /**
     * Empty Constructor.
     */
    public City() {

    }

    /**
     * @return the CityId of City
     */
    public Long getCityId() {
        return cityId;
    }

    /**
     * @param CityId City CityId.
     */
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    /**
     * Get the name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the country
     *
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Get the populationType
     *
     * @return populationType
     */
    public PopulationType getPopulationType() {
        return populationType;
    }

    /**
     * @param populationType the populationType to set
     */
    public void setPopulationType(PopulationType populationType) {
        this.populationType = populationType;
    }

    /**
     * Get the weatherType
     *
     * @return weatherType
     */
    public WeatherType getWeatherType() {
        return weatherType;
    }

    /**
     * @param weatherType the weatherType to set
     */
    public void setWeatherType(WeatherType weatherType) {
        this.weatherType = weatherType;
    }

    /**
     * Get the trips
     *
     * @return trips
     */
    @XmlTransient
    public List<Trip> getTrip() {
        return trips;
    }

    /**
     * @param trips the trips to set
     */
    public void setTrip(List<Trip> trips) {
        this.trips = trips;
    }

    /**
     * Integer representation for City instance.
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.cityId);
        return hash;
    }

    /**
     * Compares two City objects for equality. This method consider a City equal
     * to another City if their id fields have the same value.
     *
     * @param object The other City object to compare to.
     * @return true if CityIds are equals.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final City other = (City) obj;
        return Objects.equals(this.cityId, other.cityId);
    }

    /**
     * Obtains a string representation of the City.
     *
     * @return The String representing the City.
     */
    @Override
    public String toString() {
        return "City{" + "cityId=" + cityId + ", name=" + name + ", country=" + country + ", populationType=" + populationType + ", weatherType=" + weatherType + ", trip=" + trips + '}';
    }
}
