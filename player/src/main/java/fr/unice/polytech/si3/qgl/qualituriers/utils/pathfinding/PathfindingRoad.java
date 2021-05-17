package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;


import fr.unice.polytech.si3.qgl.qualituriers.render.TempoRender;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.helpers.IShapeDraw;
import fr.unice.polytech.si3.qgl.qualituriers.utils.logger.SeaDrawer;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Segment;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class PathfindingRoad {
    private static final List<PathfindingRoad> roads = new ArrayList<>();


    private final PathfindingNode from, to;
    PathfindingRoad(PathfindingNode from, PathfindingNode to) {
        roads.add(this);
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof PathfindingRoad)) return false;

        var road = (PathfindingRoad)obj;
        return (from.equals(road.from) && to.equals(road.to)) || (to.equals(road.from) && from.equals(road.to));
    }

    @Override
    public String toString() {
        return "{ " + from.getPosition().toString() + " <-> " + to.getPosition().toString() + " }";
    }

    boolean isLinckedWith(PathfindingNode node) {
        return from == node || to == node;
    }

    PathfindingNode getArriving(PathfindingNode from) {
        if(this.from == from) return to;
        else if(to == from) return this.from;
        else throw new RuntimeException("The specified node is not linckeded with this road");
    }

    /**
     * @param from Départ de la route
     * @param to Destination de la route
     * @param width Largeur de la route
     * @param obstacles Obstacles à éviter
     * @return true si, et seulement si, la route ne croise aucun obstacle
     */
    private static boolean canCreatePraticableRoad(PathfindingNode from, PathfindingNode to, double width, Stream<PositionablePolygon> obstacles) {
        return !Collisions.raycastPolygon(new Segment(from.getPosition(), to.getPosition()), width, obstacles);
    }

    /**
     * Créer une route si il est possible d'en créer une
     * @param from Départ
     * @param to Destination
     * @param width Largeur
     * @param obstacles Obstacles à éviter
     */
    public static void createIfPraticable(PathfindingNode from, PathfindingNode to, double width, Stream<PositionablePolygon> obstacles) {
        if(canCreatePraticableRoad(from, to, width, obstacles)) {
            from.createRoadTo(to);
            if(TempoRender.SeaDrawer == null) return;
            //roadDrawings.add(TempoRender.SeaDrawer.drawFuturLine(from.getPosition(), to.getPosition(), Color.GREEN));
        }
    }
}
