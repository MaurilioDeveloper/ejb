package com.rci.omega2.ejb.bo;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.utils.AppConstants;

@Singleton
@Startup
public class ConfigFileBO extends BaseBO{

    private static final Logger LOGGER = LogManager.getLogger(ConfigFileBO.class);
    
    private Properties properties;
    
    @PostConstruct
    public void init() {
       
        try {
            LOGGER.debug(" >> INIT init ");
            
            InputStream inputStream = new FileInputStream(System.getProperty("configPath") + "/config.properties");
            properties = new Properties();
            // Loading the properties
            properties.load(inputStream);
            //Printing the propertie
            
            LOGGER.debug(" >> END init ");
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
        }
    }
    
    @Lock(LockType.READ)
    public String getProperty( String key ) { 
       LOGGER.debug(" >> INIT getProperty ");
       String temp = properties.getProperty(key); 
       LOGGER.debug(" >> END getProperty ");
       return temp;

    }
    /**
     * For unit tests
     * @return
     */
    public boolean isLoaded() {
        LOGGER.debug(" >> INIT isLoaded ");
        boolean temp = properties != null;
        LOGGER.debug(" >> END isLoaded ");
        return temp;
    }
    
}
