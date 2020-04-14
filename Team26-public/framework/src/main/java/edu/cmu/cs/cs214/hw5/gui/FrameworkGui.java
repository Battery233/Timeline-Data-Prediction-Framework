package edu.cmu.cs.cs214.hw5.gui;

import edu.cmu.cs.cs214.hw5.core.*;
import edu.cmu.cs.cs214.hw5.core.datastructures.DataSet;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimePoint;
import edu.cmu.cs.cs214.hw5.core.datastructures.TimeSeries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class FrameworkGui implements FrameworkChangeListener {
    private final JFrame frame;
    private static final String DEFAULT_TITLE = "TimeSeries Data Framework";
    private static final Dimension FRAME_DIMENSION = new Dimension(1200, 1000);

    private final JPanel containerPanel;
    private final JPanel chartContainer;
    private JPanel currentChartPanel;
    private final JMenu addDataMenu;
    private final JMenu operationsMenu;
    private final JMenuItem cloneMenuItem;
    private final JMenuItem plotTimeSeriesMenuItem;
    private final JMenuItem plotMovingAverageMenuItem;
    private final JMenuItem plotGrowthRateMenuItem;
    private final List<JMenuItem> binaryOperationMenuItems = new ArrayList<>();
    private final List<JMenuItem> aggregateOperationMenuItems = new ArrayList<>();
    private final JMenuItem selectTimePointMenuItem;
    private final JMenuItem plotTimePointMenuItem;
    private final JScrollPane scrollSidePane;
    private final JPanel sidePanel;
    private final JLabel timeSeriesLabel = new JLabel("Time Series: ");
    private final JLabel genTimeSeriesLabel = new JLabel("Generated Time Series: ");
    private final JLabel timePointLabel = new JLabel("Time Points: ");
    private final JList timeSeriesList;
    private final JList genTimeSeriesList;
    private final JList timePointList;
    private final DefaultListModel timeSeriesListModel;
    private final DefaultListModel genTimeSeriesListModel;
    private final DefaultListModel timePointListModel;
    private final JScrollPane timeSeriesListScrollPane;
    private final JScrollPane genTimeSeriesListScrollPane;
    private final JScrollPane timePointListScrollPane;

    private List<TimeSeries> currentSelectedTimeSeriesList;

    private static final String NEW_CHART_MENU_TITLE = "New Chart";
    private static final String ADD_DATA_MENU_TITLE = "Add Data";

    private final Framework framework;
    private DisplayPlugin currentDisplayPlugin;

    /**
     * Constructs the GUI of the given framework
     *
     * @param frameworkCore the framework
     */
    public FrameworkGui(Framework frameworkCore) {
        framework = frameworkCore;

        frame = new JFrame(DEFAULT_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(FRAME_DIMENSION);

        containerPanel = new JPanel(new BorderLayout());

        currentChartPanel = new JPanel();
        chartContainer = new JPanel(new BorderLayout());
        chartContainer.add(currentChartPanel, BorderLayout.CENTER);
        containerPanel.add(chartContainer, BorderLayout.CENTER);

        // side panel creation
        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setPreferredSize(new Dimension(300, 1000));

        sidePanel.add(timeSeriesLabel);
        timeSeriesListModel = new DefaultListModel();
        timeSeriesList = new JList(timeSeriesListModel);
        timeSeriesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        timeSeriesList.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                currentSelectedTimeSeriesList = timeSeriesList.getSelectedValuesList();
                framework.setCurrentlySelectedTimeSeriesList(currentSelectedTimeSeriesList);
                if (currentDisplayPlugin.isTimeSeriesChart()) framework.plotTimeSeries(currentSelectedTimeSeriesList);
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        timeSeriesList.addListSelectionListener(e -> {
            currentSelectedTimeSeriesList = timeSeriesList.getSelectedValuesList();
            framework.setCurrentlySelectedTimeSeriesList(currentSelectedTimeSeriesList);
            if (currentDisplayPlugin.isTimeSeriesChart()) framework.plotTimeSeries(currentSelectedTimeSeriesList);
        });
        timeSeriesListScrollPane = new JScrollPane(timeSeriesList);
        sidePanel.add(timeSeriesListScrollPane);

        sidePanel.add(genTimeSeriesLabel);
        genTimeSeriesListModel = new DefaultListModel();
        genTimeSeriesList = new JList(genTimeSeriesListModel);
        genTimeSeriesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        genTimeSeriesList.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                currentSelectedTimeSeriesList = genTimeSeriesList.getSelectedValuesList();
                framework.setCurrentlySelectedTimeSeriesList(currentSelectedTimeSeriesList);
                if (currentDisplayPlugin.isTimeSeriesChart()) framework.plotTimeSeries(currentSelectedTimeSeriesList);
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        genTimeSeriesList.addListSelectionListener(e -> {
            currentSelectedTimeSeriesList = genTimeSeriesList.getSelectedValuesList();
            framework.setCurrentlySelectedTimeSeriesList(currentSelectedTimeSeriesList);
            if (currentDisplayPlugin.isTimeSeriesChart()) framework.plotTimeSeries(currentSelectedTimeSeriesList);
        });
        genTimeSeriesListScrollPane = new JScrollPane(genTimeSeriesList);
        sidePanel.add(genTimeSeriesListScrollPane);

        sidePanel.add(timePointLabel);
        timePointListModel = new DefaultListModel();
        timePointList = new JList(timePointListModel);
        timePointList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        timePointList.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!currentDisplayPlugin.isTimeSeriesChart())
                    framework.plotTimePoint(timePointList.getSelectedValuesList());
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        timePointList.addListSelectionListener(e -> {
            if (!currentDisplayPlugin.isTimeSeriesChart())
                framework.plotTimePoint(timePointList.getSelectedValuesList());
            else {
                JOptionPane.showMessageDialog(frame, "Current chart does not support TimePoint plotting. " +
                        "Please switch to a chart that supports TimePoint plotting.");
            }
        });
        timePointListScrollPane = new JScrollPane(timePointList);
        sidePanel.add(timePointListScrollPane);


        scrollSidePane = new JScrollPane(sidePanel);
        containerPanel.add(scrollSidePane, BorderLayout.EAST);

        frame.add(containerPanel);


        // menu bar creation
        JMenuBar menuBar = new JMenuBar();

        JMenu newChartMenu = new JMenu(NEW_CHART_MENU_TITLE);
        newChartMenu.setMnemonic(KeyEvent.VK_F);
        for (DisplayPlugin displayPlugin : framework.getDisplayPluginList()) {
            JMenuItem newChartMenuItem = new JMenuItem(displayPlugin.getChartTypeName());
            newChartMenuItem.setMnemonic(KeyEvent.VK_N);
            newChartMenuItem.addActionListener(e -> {
                framework.newChart(displayPlugin);
                currentDisplayPlugin = displayPlugin;
            });
            newChartMenu.add(newChartMenuItem);
        }
        menuBar.add(newChartMenu);

        addDataMenu = new JMenu(ADD_DATA_MENU_TITLE);
        addDataMenu.setMnemonic(KeyEvent.VK_F);
        System.out.println(framework.getDataPluginList());
        for (DataPlugin dataPlugin : framework.getDataPluginList()) {
            JMenuItem addDataMenuItem = new JMenuItem(dataPlugin.getDataSourceName());
            addDataMenuItem.setMnemonic(KeyEvent.VK_N);
            addDataMenuItem.addActionListener(e -> {
                if (dataPlugin.getAvailableDataSetNames().isEmpty()) {
                    String filepath = JOptionPane.showInputDialog(frame, dataPlugin.getPrompt(),
                            dataPlugin.getDataSourceName());
                    if (!filepath.isEmpty()) framework.addDataSet(dataPlugin, filepath);
                } else {
                    JPanel selectPanel = new JPanel();
                    selectPanel.add(new JLabel("Select data set: "));
                    DefaultComboBoxModel model = new DefaultComboBoxModel();
                    for (String dataSetName : dataPlugin.getAvailableDataSetNames()) {
                        model.addElement(dataSetName);
                    }
                    JComboBox comboBox = new JComboBox(model);
                    selectPanel.add(comboBox);

                    int result = JOptionPane.showConfirmDialog(frame, selectPanel, dataPlugin.getDataSourceName(),
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        framework.addDataSet(dataPlugin, (String) comboBox.getSelectedItem());
                    }
                }
            });
            addDataMenu.add(addDataMenuItem);
        }
        addDataMenu.addSeparator();
        JMenuItem clearTimeSeriesData = new JMenuItem("Clear All TimeSeries");
        clearTimeSeriesData.addActionListener(e -> timeSeriesListModel.removeAllElements());
        addDataMenu.add(clearTimeSeriesData);

        JMenuItem clearGenTimeSeriesData = new JMenuItem("Clear All Generated TimeSeries");
        clearGenTimeSeriesData.addActionListener(e -> genTimeSeriesListModel.removeAllElements());
        addDataMenu.add(clearGenTimeSeriesData);

        JMenuItem clearTimePointsData = new JMenuItem("Clear All TimePoints");
        clearTimePointsData.addActionListener(e -> timePointListModel.removeAllElements());
        addDataMenu.add(clearTimePointsData);

        menuBar.add(addDataMenu);

        addDataMenu.setVisible(false);

        operationsMenu = new JMenu("Operations");
        operationsMenu.setMnemonic(KeyEvent.VK_F);
        plotTimeSeriesMenuItem = new JMenuItem("Plot Time Series");
        plotTimeSeriesMenuItem.setMnemonic(KeyEvent.VK_N);
        plotTimeSeriesMenuItem.addActionListener(e -> framework.plotTimeSeries(currentSelectedTimeSeriesList));
        plotTimePointMenuItem = new JMenuItem("Plot Time Points");
        plotTimePointMenuItem.setMnemonic(KeyEvent.VK_N);
        plotTimePointMenuItem.addActionListener(e -> {
            if (!currentDisplayPlugin.isTimeSeriesChart())
                framework.plotTimePoint(timePointList.getSelectedValuesList());
            else {
                JOptionPane.showMessageDialog(frame, "Current chart does not support TimePoint plotting. " +
                        "Please switch to a chart that supports TimePoint plotting.");
            }
        });
        cloneMenuItem = createCloneMenuItem();
        plotMovingAverageMenuItem = createMovingAverageMenuItem();
        plotGrowthRateMenuItem = createGrowthRateMenuItem();
        selectTimePointMenuItem = createSelectTimePointMenuItem();

        operationsMenu.add(plotTimeSeriesMenuItem);
        operationsMenu.add(cloneMenuItem);
        operationsMenu.add(plotTimePointMenuItem);
        operationsMenu.addSeparator();
        operationsMenu.add(plotMovingAverageMenuItem);
        operationsMenu.add(plotGrowthRateMenuItem);
        operationsMenu.addSeparator();
        for (BinaryOperationPlugin binaryOperationPlugin : framework.getBinaryOperationPluginList()) {
            JMenuItem opMenuItem = binaryOperationPlugin.getMenuItem(framework, frame);
            binaryOperationMenuItems.add(opMenuItem);
            operationsMenu.add(opMenuItem);
        }
        operationsMenu.addSeparator();
        for (AggregateOperationPlugin aggregateOperationPlugin : framework.getAggregateOperationPluginList()) {
            JMenuItem opMenuItem = aggregateOperationPlugin.getMenuItem(framework, frame);
            aggregateOperationMenuItems.add(opMenuItem);
            operationsMenu.add(opMenuItem);
        }
        operationsMenu.addSeparator();
        operationsMenu.add(selectTimePointMenuItem);


        menuBar.add(operationsMenu);
        operationsMenu.setVisible(false);

        setTimeSeriesPanelVisibility(false);
        setTimePointPanelVisibility(false);

        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onNewChart(JPanel chart, boolean isTimeSeriesChart) {
        addDataMenu.setVisible(true);
        operationsMenu.setVisible(true);
        if (isTimeSeriesChart) {
            setTimeSeriesPanelVisibility(true);
            setTimePointPanelVisibility(false);
        } else {
            setTimeSeriesPanelVisibility(false);
            setTimePointPanelVisibility(true);
        }
        chartContainer.remove(currentChartPanel);
        currentChartPanel = chart;
        chartContainer.add(currentChartPanel);
        chartContainer.validate();
        chartContainer.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDataSetAdded(DataSet dataSet, boolean isGen) {
        if (isGen) {
            for (TimeSeries timeSeries : dataSet.getTimeSeries()) {
                genTimeSeriesListModel.addElement(timeSeries);
            }
        } else {
            for (TimeSeries timeSeries : dataSet.getTimeSeries()) {
                timeSeriesListModel.addElement(timeSeries);
            }
        }

        for (TimePoint timePoint : dataSet.getTimePoints()) {
            timePointListModel.addElement(timePoint);
        }
    }

    private void setTimeSeriesPanelVisibility(boolean flag) {
        plotTimeSeriesMenuItem.setVisible(flag);
        plotMovingAverageMenuItem.setVisible(flag);
        plotGrowthRateMenuItem.setVisible(flag);
        for (JMenuItem binOpMenuItem : binaryOperationMenuItems) {
            binOpMenuItem.setVisible(flag);
        }
    }

    private void setTimePointPanelVisibility(boolean flag) {
        plotTimePointMenuItem.setVisible(flag);
    }

    private JMenuItem createMovingAverageMenuItem() {
        JMenuItem movingAverageMenuItem = new JMenuItem("Moving Average");
        movingAverageMenuItem.setMnemonic(KeyEvent.VK_N);
        movingAverageMenuItem.addActionListener(e -> {
            String numDaysString = JOptionPane.showInputDialog(frame,
                    "Enter Moving Average Window Size (in # days): ");
            if (numDaysString != null) {
                int numDays = Integer.parseInt(numDaysString);
                List<TimeSeries> newTimeSeriesList = new ArrayList<>();
                for (Object selected : currentSelectedTimeSeriesList) {
                    TimeSeries timeSeries = (TimeSeries) selected;
                    newTimeSeriesList.add(TimeSeries.simpleMovingAverage(timeSeries, numDays));
                }
                DataSet mA = new DataSet(newTimeSeriesList, Collections.emptyList(), "Moving Avg Gen");
                framework.addDataSet(mA);
            }
        });
        return movingAverageMenuItem;
    }

    private JMenuItem createGrowthRateMenuItem() {
        JMenuItem growthRateMenuItem = new JMenuItem("Growth Rate");
        growthRateMenuItem.setMnemonic(KeyEvent.VK_N);
        growthRateMenuItem.addActionListener(e -> {
            List<TimeSeries> newTimeSeriesList = new ArrayList<>();
            for (Object selected : currentSelectedTimeSeriesList) {
                TimeSeries timeSeries = (TimeSeries) selected;
                newTimeSeriesList.add(TimeSeries.growthRate(timeSeries, ChronoUnit.DAYS));
            }
            DataSet gR = new DataSet(newTimeSeriesList, Collections.emptyList(), "Growth Rate Gen");
            framework.addDataSet(gR);
        });
        return growthRateMenuItem;
    }

    private JMenuItem createCloneMenuItem() {
        JMenuItem growthRateMenuItem = new JMenuItem("Clone TimeSeries");
        growthRateMenuItem.addActionListener(e -> {
            List<TimeSeries> newTimeSeriesList = new ArrayList<>();
            for (Object selected : currentSelectedTimeSeriesList) {
                TimeSeries timeSeries = (TimeSeries) selected;
                newTimeSeriesList.add(new TimeSeries(timeSeries));
            }
            DataSet cloned = new DataSet(newTimeSeriesList, Collections.emptyList(), "Clone");
            framework.addDataSet(cloned);
        });
        return growthRateMenuItem;
    }

    private JMenuItem createSelectTimePointMenuItem() {
        JMenuItem selectTimePointMenuItem = new JMenuItem("Extract TimePoint");
        selectTimePointMenuItem.setMnemonic(KeyEvent.VK_N);
        selectTimePointMenuItem.addActionListener(e -> {
            if (currentSelectedTimeSeriesList.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Must choose at least 1 time series");
            } else {
                JPanel selectPanel = new JPanel();
                selectPanel.add(new JLabel("Select Time Points: "));
                DefaultComboBoxModel model = new DefaultComboBoxModel();
                Set<LocalDate> overlapTime = TimeSeries.getOverlapTime(currentSelectedTimeSeriesList);
                for (LocalDate time : overlapTime) {
                    model.addElement(time);
                }
                JComboBox comboBox = new JComboBox(model);
                selectPanel.add(comboBox);

                int result = JOptionPane.showConfirmDialog(frame, selectPanel,
                        "Select Time Point from Time Series", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    LocalDate selectedTime = (LocalDate) comboBox.getSelectedItem();
                    for (Object selected : currentSelectedTimeSeriesList) {
                        TimeSeries timeSeries = (TimeSeries) selected;
                        String timePointName = timeSeries.getName() + " on " + selectedTime.toString();
                        timePointListModel.addElement(new TimePoint(selectedTime, timeSeries.getValue(selectedTime),
                                timePointName));
                    }
                }
            }

        });
        return selectTimePointMenuItem;
    }
}
