package service;

import ejbLocal.UserManagerEJBLocal;
import static encryption.EncryptionImplementation.decrypWithPrivateKey;
import static encryption.EncryptionImplementation.generateHash;
import entities.Customer;
import entities.EnumUserType;
import entities.User;
import exception.CreateException;
import exception.DeleteException;
import exception.ReadException;
import exception.UpdateException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/users")
public class UserREST {

    @EJB
    private UserManagerEJBLocal userManagerEJB;

    private static final Logger LOGGER = Logger.getLogger(CustomerREST.class.getName());

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createUser(User user) {
        try {
            LOGGER.log(Level.INFO, "Creating user");
            userManagerEJB.createUser(user);
        } catch (CreateException e) {
            LOGGER.log(Level.SEVERE,
                    "Error creating user",
                    e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateUser(User user) {
        try {
            LOGGER.log(Level.INFO, "Updating user");
            userManagerEJB.updateUser(user);
        } catch (UpdateException e) {
            LOGGER.log(Level.SEVERE,
                    "Error updating user",
                    e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    @DELETE
    @Path("/{userId}")
    public void deleteUser(@PathParam("userId") String userId) {
        try {
            LOGGER.log(Level.SEVERE,
                    "Deleting the customer");
            userManagerEJB.deleteUser(userId);
        } catch (DeleteException e) {
            LOGGER.log(Level.SEVERE,
                    "Error deleting the customer",
                    e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    @GET
    @Path("/byMail/{mail}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public User getUserByMail(@PathParam("mail") String mail) {
        User user = null;
        try {
            user = userManagerEJB.findUserByMail(mail);
            LOGGER.log(Level.INFO, "Retrieved customer by mail: {0}", mail);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving customer by mail: " + mail, e);
        }
        return user;
    }

    @POST
    @Path("signIn")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public User signIn(User usser) {
        User userReturn = null;
        try {
            userReturn = userManagerEJB.findUserByMail(usser.getMail()); 
            if (userReturn.getPassword().equals(generateHash(decrypWithPrivateKey(usser.getPassword())))) {
                userReturn.setPassword(null);
                return new User(userReturn.getMail(), null, userReturn.getCreationDate(), userReturn.getUserType());
            } else {
                return null;
            }

        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving customer by mail: " + usser.getMail(), e);

        } catch (Exception ex) {
            Logger.getLogger(UserREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

}
