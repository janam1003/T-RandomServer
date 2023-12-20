package service;

import ejb.CustomerManagerEJB;
import entities.Customer;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("customer")
public class CustomerREST{

    @EJB
    private CustomerManagerEJB customerManagerEJB;

    private static final Logger LOGGER = Logger.getLogger(CustomerREST.class.getName());

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCustomers() {
        try {
            List<Customer> customers = customerManagerEJB.findAllCustomers();
            LOGGER.log(Level.INFO, "Retrieved all customers");
            return Response.ok(customers).build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving all customers", e);
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/byMail/{mail}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerByMail(@PathParam("mail") String mail) {
        try {
            Customer customer = customerManagerEJB.findCustomerByMail(mail);
            LOGGER.log(Level.INFO, "Retrieved customer by mail: {0}", mail);
            return Response.ok(customer).build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving customer by mail: " + mail, e);
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/withTrips")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomersWithTrips() {
        try {
            List<Customer> customers = customerManagerEJB.findCustomersWithTrips();
            return Response.ok(customers).build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving customers with trips", e);
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/byAddress/{customerAddress}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomersByAddress(@PathParam("customerAddress") String customerAddress) {
        try {
            List<Customer> customers = customerManagerEJB.findCustomersByAddress(customerAddress);
            return Response.ok(customers).build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving customers by address: " + customerAddress, e);
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/byNameContaining/{partialName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomersByNameContaining(@PathParam("partialName") String partialName) {
        try {
            List<Customer> customers = customerManagerEJB.findCustomersByNameContaining(partialName);
            return Response.ok(customers).build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving customers by name containing: " + partialName, e);
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createCustomer(Customer customer) {
        try {
            customerManagerEJB.createCustomer(customer);
            LOGGER.log(Level.INFO, "Created customer with id: {0}", customer.getMail());
            return Response.ok().build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating customer", e);
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updateCustomer(Customer customer) {
        try {
            customerManagerEJB.updateCustomer(customer);
            LOGGER.log(Level.INFO, "Updated customer with id: {0}", customer.getMail());
            return Response.ok().build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating customer", e);
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") Long id) {
        try {
            customerManagerEJB.deleteCustomer(id);
            LOGGER.log(Level.INFO, "Deleted customer with id: {0}", id);
            return Response.ok().build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error deleting customer", e);
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}

