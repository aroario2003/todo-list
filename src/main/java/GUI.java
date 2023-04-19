package com.project.todolist;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import java.util.List;
import java.util.ArrayList;


/**
 * This class will create and start a graphical user interface to interact with
 * todolists
 *
 * @author Alejandro Rosario
 * @author Victor Rahman
 * @author Sonia Vetter
 * @author Nora Peters
 *
 * @version CPSC 240
 */
public class GUI {

    public GUI() {}

    /**
     * This method will start the GUI 
     */
    public void start() {
        //Initialize the main window and panel
        ArrayList<String> todoListNames = TodoList.getTodoLists();
        DefaultListModel<String> todoListModel = new DefaultListModel<String>();
        todoListModel.addAll(todoListNames);
        JFrame window = new JFrame("todo-gui");
        window.setPreferredSize(new Dimension(400, 290));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //Create the list of todolists in the GUI
        JList<String> listOfTodoList = new JList<String>(todoListModel); 
        Dimension listDimension = new Dimension(300, 300);
        JScrollPane pane = new JScrollPane(listOfTodoList);
        listOfTodoList.setPreferredSize(listDimension);
        contentPane.add(pane);

        JFrame itemsWin = new JFrame("Items (todo-gui)");
        JPanel itemsWinContent = new JPanel();
        itemsWinContent.setLayout(new BoxLayout(itemsWinContent, BoxLayout.Y_AXIS));

        //Button to go into a list and view the items in the list
        JButton intoListBtn = new JButton("Into list");
        intoListBtn.setPreferredSize(new Dimension(130, 20));
        intoListBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selListName = listOfTodoList.getSelectedValue();
                if (selListName == null) {
                    selListName = new TodoList().getName();  
                }
                TodoList selList = new TodoList(selListName);
                List<Item> itemsList = selList.getItems();
                DefaultListModel<Item> itemsListModel = new DefaultListModel<Item>();
                itemsListModel.addAll((ArrayList<Item>)itemsList);
                JList<Item> itemsGlist = new JList<Item>(itemsListModel);
                JScrollPane itemsScroll = new JScrollPane(itemsGlist);
                itemsGlist.setPreferredSize(new Dimension(300, 300));

                JButton markDone = new JButton("Mark done");
                markDone.setPreferredSize(new Dimension(150, 20));
                markDone.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Item item = itemsGlist.getSelectedValue();
                        itemsListModel.removeElement(item);
                        item.markAsDone();
                        selList.writeToFile();
                        itemsListModel.addElement(item);
                    }
                });

                JButton markIncomplete = new JButton("Mark incomplete");
                markIncomplete.setPreferredSize(new Dimension(170, 20));
                markIncomplete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Item item = itemsGlist.getSelectedValue();
                        itemsListModel.removeElement(item);
                        item.markAsIncomplete();
                        selList.writeToFile();
                        itemsListModel.addElement(item);
                    }
                });

                JButton markOverdue = new JButton("Mark overdue");
                markOverdue.setPreferredSize(new Dimension(160, 20));
                markOverdue.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Item item = itemsGlist.getSelectedValue();
                        itemsListModel.removeElement(item);
                        item.markAsOverdue();
                        selList.writeToFile();
                        itemsListModel.addElement(item);
                    }
                });

                JButton addItem = new JButton("Add item");
                addItem.setPreferredSize(new Dimension(130, 20));
                addItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFrame addItemWin = new JFrame("Add item (todo-gui)");
                        JPanel addItemPanel = new JPanel();
                        addItemPanel.setLayout(new BoxLayout(addItemPanel, BoxLayout.Y_AXIS));

                        JLabel itemNameLabel = new JLabel("Item name:");
                        JTextField nameField = new JTextField();
                        JLabel itemDueDateLabel = new JLabel("Item Due Date (YYYY-MM-DD):");
                        JTextField itemDueDateField = new JTextField();

                        JButton okBtn = new JButton("Ok");
                        okBtn.setPreferredSize(new Dimension(100, 20));
                        okBtn.addActionListener(new ActionListener() {
                           @Override
                           public void actionPerformed(ActionEvent e) {
                               try {
                                    Item it = new Item(nameField.getText(), LocalDate.parse(itemDueDateField.getText()));
                                    itemsListModel.addElement(it);
                                    it.markAsIncomplete();
                                    selList.addItem(it);
                                    addItemWin.dispatchEvent(new WindowEvent(addItemWin, WindowEvent.WINDOW_CLOSING));
                               } catch (DateTimeParseException ex) {
                                    JOptionPane.showMessageDialog(
                                           null, 
                                           "The date entered is in the wrong format", 
                                           "please use YYYY-MM-DD", 
                                           JOptionPane.ERROR_MESSAGE
                                   );
                               }
                            }
                        });

                        JButton cancelBtn = new JButton("Cancel");
                        cancelBtn.setPreferredSize(new Dimension(120, 20));
                        cancelBtn.addActionListener(new ActionListener() {
                           @Override
                           public void actionPerformed(ActionEvent e) {
                                addItemWin.dispatchEvent(new WindowEvent(addItemWin, WindowEvent.WINDOW_CLOSING));
                           }
                        });

                        JPanel addItemBtnPanel = new JPanel();
                        addItemBtnPanel.setLayout(new BoxLayout(addItemBtnPanel, BoxLayout.X_AXIS));
                        addItemBtnPanel.add(okBtn);
                        addItemBtnPanel.add(cancelBtn);

                        addItemPanel.add(itemNameLabel);
                        addItemPanel.add(nameField);
                        addItemPanel.add(itemDueDateLabel);
                        addItemPanel.add(itemDueDateField);
                        addItemPanel.add(addItemBtnPanel);

                        addItemWin.add(addItemPanel);
                        addItemWin.pack();
                        addItemWin.setVisible(true);
                    }
                });

                JButton deleteItem = new JButton("Delete item");
                deleteItem.setPreferredSize(new Dimension(140, 20));
                deleteItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Item item = itemsGlist.getSelectedValue();
                        itemsListModel.removeElement(item);
                        selList.removeItem(item);
                    }
                });

                JButton cancelBtn = new JButton("Cancel");
                cancelBtn.setPreferredSize(new Dimension(120, 20));
                cancelBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        itemsWin.dispatchEvent(new WindowEvent(itemsWin, WindowEvent.WINDOW_CLOSING));
                    }
                });

                JPanel itemsBtnPanel = new JPanel();
                itemsBtnPanel.setLayout(new BoxLayout(itemsBtnPanel, BoxLayout.X_AXIS));

                itemsBtnPanel.add(addItem);
                itemsBtnPanel.add(deleteItem);
                itemsBtnPanel.add(markDone);
                itemsBtnPanel.add(markIncomplete);
                itemsBtnPanel.add(markOverdue);
                itemsBtnPanel.add(cancelBtn);

                itemsWinContent.add(itemsScroll); 
                itemsWinContent.add(itemsBtnPanel);

                itemsWin.add(itemsWinContent);
                itemsWin.pack();
                itemsWin.setVisible(true);
            }
        });
        
        //Button to create a list and add it to the list of todolists in the GUI
        JButton addListBtn = new JButton("Add todolist");
        addListBtn.setPreferredSize(new Dimension(150, 20));
        addListBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame popupWin = new JFrame("Add todolist (todo-gui)");
                JPanel popupPanel = new JPanel();
                JLabel instructionText = new JLabel("TodoList name:");
                popupWin.add(instructionText);
                JTextField nameInput = new JTextField();
                JButton okBtn = new JButton("Ok");
                okBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name;
                        name = nameInput.getText();
                        if (name.equals("")) {
                           name = new TodoList().getName();
                        }
                        TodoList list = new TodoList(name);
                        todoListModel.addElement(name);
                        popupWin.dispatchEvent(new WindowEvent(popupWin, WindowEvent.WINDOW_CLOSING));
                    }
                });

                //cancel button for main todolist window
                JButton cancelBtn = new JButton("Cancel");
                cancelBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        popupWin.dispatchEvent(new WindowEvent(popupWin, WindowEvent.WINDOW_CLOSING));
                    }
                });
                //add non-button elements to popup
                popupPanel.setLayout(new BoxLayout(popupPanel, BoxLayout.Y_AXIS)); 
                popupPanel.add(instructionText);
                popupPanel.add(nameInput);

                //make button panel to add buttons to popup window
                JPanel btnPanel = new JPanel();
                btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
                btnPanel.add(okBtn);
                btnPanel.add(cancelBtn);

                popupPanel.add(btnPanel);

                //add panel to popup window
                popupWin.add(popupPanel);
                popupWin.pack();
                popupWin.setVisible(true); 
           }
        });

        JButton deleteListBtn = new JButton("Delete todolist");
        deleteListBtn.setPreferredSize(new Dimension(150, 20));
        deleteListBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TodoList list = new TodoList(listOfTodoList.getSelectedValue());
                list.deleteTodoList();
                todoListModel.removeElement(listOfTodoList.getSelectedValue());
            }
        });

        JPanel listBtnPanel = new JPanel();
        
        JButton cancelAddListBtn = new JButton("Cancel");
        cancelAddListBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }
        });
        cancelAddListBtn.setPreferredSize(new Dimension(100, 20));

        listBtnPanel.add(intoListBtn);
        listBtnPanel.add(addListBtn);
        listBtnPanel.add(deleteListBtn);
        listBtnPanel.add(cancelAddListBtn);

        contentPane.add(listBtnPanel);

        window.add(contentPane);
        window.pack();
        window.setVisible(true);
    }

}
