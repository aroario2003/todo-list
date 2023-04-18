package com.project.todolist;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

/**
 * This class will create and start a graphical user interface to interact with
 * todolists
 *
 * @author Alejandro Rosario
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
        window.setPreferredSize(new Dimension(400, 200));
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
        itemsWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel itemsWinContent = new JPanel();
        itemsWinContent.setLayout(new BoxLayout(itemsWinContent, BoxLayout.Y_AXIS));

        //Button to go into a list and view the items in the list
        JButton intoListBtn = new JButton("Into list");
        intoListBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TodoList selList = new TodoList(listOfTodoList.getSelectedValue());
                List<Item> itemsList = selList.getItems();
                if (!itemsList.isEmpty()) {
                    DefaultListModel<Item> itemsListModel = new DefaultListModel<Item>();
                    itemsListModel.addAll((ArrayList<Item>)itemsList);
                    JList<Item> itemsGlist = new JList<Item>(itemsListModel);

                    itemsWinContent.add(itemsGlist); 

                    itemsWin.add(itemsWinContent);
                    itemsWin.pack();
                    itemsWin.setVisible(true);
                }
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
                        String name = nameInput.getText();
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
