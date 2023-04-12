package com.project.todolist;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;

import java.time.LocalDate;

import org.apache.commons.cli.CommandLine;

/**
 * This is the main class of the project
 *
 * @author Alejandro Rosario
 * @version CPSC 240
 */
public class Main {


    /**
     * This method will call TodoList.createTodoList()
     *
     * @return the TodoList created
     */
    /* public static TodoList makeTodoList(String name) {

    }  */

    /**
     * This is the main method, which handles command line arguments and 
     * executing functions based off of them
     * 
     * @param args the array of arguments fed to the program
     */
    public static void main(String[] args) {
        Options options = new Options();

        Option daemon = new Option("d", "daemon", false, "Start the reminder daemon");
        options.addOption(daemon);
        Option gui = new Option("g", "gui", false, "Start the gui interface for the program"); 
        options.addOption(gui);

        CommandLineParser parser = new DefaultParser(); 
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            formatter.printHelp("todo-gui", options);
            System.exit(1);
        }

        if (cmd.hasOption(daemon)) {
            Daemon d = new Daemon();
            Item item = new Item("test", LocalDate.parse("2023-04-12"));
            d.sendMessage(item);
            d.sendNotification(item);
        } else if (cmd.hasOption(gui)) {
            System.out.println("This will start the gui");
        } else {
            formatter.printHelp("todo-gui", options);
            System.exit(0);
        }
    }
}
