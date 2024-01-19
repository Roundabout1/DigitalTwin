package com.mycompany.digitaltwin;

import javax.swing.*;
import java.awt.*;

public class FrameCompany extends JFrame {
    private JPanel textFieldPanel = new JPanel();
    public FrameCompany() {
        setTitle("FrameCompany");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        addTextField("Бюджет");
        
        addTextField("Расход на рекламу");
        
        add(textFieldPanel);
        
        setVisible(true);
    }
    
    public void addTextField(String label) {
        // create a label to display text
        JLabel l = new JLabel(label);
 
        // create a object of JTextField with 16 columns
        JTextField t = new JTextField(16);
 
        // create a panel to add buttons and textfield
        // JPanel p = new JPanel();
 
         textFieldPanel.add(l);
         textFieldPanel.add(t);
 
        // add panel to frame
        //textFieldPanel.add(p);
    }
}

