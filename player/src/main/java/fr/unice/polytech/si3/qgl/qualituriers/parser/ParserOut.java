package fr.unice.polytech.si3.qgl.qualituriers.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;

/**
 * @author williamdandrea
 */
public class ParserOut {

    private ObjectMapper om;

    public ParserOut() {
        om = new ObjectMapper();
    }

    public String returnOneJsonAction(Action action) {return "";
    }


}
