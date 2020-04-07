package edu.cmu.cs.cs214.team24.framework.gui.plugin;


import edu.cmu.cs.cs214.team24.framework.core.Framework;
import edu.cmu.cs.cs214.team24.framework.core.Plugin;
import edu.cmu.cs.cs214.team24.framework.gui.DataPluginPanel;
import edu.cmu.cs.cs214.team24.framework.gui.PluginPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

public class BrowsePanel extends JPanel {

    private final DefaultComboBoxModel<Plugin> model = new DefaultComboBoxModel<>(new Vector<>());
    private final JComboBox<Plugin> comboBox;
    private PluginPanel parent;
    private boolean isDataPlugin;

    public BrowsePanel(PluginPanel parent, boolean isDataPlugin) {
        this.parent = parent;
        this.isDataPlugin = isDataPlugin;
        setEnabled(isDataPlugin);

        setLayout(new GridLayout(2, 1));
        String pluginName = "data";
        if (!isDataPlugin) pluginName = "display";
        JLabel browseLabel = new JLabel("Select a " + pluginName + " plugin.");
        add(browseLabel);

        comboBox = new JComboBox<>(model);
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
        if (isDataPlugin) {
            comboBox.addActionListener(e -> {
                Plugin selected = (Plugin) comboBox.getSelectedItem();
                parent.onPluginChanged(selected);
            });
        }
        add(comboBox);
    }

    public void enableSelection(){
        setEnabled(true);
        comboBox.addActionListener(e -> {
            Plugin selected = (Plugin) comboBox.getSelectedItem();
            parent.onPluginChanged(selected);
        });
    }

    public void onPluginRegistered(Plugin plugin) {
        if (plugin.isDataPlugin() == isDataPlugin) model.addElement(plugin);
    }
}
