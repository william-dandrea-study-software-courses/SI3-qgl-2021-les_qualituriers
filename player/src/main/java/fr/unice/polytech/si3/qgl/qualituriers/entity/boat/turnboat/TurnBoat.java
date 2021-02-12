package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboat;


import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.BoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboat.turnboatutils.BabordTribordAngle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Cette classe a pour objectif générale de faire tourner le bateau d'un certain angle.
 */
public class TurnBoat {

    private double ECART_DOUBLE = 0.001;

    // Initial properties
    private final double finalOrientationBoat;
    private final List<Marin> sailors;
    private final List<BoatEntity> boatEntities;
    private final Transform positionBoat;

    // Intern properties
    private double differenceOfAngle;
    private List<BoatEntity> listOfOars;
    private int numberOfOars;
    private List<BabordTribordAngle> possibleAngles;

    // Final properties
    private List<Action> actionsToDo;

    /**
     * @param finalOrientationBoat
     * @param sailors
     * @param boatEntities
     * @param positionBoat
     */
    public TurnBoat(double finalOrientationBoat, List<Marin> sailors, List<BoatEntity> boatEntities, Transform positionBoat) {
        this.finalOrientationBoat = finalOrientationBoat;
        this.sailors = sailors;
        this.boatEntities = boatEntities;
        this.positionBoat = positionBoat;


        initializeAll();

    }


    public List<Action> turnBoat() {

        differenceOfAngle = generateDifferenceOfAngle(false);

        if (differenceOfAngle == 0.0) {
            return new ArrayList<Action>();
        }

        possibleAngles = generateListOfPossibleAngles(0);


        return actionsToDo;
    }







    /**
     * Les consignes du jeu disent que le bateau, pour une certaine quantité de rames, ne peut faire qu'un certains
     * nombre d'angles. Ce nombre d'angle est défini par la formule :
     *                  PI * <diff rame tribord - rame bâbord>/<nombre total de rames>
     * Cette méthode calcul donc toutes les combinaisons d'angles possibles avec le nombre de rame qu'il est nécessaire
     * pour tourner
     * @param startIndex nombre minimum de rames de chaque coté
     * @return la liste de tout les angles possibles avec le nombre de rame qu'il faut de chaque coté pour tourner
     */
    List<BabordTribordAngle> generateListOfPossibleAngles(int startIndex) {

        List<BabordTribordAngle> angles = new ArrayList<>();

        if (!boatEntities.isEmpty()) {
            if (numberOfOars % 2 == 0) {

                int numberOfAnglesPossibles = numberOfOars / 2;
                for (int bab = startIndex; bab <= numberOfAnglesPossibles; bab++) {
                    for (int tri = startIndex; tri <= numberOfAnglesPossibles ; tri++) {

                        // On sors le cas ou bab = 0 et tri = 0 car si personne ne rame ni a droite, ni a gauche, on avance pas
                        if (bab + tri <= numberOfOars && bab + tri != 0) {
                            //PI * <diff rame tribord - rame bâbord>/<nombre total de rames>
                            int diff = tri - bab;
                            double angle = (diff == 0) ? 0 : Math.PI * diff / numberOfOars ;
                            angles.add(new BabordTribordAngle(bab,tri,angle));

                        }

                    }
                }
            } else
                throw new IllegalArgumentException("ERREUR moteur de jeu : Impossible d'avoir un nombre impair de rames");
        }


        return angles;
    }

    /**
     * Cette méthode actualise la variable differenceOfAngle qui a pour but de connaitre de quel angle le bateau doit
     * tourner
     * @param privilegeNegativeAngle true si on privilégie un angle négatif pour un demi-tour, false si on préfére avoir
     *                               un angle positif lors d'un demi-tour (angle = PI)
     */
    double generateDifferenceOfAngle(boolean privilegeNegativeAngle) {

        double localDifferenceOfAngle = minimizationAngleAlgorithm(finalOrientationBoat - positionBoat.getOrientation());

        if (privilegeNegativeAngle && (localDifferenceOfAngle == Math.PI || localDifferenceOfAngle == -Math.PI)) {
            localDifferenceOfAngle = -Math.PI;
        }

        return localDifferenceOfAngle;
    }





    /**
     * Cet algorithme est un algorithme de minimisation d'un angle qui va calculer l'équivalent d'un angle en une plus
     * petite valeur
     * @param angle que l'on souhaite potentiellement reduire
     * @return angle réduit
     */
    private double minimizationAngleAlgorithm(double angle) {
        if (angle > 0.0) {
            while (angle > Math.PI) {
                angle = angle - 2 * Math.PI;
            }
        } else {
            while (angle < -Math.PI) {
                angle = angle + 2 * Math.PI;
            }
        }
        return angle;
    }



    private void initializeAll() {

        if (!boatEntities.contains(null)) {
            listOfOars = boatEntities.stream().filter(boatEntity -> boatEntity.getType().equals(BoatEntities.OAR)).collect(Collectors.toList());
            numberOfOars = listOfOars.size();
        }

        actionsToDo = new ArrayList<>();
        possibleAngles = new ArrayList<>();
    }





























































    public double getFinalOrientationBoat() { return finalOrientationBoat; }
    public List<Marin> getSailors() { return sailors; }
    public List<BoatEntity> getEntities() { return boatEntities; }
    public Transform getPositionBoat() { return positionBoat; }
    public double getDifferenceOfAngle() { return differenceOfAngle; }
    public List<Action> getActionsToDo() { return actionsToDo; }
}
