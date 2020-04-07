package edu.cmu.cs.cs214.team24;

import edu.cmu.cs.cs214.team24.framework.core.*;
import edu.cmu.cs.cs214.team24.framework.gui.MainPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndStartFramework);
    }

    private static void createAndStartFramework() {
        Framework core = new FrameworkImpl();
        MainPanel mainPanel = new MainPanel(core);
        core.setStatusChangeListener(mainPanel);

        List<Plugin> plugins;
        plugins = loadPlugins(true);
        for (Plugin p: plugins) System.out.println(p.name());
        plugins.forEach(core::registerPlugin);
        plugins = loadPlugins(false);
        plugins.forEach(core::registerPlugin);

        JFrame frame = new JFrame("Timeline Data Prediction Framework");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static List<Plugin> loadPlugins(boolean isDataPlugin) {
        List<Plugin> plugins = new ArrayList<>();
        for (Plugin p : ServiceLoader.load(Plugin.class)) {
            if (isDataPlugin && p.isDataPlugin()) plugins.add(p);
            if (!isDataPlugin  && !p.isDataPlugin()) plugins.add(p);
        }
        return plugins;
    }
}