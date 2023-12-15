package entities;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Janam
 */
@Entity
@Table(name="admin",schema="g3CRUD")
public class Admin extends User{

    @Override
    public String toString() {
        return super.toString();
    }
    
    

}
