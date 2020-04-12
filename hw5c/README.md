## TimeSeries Data Framework
### Domain

A Time Series is a series of data points indexed in time order. It is usually comprised of data points taken at successive equally spaced points in time and it is thus discrete-time data. 
Time Series can be used in a massive number of domains (just to name a few, statistics, signal processing, econometrics, math finance, weather forecasting) that time series itself has become a domain of study. 

This framework is aimed to provide analytical tools and operations on time series from any domain and allows plugins to be written to import time series from custom sources and visualize them in custom ways. For the time matter of this assignment, we have implemented basic operations including time series arithmetic, growth rate, simple moving average, and time point extraction. Further analysis, such as curve fitting, prediction, classification and segmentation, could be supported by the framework in the future. 

### APIs

We describe a few data structure classes that are exposed to the client and are most useful when programming client programs.


#### The `TimeSeries` Class

This is a class that represents a time series. A `TimeSeries` can be created in two ways:

```java
// 1: an empty time series with only a name specified
TimeSeries ts1 = new TimeSeries("Sample Time Series");

//2: from an existing time series
TimeSeries ts2 = new TimeSeries(ts1);
```

Data can be inserted into the time series by calling `insert`:

```java
// suppose the existence of a `LocalDate` object time in scope
ts1.insert(time, 1.10);
```

Now value `1.10` is inserted into `ts1` at `time`.

Getter methods work as expected:
- `getValue(LocalDate time): double`, 
- `getTimeSpan(): Set<LocalDate>`,
- `getName(): String`.

Note that `TimeSeries` implements the `Iterable` interface. This provides a handy way of iterating the time series in time order using a foreach loop, such as

```java
for (Map.Entry<LocalDate, Double> e: ts1) {
    LocalDate time = e.getKey();
    double value = e.getValue();
    // ... do something ...
}
```
A few static library methods of manipulating time series are also provided in the `TimeSeries` class. For details, please refer to the javadoc for this class. 

Classes that implement the `{Binary, Aggregate}OperationPlugin` interfaces also are able to manipulate time series. You are not required to understand this to implement data or display plugins, nor are you asked to implement this additional extension point of time series manipulation provided by our framework (in addition to data or display plugins).

#### The `TimePoint` Class

This class represents a data point in a time series. It can be seen as a degenerate time series with only one time points. We suggest using the `TimePoint` class when the data contains only one time point (e.g. GDP across countries collected at a single year) because it avoids operations on the internal `TreeMap` in a `TimeSeries` and is thus more efficient.

Constructors and getters work similarly to those of the `TimeSeries` class. An additional `sortByValue(List<TimePoint> timePointList)` method is provided to sort a list of `TimePoint`s in-place.

#### The `DataSet` Class

This class is simply a package of multiple `TimeSeries` and `TimePoint` objects for data flow in the framework (e.g. from a `DataPlugin` into the framework).

A `DataSet` can be created by

```java
// assume `tsList` of type List<TimeSeries> and `tpList` of type List<TimePoint> in scope
DataSet ds = new DataSet(tsList, tpList, "Sample DataSet");
```

Getters `getTimeSeries()` and `getTimePoints()` return list of respective data structures contained in the dataset. A constant `DataSet.EMPTY_DATASET` is provided to represent an empty dataset.


### Plugin Implementation Instructions

The functionality of the framework is extensible by implementing the two types fo interfaces: `DataPlugin` to provide custom data sources and `DisplayPlugin` to add custom visualization methods. We describe the two interfaces and their respective methods to be implemented.

#### The `DataPlugin` Interface

 The data plugin interface for extracting data sets from one type of data source.
 
```java
    /**
     * Gets the names of all data sets that the data plugin provides.
     * (These names will be displayed on the GUI). Return empty list
     * if the data plugin requires user input (e.g. file path, data
     * name, etc.) to fetch data set
     */
    List<String> getAvailableDataSetNames();

    /**
     * Get a data set from the data plugin according to the string representation
     * of the data set. For example, the string could be file path, data name, etc.
     *
     * @param dataSetName the string needed to fetch the data set
     * @return the data set
     */
    DataSet getDataSet(String dataSetName);

    /**
     * Gets the name of the data source that that data plugin uses. For example,
     * a CSV data plugin should return "CSV". Yahoo Finance Web API data plugin
     * should return "Yahoo Finance Web API". This name will be displayed in GUI
     * when the user chooses data plugin
     *
     * @return the name of the data source that the data plugin uses
     */
    String getDataSourceName();

    /**
     * Gets the user input prompt for fetching data set from the data plugin. For example,
     * a CSV data plugin should have the prompt "Enter File Path". Yahoo Finance Web
     * API data plugin should have the prompt "Enter Stock Symbol". If the data plugin
     * DOES NOT require user input to fetch data, simply return the empty string "".
     *
     * @return the user input prompt for fetching data set from the data plugin
     */
    String getPrompt();
```


