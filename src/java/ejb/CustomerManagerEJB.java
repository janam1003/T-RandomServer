package ejb;

import ejbLocal.CustomerManagerEJBLocal;
import emailRecovery.mail;
import static encryption.EncryptionImplementation.decrypWithPrivateKey;
import static encryption.EncryptionImplementation.generateHash;
import entities.Customer;
import exception.CreateException;
import exception.DeleteException;
import exception.ReadException;
import exception.UpdateException;
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
public class CustomerManagerEJB implements CustomerManagerEJBLocal {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger LOGGER = Logger.getLogger(CustomerManagerEJB.class.getName());

    /**
     * Retrieves all customers.
     *
     * @return A list of all customers.
     * @throws ReadException If there is any Exception during processing.
     */
    @Override
    public List<Customer> findAllCustomers() throws ReadException {
        List<Customer> customers = null;
        try {
            customers = entityManager.createNamedQuery("Customer.findAllCustomers", Customer.class).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving all customers", e);
            throw new ReadException(e.getMessage());
        }
        return customers;
    }

    /**
     * Retrieves a customer by their email address.
     *
     * @param mail The email address of the customer.
     * @return The customer with the specified email address.
     * @throws ReadException If there is any Exception during processing.
     */
    @Override
    public Customer findCustomerByMail(String mail) throws ReadException {
        Customer customer = null;
        try {
            customer = entityManager.createNamedQuery("Customer.findByEmail", Customer.class).setParameter("email", mail).getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving customer by mail: " + mail, e);
            throw new ReadException(e.getMessage());
        }
        return customer;
    }

    /**
     * Retrieves all customers with associated trips.
     *
     * @return A list of customers with associated trips.
     * @throws ReadException If there is any Exception during processing.
     */
    @Override
    public List<Customer> findCustomersWithTrips() throws ReadException {
        try {
            TypedQuery<Customer> query = entityManager.createNamedQuery("Customer.findWithTrips", Customer.class);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving customers with trips", e);
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Retrieves all customers order by CreationDate.
     *
     * @return A list of customers order by CreationDate.
     * @throws ReadException If there is any Exception during processing.
     */
    @Override
    public List<Customer> findAllOrderByCreationDate() throws ReadException {
        List<Customer> customers = null;
        try {
            customers = entityManager.createNamedQuery("Customer.findAllOrderDate", Customer.class).getResultList();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving all customers", e);
            throw new ReadException(e.getMessage());
        }
        return customers;
    }

    /**
     * Retrieves all customers order by CreationDate.
     *
     * @return A list of customers order by CreationDate.
     * @throws ReadException If there is any Exception during processing.
     */
    @Override
    public List<Customer> findOneWeekTrips() throws ReadException {
        List<Customer> customers = null;
        try {
            customers = entityManager.createNamedQuery("Customer.findOneWeek", Customer.class).getResultList();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving all customers", e);
            throw new ReadException(e.getMessage());
        }
        return customers;
    }

    /**
     * Creates a new customer.
     *
     * @param customer The customer to be created.
     * @throws CreateException If there is any Exception during processing.
     */
    @Override
    public void createCustomer(Customer customer) throws CreateException {
        try {
            entityManager.persist(customer);
            LOGGER.log(Level.INFO, "Created customer with email: {0}", customer.getMail());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating customer", e);
            throw new CreateException(e.getMessage());
        }
    }

    /**
     * Updates an existing customer.
     *
     * @param customer The customer to be updated.
     * @throws UpdateException If there is any Exception during processing.
     */
    @Override
    public void updateCustomer(Customer customer, boolean encrypted) throws UpdateException {

        try {
            if (encrypted == true) {
                customer.setPassword(generateHash(decrypWithPrivateKey(customer.getPassword())));
                entityManager.merge(customer);
                LOGGER.log(Level.INFO, "Updated customer with id: {0}", customer.getMail());
            } else {
                customer.setPassword(generateHash(customer.getPassword()));
                entityManager.merge(customer);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating customer", e);
            throw new UpdateException(e.getMessage());
        }

    }

    /**
     * Deletes a customer by their ID.
     *
     * @param customerId The ID of the customer to be deleted.
     * @throws DeleteException If there is any Exception during processing.
     */
    @Override
    public void deleteCustomer(String customerId) throws DeleteException {
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
            throw new DeleteException(e.getMessage());
        }
    }

    /**
     * Thsi method is to send an mail to customer for recovering its mail.
     *
     * @param customer mail to send an mail to recover password
     * @throws ReadException If there is any Exception during processing.
     */
    @Override
    public void sendRecoveryMail(Customer customer) throws ReadException {
        //mail recoverMail = new mail();
        try {
            String newPassword = mail.sendEmail(customer.getMail());
            customer.setPassword(newPassword);
            updateCustomer(customer, false);
        } catch (UpdateException e) {
            // Log or handle the specific exception related to customer update
            throw new ReadException("Error updating customer: " + e.getMessage());
        }
    }
}
