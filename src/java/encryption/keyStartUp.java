package encryption;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author 2dam
 */
@Singleton
@Startup
public class keyStartUp {

    @PostConstruct
    public void onStartup() {
        AsimetricEncryption.keyPairGenerator();
    }
}
