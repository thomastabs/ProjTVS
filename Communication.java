package prr.core;

public enum CommunicationType { SMS, VOICE; }

/**
* This class represents a communication (text or voice) made between
* two terminals.
**/

public class Communication {

    private Communication(CommunicationType type, Terminal to, Terminal from) { /* ... */ }

    // An already finished communication has a fixed size.
    // The size of a text communication is the number of characters in the message associated arredondado para cima
    // The size of a voice communication is the duration of the call in seconds.
    
    // The cost of a communication depends on the type of communication, its duration, the number of 
    // points and the number of friends of the client that initiated the communication.

    public static Communication textCommunication(Terminal to, Terminal from, int length) { /* ... */ } 
    
    public static Communication textCommunication(Terminal to, Terminal from) { /* ... */ }
    
    public void duration(int duration) { /* ... */ }
    
    public Terminal to() { /* ... */ }
    
    public Terminal from() { /* ... */ }
    
    double computeCost() { 
        /* 
        If the size of the communication is equal to 0, the cost is 0.
        If the size of the communication is less than 10 AND the number of points of the client that initiated the communication is bigger than 100 the cost is 1
        If the size of the communication is less than 10 AND the number of points of the client that initiated the communication is less than 100, the cost is 2.

        If the size of the communication is bigger or equal than 10 AND less than 120, 
        then its neccessary to consider the number of points of the client that initiated the communication and the commyunication type.
                - If the number of points is less than 75 and the comm type is TEXT, the cost is 6.  
                - If the number of points is less than 75 and the comm type is VOICE, the cost is 12.

                - If the number of points is greater than 75 or equal and the comm type is TEXT, the cost is 4.
                - If the number of points is greater than 75 or equal and the comm type is VOICE, then its necessary 
                to also consider the number of friends of the client that initiated the comm.
                    - If the number of friends is less than 4, the cost is 8.
                    - If the number of friends is greater or equal than 4, the cost is 5.

        If the size of the communication is greater or equal than 120 and the number of points of the client that initiated the communication is less than 150, 
        then the cost is 15. If the number of points is greater or equal than 150, then the cost is 12.
        ...        
        */ 
        }
    
    public double getCost() { /* ... */ }
}