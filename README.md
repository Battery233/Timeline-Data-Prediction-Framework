# Timeline Data Prediction Framework - Team 24

## Overview
This framework is designed to manage dataset with timeline, and predict data for the next time unit (e.g. a day) 
using Apache Math library polynomial regression fitter. This fitter is more suitable for quickly fitting two-dimension 
data with a timeline. Potential sophisticated fitter can be applied with more dimensions added.
It accepts two types of plugins, Data Plugin and Display Plugin.

###  Data Plugin
A data plugin for this framework should provide one or more dataset with a timeline, given the parameters 
(either multiple selection or single selection) 
configured by the user. The user must also configure the time period with a start date and an end date.  
With the configuration, data plugin can then grab the multiple datasets ready to display.  
For example:  
Dataset1: base USD, currency of CAD over 2020-01-05 t 2020-03-02  
Dataset2: base USD, currency of EUR over 2020-01-05 t 2020-03-02  
Dataset2: base USD, currency of CNY over 2020-01-05 t 2020-03-02  
The framework will then predict the next time unit of each dataset above.

###  Display Plugin
A display plugin for this framework should provide a visualization method for the specified datasets. 
Multiple datasets their predictions configured by the user in the data plugin form the input of each display plugin.  
For example:  
Given the datasets 1-3 configured from the above step, the user can choose two datasets dataset1 and dataset2.
Linechart display plugin will then draw two lines with their predictions over the same time period on the same graph.

###  User Interface
Start by inserting Data Plugins and Display Plugins in edu.cmu.cs.cs214.hw5.plugin  
Run edu.cmu.cs.cs214.hw5.Main.main()
In the right panel, according to the red hints:  
select a data plugin -> choose a start date -> choose an end date  
-> configure each parameter, notice that they can be either multiple or single selected, with hints in the
parentheses.  
-> If there is no parameter needs to be configured or configuration is completed, proceed to click the "get data" button.  
To make multiple selection, press shift.  
Once the red hint becomes blue, you can then proceed to choose a display plugin.  
select a display plugin -> configure each parameter as described above -> click the "display button".
Once the red hint becomes blue, you can then see the display picture in the left half panel.  
You can modify your plugin choice and get a new display picture at any time.
