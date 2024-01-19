package com.mycompany.digitaltwin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FrameMainMenu extends JFrame {
    
    public FrameMainMenu() {
        setTitle("My Frame");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons
        JButton start = new JButton("Старт");
        JButton graph1 = new JButton("График 1");
        JButton graph2 = new JButton("График 2");
        JButton graph3 = new JButton("График 3");
        // Add buttons to the top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(Box.createVerticalGlue());
        topPanel.add(start);
        topPanel.add(graph1);
        topPanel.add(graph2);
        topPanel.add(graph3);
        topPanel.add(Box.createVerticalGlue());


        // Add the top and bottom panels to the frame
        add(topPanel, BorderLayout.CENTER);

        // Add an action listener to the start button
        start.addActionListener((ActionEvent e) -> {
            System.out.println("The start button was clicked!");
            DigitalTwin.setup();
        });
        
        graph1.addActionListener((ActionEvent e) -> {
            System.out.println("The graph1 button was clicked!");
        });
        graph2.addActionListener((ActionEvent e) -> {
            System.out.println("The graph2 button was clicked!");
        });
        graph3.addActionListener((ActionEvent e) -> {
            System.out.println("The graph3 button was clicked!");
        });
        
        // Center the buttons horizontally
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        graph1.setAlignmentX(Component.CENTER_ALIGNMENT);
        graph2.setAlignmentX(Component.CENTER_ALIGNMENT);
        graph3.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        setVisible(true);
    }
    
}