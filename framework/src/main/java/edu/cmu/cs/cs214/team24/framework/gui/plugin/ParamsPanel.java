package edu.cmu.cs.cs214.team24.framework.gui.plugin;

import edu.cmu.cs.cs214.team24.framework.core.Plugin;
import edu.cmu.cs.cs214.team24.framework.gui.DataPluginPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

public class ParamsPanel extends JPanel {

    DataPluginPanel parent;
    Map<String, JList<String>> optionLists = new HashMap<>();

    public ParamsPanel(DataPluginPanel parent, Map<String,
            List<String>> paramOptions, Map<String, Boolean> paramsMultiple){
        this.parent = parent;
        setLayout(new FlowLayout());
        for (String param: paramOptions.keySet()){
            add(getParamPanel(param, paramOptions.get(param), paramsMultiple.get(param)));
        }
    }

    private JPanel getParamPanel(String param, List<String> options, boolean isMultiple){
        JPanel paramPanel = new JPanel();
        paramPanel.setLayout(new GridLayout(2, 1));
        JLabel paramLabel = new JLabel(param);
        paramPanel.add(paramLabel);

        JList<String> optionList = new JList<>(options.toArray(new String[0]));
        if (!isMultiple) optionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        else optionList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int position = optionList.locationToIndex(e.getPoint());
                optionList.setSelectedIndex(position);
            }
        };
        optionList.addMouseListener(mouseListener);
        optionLists.put(param, optionList);
        JScrollPane scrollPane = new JScrollPane(optionList);
        paramPanel.add(scrollPane);
        return paramPanel;
    }

}
