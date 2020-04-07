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

    private Map<String, JList<String>> optionLists = new HashMap<>();
    private Map<String, List<String>> paramOptions = new HashMap<>();

    public ParamsPanel(){
        setLayout(new FlowLayout());
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

    public Map<String, List<String>> getParamsValues(){
        Map<String, List<String>> paramsValues = new HashMap<>();
        for (String param: optionLists.keySet()){
            List<String> values = new ArrayList<>();
            JList<String> jlist = optionLists.get(param);
            int[] indices = jlist.getSelectedIndices();
            List<String> options = paramOptions.get(param);
            for (int idx: indices){
                values.add(options.get(idx));
            }
            paramsValues.put(param, values);
        }
        return paramsValues;
    }

    public void refresh(Map<String, List<String>> pOptions,
                        Map<String, Boolean> paramsMultiple){
        removeAll();
        this.paramOptions = pOptions;
        if (!paramOptions.isEmpty() && !paramsMultiple.isEmpty()) {
            for (String param : paramOptions.keySet()) {
                add(getParamPanel(param, paramOptions.get(param), paramsMultiple.get(param)));
            }
        }
        revalidate();
        repaint();
    }

}
