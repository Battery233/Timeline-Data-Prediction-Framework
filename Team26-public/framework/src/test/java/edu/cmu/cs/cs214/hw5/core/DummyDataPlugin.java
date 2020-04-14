package edu.cmu.cs.cs214.hw5.core;

import edu.cmu.cs.cs214.hw5.core.datastructures.DataSet;

import java.util.List;

public class DummyDataPlugin implements DataPlugin {
    @Override
    public List<String> getAvailableDataSetNames() {
        return null;
    }

    @Override
    public DataSet getDataSet(String dataSetName) {
        return null;
    }

    @Override
    public String getDataSourceName() {
        return null;
    }

    @Override
    public String getPrompt() {
        return null;
    }
}
