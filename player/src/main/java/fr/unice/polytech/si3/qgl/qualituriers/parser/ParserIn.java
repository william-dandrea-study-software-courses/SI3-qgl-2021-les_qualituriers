package fr.unice.polytech.si3.qgl.qualituriers.parser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;

public class ParserIn {

    private JsonNode inputNode;

    public ParserIn(){}

    public void initParser(String input) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        inputNode = om.readTree(input);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public Boat createBoat() throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Boat boat = om.treeToValue(inputNode.get("ship"), Boat.class);
        System.out.println(boat.getName());
        return boat;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public CheckPoint createCheckpoint() throws JsonProcessingException{
        CheckPoint checkPoint;
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        switch(inputNode.get("checkpoints").get("shape").get("type").toString()){
            case "rectangle":
                checkPoint = om.treeToValue(inputNode.get("checkpoints"), CheckPoint.class);
            case "circle":
                checkPoint = om.treeToValue(inputNode.get("checkpoints"), CheckPoint.class);
            case "polygon":
                checkPoint = om.treeToValue(inputNode.get("checkpoints"), CheckPoint.class);
            default:
                checkPoint = new CheckPoint();
        }

        System.out.println(checkPoint.getShapesAsString());
        return checkPoint;
    }
}
