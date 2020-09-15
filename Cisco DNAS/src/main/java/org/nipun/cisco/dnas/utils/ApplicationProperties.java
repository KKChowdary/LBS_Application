package org.nipun.cisco.dnas.utils;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.NullArgumentException;

/**
 * Global Application Properties.
 *
 * @author ramanjaneyulu.p
 *
 */
public class ApplicationProperties {
    private static final ApplicationProperties _this = new ApplicationProperties();

    static Properties props;

    static {
        props = new Properties();

        InputStream in = null;
        try {
            in = _this.getClass().getResourceAsStream("/messages.properties");

            if (in == null) {
                throw new RuntimeException("\"messages.properties\" not found.");
            }

            props.load(in);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private ApplicationProperties() {
    }

    /**
     * NOTE: doing this for the purpose of testing! This class is an abortion and needs to be removed in place of something that is NOT
     * static and extenrally configurable.
     *
     * @param properties
     */
    public static void destroyProperties() {
        props = new Properties();
        System.err.println("Properties destoryed!");
    }

    /**
     * Returns application properties
     *
     * @return a <code>Properties</code> value
     */
    public static Properties getProperties() {
        return props;
    }

    /**
     * Convenience wrapper.
     *
     * @see getProperties
     *
     * @param key
     *            a <code>String</code> value
     * @return a <code>String</code> value
     */
    public static String getProperty(final String key) {
        return (String) getProperties().get(key);
    }

    /**
     * Convenience wrapper.
     *
     * @see getProperties
     *
     * @param key
     *            a <code>String</code> value
     * @return a <code>String</code> value
     */
    public static String getProperty(final String key, final String def) {
        String s = (String) getProperties().get(key);
        return s != null ? s : def;
    }

    /**
     * NOTE: doing this for the purpose of testing! This class is an abortion and needs to be removed in place of something that is NOT
     * static and externally configurable.
     *
     * @param properties
     */
    public static void setProperties(final Properties properties) {
        if (properties == null) {
            throw new NullArgumentException("properties");
        }

        props = properties;
    }

}
