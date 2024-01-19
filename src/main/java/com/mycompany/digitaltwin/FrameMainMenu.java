package com.mycompany.digitaltwin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameMainMenu extends JFrame {
    
    public FrameMainMenu() {
        setTitle("My Frame");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons
        JButton company = new JButton("Company");
        JButton conf = new JButton("Conf");
        JButton employees = new JButton("Employees");
        JButton ingredients = new JButton("Ingredients");
        JButton pizza = new JButton("Pizza");
        JButton place = new JButton("Place");
        JButton start = new JButton("Start");

        // Add buttons to the top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(Box.createVerticalGlue());
        topPanel.add(company);
        topPanel.add(conf);
        topPanel.add(employees);
        topPanel.add(ingredients);
        topPanel.add(pizza);
        topPanel.add(place);
        topPanel.add(Box.createVerticalGlue());

        // Add the start button to the bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(start);

        // Add the top and bottom panels to the frame
        add(topPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Add an action listener to the start button
        start.addActionListener((ActionEvent e) -> {
            System.out.println("The start button was clicked!");
            DigitalTwin.setup();
        });
        
        // Center the buttons horizontally
        company.setAlignmentX(Component.CENTER_ALIGNMENT);
        conf.setAlignmentX(Component.CENTER_ALIGNMENT);
        employees.setAlignmentX(Component.CENTER_ALIGNMENT);
        ingredients.setAlignmentX(Component.CENTER_ALIGNMENT);
        pizza.setAlignmentX(Component.CENTER_ALIGNMENT);
        place.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        setVisible(true);
    }
    
}