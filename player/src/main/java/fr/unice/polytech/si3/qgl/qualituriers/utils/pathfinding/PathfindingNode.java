package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Segment;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PathfindingNode {
    private Point position;
    private final List<PathfindingRoad> roads = new ArrayList<>();

    /**
     * @param position Géolocalisation du point
     */
    PathfindingNode(Point position) {
        this.position = position;
    }


    /**
     * @param polygon Le polygone source
     * @return La liste des vertices du polygon converties en PathfindingNode
     */
    public static List<PathfindingNode> createFrom(PositionablePolygon polygon) {
        var result = new ArrayList<PathfindingNode>();
        for(var v : polygon.getShape().getVertices(polygon.getTransform()))
            result.add(new PathfindingNode(v));
        return result;
    }

    /**
     * @return Un stream des voisins
     */
    public Stream<PathfindingNode> neighboursStream() {
        return roads.stream().map(r -> r.getArriving(this));
    }

    public Point getPosition() {
        return position;
    }

    void setPosition(Point pt) {
        this.position = pt;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof PathfindingNode)) return false;
        //if (!super.equals(obj)) return false;

        PathfindingNode node = (PathfindingNode) obj;

        return this.position.equals(node.position);
    }

    /**
     * @return Creer un positionableCircle centré sur ce noeud
     */
    PositionableCircle toPositionableCircle() {
        return new PositionableCircle(new Circle(Config.BOAT_MARGIN), new Transform(getPosition(), 0));
    }

    /**
     * @param node noeud cible
     * @return s'il existe une route menant au noeud
     */
    private boolean hasRoadLeadingTo(PathfindingNode node) {
        return roads.stream().anyMatch(r -> r.isLinckedWith(node));
    }

    /**
     * Créer une route entre 2 noeuds
     * Warning: S'il il y a un obstacle sur le passage, il est ignoré
     * @param node Noeud auquel lier la route
     */
    void createRoadTo(PathfindingNode node) {
        if(!hasRoadLeadingTo(node)) {
            var road = new PathfindingRoad(node, this);
            roads.add(road);
            node.roads.add(road);
        }
    }
}
