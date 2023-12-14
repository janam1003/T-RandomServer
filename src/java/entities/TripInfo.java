package entities;

import java.util.Date;
import java.util.List;

import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

/**
 * Entity for TripInfo, which is the attribufe from the nm relation between Trip and Customer
 * @author Iñigo
 */
@Entity
@Table(name="tripInfo", schema="g3CRUD")
//QUery to get all tripInfo from a Customer
@NamedQuery(name="findAllTripInfoByCustomer", query="SELECT ti FROM TripInfo ti WHERE ti.customer = :customer")
//Query to get all tripInfo from a Trip
@NamedQuery(name="findAllTripInfoByTrip", query="SELECT ti FROM TripInfo ti WHERE ti.trip = :trip")
//Query to update tripInfo
@NamedQuery(name="updateTripInfo", query="UPDATE TripInfo ti SET ti.initialDate = :initialDate, ti.lastDate = :lastDate WHERE ti.tripInfoId = :tripInfoId")
//Query to delete tripInfo
@NamedQuery(name="deleteTripInfo", query="DELETE FROM TripInfo ti WHERE ti.tripInfoId = :tripInfoId")
//Query to get tripInfo by id
@NamedQuery(name="findTripInfoById", query="SELECT ti FROM TripInfo ti WHERE ti.tripInfoId = :tripInfoId")
//QUery to get only tripInfos that are active
@NamedQuery(name="findActiveTripInfoByCustomer", query="SELECT ti FROM TripInfo ti WHERE ti.customer = :customer AND ti.lastDate > :date")
//Query to get only tripInfos that are inactive
@NamedQuery(name="findInactiveTripInfoByCustomer", query="SELECT ti FROM TripInfo ti WHERE ti.customer = :customer AND ti.lastDate < :date")
@XmlRootElement
public class TripInfo implements Serializable{
	@EmbeddedId
	TripInfoId tripInfoId;

	@MapsId("tripId")
	@ManyToOne
	Trip trip;

	@MapsId("customerId")
	@ManyToOne
	Customer customer;

	@Temporal(TemporalType.TIMESTAMP)
	Date initialDate;

	@Temporal(TemporalType.TIMESTAMP)
	Date lastDate;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tripInfoId == null) ? 0 : tripInfoId.hashCode());
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
		TripInfo other = (TripInfo) obj;
		if (tripInfoId == null) {
			if (other.tripInfoId != null)
				return false;
		} else if (!tripInfoId.equals(other.tripInfoId))
			return false;
		return true;
	}
	public Date getInitialDate() {
		return initialDate;
	}
	public void setInitialDate(Date initialDate) {
		this.initialDate = initialDate;
	}
	public Date getLastDate() {
		return lastDate;
	}
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
	
	@Override
	public String toString() {
		return "TripInfo [tripInfoId=" + tripInfoId + ", trip=" + trip + ", customer=" + customer + ", initialDate="
				+ initialDate + ", lastDate=" + lastDate + "]";
	}
	
}
