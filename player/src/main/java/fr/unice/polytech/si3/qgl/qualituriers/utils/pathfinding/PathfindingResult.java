package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.render.TempoRender;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.helpers.IShapeDraw;
import fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding.Dijkstra.PathSteps;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Segment;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PathfindingResult {
    private static List<IShapeDraw> drawing = new ArrayList<>();


    private List<PathfindingNode> nodes;
    private boolean resolved = false;

    PathfindingResult() {
        this.nodes = new ArrayList<>();
    }

    /**
     * @param path Chemin issue de Dijkstra
     * @param prefix Les premiers noeuds du chemin
     * @return Le chemin de Dijkstra concaténé avec un préfixe
     */
    public static PathfindingResult createFrom(PathSteps path, List<PathfindingNode> prefix) {
        if(path == null) return null;
        var res = new PathfindingResult();
        res.nodes.addAll(prefix);
        res.nodes.addAll(path.getNodes());
        return res;
    }

    /**
     * @param index
     * @return Le index-ieme noeud du chemin
     */
    PathfindingNode get(int index) {
        return nodes.get(index);
    }

    /**
     * @return Le dernier noeud du chemin
     */
    PathfindingNode getLast() {
        return nodes.get(nodes.size() - 1);
    }

    /**
     * @return Le nombre de noeuds définissant le chemin
     */
    int size() {
        return nodes.size();
    }

    /**
     * Ajoute un noeud à la fin du chemin
     * @param node
     */
    void addNode(PathfindingNode node) {
        this.nodes.add(node);
    }

    /**
     * @return Une copy du chemin
     */
    PathfindingResult copy() {
        var path = new PathfindingResult();
        path.nodes = new ArrayList<>(this.nodes);
        path.resolved = resolved;
        return path;
    }

    /**
     * @return La longueur du chemin en unité géographique
     */
    double length() {
        double result = 0;
        for(int i = 0; i < size() - 1; i++) {
            result += nodes.get(i + 1).getPosition().substract(nodes.get(i).getPosition()).length();
        }
        return result;
    }

    /**
     * @param startWayPoint à partir d'où vérifier le chemin
     * @param obstacles les obstacles à éviter
     * @return true si, et seulement si, le chemin ne croise aucun obstacle
     */
    boolean pathIsCorrect(int startWayPoint, List<PositionablePolygon> obstacles) {
        for (int i = startWayPoint; i < nodes.size() - 1; i++) {
            var nodeA = nodes.get(i);
            var nodeB = nodes.get(i + 1);

            if(Collisions.raycastPolygon(new Segment(nodeA.getPosition(), nodeB.getPosition()), 3 * Config.BOAT_MARGIN, obstacles.stream()))
                return false;
        }
        return true;
    }

    public void draw() {
        if(TempoRender.SeaDrawer == null) return;
        drawing.forEach(IShapeDraw::destroy);
        drawing.clear();

        for(int i = 0; i < nodes.size() - 1; i++) {
            drawing.add(TempoRender.SeaDrawer.drawFuturLine(nodes.get(i).getPosition(), nodes.get(i + 1).getPosition(), Color.RED));
        }
    }
}
