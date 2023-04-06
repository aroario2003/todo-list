package com.project.todolist;

import java.util.HashMap;
import java.util.Properties;
import java.util.Date;

import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.io.IOException;
import java.awt.Toolkit;
import java.awt.AWTException;
import java.awt.Image;

import javax.mail.Authenticator;
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * This class will handle sending all notifications and starting a background process
 * which will check due dates of items in todo lists on an interval
 *
 * @author Alejandro Rosario
 * @version CPSC 240
 */
public class Daemon {

    /**
     * The constructor does nothing because it is not neccessary, however in order to declare instances  
     * the constructor must be present
     */
    public Daemon() {}
   
    /**
     * This method will spawn a thread and fork it into the background to check for due items on 
     * a specified interval
     */
    public void daemon() {

    }

    /**
     * This method will send a notification to the user when an item is due, should check OS
     *
     * @param item the item to send the notification about
     */
    public synchronized void sendNotification(Item item) {
        String os = System.getProperty("os.name");
        if (os.contains("Linux")) {
            ProcessBuilder process = new ProcessBuilder(
                "notify-send",
                "todo-gui Reminder",
                "The item " + item.getName() + " is due on " + item.getDueDate()
            );
            try {
                process.inheritIO().start();
            } catch(IOException e) {
                e.printStackTrace();
            }
        } else if (os.contains("Mac")) {
            ProcessBuilder process = new ProcessBuilder(
                "osascript", "-e", 
                "display notification \"" 
                + "The item " 
                + item.getName() 
                + " is due on \"" 
                + item.getDueDate() 
                + "\""
                + " with title \"" 
                + "todo-gui Reminder" 
                + "\""
            );
        } else if (SystemTray.isSupported()) {
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");

        TrayIcon trayIcon = new TrayIcon(image, "todo-gui");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("todo-gui");
        try {
            tray.add(trayIcon);
        } catch(AWTException e) {
            e.printStackTrace();
        } 
        trayIcon.displayMessage(
                "todo-gui Reminder", 
                "The item " + item.getName() + " is due on " + item.getDueDate(), 
                MessageType.INFO
            );
        } else {
            System.out.println("Your operating system may not be supported for notifications");
            System.exit(1);
        }
    }

    /**
     * This method will send a text message to the user when an item is due, should not check OS
     *
     * @param item the item to send the message about
     */
    public synchronized void sendMessage(Item item) {
        Config conf = new Config(false);
        HashMap<String, String> userConfig =  Config.getUserConfigMap();
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", userConfig.get("port"));
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userConfig.get("email"), userConfig.get("password"));
            }
        };

        Session se = Session.getInstance(props, auth);

        try {
            MimeMessage msg = new MimeMessage(se);
            msg.addHeader("Content-type", "text/html; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(userConfig.get("email"), "todo-gui"));
            msg.setReplyTo(InternetAddress.parse(userConfig.get("email"), false));
            msg.setSubject("todo-gui reminder", "UTF-8");
            msg.setText("Some item is due", "UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(
                Message.RecipientType.TO, 
                InternetAddress.parse(
                    userConfig.get("phonenumber") + "@" + conf.determineCarrierExtension(userConfig.get("carrier")), 
                    false
                )
            );
            
            Transport.send(msg);
        } catch (Exception e) {
            System.out.println("Error sending reminder message");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
