package dataProvider;

import org.openxmlformats.schemas.drawingml.x2006.chart.STHPercentWithSymbol;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {
    private static Properties properties;
    private static final String ConfigFilePath = "src/test/java/dataProvider/ConfigFileReader.java";
    static {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(ConfigFilePath));
            properties = new Properties();

            try {
                properties.load(bufferedReader);
                bufferedReader.close();
            }
            catch (IOException ioException){
                System.out.println("Exception while reading the file: "+ioException);
            }
        }
        catch (FileNotFoundException fileNotFoundException){
            System.out.println("File is not present at the defined location: "+fileNotFoundException);
        }
    }

    public static String getUrl(){
        String siteUrl = properties.getProperty("url");
        if (siteUrl != null)
            return siteUrl;
        else throw new RuntimeException("url is not defined in to config.properties file");
    }

    public static String getMode(){
        String headlessModeValue = properties.getProperty("headless");
        if (headlessModeValue != null)
        return headlessModeValue;
        else throw new RuntimeException("Headless mode is not defined in to the config.properties file");
    }

    public static String getBrowser(){
        String browser = properties.getProperty("browser");
        if (browser != null)
            return browser;
        else throw new RuntimeException("browser value is not defined in to config.properties file");
    }

    public static String getImplicitWait(){
        String implicitWait = properties.getProperty("implicitWait");
        if (implicitWait != null)
            return implicitWait;
        else throw new RuntimeException("implicitWait is not defined in to config.properties file");
    }

    public static String getPageLoadTimeOut(){
        String pageLoadTimeOut = properties.getProperty("pageLoadTimeOut");
        if (pageLoadTimeOut != null)
            return pageLoadTimeOut;
        else throw new RuntimeException("pageLoadTimeOut is not defined in to config.properties file");
    }

    public static String getTestDataFilePath(){
        String testDataFilePath = properties.getProperty("testDataFilePath");
        if (testDataFilePath != null)
            return testDataFilePath;
        else throw new RuntimeException("testDataFilePath is not defined in to config.properties file");
    }

    public static String getUserName(){
        String userName = properties.getProperty("username");
        if (userName != null)
            return userName;
        else throw new RuntimeException("username is not defined in to config.properties file");
    }

    public static String getPassword(){
        String password = properties.getProperty("password");
        if (password != null)
            return password;
        else throw new RuntimeException("password is not defined in to config.properties file");
    }
}
