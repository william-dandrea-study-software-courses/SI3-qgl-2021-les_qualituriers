package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.render.TempoRender;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.logger.SeaDrawer;
import fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding.Dijkstra.Dijkstra;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Segment;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class PathfindingProblem {
    private final List<PositionablePolygon>  polygons = new ArrayList<>();
    private final List<PositionablePolygon>  enlargedPolygons = new ArrayList<>();

    private final List<PathfindingNode> nodes = new ArrayList<>();

    private final PathfindingNode startPosition;
    private final PathfindingNode goal;


    PathfindingProblem(PathfindingNode startPosition, PathfindingNode goal) {
        this.startPosition = startPosition;
        this.goal = goal;
    }

    /**
     * TESTED
     * Ajoute un polygone à éviter
     * @param polygon
     */
    void addPolygon(PositionablePolygon polygon) {

        polygons.add(polygon);
        var enlarged = polygon.enlargeOf(Config.BOAT_MARGIN * 4);
        enlargedPolygons.add(enlarged);

        //SeaDrawer.drawPolygon(enlarged, Color.magenta);
    }

    List<PositionablePolygon> getPolygons() {
        return this.polygons;
    }

    List<PositionablePolygon> getEnlargedPolygons() {
        return this.enlargedPolygons;
    }

    /**
     * TESTED
     * @param node
     * @return Un noeud en dehors des polygones
     */
    PathfindingNode getNearestOutsideLimitNode(PathfindingNode node) {
        node = new PathfindingNode(node.getPosition());
        var poly = Collisions.getCollidingPolygon(node.toPositionableCircle(), enlargedPolygons.stream());

        // Cas très très rare: Si le point est au centre d'un obstacle (voir impossible sinon le bateau serait dans l'incapacité de l'atteindre)
        if(poly != null && node.getPosition().equals(poly.getTransform().getPoint()))
            node.setPosition(node.getPosition().add(new Point(0, 50)));

        // Move the point while it collid with an enlarged polygon.
        while(poly != null) {
            Point pt = node.getPosition();
            var dir = pt.substract(poly.getTransform().getPoint());
            var dist = dir.length();
            dir = dir.normalized();

            for(double d = dist; Collisions.isColliding(node.toPositionableCircle(), poly); d += 50)
                node.setPosition(poly.getTransform().getPoint().add(dir.scalar(d)));

            poly = Collisions.getCollidingPolygon(node.toPositionableCircle(), enlargedPolygons.stream());
        }
        return node;
    }

    /**
     * Génère les noeuds à partir des Polygones agrandis
     * TESTED
     */
    void generateNodes() {
        for(var poly : enlargedPolygons) {
            nodes.addAll(PathfindingNode.createFrom(poly));
        }
    }

    List<PathfindingNode> getNodes() {
        return this.nodes;
    }

    /**
     * Génère les routes entre les noeuds
     * TESTED
     */
    void generateRoads() {
        // checking one by one if a road canBeCreated between the nodes
        for(var n1 : nodes) {
            for(var n2 : nodes) {
                if(n1 != n2)
                    PathfindingRoad.createIfPraticable(n1, n2, Config.BOAT_MARGIN * 3, polygons.stream());
            }
        }
    }

    /**
     * @return Le résultat de la recherche de chemin
     */
    PathfindingResult solve() {
        // Get node outside the limit to counter StackOverflows
        var pseudoStart = getNearestOutsideLimitNode(startPosition);
        var pseudoGoal = getNearestOutsideLimitNode(goal);

        // Generating nodes from polygons
        generateNodes();

        // Add the pseudo goal and start found earlier before generating roads
        nodes.add(pseudoGoal);
        nodes.add(pseudoStart);

        // Generate road between the nodes
        generateRoads();

        SeaDrawer.drawPin(pseudoGoal.getPosition(), Color.YELLOW);
        SeaDrawer.drawPin(pseudoStart.getPosition(), Color.YELLOW);

        // Prepare the path with the starting nodes

        List<PathfindingNode> pathDeb = new ArrayList<>();
        pathDeb.add(startPosition);
        if(startPosition.equals(pseudoStart)) pathDeb = new ArrayList<>();
        var path = PathfindingResult.createFrom(Dijkstra.execute(pseudoStart, pseudoGoal, this.nodes), pathDeb);

        // Checking if a path exist
        if(path == null)
            return null;

        // add the final node
        if(!pseudoGoal.equals(goal))
            path.addNode(goal);

        return path;
    }
}
