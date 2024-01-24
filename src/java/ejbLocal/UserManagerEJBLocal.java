package ejbLocal;

import entities.User;
import exception.CreateException;
import exception.DeleteException;
import exception.ReadException;
import exception.UpdateException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author danid
 */
@Local
public interface UserManagerEJBLocal {
    
    
     /**
     * Creates a new user.
     *
     * @param user The User to be created.
     * @throws CreateException If there is any Exception during processing.
     */
    public void createUser(User user) throws CreateException;

    /**
     * Updates an existing customer.
     *
     * @param user The customer to be updated.
     * @throws UpdateException If there is any Exception during processing.
     */
    public void updateUser(User user) throws UpdateException;

    /**
     * Deletes a customer by their ID.
     *
     * @param userId The ID of the customer to be deleted.
     * @throws DeleteException If there is any Exception during processing.
     */
    public void deleteUser(String userId) throws DeleteException;
        /**
     * Retrieves a customer by their email address.
     *
     * @param mail The email address of the customer.
     * @return The user with the specified email address.
     * @throws ReadException If there is any Exception during processing.
     */
    public User findUserByMail(String mail) throws ReadException;
    
    /**
     * Method to send an user a new password  to recover an mail
     * 
     * @param user to send an user a new password
     * @throws ReadException If there is any Exception during processing.
     */
    public void sendRecoveryMail(User user) throws ReadException;
    
}
