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
 *
 * @author danid
 */
@Stateless
public class UserManagerEJB implements UserManagerEJBLocal {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger LOGGER = Logger.getLogger(CustomerManagerEJB.class.getName());

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
     * Retrieves a customer by their email address.
     *
     * @param mail The email address of the customer.
     * @return The user with the specified email address.
     * @throws ReadException If there is any Exception during processing.
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
