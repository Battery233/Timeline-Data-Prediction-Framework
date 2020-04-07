package edu.cmu.cs.cs214.team24.framework.gui.plugin;


import edu.cmu.cs.cs214.team24.framework.core.Framework;
import edu.cmu.cs.cs214.team24.framework.core.Plugin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

public class BrowsePanel extends JPanel implements ItemListener {

    private Framework core;
    private boolean isDataPlugin;
    private final JLabel browseLabel;
    private final DefaultComboBoxModel<Plugin> model = new DefaultComboBoxModel<>(new Vector<>());

    public BrowsePanel(Framework framework, boolean isDataPlugin) {
        core = framework;
        this.isDataPlugin = isDataPlugin;

        setLayout(new GridLayout(2, 1));
        browseLabel = new JLabel("Select a data plugin.");
        add(browseLabel);

        JComboBox<Plugin> comboBox = new JComboBox<>(model);
        // display the plugins by names
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value != null) {
                    setText(((Plugin)value).name());
                }
                return this;
            }
        });

        comboBox.addActionListener(e -> {
            Plugin selected = (Plugin) comboBox.getSelectedItem();
            if (isDataPlugin) core.setCurrentDataPlugin(selected);
            else core.setCurrentDisplayPlugin(selected);
        });
        add(comboBox);
    }

    public void onPluginRegistered(Plugin plugin) {
        model.addElement(plugin);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}
