package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;

public class SelectCurrentCheckpointPathfinder implements IPathfinder {
    @Override
    public int getPriorityRank() {
        return Integer.MAX_VALUE;
    }

    @Override
    public CheckPoint getNextCheckpoint(PathfindingContext context) {
        return null;
    }
}
