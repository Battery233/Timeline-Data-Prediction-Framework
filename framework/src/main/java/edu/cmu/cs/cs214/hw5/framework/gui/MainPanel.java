package edu.cmu.cs.cs214.hw5.framework.gui;

import edu.cmu.cs.cs214.hw5.framework.core.Framework;
import edu.cmu.cs.cs214.hw5.framework.core.Plugin;
import edu.cmu.cs.cs214.hw5.framework.core.StatusChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Font;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

public class MainPanel extends JPanel implements StatusChangeListener {

    private final Framework core;
    private final PluginPanel dataPluginPanel;
    private final PluginPanel displayPluginPanel;
    private final JPanel display = new JPanel();
    private final JScrollPane scroll;
    private final JPanel pluginPanel = new JPanel();

    public MainPanel(Framework framework) {
        core = framework;
        dataPluginPanel = new DataPluginPanel(this, core);
        displayPluginPanel = new DisplayPluginPanel(this, core);

        pluginPanel.setLayout(new BoxLayout(pluginPanel, BoxLayout.PAGE_AXIS));
        getTitlePanel(true);
        pluginPanel.add(dataPluginPanel);
        getTitlePanel(false);
        pluginPanel.add(displayPluginPanel);

        JScrollPane rightScroll = new JScrollPane(pluginPanel);
        rightScroll.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        setLayout(new BorderLayout());
        add(rightScroll, BorderLayout.EAST);

        scroll = new JScrollPane(display);
        scroll.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scroll, BorderLayout.CENTER);
    }

    private void getTitlePanel(boolean isDataPlugin) {
        JPanel title = new JPanel();
        String titleText = "Data Plugin Area";
        if (!isDataPlugin) titleText = "Display Plugin Area";
        JLabel label = new JLabel(titleText);
        label.setFont(new Font("title", Font.BOLD, 18));
        title.add(label);
        pluginPanel.add(title);
    }

    @Override
    public void onPluginRegistered(Plugin plugin) {
        if (plugin.isDataPlugin()) dataPluginPanel.onPluginRegistered(plugin);
        else displayPluginPanel.onPluginRegistered(plugin);
    }

    public void onGetDataSuccess() {
        displayPluginPanel.enableBrowsePanel();
    }

    public void onDataPluginChanged() {
        displayPluginPanel.disableBrowsePanel();
    }

    public void onDisplayChanged(JPanel newDisplay) {
        display.removeAll();
        display.add(newDisplay);
        scroll.revalidate();
        scroll.repaint();
    }
}
