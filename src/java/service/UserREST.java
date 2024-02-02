package service;

import ejbLocal.UserManagerEJBLocal;
import static encryption.EncryptionImplementation.decrypWithPrivateKey;
import static encryption.EncryptionImplementation.generateHash;
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

/**
 * RESTful web service for managing users.
 *
 * @author dani
 */
@Path("/users")
public class UserREST {

    // Injecting the UserManagerEJBLocal EJB for handling user-related operations.
    @EJB
    private UserManagerEJBLocal userManagerEJB;

    // Logger for logging messages.
    private static final Logger LOGGER = Logger.getLogger(CustomerREST.class.getName());

    /**
     * Creates a new user.
     *
     * @param user User object to be created.
     */
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

    /**
     * Updates an existing user.
     *
     * @param user User object with updated information.
     */
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

    /**
     * Deletes a user by userId.
     *
     * @param userId The ID of the user to be deleted.
     */
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

    /**
     * Retrieves a user by email.
     *
     * @param mail User's email address.
     * @return User object in JSON or XML format.
     */
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

    /**
     * Handles user sign-in.
     *
     * @param user User object with login credentials.
     * @return User object with sensitive information removed or null if
     * authentication fails.
     */
    @POST
    @Path("signIn")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public User signIn(User user) {
        User userReturn = null;
        try {
            userReturn = userManagerEJB.findUserByMail(user.getMail());
            if (userReturn.getPassword().equals(generateHash(decrypWithPrivateKey(user.getPassword())))) {
                userReturn.setPassword(null);
                return new User(userReturn.getMail(), null, userReturn.getCreationDate(), userReturn.getUserType());
            } else {
                return null;
            }

        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving customer by mail: " + user.getMail(), e);

        } catch (Exception ex) {
            Logger.getLogger(UserREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

}
