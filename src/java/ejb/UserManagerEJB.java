package ejb;

import ejbLocal.UserManagerEJBLocal;
import entities.User;
import exception.CreateException;
import exception.DeleteException;
import exception.ReadException;
import exception.UpdateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EJB class for managing User entity CRUD operations..
 *
 * @author danid
 */
@Stateless
public class UserManagerEJB implements UserManagerEJBLocal {

    /**
     * The EntityManager is used to interact with the persistence context and
     * manage User entities.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Logger for logging messages related to user management operations.
     */
    private static final Logger LOGGER = Logger.getLogger(CustomerManagerEJB.class.getName());

    /**
     * Creates a new user in the system.
     *
     * @param user The User object to be created.
     * @throws CreateException If there is an error creating the user.
     */
    @Override
    public void createUser(User user) throws CreateException {
        try {
            entityManager.persist(user);
            LOGGER.log(Level.INFO, "Created user with email: {0}", user.getMail());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating user", e);
            throw new CreateException(e.getMessage());

        }
    }

    /**
     * Updates an existing user in the system.
     *
     * @param user The User object with updated information.
     * @throws UpdateException If there is an error updating the user.
     */
    @Override
    public void updateUser(User user) throws UpdateException {
        try {
            entityManager.merge(user);
            LOGGER.log(Level.INFO, "Updated user with email: {0}", user.getMail());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating customer", e);
            throw new UpdateException(e.getMessage());
        }
    }

    /**
     * Deletes a user from the system using the user's ID.
     *
     * @param userId The ID of the user to be deleted.
     * @throws DeleteException If there is an error deleting the user.
     */
    @Override
    public void deleteUser(String userId) throws DeleteException {
        try {
            User user = entityManager.find(User.class, userId);
            if (user != null) {
                entityManager.remove(user);
                LOGGER.log(Level.INFO, "Deleted user with id: {0}", userId);

            } else {
                LOGGER.log(Level.INFO, "User with id {0} not found", userId);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error deleting user", e);
            throw new DeleteException(e.getMessage());
        }
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param mail The email address of the user.
     * @return The user with the specified email address.
     * @throws ReadException If there is an error retrieving the user by email.
     */
    @Override
    public User findUserByMail(String mail) throws ReadException {
        User user = null;
        try {
            user = entityManager.createNamedQuery("User.findByEmail", User.class).setParameter("email", mail).getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving customer by mail: " + mail, e);
            throw new ReadException(e.getMessage());
        }
        return user;
    }

}
