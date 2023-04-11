package com.project.todolist;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

/** 
 * This class represents what a todolist is and how it works
 *
 * @author Alejandro Rosario
 * @version CPSC 240
 */
public class TodoList {

    private ArrayList<Item> list;
    private String path;
    private String name;

    /**
     * Constructor for when no name is given, name will be randomly generated.
     * Also determines path for todo list files based on the operating system.
     */
    public TodoList() {
        this.name = generateRandName();

        String os = System.getProperty("os.name");    
        if (os.contains("Linux")) {
            String homeDir = System.getProperty("user.home");
            this.path = homeDir + "/.local/share/todolists/";
            File todoPath = new File(this.path);
            if (!todoPath.exists()) {
                todoPath.mkdir();
            }
            String todoListFile = this.path + this.name + ".txt";
            File todoList = new File(todoListFile);
            if (!todoList.exists()) {
                try {
                    todoList.createNewFile();
                } catch(IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        } else if (os.contains("Windows")) {
          this.path = "C:\\%PROGRAMDATA\\todo-list\\todos\\";  
          File todoPath = new File(this.path);
          if (!todoPath.exists()) {
              todoPath.mkdirs();
          }
          String todoListFile = this.path + this.name + ".txt";
          File todoList = new File(todoListFile);
          if (!todoList.exists()) {
              try {
                todoList.createNewFile();
              } catch(IOException e) {
                e.printStackTrace();
                 System.exit(1);
              }
          }
       } else {
           System.out.println("Operating System not supported!");
           System.exit(1);
       }

    }

    /**
     * Constructor for when the name of the todolist is given
     *
     * @param name the name of the todolist to create
     */
    public TodoList(String name) {
        this.name = name;

        String os = System.getProperty("os.name");    
        if (os.contains("Linux")) {
            String homeDir = System.getProperty("user.home");
            this.path = homeDir + "/.local/share/todolists/";
            File todoPath = new File(this.path);
            if (!todoPath.exists()) {
                todoPath.mkdir();
            }
            String todoListFile = this.path + this.name + ".txt";
            File todoList = new File(todoListFile);
            if (!todoList.exists()) {
                try {
                    todoList.createNewFile();
                } catch(IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        } else if (os.contains("Windows")) {
          this.path = "C:\\%PROGRAMDATA\\todo-list\\todos\\";  
          File todoPath = new File(this.path);
          if (!todoPath.exists()) {
              todoPath.mkdirs();
          }
          String todoListFile = this.path + this.name + ".txt";
          File todoList = new File(todoListFile);
          if (!todoList.exists()) {
              try {
                todoList.createNewFile();
              } catch(IOException e) {
                e.printStackTrace();
                 System.exit(1);
              }
          }
       } else {
           System.out.println("Operating System not supported!");
           System.exit(1);
       }
    }

    /**
     * This method will write the entire todo list to the file
     */
    public void writeToFile() {
        File todoList = new File(this.path + this.name + ".txt");
        try {
            PrintWriter out = new PrintWriter(todoList);
            out.write("");
            for (Item i : list) {
                out.println(i.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * This method will read a todo list from a file 
     *
     * @return An arraylist of Items which represents the contents of the file
     */
    public ArrayList<Item> readFromFile() {
        String filePath = this.path + this.name + ".txt";
        File todoList = new File(filePath);
        try {
            Scanner in = new Scanner(todoList);  
            while (in.hasNext()) {
                String line = in.next();
                String[] itemParts = line.split(" ");
                String itemDone = itemParts[0]; 
                String itemName = itemParts[1];
                LocalDate itemDueDate = LocalDate.parse(itemParts[2]);
                Item todoItem = new Item(itemName, itemDueDate);
                todoItem.markAsIncomplete();
                this.list.add(todoItem);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return this.list; 

    }

    /**
     * This method will delete a todo list from the todo list directory 
     * */
    public void deleteTodoList() {
        File todoList = new File(this.path + this.name + ".txt");
        if (todoList.exists()) {
            todoList.delete();
        }
    }

    /**
     * This method will create a empty todo list in the todo list directory
     */
    public void createTodoList() {
       File todoList = new File(this.path + this.name + ".txt");
       if (!todoList.exists()) {
           try {
               todoList.createNewFile();
           } catch (IOException e) {
               e.printStackTrace();
               System.exit(1);
           }
        }
    }

    /**
     * This method will add an item to a todo list with all relevant information
     */
    public void addItem(Item item) {
        this.list = readFromFile();
        this.list.add(item);
        writeToFile();
    }

    /**
     * This method will remove an item from a todo list
     */
    public void removeItem(Item item) {
        this.list = readFromFile();
        this.list.remove(item);
        writeToFile();
    }
    
    /**
     * This method will generate a random string of characters to append to
     * the name of the todo list when the name is not given to the constructor
     *
     * @return the string which is the random set of characters that gets appended to the name of the todo list
     */
    public String generateRandName() {
        byte[] bytesArr = new byte[5];
        new Random().nextBytes(bytesArr);
        String randId = new String(bytesArr, Charset.forName("UTF-8"));
        this.name = "todo-" + randId;
        return this.name;
    }

}
