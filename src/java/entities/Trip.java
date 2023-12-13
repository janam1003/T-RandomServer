package entities;

import java.util.List;

/**
 *
 * @author Iñigo
 */
public class Trip {
	private Integer id;
	private TripType tripType;
	private List<City> cities;
	private String description;
	
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
