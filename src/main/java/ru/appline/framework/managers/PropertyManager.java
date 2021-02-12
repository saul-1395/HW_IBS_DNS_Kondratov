package ru.appline.framework.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyManager {

    private static PropertyManager propertyManager;
    private final Properties properties = new Properties();

    public PropertyManager() {
        applicationProperties();
    }


    public static PropertyManager getPropertyManager() {
        if(propertyManager==null){
            propertyManager = new PropertyManager();
        }
        return propertyManager;
    }

    /**
     * получаем файл с расширением  Properties
     */
    private void applicationProperties() {
        try {
            properties.load(new FileInputStream(
                    new File("src/main/resources/" +
                            System.getProperty("castom", "application") + ".properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * метод получения значений по ключу
     */
    public String getProperties(String key) {
        System.out.println(" key " + key);
        return properties.getProperty(key);
    }
}
