# SwitchFilter
A program to filter a list of keyboard switches from a properly-formatted .csv file.

An example functional .csv file is included in this repository. The columns must be Model, Feel, Actuation, Pre-Travel, Total Travel, and Mount, respectively.

* Model: Name of the switch
* Actuation: Force needed to activate the switch
* Pre-travel: Travel distance before actuation, in mm
* Total Travel: Travel distance before bottoming out, in mm
* Mount: Mount type (typically PCB or plate)

To run, place the .java and .csv files in the same directory and run "java .\Switches.java" in the console. Output will be to the console, including the names of all the matching switches and the total number of matches.