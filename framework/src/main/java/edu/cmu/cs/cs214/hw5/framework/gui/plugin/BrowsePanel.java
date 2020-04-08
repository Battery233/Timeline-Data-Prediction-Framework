package edu.cmu.cs.cs214.hw5.framework.gui.plugin;


import edu.cmu.cs.cs214.hw5.framework.core.Plugin;
import edu.cmu.cs.cs214.hw5.framework.gui.PluginPanel;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Vector;

public class BrowsePanel extends JPanel {

    private final DefaultComboBoxModel<Plugin> model = new DefaultComboBoxModel<>(new Vector<>());
    private final JComboBox<Plugin> comboBox;
    private final PluginPanel parent;
    private final boolean isDataPlugin;

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
                if (value != null) {
                    setText(((Plugin) value).name());
                }
                return this;
            }
        });
        if (isDataPlugin) {
            addComboBoxListener();
        }
        add(comboBox);
    }

    public void enableSelection() {
        setEnabled(true);
        addComboBoxListener();
    }

    public void disableSelection() {
        setEnabled(false);
        clearComboBoxListener();
    }

    public void onPluginRegistered(Plugin plugin) {
        if (plugin.isDataPlugin() == isDataPlugin) model.addElement(plugin);
    }

    private void addComboBoxListener() {
        comboBox.addActionListener(e -> {
            Plugin selected = (Plugin) comboBox.getSelectedItem();
            parent.onPluginChanged(selected);
        });
    }

    private void clearComboBoxListener() {
        for (ActionListener al : comboBox.getActionListeners()) {
            comboBox.removeActionListener(al);
        }
    }
}
