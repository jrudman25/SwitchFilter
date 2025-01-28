A program to filter a list of keyboard switches from a properly-formatted .csv file
An example functional .csv file is included in this repository. The columns must be Model, Feel, Actuation, Pre-Travel, Total Travel, and Mount, respectively
Model is the name of the switch, Actuation is the actuation force, Pre-travel is the required switch travel distance before actuation in mm, Total Travel is the travel distance before bottoming out, and Mount is the mount type (typically PCB or plate)
To run, place the .java and .csv file in the same directory and run "java .\Switches.java" in the console
Output will be to the console, including the names of all the matching switches and the total number of matches
