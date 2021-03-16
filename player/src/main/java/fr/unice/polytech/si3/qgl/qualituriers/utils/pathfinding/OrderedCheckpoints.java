package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;

import java.util.Comparator;

public class OrderedCheckpoints implements IPathfinder {
    @Override
    public int getPriorityRank() {
        return 0;
    }

    @Override
    public CheckPoint getNextCheckpoint(PathfindingContext context) {
        var checkpoint = context.getCheckPoints().stream()
                .min(Comparator.comparingDouble(cp ->
                        cp.getPosition().getPoint().substract(context.getBoat().getPosition().getPoint()).length())
                );

        if(checkpoint.isEmpty()) return new CheckPoint(context.getBoat().getPosition(), new Circle(10));
        else return checkpoint.get();
    }
}
