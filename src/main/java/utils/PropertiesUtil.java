package utils;

/**
 * 用于加载配置
 */

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Description: <br/>
 * Date: 2017年12月19日 上午10:27:20 <br/>
 *
 * @author xuC
 * @version
 * @see
 */
public class PropertiesUtil {

    private static java.util.Properties props;

    static {
        loadProps();
    }

    /**
     * 加载配置
     */
    synchronized static private void loadProps() {
        props = new Properties();
        InputStream in = null;
        try {
            // <!--第一种，通过类加载器进行获取properties文件流-->
            if (!Objects.isNull(ClassLoader.getSystemClassLoader().getResource("application.yml"))) {
                String path = ClassLoader.getSystemClassLoader().getResource("application.yml").getPath();
                props = yaml2Properties(path);
            }

            if (!Objects.isNull(ClassLoader.getSystemClassLoader().getResource("application.properties"))) {
                in = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties");
                props.load(in);
            }
            // <!--第二种，通过类进行获取properties文件流-->
            // in = Properties.class.getResourceAsStream("/jdbc.properties");
        } catch (FileNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
        System.out.println("Configuration load complete！");
    }

    public static String getProperty(String key) {
        if (null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if (null == props) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }

    private static Properties yaml2Properties(String path) {
        final String DOT = ".";
        Properties properties = new Properties();

        YAMLFactory yamlFactory = new YAMLFactory();
        try {
            YAMLParser parser = yamlFactory.createParser(new InputStreamReader(new FileInputStream(path), Charset.forName("utf-8")));

            String key = "";
            String value = "";
            JsonToken token = parser.nextToken();
            while (token != null) {
                if (JsonToken.START_OBJECT.equals(token)) {
                    // do nothing
                } else if (JsonToken.FIELD_NAME.equals(token)) {
                    if (key.length() > 0) {
                        key = key + ".";
                    }
                    key = key + parser.getCurrentName();

                    token = parser.nextToken();
                    if (JsonToken.START_OBJECT.equals(token)) {
                        continue;
                    }
                    value = parser.getText();
                    properties.setProperty(key, value);
                    int dotOffset = key.lastIndexOf(DOT);
                    if (dotOffset > 0) {
                        key = key.substring(0, dotOffset);
                    }
                    value = null;
                } else if (JsonToken.END_OBJECT.equals(token)) {
                    int dotOffset = key.lastIndexOf(DOT);
                    if (dotOffset > 0) {
                        key = key.substring(0, dotOffset);
                    } else {
                        key = "";
                    }
                }
                token = parser.nextToken();
            }
            parser.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}

