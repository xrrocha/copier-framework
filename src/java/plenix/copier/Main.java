package plenix.copier;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sourceforge.yamlbeans.YamlReader;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getSimpleName());
    
    private static final Map<String, Class<?>> classTagMap = new HashMap<String, Class<?>>();
    private static final Map<Class<?>, Map<String, Class<?>>> propertyMap = new HashMap<Class<?>, Map<String, Class<?>>>();
    
    static {
        try {
            Enumeration<URL> locations = Main.class.getClassLoader().getResources("recordConfig.yaml");
            while (locations.hasMoreElements()) {
                URL location = locations.nextElement();
                YamlReader reader = new YamlReader(new InputStreamReader(location.openStream()));

                if (logger.isLoggable(Level.FINE)) logger.fine("INIT: Loading tags from '" + location + "'");
                @SuppressWarnings("unchecked")
                Map<String, String> tagMap = (Map<String, String>) reader.read();
                if (tagMap != null) {
                    for (String tagName: tagMap.keySet()) {
                        String className = tagMap.get(tagName);
                        try {
                            Class<?> clazz = Class.forName(className);
                            if (logger.isLoggable(Level.FINE)) logger.fine("INIT: Tag '" + tagName + "' maps to class '" + clazz.getName() + "'");
                            classTagMap.put(tagName, clazz);
                        } catch (Exception e) {
                            logger.severe("Error loading class '" + className + "'");
                        }
                    }
                }
                
                if (logger.isLoggable(Level.FINE)) logger.fine("INIT: Loading property element types from '" + location + "'");
                @SuppressWarnings("unchecked")
                Map<String, Map<String, String>> classMap = (Map<String, Map<String, String>>) reader.read();
                if (classMap != null) {
                    for (String className: classMap.keySet()) {
                        try {
                            Class<?> targetClass = Class.forName(className);
                            Map<String, String> propertyNameMap  = classMap.get(className);
                            Map<String, Class<?>> propertyClassMap = new HashMap<String, Class<?>>();
                            for (String propertyName: propertyNameMap.keySet()) {
                                String propertyClassName = propertyNameMap.get(propertyName);
                                Class<?> propertyClass = Class.forName(propertyClassName);
                                propertyClassMap.put(propertyName, propertyClass);
                            }
                            propertyMap.put(targetClass, propertyClassMap);
                        } catch (Exception e) {
                            logger.severe(e.toString());
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
        
        YamlReader reader = new YamlReader(new FileReader(yamlFilename));
        for (String tagName: classTagMap.keySet()) {
            reader.getConfig().setClassTag(tagName, classTagMap.get(tagName));
        }
        for (Class<?> targetClass: propertyMap.keySet()) {
            Map<String, Class<?>> propertyNameMap = propertyMap.get(targetClass);
            for (String propertyName: propertyNameMap.keySet()) {
                Class<?> propertyClass = propertyNameMap.get(propertyName);
                reader.getConfig().setPropertyElementType(targetClass, propertyName, propertyClass);
            }
        }

        Copier<?> copier = reader.read(Copier.class);
        
        copier.copy();
    }
}
