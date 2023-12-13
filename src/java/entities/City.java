package entities;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Janam
 */
@Entity
@Table(name = "city", schema = "g3crud")
@NamedQueries({
    @NamedQuery(name = "findallCity", query = "SELECT c FROM City c")
    ,
    @NamedQuery(name = "findCityById", query = "SELECT c FROM City c WHERE c.cityId = :cityId")
    ,
    @NamedQuery(name = "findCityByCountry", query = "SELECT c FROM City c WHERE c.country = :country")
    ,
    @NamedQuery(name = "findCityByPopulationType", query = "SELECT c FROM City c WHERE c.populationType = :populationType")
})
@XmlRootElement
public class City implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cityId;

    @NotNull
    private String name;

    private String country;

    @Enumerated(EnumType.STRING)
    private PopulationType populationType;

    @Enumerated(EnumType.STRING)
    private WeatherType weatherType;

    @ManyToMany(mappedBy = "City", fetch = EAGER)
    private Set<Trip> trip;

    public City() {

    }

    public City(Long cityId, String name, String country, PopulationType populationType, WeatherType weatherType, Set<Trip> trip) {
        this.cityId = cityId;
        this.name = name;
        this.country = country;
        this.populationType = populationType;
        this.weatherType = weatherType;
        this.trip = trip;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public PopulationType getPopulationType() {
        return populationType;
    }

    public void setPopulationType(PopulationType populationType) {
        this.populationType = populationType;
    }

    public WeatherType getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(WeatherType weatherType) {
        this.weatherType = weatherType;
    }

    public Set<Trip> getTrip() {
        return trip;
    }

    public void setTrip(Set<Trip> trip) {
        this.trip = trip;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.cityId);
        return hash;
    }

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

    @Override
    public String toString() {
        return "City{" + "cityId=" + cityId + ", name=" + name + ", country=" + country + ", populationType=" + populationType + ", weatherType=" + weatherType + ", trip=" + trip + '}';
    }
}
