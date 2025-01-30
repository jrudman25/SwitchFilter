/**
 * SwitchesTest.java
 * Tests Switches.java using junit
 * @author Jordan Rudman
 * @version 1.0
 */

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class SwitchesTest {

    @Before
    // Set up any necessary variables for testing
    public void setUp() {
        Switches.actuForceMin = "50";
        Switches.actuForceMax = "60";
        Switches.preTravelMin = "2.0";
        Switches.preTravelMax = "2.5";
        Switches.totalTravelMin = "3.5";
        Switches.totalTravelMax = "4.0";
        Switches.feel = "Tactile";
        Switches.mount = "Plate";
    }

    @Test
    // Test feel (Tactile)
    public void testFeelCheck() {
        assertFalse(Switches.feelCheck("Clicky"));
        assertTrue(Switches.feelCheck("Tactile"));
        assertTrue(Switches.feelCheck("tactile"));
        assertTrue(Switches.feelCheck("TACTILE"));
        assertFalse(Switches.feelCheck("Tactle"));
        assertFalse(Switches.feelCheck("Tactiles"));
        assertTrue(Switches.feelCheck("Clicky/Tactile"));
        assertTrue(Switches.feelCheck("Tactile/Linear"));
        assertFalse(Switches.feelCheck("Clicky/Linear"));
        assertFalse(Switches.feelCheck("Really Clicky"));
        Switches.feel = "none";
        assertTrue(Switches.feelCheck("none"));
        assertTrue(Switches.feelCheck("Clicky"));
        assertTrue(Switches.feelCheck("Tactile"));
    }

    @Test
    // Test actuation force (50-60g)
    public void testActuationCheck() {
        assertFalse(Switches.actuationCheck("45g"));
        assertTrue(Switches.actuationCheck("50g"));
        assertTrue(Switches.actuationCheck("55g"));
        assertTrue(Switches.actuationCheck("60g"));
        assertFalse(Switches.actuationCheck("65g"));
        assertFalse(Switches.actuationCheck("47/62g"));
        assertTrue(Switches.actuationCheck("57/62g"));
        assertTrue(Switches.actuationCheck("47/52g"));
        assertTrue(Switches.actuationCheck("52/57g"));
        assertTrue(Switches.actuationCheck("47/52/67g"));
        assertFalse(Switches.actuationCheck("47/62/67g"));
    }

    @Test
    // Test pre-travel (2.0-2.5mm)
    public void testPreTCheck() {
        assertFalse(Switches.preTCheck("1.0mm"));
        assertTrue(Switches.preTCheck("2.0mm"));
        assertTrue(Switches.preTCheck("2.2mm"));
        assertTrue(Switches.preTCheck("2.5mm"));
        assertFalse(Switches.preTCheck("3.0mm"));
    }

    @Test
    // Test total travel (3.5-4.0mm)
    public void testTotalTCheck() {
        assertFalse(Switches.totalTCheck("3.0mm"));
        assertTrue(Switches.totalTCheck("3.5mm"));
        assertTrue(Switches.totalTCheck("3.7mm"));
        assertTrue(Switches.totalTCheck("4.0mm"));
        assertFalse(Switches.totalTCheck("5.0mm"));
    }

    @Test
    // Test mount (Plate)
    public void testMountCheck() {
        assertFalse(Switches.mountCheck("PCB"));
        assertTrue(Switches.mountCheck("Plate"));
        assertTrue(Switches.mountCheck("plate"));
        assertTrue(Switches.mountCheck("PLATE"));
        assertFalse(Switches.mountCheck("Plat"));
        assertFalse(Switches.mountCheck("Plates"));
        Switches.mount = "none";
        assertTrue(Switches.mountCheck("none"));
        assertTrue(Switches.mountCheck("PCB"));
        assertTrue(Switches.mountCheck("Plate"));
    }
}
