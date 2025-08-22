# SwitchFilter
A program to filter a list of keyboard switches from a properly-formatted .csv file.

An example functional .csv file is included in this repository. The columns must be Model, Feel, Actuation, Pre-Travel, Total Travel, and Mount, respectively.

* Model: Name of the switch
* Actuation: Force needed to activate the switch
* Pre-travel: Travel distance before actuation, in mm
* Total Travel: Travel distance before bottoming out, in mm
* Mount: Mount type (typically PCB or plate)

To use the exe, simply download it and run it.

To use the JAR, first make sure you have Java installed. Then, download the JAR and run it.

To use in terminal, place Switches.java and .csv file in the same directory and compile (I used javac). Then, run "java .\Switches.java" in the console and follow the prompts. Output will be to the console, including the names of all the matching switches and the total number of matches.
