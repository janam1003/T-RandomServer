package entities;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class TripInfoId {
	private Integer tripId;
	private Integer customerId;
}
