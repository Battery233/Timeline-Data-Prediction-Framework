package edu.cmu.cs.cs214.hw5.framework.gui.plugin;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

public class ParamsPanel extends JPanel {

    private Map<String, JList<String>> optionLists = new HashMap<>();
    private Map<String, List<String>> paramOptions = new HashMap<>();
    private JScrollPane scroll;

    public ParamsPanel(){
        setLayout(new FlowLayout());
    }

    private JPanel getParamPanel(String param, List<String> options, boolean isMultiple){
        JPanel paramPanel = new JPanel();
        paramPanel.setLayout(new BoxLayout(paramPanel,BoxLayout.PAGE_AXIS));
        JLabel paramLabel = new JLabel(param);
        paramPanel.add(paramLabel);
        String selectionText = "(single selection)";
        if (isMultiple) selectionText = "(multiple selection)";
        JLabel selectionLabel = new JLabel(selectionText);
        paramPanel.add(selectionLabel);

        JList<String> optionList = new JList<>(options.toArray(new String[0]));
        if (!isMultiple) optionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        else optionList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int position = optionList.locationToIndex(e.getPoint());
                optionList.setSelectedIndex(position);
            }
        };
        optionList.addMouseListener(mouseListener);
        optionLists.put(param, optionList);
        JScrollPane scrollPane = new JScrollPane(optionList);
        scrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        paramPanel.add(scrollPane);
        return paramPanel;
    }

    public Map<String, List<String>> getParamsValues(){
        Map<String, List<String>> paramsValues = new HashMap<>();
        for (String param: optionLists.keySet()){
            List<String> values = new ArrayList<>();
            JList<String> jList = optionLists.get(param);
            int[] indices = jList.getSelectedIndices();
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
        optionLists = new HashMap<>();
        if (!paramOptions.isEmpty() && !paramsMultiple.isEmpty()) {
            for (String param : paramOptions.keySet()) {
                add(getParamPanel(param, paramOptions.get(param), paramsMultiple.get(param)));
            }
        }
        scroll.revalidate();
        scroll.repaint();
    }

    public void setScrollPane(JScrollPane sgp) {
        scroll = sgp;
    }
}
