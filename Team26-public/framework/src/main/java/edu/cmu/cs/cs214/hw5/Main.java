package edu.cmu.cs.cs214.hw5;

import edu.cmu.cs.cs214.hw5.core.*;
import edu.cmu.cs.cs214.hw5.gui.FrameworkGui;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * The Main program for running the framework
 */
public class Main {
    /**
     * The main method for running the framework program
     *
     * @param args the program argument (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndStartFramework);
    }

    private static void createAndStartFramework() {
        Framework framework = new FrameworkImpl();

        List<BinaryOperationPlugin> binaryOperationPlugins = loadBinaryOperationPlugins();
        binaryOperationPlugins.forEach(framework::registerBinaryOperationPlugin);

        List<AggregateOperationPlugin> aggregateOperationPlugins = loadAggregateOperationPlugins();
        aggregateOperationPlugins.forEach(framework::registerAggregateOperationPlugin);

        List<DisplayPlugin> displayPlugins = loadDisplayPlugins();
        displayPlugins.forEach(framework::registerDisplayPlugin);

        List<DataPlugin> dataPlugins = loadDataPlugins();
        dataPlugins.forEach(framework::registerDataPlugin);

        FrameworkChangeListener gui = new FrameworkGui(framework);
        framework.setChangeListener(gui);

    }

    private static List<BinaryOperationPlugin> loadBinaryOperationPlugins() {
        ServiceLoader<BinaryOperationPlugin> binaryOperationPluginList =
                ServiceLoader.load(BinaryOperationPlugin.class);
        List<BinaryOperationPlugin> result = new ArrayList<>();
        for (BinaryOperationPlugin binaryOperationPlugin : binaryOperationPluginList) {
            System.out.println("Loaded binary operation plugin " + binaryOperationPlugin.getOpName());
            result.add(binaryOperationPlugin);
        }
        return result;
    }

    private static List<AggregateOperationPlugin> loadAggregateOperationPlugins() {
        ServiceLoader<AggregateOperationPlugin> aggregateOperationPluginList =
                ServiceLoader.load(AggregateOperationPlugin.class);
        List<AggregateOperationPlugin> result = new ArrayList<>();
        for (AggregateOperationPlugin aggregateOperationPlugin : aggregateOperationPluginList) {
            System.out.println("Loaded aggregate operation plugin " + aggregateOperationPlugin.getOpName());
            result.add(aggregateOperationPlugin);
        }
        return result;
    }

    private static List<DataPlugin> loadDataPlugins() {
        ServiceLoader<DataPlugin> dataPluginList = ServiceLoader.load(DataPlugin.class);
        List<DataPlugin> result = new ArrayList<>();
        for (DataPlugin dataPlugin : dataPluginList) {
            System.out.println("Loaded data plugin " + dataPlugin.getDataSourceName());
            result.add(dataPlugin);
        }
        return result;
    }

    private static List<DisplayPlugin> loadDisplayPlugins() {
        ServiceLoader<DisplayPlugin> displayPluginList = ServiceLoader.load(DisplayPlugin.class);
        List<DisplayPlugin> result = new ArrayList<>();
        for (DisplayPlugin displayPlugin : displayPluginList) {
            System.out.println("Loaded display plugin " + displayPlugin.getChartTypeName());
            result.add(displayPlugin);
        }
        return result;
    }
}
