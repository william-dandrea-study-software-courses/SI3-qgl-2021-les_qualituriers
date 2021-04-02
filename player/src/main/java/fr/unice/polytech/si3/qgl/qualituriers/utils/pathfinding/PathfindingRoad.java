package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;


import fr.unice.polytech.si3.qgl.qualituriers.render.TempoRender;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Segment;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PathfindingRoad {
    private static final List<PathfindingRoad> roads = new ArrayList<>();


    private final PathfindingNode from, to;
    PathfindingRoad(PathfindingNode from, PathfindingNode to) {
        roads.add(this);
        this.from = from;
        this.to = to;
    }

    boolean isLinckedWith(PathfindingNode node) {
        return from == node || to == node;
    }

    PathfindingNode getArriving(PathfindingNode from) {
        if(this.from == from) return to;
        else if(to == from) return this.from;
        else throw new RuntimeException("The specified node is not linckeded with this road");
    }

    double length() {
        return from.getPosition().substract(to.getPosition()).length();
    }

    void destroy() {
        roads.remove(this);
        from.removeRoad(this);
        to.removeRoad(this);
    }

    boolean isPraticable(double width, Stream<PositionablePolygon> obstacles) {
        return canCreatePraticableRoad(from, to, width, obstacles);
    }

    private static boolean canCreatePraticableRoad(PathfindingNode from, PathfindingNode to, double width, Stream<PositionablePolygon> obstacles) {
        return !Collisions.raycastPolygon(new Segment(from.getPosition(), to.getPosition()), width, obstacles);
    }

    public static void createIfPraticable(PathfindingNode from, PathfindingNode to, double width, Stream<PositionablePolygon> obstacles) {
        if(canCreatePraticableRoad(from, to, width, obstacles))
            from.createRoadTo(to);
    }

    public static void draw() {
        if(TempoRender.SeaDrawer != null) {
            for(var r : roads) {
                TempoRender.SeaDrawer.drawLine(r.from.getPosition(), r.to.getPosition(), Color.MAGENTA);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