#### The `DisplayPlugin` Interface

The display plugin interface for generating plots of either time series or time points. Note that a display plugin must only support one type of chart. To add more chart types, please implement a separate display plugins for each. In addition, display plugins should wrap their chart into a Java Swing `JPanel` before returning the chart to the framework.

```java
/**
     * Gets the chart type name that the display plugin supports. For example,
     * a display plugin that implements line chart should return "Line Chart".
     * @return The name of the chart type that the display plugin supports
     */
    String getChartTypeName();

    /**
     * Returns a boolean flag indicating whether the display plugins' chart type
     * supports time series or not. For example, a line chart display plugin
     * should return true (since line chart plots time series), while a bar chart
     * display plugin should return false (since bar chart plots time points).
     * @return true if the display plugin's chart type supports time series, false if
     * the display plugin's chart type supports time points.
     */
    Boolean isTimeSeriesChart();

    /**
     * Gets an empty chart with no data.
     * @return an empty chart
     */
    JPanel getEmptyChart();

    /**
     * Plots the time series on the chart and return the chart as a JPanel.
     * If the display plugin's chart type does not support time series,
     * then return the empty chart. (for example, a display plugin implementing
     * heat map should return the empty chart since heat map cannot plot time
     * series)
     * @param timeSeriesList the list of time series that needs to be plotted
     *                       It is guaranteed that every time series in this
     *                       list has the same time unit. (For example, it could
     *                       be a list of day-based time series)
     * @return the plotted chart showing all time series
     */
    JPanel getTimeSeriesChart(List<TimeSeries> timeSeriesList);

    /**
     * Plots the time points on the chart and return the chart as a JPanel
     * If the display plugin's chart type does not support time points,
     * then return the empty chart. (for example, a display plugin implementing
     * line chart should return the empty chart since line chart is meant to
     * plot time series)
     * @param timePointList the list of time points that needs to be plotted
     * @return the plotted chart showing all time points
     */
    JPanel getTimePointChart(List<TimePoint> timePointList);
```

### Running the Framework with New Plugins
The plugins are loaded into the framework using `java.util.ServiceLoader`. To correctly load your plugins, please follow the steps below. Suppose the plugin project is called `myPluginProject`. 

#### Build Dependency
First, to add dependency to the framework, in `myPluginProject/settings.gradle`, add the following:

```gradle
include ':framework'
project(':framework').projectDir = file('../framework') // or your path to framework project
```

and in `myPluginProject/build.gradle`, add the following:

```gradle
mainClassName = 'edu.cmu.cs.cs214.hw5.Main' // or your Main class

dependencies {
    compile project(':framework')
}
```

#### Loading Plugins with `java.util.ServiceLoader`
Under `/myPluginProject/src/main/resources/META-INF/services/` (create if dir non-existent), create a file `edu.cmu.cs.cs214.hw5.core.DataPlugin` with its content being class names of your data plugins. An example is

```
edu.cmu.cs.cs214.hw5.plugins.data.CSVDataPlugin
edu.cmu.cs.cs214.hw5.plugins.data.YahooFinanceWebAPIPlugin
```

and another file `edu.cmu.cs.cs214.hw5.core.DisplayPlugin` with its content being class names of your display plugins. An example is

```
edu.cmu.cs.cs214.hw5.plugins.display.LineChartDisplayPlugin
edu.cmu.cs.cs214.hw5.plugins.display.RankingChartDisplayPlugin
```

#### Running
Now, running `$ gradle run` under `myPluginProject/` will launch the framework with your plugins automatically loaded. Good to go!


#### Navigating through the GUI
After the GUI program runs, the `New Chart` menu item will be displayed on the top left corner.

You must select a chart type from the `New Chart` drop down menu first. After that, other GUI 
components such as the `Add Data` menu and the `Operations` menu will display.

Use the menu items under `Add Data` to import data from the data plugins.

Use the menu items under `Operations` to apply the operations implemented by the binary operation
plugins and the aggregate operation plugins to the imported data.

The imported time series data will be displayed on the `Time Series` panel shown on the right of the frame. You can use the `Extract Data Point` operation under the `Operations` menu to choose time points from the selected time series and import them into the `Time Points` panel.

One important thing to note is that when a chart type that does not support time series is chosen through
`New Chart`, the `Operations` menu will not display menu items for operations on time series. 

The `Generated Time Series` panel contain all the time series that are computed from other time series 
through the operations implemented by the binary operation plugins and the aggregate opertion plugins

