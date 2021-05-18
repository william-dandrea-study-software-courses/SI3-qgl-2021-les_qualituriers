package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding.Dijkstra;

import fr.unice.polytech.si3.qgl.qualituriers.utils.logger.SeaDrawer;
import fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding.PathfindingNode;
import fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding.PathfindingResult;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Dijkstra {

    private static DijkstraStore init(List<PathfindingNode> nodes) {
        DijkstraStore store = new DijkstraStore();
        nodes.forEach(n -> store.canUse.put(n, true));
        nodes.forEach(n -> store.shortestPaths.put(n, new PathSteps(n)));
        return store;
    }

    private static void drawAll(DijkstraStore store) {
        for(var e : store.shortestPaths.values()) {
            PathfindingResult.createFrom(e, new ArrayList<>()).draw();
        }
        SeaDrawer.waitIfDebugMode(100);
    }

    public static PathSteps execute(PathfindingNode start, PathfindingNode goal, List<PathfindingNode> nodes) {
        var store = init(nodes);
        PathSteps searchingNode = PathSteps.root(start);
        while(store.canUse.values().stream().anyMatch(Boolean::booleanValue)) {
            List<PathSteps> paths = new ArrayList<>();

            // Set the visited nodes to not reachable
            store.canUse.replace(searchingNode.last(), false);

            // Set the node to process
            PathSteps finalSearchingNode = searchingNode;

            // Get not processed neighbours
            searchingNode.last().neighboursStream()
                    .filter(store.canUse::get)
                    .forEach(n -> paths.add(finalSearchingNode.complete(n)));

            // Foreach neighbours
            for(var p : paths) {
                // Siwtch of shortest path if it is valuable
                if(store.shortestPaths.get(p.last()).length() > p.length())
                    store.shortestPaths.replace(p.last(), p);
            }

            // getting the shortest path
            var minimalCurrentPath = store.shortestPaths.values().stream()
                    .filter(p -> store.canUse.get(p.last()))
                    //.filter(p -> p.last().neighboursStream().anyMatch(n -> store.canUse.get(n)))
                    .min(Comparator.comparingDouble(PathSteps::length));

            // For IDE
            if(minimalCurrentPath.isEmpty()) break;

            searchingNode = minimalCurrentPath.get();
        }

        var shortest = store.shortestPaths.get(goal);
        return shortest.length() == Double.MAX_VALUE ? null : shortest;
    }
}
