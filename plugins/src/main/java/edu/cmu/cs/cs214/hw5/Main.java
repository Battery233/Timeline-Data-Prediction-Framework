package edu.cmu.cs.cs214.hw5;

import edu.cmu.cs.cs214.hw5.framework.core.Framework;
import edu.cmu.cs.cs214.hw5.framework.core.FrameworkImpl;
import edu.cmu.cs.cs214.hw5.framework.core.Plugin;
import edu.cmu.cs.cs214.hw5.framework.gui.MainPanel;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * The main class to run the framework using GUI.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndStartFramework);
    }

    /**
     * Create and start the framework.
     */
    private static void createAndStartFramework() {
        Framework core = new FrameworkImpl();
        MainPanel mainPanel = new MainPanel(core);
        core.setStatusChangeListener(mainPanel);

        List<Plugin> plugins;
        plugins = loadPlugins(true);
        for (Plugin p : plugins) System.out.println(p.name());
        plugins.forEach(core::registerPlugin);
        plugins = loadPlugins(false);
        plugins.forEach(core::registerPlugin);

        JFrame frame = new JFrame("Timeline Data Prediction Framework");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        frame.setPreferredSize(new Dimension(1000, 1000));
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);
    }

    /**
     * Load the plugin from META-INFO
     * @param isDataPlugin if the plugin is a data plugin
     * @return the list of plugins
     */
    public static List<Plugin> loadPlugins(boolean isDataPlugin) {
        List<Plugin> plugins = new ArrayList<>();
        for (Plugin p : ServiceLoader.load(Plugin.class)) {
            if (isDataPlugin && p.isDataPlugin()) plugins.add(p);
            if (!isDataPlugin && !p.isDataPlugin()) plugins.add(p);
        }
        return plugins;
    }
}