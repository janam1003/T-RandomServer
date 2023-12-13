package entities;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Janam
 */
public class TripInfo {
	TripInfoId tripInfoId;
	Date initialDate;
	Date lastDate;
	Integer maxCustomer;
	List <Customer> customerList;

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
	public Integer getMaxCustomer() {
		return maxCustomer;
	}
	public void setMaxCustomer(Integer maxCustomer) {
		this.maxCustomer = maxCustomer;
	}
	public List<Customer> getCustomerList() {
		return customerList;
	}
	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}
	
}
