package service;

import ejbLocal.CustomerManagerEJBLocal;
import static encryption.EncryptionImplementation.decrypWithPrivateKey;
import entities.Customer;
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
        } catch (Exception e) {
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
        } catch (Exception e) {
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
        } catch (Exception e) {
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving customers with more than a week trips ");
        }
        return customers;
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createCustomer(Customer customer) {
        try {
            ejb.createCustomer(customer);
            LOGGER.log(Level.INFO, "Created customer with id: {0}", customer.getMail());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating customer", e);
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateCustomer(Customer customer, int encrypted) {
        try {
            if (encrypted == 1) {
                customer.setPassword(decrypWithPrivateKey(customer.getPassword()));
                ejb.updateCustomer(customer);
            } else {
                ejb.updateCustomer(customer);

            }
            LOGGER.log(Level.INFO, "Updated customer with id: {0}", customer.getMail());
        } catch (Exception e) {
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
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error deleting customer", e);
        }
    }
}
