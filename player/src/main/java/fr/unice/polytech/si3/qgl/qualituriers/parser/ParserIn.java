package fr.unice.polytech.si3.qgl.qualituriers.parser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import fr.unice.polytech.si3.qgl.qualituriers.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Position;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Polygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParserIn {

    private ObjectMapper om;
    private JsonNode inputNode;

    public ParserIn(){
        om = new ObjectMapper();
    }

    public ParserIn(JsonNode inputNode) {
        om = new ObjectMapper();
        this.inputNode = inputNode;
    }

    public void initParser(String input) throws JsonProcessingException {
        inputNode = om.readTree(input);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public Boat createBoat() throws JsonProcessingException{
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Boat boat = om.treeToValue(inputNode.get("ship"), Boat.class);
        System.out.println(boat.getName());
        return boat;
    }


    /**
     * Cette méthode crée un nouveau checkpoint en parsant l'inputNode qui  été initialisé précédemment
     * MODIF : william
     * @return List de CheckPoints Optionnal adapté au type de forme, si erreur ou rien, return List vide
     * @throws JsonProcessingException
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public List<Optional<CheckPoint>> createCheckpoint() throws JsonProcessingException{

        List<Optional<CheckPoint>> checkPoint = new ArrayList<>();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        if (inputNode != null && inputNode.get("goal") != null && inputNode.get("goal").get("checkpoints") != null) {
            JsonNode checkpointsArrays = inputNode.get("goal").get("checkpoints");

            // Les checkpoints sont concidérés comme des arrayx car il y en a plusieurs, on va donc extraire chaque
            // checkpoints un par un.
            if (checkpointsArrays.isArray()) {
                for (JsonNode objNode : checkpointsArrays) {

                    checkPoint.add(returnTheGoodCheckpoint(objNode));

                }
            }
        }

        return checkPoint;
    }


    /**
     * Cette méthode récupére un checkPoint sous la forme d'un JsonNode et le transforme en un Optionnal<CheckPoint>
     * @param jsonNode qui est le json initial de l'objet checkpoint
     * @return l'objet Checkpoint wrapper d'un optionnal si il existe, Optionnal.empty sinon
     * @throws JsonProcessingException
     */
    Optional<CheckPoint> returnTheGoodCheckpoint(JsonNode jsonNode) throws JsonProcessingException {

        if (jsonNode == null || inputNode == null) {
            return Optional.empty();
        }



        JsonNode positionJsonNode = jsonNode.get("position");
        JsonNode shapeJsonNode = jsonNode.get("shape");
        // On recupere la position
        Position position = om.treeToValue(positionJsonNode, Position.class);

        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        if (shapeJsonNode != null) {
            if (shapeJsonNode.get("type").toString().contains("rectangle")) {
                Rectangle rect = om.treeToValue(shapeJsonNode, Rectangle.class);
                return Optional.of(new CheckPoint(position,rect));
            }

            if (shapeJsonNode.get("type").toString().contains("circle")) {
                Circle circle = om.treeToValue(shapeJsonNode, Circle.class);
                return Optional.of(new CheckPoint(position,circle));
            }

            // TODO: 25/01/2021  Gerer les polygon quand on aura la structure exacte
            if (shapeJsonNode.get("type").toString().contains("polygon")) {

                //System.out.println(shapeJsonNode);
                //Polygon polygon = om.treeToValue(shapeJsonNode, Polygon.class);
                //return Optional.of(new CheckPoint(position,polygon));
            }
        }

        return Optional.empty();

    }
}
