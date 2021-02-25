package fr.unice.polytech.si3.qgl.qualituriers.utils.logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexandre Arcil
 * Représente le logger utilisé par Cockpit.
 */
public class CockpitLogger implements ILogger {

    private final List<String> messages;

    public CockpitLogger() {
        this.messages = new ArrayList<>();
    }

    /**
     * Log un message. Ils sera ensuite mis dans un fichier lors de execution du programme.
     * @param message le message à logger
     */
    @Override
    public void log(String message) {
        this.messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }
}
