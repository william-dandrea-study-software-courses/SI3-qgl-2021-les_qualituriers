package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding.dijkstra;

import fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding.PathfindingNode;

import java.util.HashMap;
import java.util.Map;

public class DijkstraStore {
    Map<PathfindingNode, PathSteps> shortestPaths = new HashMap<>();
    Map<PathfindingNode, Boolean> canUse = new HashMap<>();
}
