package com.project.todolist;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.CommandLine;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * This is the main class of the project
 *
 * @author Alejandro Rosario
 * @author Victor Rahman
 * @author Sonia Vetter
 * @author Nora Peters
 *
 * @version CPSC 240
 */
public class Main {

    /**
     * This is the main method, which handles command line arguments and 
     * executing functions based off of them
     * 
     * @param args the array of arguments fed to the program
     */
    public static void main(String[] args) {
        Options options = new Options();

        Option help = new Option("h", "help", false, "Print this help menu");
        options.addOption(help);
        Option daemon = new Option("d", "daemon", false, "Start the reminder daemon");
        options.addOption(daemon);
        Option gui = new Option("g", "gui", false, "Start the gui interface for the program"); 
        options.addOption(gui);
        Option addItem = new Option("a", "add-item", true, "Add an item to a todo list");
        addItem.setArgs(3);
        options.addOption(addItem);
        Option deleteItem = new Option("D", "delete-item", true, "delete a specific todo item from the corresponding todolist");
        deleteItem.setArgs(2);
        options.addOption(deleteItem);
        Option createTodoList = new Option("c", "create-list", true, "Create an empty todolist with the corresponding name");
        createTodoList.setArgs(1);
        options.addOption(createTodoList);
        Option createTodoListWithRandName = new Option("C", "create-unnamed-list", false, "Create an empty todolist with an random name");
        options.addOption(createTodoListWithRandName);
        Option deleteTodoList = new Option("dl", "delete-list", true, "Delete an existing todolist with the corresponding name");
        deleteTodoList.setArgs(1);
        options.addOption(deleteTodoList);
        Option markAsDone = new Option("md", "mark-as-done", true, "Mark an item as done in a todolist");
        markAsDone.setArgs(2);
        options.addOption(markAsDone);
        Option markAsIncomplete = new Option("mi", "mark-as-incomplete", true, "Mark an item as incomplete in a todolist");
        markAsIncomplete.setArgs(2);
        options.addOption(markAsIncomplete);
        Option markAsOverdue = new Option("mo", "mark-as-overdue", true, "Mark an item as overdue in a todolist");
        markAsOverdue.setArgs(2);
        options.addOption(markAsOverdue);

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
            ArrayList<Item> due = TodoList.getDueItems();
            Daemon d = new Daemon();
            d.daemon();
        } else if (cmd.hasOption(gui)) {
            GUI g = new GUI();
            g.start();
        } else if (cmd.hasOption(addItem)) {
            String[] opts = cmd.getOptionValues("a");
            TodoList todo = new TodoList(opts[0]);
            Item it = new Item(opts[1], LocalDate.parse(opts[2]));
            if (!it.isDue()) {
                it.markAsIncomplete();
                todo.addItem(it);
            } else {
                System.out.println("You cannot add items which are already due!");
                System.exit(1);
            }
        } else if (cmd.hasOption(createTodoList))  {
            String listName = cmd.getOptionValue("c");
            TodoList todo = new TodoList(listName); 
        } else if (cmd.hasOption(createTodoListWithRandName)) {
            TodoList todo = new TodoList();
        } else if (cmd.hasOption(deleteTodoList)) {
            String listName = cmd.getOptionValue("dl");
            TodoList todo = new TodoList(listName);
            todo.deleteTodoList();
        } else if (cmd.hasOption(deleteItem)) {
            String[] opts = cmd.getOptionValues("D");
            String todoListName = opts[0];
            String todoItem = opts[1];
            TodoList todo = new TodoList(todoListName);
            try {
                Item item = todo.searchForItem(todoItem);           
                todo.removeItem(item);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.exit(1);
            }
        } else if (cmd.hasOption(markAsDone)) {
            String[] opts = cmd.getOptionValues("md");
            String todoListName = opts[0];
            String itemName = opts[1];
            TodoList todo = new TodoList(todoListName);
            List<Item> itemsList = todo.readFromFile(); 
            for (Item i : itemsList) {
                if (i.getName().equals(itemName)) {
                    i.markAsDone();
                }
            }
            todo.writeToFile();
        } else if (cmd.hasOption(markAsIncomplete)) {
            String[] opts = cmd.getOptionValues("mi");
            String todoListName = opts[0];
            String itemName = opts[1];
            TodoList todo = new TodoList(todoListName);
            List<Item> itemsList = todo.readFromFile(); 
            for (Item i : itemsList) {
                if (i.getName().equals(itemName)) {
                    i.markAsIncomplete();
                }
            }
            todo.writeToFile();
        } else if (cmd.hasOption(markAsOverdue)) {
            String[] opts = cmd.getOptionValues("mo");
            String todoListName = opts[0];
            String itemName = opts[1];
            TodoList todo = new TodoList(todoListName);
            List<Item> itemsList = todo.readFromFile(); 
            for (Item i : itemsList) {
                if (i.getName().equals(itemName)) {
                    i.markAsOverdue();
                }
            }
            todo.writeToFile();
        } 
        else if (cmd.hasOption("h")) {
            formatter.printHelp("todo-gui", options);
        } else {
            formatter.printHelp("todo-gui", options);
            System.exit(0);
        }
    }
}
