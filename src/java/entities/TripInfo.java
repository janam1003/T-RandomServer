package entities;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity for TripInfo, which is the attribufe from the nm relation between Trip and Customer
 * @author Iñigo
 */
@Entity
@Table(name = "tripInfo", schema = "g3CRUD")
@NamedQueries({
    //QUery to get all tripInfo from a Customer
    @NamedQuery(name = "findAllTripInfoByCustomer", query = "SELECT ti FROM TripInfo ti WHERE ti.customer = :customer"),
    //Query to get all tripInfo from a Trip
    @NamedQuery(name = "findAllTripInfoByTrip", query = "SELECT ti FROM TripInfo ti WHERE ti.trip = :trip"),
    //QUery to get only tripInfos that are active
    @NamedQuery(name = "findActiveTripInfoByCustomer", query = "SELECT ti FROM TripInfo ti WHERE ti.customer = :customer AND ti.lastDate > :date"),
    //Query to get only tripInfos that are inactive
    @NamedQuery(name = "findInactiveTripInfoByCustomer", query = "SELECT ti FROM TripInfo ti WHERE ti.customer = :customer AND ti.lastDate < :date")
})

@XmlRootElement
public class TripInfo implements Serializable{
	@EmbeddedId
	TripInfoId tripInfoId;

        @JoinColumn(name="tripId", updatable=false, insertable=false)
	@ManyToOne
	Trip trip;

	@JoinColumn(name="customerId", updatable=false, insertable=false)
	@ManyToOne
	Customer customer;

	@Temporal(TemporalType.TIMESTAMP)
	LocalDate initialDate;

	@Temporal(TemporalType.TIMESTAMP)
	LocalDate lastDate;

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
	//@XmlTransient
	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}
	@XmlTransient
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
           
    public TripInfoId getTripInfoId() {
        return tripInfoId;
    }

    public void setTripInfoId(TripInfoId tripInfoId) {
        this.tripInfoId = tripInfoId;
    }
        
	public LocalDate getInitialDate() {
		return initialDate;
	}
	public void setInitialDate(LocalDate initialDate) {
		this.initialDate = initialDate;
	}
	public LocalDate getLastDate() {
		return lastDate;
	}
	public void setLastDate(LocalDate lastDate) {
		this.lastDate = lastDate;
	}
	
	@Override
	public String toString() {
		return "TripInfo [tripInfoId=" + tripInfoId + ", trip=" + trip + ", customer=" + customer + ", initialDate="
				+ initialDate + ", lastDate=" + lastDate + "]";
	}
	
}
