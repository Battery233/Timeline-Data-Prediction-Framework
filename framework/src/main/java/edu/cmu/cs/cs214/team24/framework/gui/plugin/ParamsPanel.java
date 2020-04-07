package edu.cmu.cs.cs214.team24.framework.gui.plugin;

import edu.cmu.cs.cs214.team24.framework.core.Plugin;
import edu.cmu.cs.cs214.team24.framework.gui.DataPluginPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ParamsPanel extends JPanel {

    public ParamsPanel(DataPluginPanel parent, Map<String, List<String>> paramOptions){
        setLayout(new FlowLayout());

    }

    private JPanel getParamPanel(String param, List<String> options){
        JPanel paramPanel = new JPanel();
        paramPanel.setLayout(new GridLayout(2, 1));
        JLabel paramLabel = new JLabel(param);
        paramPanel.add(paramLabel);

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(new Vector<>());
        JComboBox<String> comboBox = new JComboBox<>(model);
        for (String option: options) {
            model.addElement(option);
        }
        comboBox.addActionListener(e -> {
            String selected = (String) comboBox.getSelectedItem();
        });

        paramPanel.add(comboBox);
        return paramPanel;
    }

}
