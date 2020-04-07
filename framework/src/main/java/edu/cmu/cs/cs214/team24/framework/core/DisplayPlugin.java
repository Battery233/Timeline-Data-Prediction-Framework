package edu.cmu.cs.cs214.team24.framework.core;

import javax.swing.JPanel;

public interface DisplayPlugin extends Plugin{

    void setDisplayDataSet(DisplayDataSet data);

    JPanel display();
}
