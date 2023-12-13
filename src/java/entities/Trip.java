package entities;

import java.util.List;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

/**
 *	Entity for Trip, it has id, cities, tripType and description
 * @author Iñigo
 */
@Entity
@Table(name="trip", schema="g3CRUD")
//Query to get all the trips
@NamedQuery(name="findAllTrips", query="SELECT t FROM Trip t")
//Query to create a trip with the tripType, description and cities 
@NamedQuery(name="createTrip", query="INSERT INTO Trip (tripType, description, cities) VALUES (:tripType, :description, :cities)")
//Query to delete a trip with the id
@NamedQuery(name="deleteTrip", query="DELETE FROM Trip t WHERE t.id = :id")
//Query to update a trip with the id, tripType, description and cities
@NamedQuery(name="updateTrip", query="UPDATE Trip t SET t.tripType = :tripType, t.description = :description, t.cities = :cities WHERE t.id = :id")
//Query to get a trip with the id
@NamedQuery(name="findTripById", query="SELECT t FROM Trip t WHERE t.id = :id")
//Query to get a trip with the tripType
@NamedQuery(name="findTripByTripType", query="SELECT t FROM Trip t WHERE t.tripType = :tripType")

public class Trip implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	//TODO: how to add cities to the trip? new method?
	@OneToMany(cascade=ALL, mappedBy="trip")
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trip other = (Trip) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

    public TripType getTripType() {
		return tripType;
	}
	public void setTripType(TripType tripType) {
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

}
