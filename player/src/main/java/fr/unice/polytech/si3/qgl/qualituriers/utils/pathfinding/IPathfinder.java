package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;

public interface IPathfinder {
    int getPriorityRank();
    CheckPoint getNextCheckpoint(PathfindingContext context);
}
