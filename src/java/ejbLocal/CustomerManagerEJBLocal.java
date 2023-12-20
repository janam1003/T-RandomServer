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
     * Retrieves all customers with a specific address.
     *
     * @param customerAddress The address to filter by.
     * @return A list of customers with the specified address.
     */
    List<Customer> findCustomersByAddress(String customerAddress);

    /**
     * Retrieves all customers whose name contains a specified partial name.
     *
     * @param partialName The partial name to search for.
     * @return A list of customers with names containing the specified partial name.
     */
    List<Customer> findCustomersByNameContaining(String partialName);

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
    void deleteCustomer(Long customerId);
}
