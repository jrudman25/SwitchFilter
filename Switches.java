import java.io.*;
import java.util.*;

/**
 * Switches.java
 * A program to filter a list of keyboard switches from a properly-formatted .csv file
 * To use, place a .csv file of the switch chart in the same directory as this file and run. Output will be to terminal.
 * @author Jordan Rudman
 * @version 1.0
 */
public class Switches {

    int accForce = -1;
    int preTravel = -1;
    String feel = "";
    String mount = "";

    /**
     * Main method, runs the program.
     */
    public static void main(String[] args) {
        try {
            List<String> list = new ArrayList<String>(); // the list to hold matching switches
            File file = new File("./switchesformatted.csv");
            Scanner sc = new Scanner(file);
            sc.useDelimiter(",");
            String line = sc.nextLine(); // skip first line with column names
            while(sc.hasNextLine()) { // run until EOF
                line = sc.nextLine();
                String[] arr = line.split(",");
                boolean forceCheck = actuationCheck(arr[2]);
                boolean preTCheck = preTCheck(arr[3]);
                if ( arr[1].equals("Linear")  && forceCheck && preTCheck ){
                    list.add("\n" + arr[0]);
                }
            }
            // sort alphabetically and print list + size
            System.out.println(list);
            System.out.println("Switch count: " + list.size());
            sc.close();
        }
        catch(FileNotFoundException e) {
            System.out.println("File not found! Make sure the file is in the same directory as this program and that your .csv file is named 'switchesformatted.csv' unless you've edited this program to change the file name input!");
        }
    }

    /**
     * Filters switches by actuation force
     * @param String The actuation force to check 
     * @return True if actuation force is within range, false otherwise
     */
    public static boolean actuationCheck(String str) {
        String force = str.replaceAll("g", "");
        if(force.contains("/")) {
            String[] splitForces = force.split("/");
            for (String aForce : splitForces) {
                if(Double.parseDouble(aForce) >= 55.0 && Double.parseDouble(aForce) <= 85.0) {
                    return true;
                }
            }
        }
        else if(Double.parseDouble(force) >= 55.0 && Double.parseDouble(force) <= 85.0) {
            return true;
        }
        return false;
    }

    /**
     * Filters switches by actuation force
     * @param String The pre-travel distance to check 
     * @return True if pre-travel distance is within range, false otherwise 
     */
    public static boolean preTCheck(String str) {
        String force = str.replaceAll("mm", "");
        if(Double.parseDouble(force) >= 1.9) {
            return true;
        }
        return false;
    }
}
