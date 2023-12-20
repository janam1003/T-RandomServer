/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
@Local(CustomerManagerEJBLocal.class)
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
}
