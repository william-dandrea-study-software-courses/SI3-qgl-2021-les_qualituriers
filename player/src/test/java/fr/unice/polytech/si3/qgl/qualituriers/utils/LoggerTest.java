package fr.unice.polytech.si3.qgl.qualituriers.utils;

import fr.unice.polytech.si3.qgl.qualituriers.utils.logger.CockpitLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Alexandre Arcil
 */
public class LoggerTest {

    private CockpitLogger cockpitLogger;

    @BeforeEach
    private void init() {
        this.cockpitLogger = new CockpitLogger();
    }

    @Test
    public void emptyLog() {
        assertEquals(0, this.cockpitLogger.getMessages().size());
    }

    @Test
    public void log() {
        this.cockpitLogger.log("test");
        assertEquals(Collections.singletonList("test"), this.cockpitLogger.getMessages());
    }

}
