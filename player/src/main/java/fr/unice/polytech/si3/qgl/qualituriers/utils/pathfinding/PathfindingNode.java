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
    private final PositionablePolygon owner;
    private final List<PathfindingRoad> roads = new ArrayList<>();

    PathfindingNode(Point position, PositionablePolygon owner) {
        this.position = position;
        this.owner = owner;
    }

    public static List<PathfindingNode> createFrom(PositionablePolygon polygon) {
        var result = new ArrayList<PathfindingNode>();
        for(var v : polygon.getShape().getVertices(polygon.getTransform()))
            result.add(new PathfindingNode(v, polygon));
        return result;
    }

    public List<PathfindingNode> neighbours() {
        var result = new ArrayList<PathfindingNode>();
        neighboursStream().forEach(result::add);
        return result;
    }

    public Stream<PathfindingNode> neighboursStream() {
        return roads.stream().map(r -> r.getArriving(this));
    }

    void removeRoad(PathfindingRoad road) {
        this.roads.remove(road);
    }

    public Point getPosition() {
        return position;
    }

    void setPosition(Point pt) {
        this.position = pt;
    }

    List<PathfindingRoad> getRoads() {
        return this.roads;
    }

    PositionablePolygon getOwner() {
        return owner;
    }

    double calculateHeuristic(PathfindingNode goal) {
        return goal.getPosition().substract(position).length();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;

        PathfindingNode node = (PathfindingNode) obj;

        var pos = this.position.equals(node.position);
        var pol = this.owner.equals(node.owner);
        return pos && pol;
    }

    PositionableCircle toPositionableCircle() {
        return new PositionableCircle(new Circle(Config.BOAT_MARGIN), new Transform(getPosition(), 0));
    }

    private boolean hasRoadLeadingTo(PathfindingNode node) {
        return roads.stream().anyMatch(r -> r.isLinckedWith(node));
    }

    void createRoadTo(PathfindingNode node) {
        if(!hasRoadLeadingTo(node)) {
            var road = new PathfindingRoad(node, this);
            roads.add(road);
            node.roads.add(road);
        }
    }
}
