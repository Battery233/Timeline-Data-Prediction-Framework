package edu.cmu.cs.cs214.hw5.framework.core;

import javax.swing.JPanel;

public interface DisplayPlugin extends Plugin{

    void setDataSet(DataSet metaData);

    void setDisplayDataSet(DisplayDataSet data);

    JPanel display();
}
