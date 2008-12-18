package plenix.copier;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sourceforge.yamlbeans.YamlConfig;
import net.sourceforge.yamlbeans.YamlReader;
import plenix.copier.Copier;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getSimpleName());
    
    private static final YamlConfig config = new YamlConfig();
    static {
        try {
            Enumeration<URL> locations = Main.class.getClassLoader().getResources("recordCopierConfig.yaml");
            while (locations.hasMoreElements()) {
                URL location = locations.nextElement();
                YamlReader reader = new YamlReader(new InputStreamReader(location.openStream()));

                @SuppressWarnings("unchecked")
                Map<String, String> tagMap = (Map<String, String>) reader.read();
                if (tagMap != null) {
                    if (logger.isLoggable(Level.FINE)) logger.fine("Loading tags from '" + location + "'");
                    for (String tagName: tagMap.keySet()) {
                        String className = tagMap.get(tagName);
                        try {
                            Class<?> clazz = Class.forName(className);
                            config.setClassTag(tagName, clazz);
                            if (logger.isLoggable(Level.FINE)) logger.fine("Tag '" + tagName + "' maps to class '" + clazz.getName() + "'");
                        } catch (Exception e) {
                            logger.severe("Error loading class '" + className + "': " + e);
                        }
                    }
                }
                
                @SuppressWarnings("unchecked")
                Map<String, Map<String, String>> classMap = (Map<String, Map<String, String>>) reader.read();
                if (classMap != null) {
                    if (logger.isLoggable(Level.FINE)) logger.fine("Loading property element types from '" + location + "'");
                    for (String className: classMap.keySet()) {
                        try {
                            Class<?> targetClass = Class.forName(className);
                            Map<String, String> propertyNameMap  = classMap.get(className);
                            for (String propertyName: propertyNameMap.keySet()) {
                                String propertyClassName = propertyNameMap.get(propertyName);
                                Class<?> propertyType = Class.forName(propertyClassName);
                                config.setPropertyElementType(targetClass, propertyName, propertyType);
                                if (logger.isLoggable(Level.FINE)) logger.fine("Property '" + propertyName + "' of class '" + className + "' is of type '" + propertyType + "'");
                            }
                        } catch (Exception e) {
                            logger.severe("Error setting property type for '" + className + "': " + e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        String yamlFilename = args[0];
        logger.info("Copier: processing config file: " + yamlFilename);

        YamlReader reader = new YamlReader(new FileReader(yamlFilename), config);

        Copier<?> copier = reader.read(Copier.class);
        
        long start = System.currentTimeMillis();
        copier.copy();
        long end = System.currentTimeMillis();
        
        logger.info("Elapsed time: " + ((end - start) / 1000) + " seconds");
    }
}
