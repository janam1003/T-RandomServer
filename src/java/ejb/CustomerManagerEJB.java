package ejb;

import ejbLocal.CustomerManagerEJBLocal;
import entities.Customer;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * EJB for managing Customer entities.
 */
@Stateless
public class CustomerManagerEJB {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger LOGGER = Logger.getLogger(CustomerManagerEJB.class.getName());

    /**
     * Retrieves all customers.
     *
     * @return A list of all customers.
     */
    public List<Customer> findAllCustomers() {
        try {
            TypedQuery<Customer> query = entityManager.createNamedQuery("findAllCustomers", Customer.class);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving all customers", e);
            throw new RuntimeException("Error retrieving all customers", e);
        }
    }

    /**
     * Retrieves a customer by their email address.
     *
     * @param mail The email address of the customer.
     * @return The customer with the specified email address.
     */
    public Customer findCustomerByMail(String mail) {
        try {
            TypedQuery<Customer> query = entityManager.createNamedQuery("findCustomersByMail", Customer.class);
            query.setParameter("mail", mail);
            return query.getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving customer by mail: " + mail, e);
            throw new RuntimeException("Error retrieving customer by mail: " + mail, e);
        }
    }

    /**
     * Retrieves all customers with associated trips.
     *
     * @return A list of customers with associated trips.
     */
    public List<Customer> findCustomersWithTrips() {
        try {
            TypedQuery<Customer> query = entityManager.createNamedQuery("Customer.findWithTrips", Customer.class);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving customers with trips", e);
            throw new RuntimeException("Error retrieving customers with trips", e);
        }
    }

    /**
     * Retrieves all customers with a specific address.
     *
     * @param customerAddress The address to filter by.
     * @return A list of customers with the specified address.
     */
    public List<Customer> findCustomersByAddress(String customerAddress) {
        try {
            TypedQuery<Customer> query = entityManager.createNamedQuery("Customer.findByAddress", Customer.class);
            query.setParameter("customerAddress", customerAddress);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving customers by address: " + customerAddress, e);
            throw new RuntimeException("Error retrieving customers by address: " + customerAddress, e);
        }
    }

    /**
     * Retrieves all customers whose name contains a specified partial name.
     *
     * @param partialName The partial name to search for.
     * @return A list of customers with names containing the specified partial name.
     */
    public List<Customer> findCustomersByNameContaining(String partialName) {
        try {
            TypedQuery<Customer> query = entityManager.createNamedQuery("Customer.findByNameContaining", Customer.class);
            query.setParameter("partialName", "%" + partialName + "%");
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving customers by name containing: " + partialName, e);
            throw new RuntimeException("Error retrieving customers by name containing: " + partialName, e);
        }
    }

    /**
     * Creates a new customer.
     *
     * @param customer The customer to be created.
     */
    public void createCustomer(Customer customer) {
        try {
            entityManager.persist(customer);
            LOGGER.log(Level.INFO, "Created customer with email: {0}", customer.getMail());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating customer", e);
            throw new RuntimeException("Error creating customer", e);
        }
    }

    /**
     * Updates an existing customer.
     *
     * @param customer The customer to be updated.
     */
    public void updateCustomer(Customer customer) {
        try {
            entityManager.merge(customer);
            LOGGER.log(Level.INFO, "Updated customer with id: {0}", customer.getMail());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating customer", e);
            throw new RuntimeException("Error updating customer", e);
        }
    }

    /**
     * Deletes a customer by their ID.
     *
     * @param customerId The ID of the customer to be deleted.
     */
    public void deleteCustomer(Long customerId) {
        try {
            Customer customer = entityManager.find(Customer.class, customerId);
            if (customer != null) {
                entityManager.remove(customer);
                LOGGER.log(Level.INFO, "Deleted customer with id: {0}", customerId);
            } else {
                LOGGER.log(Level.WARNING, "Customer with id {0} not found", customerId);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error deleting customer", e);
            throw new RuntimeException("Error deleting customer", e);
        }
    }
}
