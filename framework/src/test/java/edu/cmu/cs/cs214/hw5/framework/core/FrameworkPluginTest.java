package edu.cmu.cs.cs214.hw5.framework.core;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.mockito.internal.util.reflection.FieldSetter;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class FrameworkPluginTest {

    @InjectMocks
    private FrameworkImpl mockFramework;

    @Mock
    private DataPlugin mockDataPlugin;
    @Mock
    private DisplayPlugin mockDisplayPlugin;
    @Mock
    private StatusChangeListener mockListener;
    @Mock
    private Date mockStartDate;
    @Mock
    private Date mockEndDate;
    @Mock
    private Map<String, List<String>> mockParams;
    @Mock
    private DataSet mockDataSet;

    @Captor
    private ArgumentCaptor<DataSet> dataSetArgumentCaptor;
    @Captor
    private ArgumentCaptor<DisplayDataSet> displayDataSetArgumentCaptor;

    @Before
    public void setUp() throws NoSuchFieldException {
        MockitoAnnotations.initMocks(this);
        mockFramework.setCurrentDataPlugin(mockDataPlugin);
        mockFramework.setCurrentDisplayPlugin(mockDisplayPlugin);
        mockFramework.setStatusChangeListener(mockListener);
        FieldSetter.setField(mockFramework, mockFramework.getClass().getDeclaredField("dataset"), mockDataSet);
    }

    @Test
    public void setPlugins(){
        verify(mockDisplayPlugin).clearToDisplay();
    }

    @Test
    public void registerPlugin(){
        mockFramework.registerPlugin(mockDataPlugin);
    }

    @Test
    public void setDisplayPluginOptions(){
        mockFramework.setDisplayPluginOptions();
        verify(mockDisplayPlugin).setDataSet(dataSetArgumentCaptor.capture());
    }

    @Test
    public void getParamOptions(){
        mockFramework.getParamOptions(true);
        verify(mockDataPlugin).getParamOptions();
        mockFramework.getParamOptions(false);
        verify(mockDisplayPlugin).getParamOptions();
    }

    @Test
    public void getParamsMultiple(){
        mockFramework.getAreDataParamsMultiple(true);
        verify(mockDataPlugin).areParamsMultiple();
        mockFramework.getAreDataParamsMultiple(false);
        verify(mockDisplayPlugin).areParamsMultiple();
    }

    @Test
    public void setPluginParameters(){
        mockFramework.setPluginParameters(true, mockParams, mockStartDate, mockEndDate);
        verify(mockDataPlugin).setTimePeriod(mockStartDate, mockEndDate);
    }

    @Test
    public void getData(){
        mockFramework.getData();
        verify(mockDataPlugin).getData();
    }

}
