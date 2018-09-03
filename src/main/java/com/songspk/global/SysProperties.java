package com.songspk.global;

import java.io.IOException;
import java.util.Properties;

public class SysProperties {
    private static Properties prop = null;

    private SysProperties() {
    }

    public static Properties getInstance() {
        if (prop == null) {

            ClassLoader loader = SysProperties.class.getClassLoader();
            if (loader == null)
                loader = ClassLoader.getSystemClassLoader();

            String propFile = "application.properties";
            java.net.URL url = loader.getResource(propFile);
            prop = new Properties();

            try {
                prop.load(url.openStream());
            } catch (IOException ex) {
            }
        }

        return prop;
    }
    public static String getBaseDir() {
        return SysProperties.getInstance().getProperty("BASE_DIR");
    }
    public static String getProperty(String prop) {
        return SysProperties.getInstance().getProperty(prop);
    }

}
