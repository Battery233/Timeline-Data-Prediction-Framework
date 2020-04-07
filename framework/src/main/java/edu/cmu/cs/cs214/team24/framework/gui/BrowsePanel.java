package edu.cmu.cs.cs214.team24.framework.gui;


import edu.cmu.cs.cs214.team24.framework.core.Plugin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;

public class BrowsePanel extends JPanel implements ItemListener {

    Vector comboBoxItems=new Vector();
    final DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItems);

    public BrowsePanel() {
        setLayout(new GridLayout(2, 1));
        JLabel browseLabel = new JLabel("Select a data plugin.");
        add(browseLabel);
        JComboBox comboBox = new JComboBox(model);
        add(comboBox);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    public void onPluginRegistered(Plugin plugin) {
//        comboBoxItems.add(plugin);
        model.addElement(plugin);
    }
}
