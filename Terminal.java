public enum TerminalMode { OFF, NORMAL, SILENT, BUS; }

public class Terminal {
    // creates a terminal with a given identifier and associated to the given client.
    Terminal(String id, Client client) { 
        /* 
        Terminals are identified by a unique identifier, cadeia de caracteres numericos.

        A new terminal is created with a saldo of 0.
        Each terminal has a saldo value which is the difference between the comms value and the amount paid by the client.

        Communications are charged according to the tariff of the terminal network.

        When a terminal is created, it is in the OFF mode. 
        ... 
        */ 
    }

    // Returns the mode of this terminal
    public final TerminalMode getMode() { /* ... */ }


    // Decreases the debt of this terminal by the given amount. The amount must be a number greater than 5 cents
    // It is only possible to pay if the terminal is in the OFF mode and the amount is a number greater or equal to 5 cents.
    public void pay(int amount) { /* ... */ }

    // can only be made with a turned on terminal that is not BUSY 
    // returns the balance of this terminal
    public int balance() { /* ... */ }

    // a non-occupied terminal which is turned on can be used to make a communication with other terminals even itself
    // the sending of a message is instantaneous but the receiving of a message depends on the terminal being turned on

    // send a SMS to terminal to with text msg. Returns if the SMS was successfully delivered.
    public boolean sendSMS(Terminal to, String msg) { /* ... */ }

    // receives a SMS from terminal from with text msg
    // The receiving of a message depends on the terminal being turned on.

    // If the terminal is in SIlENT mode, the client associated to the terminal that sends the message 
    // has to be part of the friend list of the client associated to the receiving terminal.
    public void receiveSMS(Terminal from, String msg) { /* ... */ }

    // can only be made with a turned on terminal that is not BUSY and if the 
    // receiving terminal is not in the BUSY mode and its in the normal mode
    // if the call is established, both terminals go to the BUSY mode going to their previous mode when the call ends

    // start a voice call with terminal to
    public void makeVoiceCall(Terminal to) { /* ... */ }

    // to invoke over the receiving terminal of a voice call (represented by c). The voice
    // call is established if the terminal accepts the call, otherwise it throws an exception.
    void acceptVoiceCall(Communication c) { /* ... */ }

    // turns on this terminal
    public void turnOn() { 
        /* 
        When a terminal is turned on, it is in the normal mode.
        ... 
        */ 
    }

    // can only be made with a turned on terminal that is not BUSY
    // turns off this terminal
    public void turnOff() { /* ... */ }

    // a terminal turned on can be in one of the following modes: normal, silent or busy
    // toggles the On mode: normal to silent or silent to normal
    public void toggleOnMode() { /* ... */ }

    // Ends the ongoing communication.
    public void endOngoingCommunication() { /* ... */ }

    // if the invoking these class methods doesnt do any of the following implementations 
    // then the method should not have any effect and throw an exception InvalidInvocationException
}