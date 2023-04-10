package com.project.todolist;

import java.util.ArrayList;
import java.util.Random;

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
     * Constructor for when no name is given, name will be randomly generated
     */
    public TodoList() {
        Random r = new Random();
        int randId = r.nextInt(100,999);
        this.name = "user-"+randId;

    }

    /**
     * Constructor for when the name of the todolist is given
     *
     * @param name the name of the todolist to create
     */
    public TodoList(String name) {
        this.name = name;
    }

    /**
     * This method will write the entire todo list to the file
     */
    public void writeToFile() {

    }

    /**
     * This method will read a todo list from a file 
     *
     * @return An arraylist of Items which represents the contents of the file
     */
    public ArrayList<Item> readFromFile() {
        ArrayList<Item> a = new ArrayList<>();
        
        return a; 

    }

    /**
     * This method will delete a todo list from the todo list directory 
     * */
    public void deleteTodoList() {

    }

    /**
     * This method will create a empty todo list in the todo list directory
     */
    public void createTodoList() {

    }
    
    /**
     * This method will generate a random string of characters to append to
     * the name of the todo list when the name is not given to the constructor
     *
     * @return the string which is the random set of characters that gets appended to the name of the todo list
     */
    public String generateRandName() {
        return "blah";
    }

}
