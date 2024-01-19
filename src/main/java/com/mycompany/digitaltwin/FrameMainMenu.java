package com.mycompany.digitaltwin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FrameMainMenu extends JFrame {
    
    public FrameMainMenu() {
        setTitle("My Frame");
        setSize(200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons
        JButton start = new JButton("Старт");
        // Add buttons to the top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(Box.createVerticalGlue());
        topPanel.add(start);
        topPanel.add(Box.createVerticalGlue());


        // Add the top and bottom panels to the frame
        add(topPanel, BorderLayout.CENTER);

        // Add an action listener to the start button
        start.addActionListener((ActionEvent e) -> {
            System.out.println("The start button was clicked!");
            DigitalTwin.setup();
        });
      
        
        // Center the buttons horizontally
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        setVisible(true);
    }
    
}