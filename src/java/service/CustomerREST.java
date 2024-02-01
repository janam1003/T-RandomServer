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
            throw new InternalServerErrorException(e);
        }
        return customers;
    }

    @GET
    @Path("/byMail/{mail}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Customer getCustomerByMail(@PathParam("mail") String mail) {
        Customer customer = null;
        try {
            customer = ejb.findCustomerByMail(mail);
            LOGGER.log(Level.INFO, "Retrieved customer by mail: {0}", mail);
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving customer by mail: " + mail, e);
            throw new InternalServerErrorException(e);
        }
        return customer;
    }

    @GET
    @Path("/withTrips")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Customer> getCustomersWithTrips() {
        List<Customer> customers = null;
        try {
            customers = ejb.findCustomersWithTrips();
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving customers with trips", e);
            throw new InternalServerErrorException(e);
        }
        return customers;
    }

    @GET
    @Path("/CreationDate")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Customer> getCustomersOrderByCreationDate() {
        List<Customer> customers = null;
        try {
            customers = ejb.findAllOrderByCreationDate();
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving customers by cretion date: ");
            throw new InternalServerErrorException(e);
        }
        return customers;
    }

    @GET
    @Path("/MoreThanOneWeekTrips")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Customer> getCustomersOneWeekTrips() {
        List<Customer> customers = null;
        try {
            customers = ejb.findOneWeekTrips();
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving customers with more than a week trips ");
            throw new InternalServerErrorException(e);
        }
        return customers;
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createCustomer(Customer customer) {
        try {
            System.out.println("customer desencriptar = " + customer + "CUSTOMER REST RECIBE = " + decrypWithPrivateKey(customer.getPassword()) + " y ahora hasheado= " + generateHash(customer.getPassword()));
            customer.setPassword(generateHash(decrypWithPrivateKey(customer.getPassword())));
            ejb.createCustomer(customer);
            LOGGER.log(Level.INFO, "Created customer with id: {0}", customer.getMail());
        } catch (CreateException e) {
            LOGGER.log(Level.SEVERE, "Error creating customer", e);
            throw new InternalServerErrorException(e);
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateCustomer(Customer customer, @PathParam("encrypted") boolean encrypted) {
        try {
            LOGGER.info("Updating customer");
            customer.setPassword(generateHash(customer.getPassword()));
            ejb.updateCustomer(customer, encrypted);
            LOGGER.log(Level.INFO, "Updated customer with id: {0}", customer.getMail());
        } catch (UpdateException e) {
            LOGGER.log(Level.SEVERE, "Error updating customer", e);
            throw new InternalServerErrorException(e);
        }
    }

    @DELETE
    @Path("/Delete/{id}")
    public void deleteCustomer(@PathParam("id") String id) {
        try {
            ejb.deleteCustomer(id);
            LOGGER.log(Level.INFO, "Deleted customer with id: {0}", id);
        } catch (DeleteException e) {
            LOGGER.log(Level.SEVERE, "Error deleting customer", e);
            throw new InternalServerErrorException(e);
        }
    }

    @PUT
    @Path("sendRecoveryEmail")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void sendRecoveryEmail(Customer customer) throws ReadException {
        try {
            ejb.sendRecoveryMail(customer);
            LOGGER.info("Recovery email sent successfully");
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error sending a recovery mail to a customer", e.getMessage());
            throw new InternalServerErrorException(e);

        }
    }
}
