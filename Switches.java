/**
 * Switches.java
 * A program to filter a list of keyboard switches from a properly-formatted .csv file
 * To use, place a .csv file of the switch chart in the same directory as this file and run. Output will be to terminal.
 * @author Jordan Rudman
 * @version 2.0
 */

import java.io.*;
import java.util.*;

public class Switches {

    static String actuForceMin;
    static String actuForceMax;
    static String preTravelMin;
    static String preTravelMax;
    static String totalTravelMin;
    static String totalTravelMax;
    static String feel;
    static String mount;

    /**
     * Main method, runs the program.
     */
    public static void main(String[] args) {
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter desired minimum actuation force in g, or 0 for none");
            actuForceMin = input.nextLine();
            System.out.println("Enter desired maximum actuation force in g, or 999 for none");
            actuForceMax = input.nextLine();
            System.out.println("Enter desired minimum pre-travel in mm, or 0 for none");
            preTravelMin = input.nextLine();
            System.out.println("Enter desired maximum pre-travel in mm, or 999 for none");
            preTravelMax = input.nextLine();
            System.out.println("Enter desired minimum total travel in mm, or 0 for none");
            totalTravelMin = input.nextLine();
            System.out.println("Enter desired maximum total travel in mm, or 999 for none");
            totalTravelMax = input.nextLine();
            System.out.println("Enter desired switch feel (tactile, clicky, linear, or silent linear), or type \"none\"");
            feel = input.nextLine();
            System.out.println("Enter desired mount type (plate, PCB), or type \"none\"");
            mount = input.nextLine();
            input.close();

            List<String> list = new ArrayList<String>(); // the list to hold matching switches
            File file = new File("./switchesformatted.csv");
            Scanner sc = new Scanner(file);
            sc.useDelimiter(",");
            String line = sc.nextLine(); // skip first line with column names
            while(sc.hasNextLine()) { // run until EOF
                line = sc.nextLine();
                String[] arr = line.split(",");
                boolean feelCheck = feelCheck(arr[1]);
                boolean forceCheck = actuationCheck(arr[2]);
                boolean preTCheck = preTCheck(arr[3]);
                boolean totalTCheck = totalTCheck(arr[4]);
                boolean mountCheck = mountCheck(arr[5]);
                if(forceCheck && preTCheck && mountCheck && feelCheck && totalTCheck) {
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
     * Filters switches by feel
     * @param String The feel type to check 
     * @return True if feel type matches, false otherwise
     */
    public static boolean feelCheck(String str) {
        if(str.contains("/")) {
            String[] splitFeels = str.split("/");
            for(String aFeel : splitFeels) {
                if(aFeel.equalsIgnoreCase(feel) || feel.equalsIgnoreCase("none")) {
                    return true;
                }
            }
        }
        else if(str.equalsIgnoreCase(feel) || feel.equalsIgnoreCase("none")){
            return true;
        }
        return false;
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
                if(Double.parseDouble(aForce) >= Double.parseDouble(actuForceMin) && Double.parseDouble(aForce) <= Double.parseDouble(actuForceMax)) {
                    return true;
                }
            }
        }
        else if(Double.parseDouble(force) >= Double.parseDouble(actuForceMin) && Double.parseDouble(force) <= Double.parseDouble(actuForceMax)) {
            return true;
        }
        return false;
    }

    /**
     * Filters switches by pre-travel distance
     * @param String The pre-travel distance to check 
     * @return True if pre-travel distance is within range, false otherwise 
     */
    public static boolean preTCheck(String str) {
        String force = str.replaceAll("mm", "");
        if(Double.parseDouble(force) >= Double.parseDouble(preTravelMin) && Double.parseDouble(force) <= Double.parseDouble(preTravelMax)) {
            return true;
        }
        return false;
    }

    /**
     * Filters switches by total travel distance before bottoming out
     * @param String The total travel distance to check 
     * @return True if total travel distance is within range, false otherwise 
     */
    public static boolean totalTCheck(String str) {
        String force = str.replaceAll("mm", "");
        if(Double.parseDouble(force) >= Double.parseDouble(totalTravelMin) && Double.parseDouble(force) <= Double.parseDouble(totalTravelMax)) {
            return true;
        }
        return false;
    }

    /**
     * Filters switches by mount type
     * @param String The mount type to check 
     * @return True if mount type matches, false otherwise
     */
    public static boolean mountCheck(String str) {
        if(str.contains("/")) {
            String[] splitTypes = str.split("/");
            for(String aType : splitTypes) {
                if(aType.equalsIgnoreCase(mount) || mount.equalsIgnoreCase("none")) {
                    return true;
                }
            }
        }
        else if(str.equalsIgnoreCase(mount) || mount.equalsIgnoreCase("none")){
            return true;
        }
        return false;
    }
}
