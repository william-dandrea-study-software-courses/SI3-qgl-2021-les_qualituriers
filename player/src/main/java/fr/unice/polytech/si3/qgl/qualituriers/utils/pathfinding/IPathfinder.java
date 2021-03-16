package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;

public interface IPathfinder {

    /**
     * @return La priorité du chercheur. Influe sur l'ordre d'exécution des différents chercheurs.
     */
    int getPriorityRank();

    /**
     * @param context: Le contexte dans lequel rechercher le chemin.
     * @return Le prochain checkpoint de sorte a limité la distance parcourue
     */
    CheckPoint getNextCheckpoint(PathfindingContext context);
}
