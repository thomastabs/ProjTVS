package test;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

import ap.Client;
import ap.Terminal;

public class TestClient {

    // Test Case 1: SUCCESS
    /*
    c.name length = 40
    Number of terminals = 4
    Number of points = 90
    Maxfriends = 5 * terminals − 3
    c.ti (condition1)= True
    Client !in friends (condition2) = true
    Expected Result = SUCCESS
    */
    @Test
    public void testCase1_Success() {
        Client client = new Client("A".repeat(40), 90, new Terminal("T0"));
        for (int i = 1; i < 4; i++) {
            client.addTerminal(new Terminal("T" + i));
        }

        int maxFriends = 5 * client.getNumberOfTerminals() - 3;
        for (int i = 0; i < maxFriends; i++) {
            client.addFriend(new Client("F" + i, i, new Terminal("FT" + i)));
        }

        assertEquals(client.getName().length(), 40);
        assertEquals(client.getPoints(), 90);
        assertEquals(client.getNumberOfTerminals(), 4);
        assertFalse(client.hasFriend(client));
    }

    // Test Case 2: FAIL (Name too long)
    /*
    c.name length = 41
    Number of terminals = 5
    Number of points = 100
    Maxfriends = 5 * terminals − 3
            c.ti (condition1)= True
    Client !in friends (condition2) = true
    Expected Result = FAIL
    */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCase2_Fail_NameTooLong() {
        new Client("B".repeat(41), 100, new Terminal("T0"));
    }

    // Test Case 3: SUCCESS (Min terminal count)
    /*
    c.name length = 1
    Number of terminals = 1
    Number of points = 110
    Maxfriends = 5 * terminals − 3
    c.ti (condition1)= True
    Client !in friends (condition2) = true
    Expected Result = SUCCESS
     */
    @Test
    public void testCase3_Success_MinTerminals() {
        Client client = new Client("C", 110, new Terminal("T0"));
        int maxFriends = 5 * client.getNumberOfTerminals() - 3;

        for (int i = 0; i < maxFriends; i++) {
            client.addFriend(new Client("F" + i, i, new Terminal("FT" + i)));
        }

        assertEquals(client.getPoints(), 110);
        assertEquals(client.getNumberOfTerminals(), 1);
        assertFalse(client.hasFriend(client));
    }

    // Test Case 4: FAIL (Zero terminals)
    /*
    c.name length = 2
    Number of terminals = 0
    Number of points = 120
    Maxfriends = 5 * terminals − 3
            c.ti (condition1)= True
    Client !in friends (condition2) = true
    Expected Result = FAIL
     */
    @Test(expectedExceptions = IllegalStateException.class)
    public void testCase4_Fail_ZeroTerminals() {
        Client client = new Client("D", 120, null); // No terminal
        client.validate(); // Hypothetical method to validate internal state
    }

    // Test Case 5: SUCCESS (High terminal count)
    /*
    c.name length = 3
    Number of terminals = 9
    Number of points = 130
    Maxfriends = 5 * terminals − 3
            c.ti (condition1)= True
    Client !in friends (condition2) = true
    Expected Result = SUCCESS

     */
    @Test
    public void testCase5_Success_HighTerminalCount() {
        Client client = new Client("E", 130, new Terminal("T0"));
        for (int i = 1; i < 9; i++) {
            client.addTerminal(new Terminal("T" + i));
        }

        int maxFriends = 5 * client.getNumberOfTerminals() - 3;
        for (int i = 0; i < maxFriends; i++) {
            client.addFriend(new Client("F" + i, i, new Terminal("FT" + i)));
        }

        assertEquals(client.getNumberOfTerminals(), 9);
        assertFalse(client.hasFriend(client));
    }

    // Test Case 6: FAIL (Too many terminals)
    /*
    c.name length = 4
    Number of terminals = 10
    Number of points = 140
    Maxfriends = 5 * terminals − 3
            c.ti (condition1)= True
    Client !in friends (condition2)= true
    Expected Result = FAIL
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCase6_Fail_TooManyTerminals() {
        Client client = new Client("F", 140, new Terminal("T0"));
        for (int i = 1; i <= 10; i++) {
            client.addTerminal(new Terminal("T" + i));
        }
    }

    // Test Case 7: SUCCESS (Zero points)
    /*
    c.name length = 5
    Number of terminals = 1
    Number of points = 0
    Maxfriends = 5 * terminals − 3
            c.ti (condition1)= True
    Client !in friends (condition2)= true
    Expected Result = SUCCESS

     */
    @Test
    public void testCase7_Success_ZeroPoints() {
        Client client = new Client("G", 0, new Terminal("T0"));
        int maxFriends = 5 * client.getNumberOfTerminals() - 3;

        for (int i = 0; i < maxFriends; i++) {
            client.addFriend(new Client("F" + i, i, new Terminal("FT" + i)));
        }

        assertEquals(client.getPoints(), 0);
        assertEquals(client.getNumberOfTerminals(), 1);
        assertFalse(client.hasFriend(client));
    }

    // Test Case 8: FAIL (Negative points)
    /*
    c.name length = 6
    Number of terminals = 2
    Number of points = -1
    Maxfriends = 5 * terminals − 3
    c.ti (condition1)= True
    Client !in friends = (condition2)true
    Expected Result = FAIL
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCase8_Fail_NegativePoints() {
        new Client("H", -1, new Terminal("T0"));
    }
}
