package com.project.todolist;

import java.util.HashMap;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;

/**
 * This class makes and parses the configuration file in order to 
 * make SMTP and IMAP work
 *
 * @author Alejandro Rosario
 * @version CPSC 240
 */
public class Config {

    private final String EMAIL;
    private final String PASSWORD;
    private final String CARRIER;
    private final String PORT = "465";
    private final String CONFIGNAME = "config.json";
    private String phoneNumber;
    private Path configPath;
    private String interval;

    /**
     * The constructor to either parse the existing user config or make a default config as an example
     *
     * @param writeDefault determines whether to create a default config or not
     */
    public Config(boolean writeDefault) {
        this.EMAIL = "";
        this.PASSWORD = "";
        this.CARRIER = "";
        this.phoneNumber = "";
        if (writeDefault) {
            writeDefaultConfig();
        }
    }

    /**
     * The constructor for the Config class which takes in values for config keys
     *
     * @param email the email which should be used to send SMS messages over SMTP
     * @param password the app specific password that the user would like to use to use gmail with SMTP
     * @param carrier the carrier which the user uses on their mobile phone
     * @param phoneNumber the phone number to send the message to 
     */
    public Config(String email, String password, String carrier, String phoneNumber) {
        EMAIL = email;
        PASSWORD = password;
        CARRIER = carrier;
        this.phoneNumber = phoneNumber;
    }

    /**
     * This method will get the OS in order to make decisions in other parts of the
     * program
     *
     * @return the string which is the OS name
     */
    private String getOS() {
        return System.getProperty("os.name");
    } 

    /**
     * This method will determine which path should be used to store the 
     * config.json file based on the os which the user is on. If the path 
     * or config file do not exists it will create them.
    */
    public void determineUserConfigPath() {
        String os = getOS();
        if (os.contains("Windows")) {
            String configDirStr = "C:\\%PROGRAMDATA%\\todo-list\\config\\"; 
            File configDir = new File(configDirStr); 
            if (!configDir.exists()) {
                configDir.mkdirs();
            }
            this.configPath = Paths.get(configDirStr + this.CONFIGNAME);
            if (!this.configPath.toFile().exists()) {
                this.configPath.toFile().createNewFile();
            }
        } else if (os.contains("Linux")) {
            String home = System.getProperty("user.home");
            File configDir = new File(home + "/.config/todo-list");
            if (!configDir.isDirectory()) {
                configDir.mkdir();
            }
            this.configPath = Paths.get(home + "/.config/todo-list/" + this.CONFIGNAME);
            if (!this.configPath.toFile().exists()) {
                try {
                    this.configPath.toFile().createNewFile();
                } catch (IOException e) {
                    System.out.println("Oops something went wrong creating the config file!");
                    System.exit(1);
                }
            }
        } else {
            System.out.println("Operating system not supported!");
            System.exit(1);
        }
    }

    /**
     * This method will create a default config for the user
     * 
     * @return the corresponding hashmap which represents the configuration options and values
     */
    private HashMap<String, String> makeDefaultConfig() {
        HashMap<String, String> defaultConfig = new HashMap<>();
        defaultConfig.put("email", "example@gmail.com");
        defaultConfig.put("password", "yourpaswordhere");
        defaultConfig.put("carrier", "somecarrier");
        defaultConfig.put("port", this.PORT);
        defaultConfig.put("phonenumber", "1111111111");
        return defaultConfig;
    }

    /**
     * This method will serialize the hashmap returned by makeDefaultConfig() and write the json to 
     * the config.json file
     */
    public void writeDefaultConfig() {
        HashMap<String, String> defaultConf = makeDefaultConfig();
        String json = serializeData(defaultConf);
        configure(json);
    }

    /**
     * This method will create the config HashMap based off of the arguments to the constructor 
     *
     * @return the hashmap which represents keys and values in the config file
     */
    private HashMap<String, String> makeUserConfig() {
        HashMap<String, String> config = new HashMap<>();
        config.put("email", this.EMAIL);
        config.put("password", this.PASSWORD);
        config.put("carrier", this.CARRIER);
        config.put("port", this.PORT);
        config.put("phonenumber", this.phoneNumber);
        return config;
    }

    /**
     * This method will serialize the hashmap returned by makeUserConfig() and write the json to 
     * the config.json file
     */
    public void writeUserConfig() {
        HashMap<String, String> config = makeUserConfig();
        String json = serializeData(config);
        configure(json);
    }

    /**
     * This method will use a hashmap to determine the carrier extension to be used 
     *
     * @param carrier the carrier used by the user
     * @return the string which is the users carrier extension
     */ 
    public String determineCarrierExtension(String carrier) {
        HashMap<String, String> carriers = new HashMap<>();
        carriers.put("verizon", "vtext.com");
        carriers.put("tmobile", "tmomail.net");
        carriers.put("sprint", "messaging.sprintpcs.com");
        carriers.put("at&t", "txt.att.net");
        carriers.put("boost", "smsmyboostmobile.com");
        carriers.put("cricket", "sms.cricketwireless.net");
        carriers.put("uscellular", "email.uscc.net");
        String userCarrier = carriers.get(carrier);
        return userCarrier;
    }

    /** 
    * This method will parse the json from the config and deserialize into a hashmap
    * so that keys and values can be associated
    *
    * @return the hashmap which represents the keys and values in the config.json file
    */
    private HashMap<String, String> parseUserConfig() {
       String content = "";
       determineUserConfigPath(); 
       try {
           content = Files.readString(this.configPath); 
       } catch (IOException e) {
           System.out.println("File could not be read!");
           System.exit(1);
       }
       Gson gson = new Gson();
       HashMap<String, String> configJson = gson.fromJson(content, HashMap.class);
       return configJson;
    } 

    /**
     * Gets the hashmap which is returned from parseUserConfig()
     *
     * @return the resulting hashmap from parseUserConfig()
     */
    public static HashMap<String, String> getUserConfigMap() {
        Config conf = new Config(false);
        return conf.parseUserConfig();
    }

    /**
     * This method will take take the interval which the user puts into the 
     * config.json file and parse it into a format which is usable in the Code
     * such as if the user puts 2m than the method will do calculations to get
     * 2 minutes in miliseconds. Options for the interval config option include:
     * s m h d w M y.
     *
     * @return the new integer which represents the amount of miliseconds to wait before
     * running the daemon again
     */
    public int calculateInterval() {
        
    }

    /**
     * This method will write the config json string to the config.json file
     */
    public void configure(String json) {
       determineUserConfigPath(); 
       File config = new File(this.configPath.toString());
       if (!config.exists()) {
           try {
                config.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
       } else {
           try {
                FileWriter out = new FileWriter(config);
                out.write(json);
                out.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
    }

    /**
     * This method will serialize a hashmap of key value pairs into json
     *
     * @param config the hashmap to be serialized into json
     * @return the json string which results from serialization of the config hashmap
     */
    public String serializeData(HashMap<String, String> config) {
        Gson gson = new Gson();
        String json = gson.toJson(config);
        return json;
    }
}
