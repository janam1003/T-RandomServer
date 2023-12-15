package entities;

import java.io.Serializable;
import javax.persistence.Embeddable;
/**
 * Entity for TripInfoId, which is the id for TripInfo
 * @author Iñigo
 */
@Embeddable
public class TripInfoId implements Serializable{
	private Integer tripId;
	private String customerId;
	
	public Integer getTripId() {
		return tripId;
	}
	public void setTripId(Integer tripId) {
		this.tripId = tripId;
	}

	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
}
