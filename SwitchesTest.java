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
        Switches.mount = "none";
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
    // Test feel
    public void testFeelCheck() {
        assertFalse(Switches.feelCheck("Clicky"));
        //assertTrue(Switches.feelCheck("none"));
        assertTrue(Switches.feelCheck("Tactile"));
        assertTrue(Switches.feelCheck("tactile"));
        assertTrue(Switches.feelCheck("TACTILE"));
        assertFalse(Switches.feelCheck("Tactle"));
        assertTrue(Switches.feelCheck("Clicky/Tactile"));
        assertTrue(Switches.feelCheck("Tactile/Linear"));
        assertFalse(Switches.feelCheck("Clicky/Linear"));
    }
}
