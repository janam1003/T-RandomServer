package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
/**
 * Entity for TripInfoId, which is the id for TripInfo
 * @author Iñigo
 */
@Embeddable
public class TripInfoId implements Serializable{
	private Integer tripId;
	private String customerId;
	
        public TripInfoId (Integer tripId, String customerId) {
            this.customerId = customerId;
            this.tripId = tripId;
        }
        
        public TripInfoId() {
        }

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.tripId);
        hash = 23 * hash + Objects.hashCode(this.customerId);
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
        final TripInfoId other = (TripInfoId) obj;
        if (!Objects.equals(this.customerId, other.customerId)) {
            return false;
        }
        if (!Objects.equals(this.tripId, other.tripId)) {
            return false;
        }
        return true;
    }
        
}
