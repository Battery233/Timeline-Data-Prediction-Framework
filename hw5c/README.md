# How to run our plugins

Use **gradle run** command to execute the program.

## Data Plugins

### Currency Rate Data Plugin
This plugin requires input of three-character currency code of any country as the base currency. 

### Advanced Daily Stock Data Plugin 
This plugin requires an API key from www.eodhistoricaldata.com. You can have up to 20 API requests every day with a free account. Replace the content of AdvancedDailyStockDataPlugin.txt under the hw5c/src/main/resources/API-KEY/ with your own API key.  
This plugin requires an input of stock code.

### Federal Reserve Historical Economic Data plugin 
This plugin requires an API key from www.quandl.com. You need to input the API key to the pop up window when you run the plugin.

## Display Plugins

### Chart Matrix Display Plugin
This a plugin for displaying TimeSeries.  
This plugin displays each time series data as a line chart. Together combine multiple of them as a chart matrix.

### Bar Chart Display Plugin
This a plugin for displaying TimePoint.  
This plugin displays multiple time point data on the same bar chart.

### Pie Chart Display Plugin
This a plugin for displaying TimePoint.  
This plugin displays multiple time point data on the same pie chart.


