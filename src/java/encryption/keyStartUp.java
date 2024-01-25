/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryption;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

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
