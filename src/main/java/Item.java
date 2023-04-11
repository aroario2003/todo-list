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
     * Constructor for when all item fields are given
     *
     * @param name the name of the item 
     * @param done the current ItemState 
     * @param dueDate the due date of the item 
     */
    public Item(String name, LocalDate dueDate) {
        this.name = name;
        this.dueDate = dueDate;
        this.itemState = ItemState.Done;
    }

    /**
     * This method will mark an item as done 
     */
    public void markAsDone() {
        this.done = "";

    }

    /**
     * This method will mark an item as incomplete
     */
    public void markAsIncomplete() {
        this.done = "";
    }

    /**
     * This method will mark an item as overdue
     */
    public void markAsOverdue() {
        this.done = "󰃰";
    }   

    /** 
     * This method will set the item state based on whether it is done, incomplete or overdue
     */
    public void setItemState() {
        if (this.done.equals("")) {
            this.itemState = ItemState.Done;
        } else if (this.done.equals("")) {
            this.itemState = ItemState.Incomplete;
        } else if (this.done.equals("󰃰")) {
            this.itemState = ItemState.Overdue;
        }
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
     * This method check is an item is due or not
     *
     * @return a boolean representing whether or not an item is due
     */
    public boolean isDue() {
        return this.dueDate.equals(LocalDate.now()) || this.dueDate.isBefore(LocalDate.now());
    }


    /**
     * This method converts Item to a string so that it can be written to todo list file
     *
     * @return the string which corresponds to the current Item
     */
    public String toString() {
        return this.done + " " + this.name + " " + this.dueDate;
    }

}
