import Terminal;

package prr.core;
/**
* This class represents a client.
**/

public class Client {
    // Creates a client with a single terminal. The provided terminal cannot be null
    public Client(String name, int taxNumber, Terminal term) { 
        /* 
        Also creates a friend list that is initially empty and tthe client cannot be friend with itself.
        ... 
        */ 
}
    
    public void updateName(String n) { /* ... */ }
    
    public String getName() { 
        /* 
        Returns the name of the client. The name is a string with a maximum length of 40 characters.
        ... 
        */ 
    }
    

    // Each client has a number of points that vary from 0 to 200 points.
    // When a client is created, it has 20 points. 
    // The number of points of a client affects communications cost.

    // updates the number of points of the client. It can be a positive or negative number.
    public void updatePoints(int p) { /* ... */ }
    public int getPoints() { /* ... */ }
 

    public void addFriend(Client c) { 
        
        /* 
        Number of friends of a client is limited to the number of terminals of the client times 5 and minus 3.
        5 ∗ #terminals − 3
        ... 
        */ 
    }
    
    public boolean removeFriend(Client c) { /* ... */ }
    
    public boolean hasFriend(Client c) { /* ... */ }
    
    public void addTerminal(Terminal terminal) { 
        /* 
        Adds a terminal to the client. The number of client terminals vary from 1 to 9 terminals.
        ... 
        */ 
    }
    
    public boolean removeTerminal(Terminal terminal) { 
        /* 
        Removes a terminal from the client. The number of client terminals vary from 1 to 9 terminals.

        This method returns a boolean indicating if the deletion was successfull.
        A terminal can be removed only if it belongs to the client's terminals list and if the saldo nao for negativo.
        If the terminal does not belong to the client's terminals list, the method returns false.
        ... 
        */ 
    }
    
    // returns the number of terminals of this client
    public int numberOfTerminals() { /* ... */ }


    //It must be considered that invoking class methods that put the client in 
    //an invalid state should be aborted and thrown InvalidOperationException.
}