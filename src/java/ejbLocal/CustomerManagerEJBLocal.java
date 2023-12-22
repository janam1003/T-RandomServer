/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbLocal;

import entities.Customer;

import javax.ejb.Local;
import java.util.List;

/**
 * Local interface for managing Customer entities.
 */
@Local
public interface CustomerManagerEJBLocal {

    /**
     * Retrieves all customers.
     *
     * @return A list of all customers.
     */
    List<Customer> findAllCustomers();

    /**
     * Retrieves a customer by their email address.
     *
     * @param mail The email address of the customer.
     * @return The customer with the specified email address.
     */
    Customer findCustomerByMail(String mail);

    /**
     * Retrieves all customers with associated trips.
     *
     * @return A list of customers with associated trips.
     */
    List<Customer> findCustomersWithTrips();
    /**
     * Retrieves all customers ordered by they day they where created.
     *
     * @return A list of customers with customers ordered by they day they where created.
     */
    
    List<Customer> findAllOrderByCreationDate();
    /**
     * Retrieves all customers with trips longer than one week.
     *
     * @return A list of customers trips longer than one week.
     */
    
    
    List<Customer> findOneWeekTrips();
        /**
     * Creates a new customer.
     *
     * @param customer The customer to be created.
     */
    void createCustomer(Customer customer);

    /**
     * Updates an existing customer.
     *
     * @param customer The customer to be updated.
     */
    void updateCustomer(Customer customer);

    /**
     * Deletes a customer by their ID.
     *
     * @param customerId The ID of the customer to be deleted.
     */
    void deleteCustomer(String customerId);
}
