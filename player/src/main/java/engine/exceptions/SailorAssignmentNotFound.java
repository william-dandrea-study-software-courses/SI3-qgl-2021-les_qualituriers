package engine.exceptions;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.BoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

public class SailorAssignmentNotFound extends RuntimeException{

    public SailorAssignmentNotFound(int sailorId, Point sailorPosition, BoatEntities assigment) {
        super("Assigment " + assigment.toString() + " were not found for the sailor " + sailorId + " at the position " + sailorPosition);
    }
}
