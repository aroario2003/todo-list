package com.project.todolist;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
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
 * @author Victor Rahman
 * @author Sonia Vetter
 * @author Nora Peters
 *
 * @version CPSC 240
 */
public class TodoList {

    private List<Item> list = new ArrayList<>();
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
            String todoListFile = this.path + "todo-" + this.name + ".txt";
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
          this.path = "C:\\%PROGRAMDATA%\\todo-list\\todos\\";  
          File todoPath = new File(this.path);
          if (!todoPath.exists()) {
              todoPath.mkdirs();
          }
          String todoListFile = this.path + "todo-" + this.name + ".txt";
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
            String todoListFile = this.path + "todo-" + this.name + ".txt";
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
          this.path = "C:\\%PROGRAMDATA%\\todo-list\\todos\\";  
          File todoPath = new File(this.path);
          if (!todoPath.exists()) {
              todoPath.mkdirs();
          }
          String todoListFile = this.path + "todo-" + this.name + ".txt";
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
        File todoList = new File(this.path + "todo-" + this.name + ".txt");
        try {
            PrintWriter out = new PrintWriter(todoList);
            out.write("");
            out.flush();
            for (Item i : this.list) {
                out.println(i.toString());
            }
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * This method will read a todo list from a file 
     *
     * @return A List of Items which represents the contents of the file
     */
    public List<Item> readFromFile() {
        String filePath = this.path + "todo-" + this.name + ".txt";
        File todoList = new File(filePath);
        this.list.clear();
        try {
            Scanner in = new Scanner(todoList);  
            while (in.hasNext()) {
                String line = in.nextLine();
                String[] itemParts = line.split("     ");
                String itemDone = itemParts[0]; 
                String itemName = itemParts[1];
                LocalDate itemDueDate = LocalDate.parse(itemParts[2]);
                Item todoItem = new Item(itemName, itemDueDate);
                todoItem.markWith(itemDone);
                this.list.add(todoItem);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return this.list; 
    }

    /**
     * This method will get all the due items from every TodoList.
     *
     * @return the due items from every todolist
     *
     */
    public static ArrayList<Item> getDueItems() {
        //Code is repeated but that is neccessary as the constructor
        //for the class creates files and we do not want that happening 
        //when this method is executed in the daemon. Therefore, we will
        //repeat some of the code from the constructor.
        String os = System.getProperty("os.name");
        ArrayList<Item> dueItems = new ArrayList<>(); 
        if (os.equals("Linux")) {
            String homeDir = System.getProperty("user.home");
            File todoDir = new File(homeDir + "/.local/share/todolists/");
            File[] todos = todoDir.listFiles(); 
            if (todos != null) {
                for (File child : todos) {
                    if (child.isFile()) {
                       File todolist = new File(child.getPath());
                       try {
                            Scanner fileReader = new Scanner(todolist);
                            while (fileReader.hasNext()) {
                                String line = fileReader.nextLine();
                                String[] itemParts = line.split("     ");
                                String itemDone = itemParts[0]; 
                                String itemName = itemParts[1];
                                LocalDate itemDueDate = LocalDate.parse(itemParts[2]);
                                Item todoItem = new Item(itemName, itemDueDate);
                                if (todoItem.isDue()) {
                                    dueItems.add(todoItem);
                                }
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            System.exit(1);
                        }
                    }
                }
            }
        } else if (os.equals("Windows")) {
            File todoDir = new File("C:\\%PROGRAMDATA%\\todo-list\\todos\\");  
            File[] todos = todoDir.listFiles(); 
            if (todos != null) {
                for (File child : todos) {
                    if (child.isFile()) {
                        File todolist = new File(child.getPath());
                        try {
                            Scanner fileReader = new Scanner(todolist);
                            while (fileReader.hasNext()) {
                                String line = fileReader.next();
                                String[] itemParts = line.split("    ");
                                String itemDone = itemParts[0]; 
                                String itemName = itemParts[1];
                                LocalDate itemDueDate = LocalDate.parse(itemParts[2]);
                                Item todoItem = new Item(itemName, itemDueDate);
                                if (todoItem.isDue()) {
                                    dueItems.add(todoItem);
                                }
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            System.exit(1);
                        }
                    }
                }  
            }
        }
        return dueItems;
    }

    /**
     * This method will delete a todo list from the todo list directory 
     * */
    public void deleteTodoList() {
        File todoList = new File(this.path + "todo-" + this.name + ".txt");
        if (todoList.exists()) {
            todoList.delete();
        }
    }

    /**
     * This method will add an item to a todo list with all relevant information
     *
     * @param item the item to add to the todolist
     */
    public void addItem(Item item) {
        this.list = readFromFile();
        this.list.add(item);
        writeToFile();
    }

    /**
     * This method will remove an item from a todo list
     *
     * @param item the item to delete or remove from the todolist
     */
    public void removeItem(Item item) {
        this.list = readFromFile();
        for (int i = 0; i < this.list.size(); i++) {
            Item it = this.list.get(i);
            if (it.getName().equals(item.getName())) {
                this.list.remove(it);
            }
        }
        writeToFile();
    }

    /**
     * This method will search for specific item in a specific todo List
     *
     * @param name the name of the item to search for in the todo list
     * @return the item if it is found otherwise null
     */
    public Item searchForItem(String name) throws FileNotFoundException {
       File todoList = new File(this.path + "todo-" + this.name + ".txt");
       Item item = new Item("", LocalDate.now());
       if (todoList.exists()) {
           Scanner in = new Scanner(todoList);
           while (in.hasNextLine()) {
               String line = in.nextLine();
               if (line.contains(name)) {
                    String[] itemParts = line.split("     ");
                    String itemDone = itemParts[0];
                    String itemName = itemParts[1];
                    LocalDate dueDate = LocalDate.parse(itemParts[2]);
                    item = new Item(itemName, dueDate);
                    item.markWith(itemDone);
               } else {
                   continue;
               }
           }
           return item;
       } else {
           throw new FileNotFoundException("The todolist you specified does not exist in any paths being searched");
       }
    } 

    /**
     * This method will get all the items in a todolist
     *
     * @return the list of items in the specified todolist
     */
    public List<Item> getItems() {
        List<Item> itemsList = readFromFile();
        return itemsList;
    }

    /**
     * This method gets the name of the todolist created or existing
     *
     * @return the string which represents the name of the todolist
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method will walk the directory where all todolists must be stored and 
     * get all of the names
     *
     * @return the ArrayList of names of the todolists in the todolist directory
     */
    public static ArrayList<String> getTodoLists() {
        String os = System.getProperty("os.name");
        String homeDir = System.getProperty("user.home");
        
        String path = "";
        
        if (os.contains("Linux")) {
            path = homeDir + "/.local/share/todolists/";
        } else if (os.contains("Windows")) {
            path = "C:\\%PROGRAMDATA%\\todo-list\\todos";
        }
        ArrayList<String> todoListNames = new ArrayList<>();
        File todoListPath = new File(path);
        File[] todoLists = todoListPath.listFiles();
        if (todoLists != null) {
            for (File todoList : todoLists) {
                if (todoList.isFile()) {
                    String[] todoListNameParts = todoList.getName().toString().split("-");
                    String[] todoListNameAndExt = todoListNameParts[1].split("\\.");
                    String todoListName = todoListNameAndExt[0];
                    todoListNames.add(todoListName);
                }
            }
        }
        return todoListNames;
    }
    
    /**
     * This method will generate a random string of characters to append to
     * the name of the todo list when the name is not given to the constructor
     *
     * @return the string which is the random set of characters that gets appended to the name of the todo list
     */
    public String generateRandName() {
        int leftLimit = 97; 
        int rightLimit = 122; 
        int targetStringLength = 5;
        Random random = new Random();

        String randId = random.ints(leftLimit, rightLimit + 1)
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
        this.name = randId;
        return this.name;
    }

}
