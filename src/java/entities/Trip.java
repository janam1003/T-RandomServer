package entities;

import java.io.Serializable;
import java.util.List;
import static javax.persistence.CascadeType.REMOVE;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity for Trip, it has id, cities, tripType and description
 *
 * @author Iñigo
 */
@Entity
@Table(name = "trip", schema = "g3CRUD")
@NamedQueries({
    //Query to get all the trips
    @NamedQuery(name = "findAllTrips", query = "SELECT t FROM Trip t")
    ,
    //Query to get a trip with the tripType
    @NamedQuery(name = "findTripsByTripType", query = "SELECT t FROM Trip t WHERE t.tripType = :tripType")
})
@XmlRootElement
public class Trip implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToMany(mappedBy = "trip", cascade = REMOVE, fetch = FetchType.EAGER)
    private List<TripInfo> tripInfo;

    @ManyToMany(fetch = EAGER)
    @JoinTable(schema = "g3CRUD", name = "trip_cities", joinColumns = @JoinColumn(name = "trip_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "city_id", referencedColumnName = "cityId"))
    private List<City> cities;

    @Enumerated(EnumType.STRING)
    private EnumTripType tripType;

    private String description;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
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
        Trip other = (Trip) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @XmlTransient
    public List<TripInfo> getTripInfo() {
        return tripInfo;
    }

    public void setTripInfo(List<TripInfo> tripInfo) {
        this.tripInfo = tripInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EnumTripType getTripType() {
        return tripType;
    }

    public void setTripType(EnumTripType tripType) {
        this.tripType = tripType;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Trip [id=" + id + ", cities=" + cities + ", tripType=" + tripType + ", description=" + description
                + "]";
    }

}
