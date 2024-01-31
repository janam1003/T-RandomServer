package service;

import ejbLocal.CustomerManagerEJBLocal;
import static encryption.EncryptionImplementation.decrypWithPrivateKey;
import static encryption.EncryptionImplementation.generateHash;
import entities.Customer;
import exception.CreateException;
import exception.DeleteException;
import exception.ReadException;
import exception.UpdateException;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("customer")
public class CustomerREST {

    @EJB
    private CustomerManagerEJBLocal ejb;

    private static final Logger LOGGER = Logger.getLogger(CustomerREST.class.getName());

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Customer> getAllCustomers() {
        List<Customer> customers = null;
        try {
            customers = ejb.findAllCustomers();
            LOGGER.log(Level.INFO, "Retrieved all customers");
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving all customers", e);
        }
        return customers;
    }

    @GET
    @Path("/byMail/{mail}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Customer getCustomerByMail(@PathParam("mail") String mail) {
        Customer customer = null;
        try {
            customer = ejb.findCustomerByMail(mail);
            LOGGER.log(Level.INFO, "Retrieved customer by mail: {0}", mail);
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving customer by mail: " + mail, e);
        }
        return customer;
    }

    @GET
    @Path("/withTrips")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Customer> getCustomersWithTrips() {
        List<Customer> customers = null;
        try {
            customers = ejb.findCustomersWithTrips();
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving customers with trips", e);
        }
        return customers;
    }

    @GET
    @Path("/CreationDate")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Customer> getCustomersOrderByCreationDate() {
        List<Customer> customers = null;
        try {
            customers = ejb.findAllOrderByCreationDate();
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving customers by address: ");
        }
        return customers;
    }

    @GET
    @Path("/MoreThanOneWeekTrips")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Customer> getCustomersOneWeekTrips() {
        List<Customer> customers = null;
        try {
            customers = ejb.findOneWeekTrips();
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving customers with more than a week trips ");
        }
        return customers;
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createCustomer(Customer customer) {
        try {
            customer.setPassword(generateHash(decrypWithPrivateKey(customer.getPassword())));
            ejb.createCustomer(customer);
            LOGGER.log(Level.INFO, "Created customer with id: {0}", customer.getMail());
        } catch (CreateException e) {
            LOGGER.log(Level.SEVERE, "Error creating customer", e);
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateCustomer(Customer customer, @PathParam("encrypted") boolean encrypted) {
        try {
            customer.setPassword(generateHash(customer.getPassword()));
            ejb.updateCustomer(customer, encrypted);
            LOGGER.log(Level.INFO, "Updated customer with id: {0}", customer.getMail());
        } catch (UpdateException e) {
            LOGGER.log(Level.SEVERE, "Error updating customer", e);
        }
    }

    /**
     * Test para push
     *
     * @param id
     * @return
     */
    @DELETE
    @Path("/Delete/{id}")
    public void deleteCustomer(@PathParam("id") String id) {
        try {
            ejb.deleteCustomer(id);
            LOGGER.log(Level.INFO, "Deleted customer with id: {0}", id);
        } catch (DeleteException e) {
            LOGGER.log(Level.SEVERE, "Error deleting customer", e);
        }
    }

    @PUT
    @Path("sendRecoveryEmail")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void sendRecoveryEmail(Customer entity) {
        try {
            ejb.sendRecoveryMail(entity);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "Error sending a recovery mail to a customer", ex.getMessage());
        }

    }
}
