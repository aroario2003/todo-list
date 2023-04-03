package com.project.todolist;

import java.time.LocalDate;

/**
 * This class describes what a todo item is and what methods it can use
 *
 * @author Alejandro Rosario
 * @version CPSC 240
 */
public class Item {

    private LocalDate dueDate;
    private String name;
    private String done;
    private ItemState itemState;

    /**
     * Constructor for when only name is given
     *
     * @param name the name of the Item
     */
    public Item(String name) {
        this.name = name;
    }

    /**
     * Constructor for when only name and the current itemstate are given
     *
     * @param name the naem of the item
     * @param done the state of the item
     */
    public Item(String name, String done) {
        this.name = name;
        this.done = done;
    }

    /**
     * Constructor for when all item fields are given
     *
     * @param name the name of the item 
     * @param done the current ItemState 
     * @param dueDate the due date of the item 
     */
    public Item(String name, String done, LocalDate dueDate) {
        this.name = name;
        this.done = done;
        this.dueDate = dueDate;
    }

    /**
     * This method will mark an item as done 
     */
    public void markAsDone() {

    }

    /**
     * This method will mark an item as incomplete
     */
    public void markAsIncomplete() {

    }

    /**
     * This method will mark an item as overdue
     */
    public void markAsOverdue() {

    }   

    /** 
     * This method will set the item state based on whether it is done, incomplete or overdue
     */
    public void setItemState() {

    }

    /**
     * This method will give the user a list of items to add a due date to and
     * allow them to select one and will ask for teh due date and will append it to the item
     */
    public void addDueDate() {

    }

    /**
     * This method gets the name of the item 
     *
     * @return the name of the item
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method get the due date of the item
     *
     * @return the due date of the item
     */
    public LocalDate getDueDate() {
        return this.dueDate;
    }

    /**
     * This method converts Item to a string so that it can be written to todo list file
     *
     * @return the string which corresponds to the current Item
     */
    public String toString() {
        return this.done + " " + this.name + "\n" + this.dueDate;
    }

}
