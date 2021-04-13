package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding.Dijkstra;

import fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding.PathfindingNode;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class DijkstraStore {
    Map<PathfindingNode, PathSteps> shortestPaths = new HashMap<>();
    Map<PathfindingNode, Boolean> canUse = new HashMap<>();
}
