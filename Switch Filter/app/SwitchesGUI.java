/**
 * SwitchesGUI.java
 * A program to filter a list of keyboard switches from a properly-formatted .csv file. Now with GUI!
 * 
 * @author Jordan Rudman
 * @version 1.0
 */

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class SwitchesGUI extends JFrame {

    // GUI components
    private JTextField actuForceMinField = new JTextField(5);
    private JTextField actuForceMaxField = new JTextField(5);
    private JTextField preTravelMinField = new JTextField(5);
    private JTextField preTravelMaxField = new JTextField(5);
    private JTextField totalTravelMinField = new JTextField(5);
    private JTextField totalTravelMaxField = new JTextField(5);
    private JComboBox<String> feelBox = new JComboBox<>(new String[]{"Any", "tactile", "clicky", "linear", "silent tactile", "silent linear"});
    private JComboBox<String> mountBox = new JComboBox<>(new String[]{"Any", "plate", "PCB"});
    private JTextArea outputArea = new JTextArea(15, 40);

    // Filters (stored from fields)
    private static String actuForceMin;
    private static String actuForceMax;
    private static String preTravelMin;
    private static String preTravelMax;
    private static String totalTravelMin;
    private static String totalTravelMax;
    private static String feel;
    private static String mount;

    public SwitchesGUI() {
        setTitle("Keyboard Switch Filter");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.add(new JLabel("Actuation Force Min (g):"));
        inputPanel.add(actuForceMinField);
        inputPanel.add(new JLabel("Actuation Force Max (g):"));
        inputPanel.add(actuForceMaxField);
        inputPanel.add(new JLabel("Pre-Travel Min (mm):"));
        inputPanel.add(preTravelMinField);
        inputPanel.add(new JLabel("Pre-Travel Max (mm):"));
        inputPanel.add(preTravelMaxField);
        inputPanel.add(new JLabel("Total Travel Min (mm):"));
        inputPanel.add(totalTravelMinField);
        inputPanel.add(new JLabel("Total Travel Max (mm):"));
        inputPanel.add(totalTravelMaxField);
        inputPanel.add(new JLabel("Feel:"));
        inputPanel.add(feelBox);
        inputPanel.add(new JLabel("Mount:"));
        inputPanel.add(mountBox);

        JButton filterButton = new JButton("Filter Switches");
        filterButton.addActionListener(e -> filterSwitches());

        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(inputPanel, BorderLayout.NORTH);
        add(filterButton, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Pulls info from .csv and user input
     */
    private void filterSwitches() {
        try {
            // Grab values from fields
            actuForceMin = actuForceMinField.getText().isEmpty() ? "0" : actuForceMinField.getText();
            actuForceMax = actuForceMaxField.getText().isEmpty() ? "999" : actuForceMaxField.getText();
            preTravelMin = preTravelMinField.getText().isEmpty() ? "0" : preTravelMinField.getText();
            preTravelMax = preTravelMaxField.getText().isEmpty() ? "999" : preTravelMaxField.getText();
            totalTravelMin = totalTravelMinField.getText().isEmpty() ? "0" : totalTravelMinField.getText();
            totalTravelMax = totalTravelMaxField.getText().isEmpty() ? "999" : totalTravelMaxField.getText();

            feel = feelBox.getSelectedItem().toString();
            if(feel.equals("Any")) {
                feel = "none";
            }
            mount = mountBox.getSelectedItem().toString();
            if(mount.equals("Any")) {
                mount = "none";
            }

            List<String> list = new ArrayList<>();
            InputStream input = SwitchesGUI.class.getResourceAsStream("/switchesformatted.csv");
            if (input == null) {
                throw new FileNotFoundException("switchesformatted.csv not found in JAR!");
            }

            Scanner sc = new Scanner(input);
            sc.nextLine(); // skip header

            while(sc.hasNextLine()) {
                String[] arr = sc.nextLine().split(",");
                boolean feelCheck = feelCheck(arr[1]);
                boolean forceCheck = actuationCheck(arr[2]);
                boolean preTCheck = preTCheck(arr[3]);
                boolean totalTCheck = totalTCheck(arr[4]);
                boolean mountCheck = mountCheck(arr[5]);
                if (forceCheck && preTCheck && mountCheck && feelCheck && totalTCheck) {
                    list.add(arr[0]);
                }
            }
            sc.close();

            Collections.sort(list);
            outputArea.setText(String.join("\n", list) + "\n\nSwitch count: " + list.size());

        } catch(Exception ex) {
            outputArea.setText("Error: " + ex.getMessage());
        }
    }

    /**
     * Filters switches by feel
     * @param String The feel type to check 
     * @return True if feel type matches, false otherwise
     */
    public static boolean feelCheck(String str) {
        if(feel.equalsIgnoreCase("none")) {
            return true;
        }
        if(str.equalsIgnoreCase(feel))
        {
            return true;
        }
        else if(str.contains("/")) {
            for(String aFeel : str.split("/")) {
                if(aFeel.equalsIgnoreCase(feel)) return true;
            }
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
            for(String aForce : force.split("/")) {
                if(Double.parseDouble(aForce) >= Double.parseDouble(actuForceMin)
                        && Double.parseDouble(aForce) <= Double.parseDouble(actuForceMax)) {
                    return true;
                }
            }
        } else if(Double.parseDouble(force) >= Double.parseDouble(actuForceMin)
                && Double.parseDouble(force) <= Double.parseDouble(actuForceMax)) {
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
        String val = str.replaceAll("mm", "");
        return Double.parseDouble(val) >= Double.parseDouble(preTravelMin)
                && Double.parseDouble(val) <= Double.parseDouble(preTravelMax);
    }

    /**
     * Filters switches by total travel distance before bottoming out
     * @param String The total travel distance to check 
     * @return True if total travel distance is within range, false otherwise 
     */
    public static boolean totalTCheck(String str) {
        String val = str.replaceAll("mm", "");
        return Double.parseDouble(val) >= Double.parseDouble(totalTravelMin)
                && Double.parseDouble(val) <= Double.parseDouble(totalTravelMax);
    }

    /**
     * Filters switches by mount type
     * @param String The mount type to check 
     * @return True if mount type matches, false otherwise
     */
    public static boolean mountCheck(String str) {
        if (mount.equalsIgnoreCase("none")) {
            return true;
        }
        if (str.equalsIgnoreCase(mount)) {
            return true;
        }
        if (str.contains("/")) {
            for (String aType : str.split("/")) {
                if (aType.equalsIgnoreCase(mount)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Main method, runs the program.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SwitchesGUI().setVisible(true));
    }
}
