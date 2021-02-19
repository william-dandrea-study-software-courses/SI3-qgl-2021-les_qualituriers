package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboatsecond;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.BoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.OarBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboatsecond.utils.DistanceDisposition;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SortedDispositionDistanceStrategy {


    public List<Disposition> getIdealDisposition(Transform checkPoint, Boat boat) {

        List<Disposition> finalList = new ArrayList<>();

        var listOfRepartitions = getIdealRepartition(checkPoint, boat);
        System.out.println(listOfRepartitions);
        for (DistanceDisposition dispo: listOfRepartitions) {
            finalList.add(new Disposition(dispo.getBabordOar(), dispo.getTribordOar()));
        }
        return finalList;
    }


    /**
     * Cette méthode génére la liste des dispositions de rames possibles, classés dans l'ordre. En premier position sera
     * la disposition qui permet de parcourir le plus de distance vers le checkpoint
     * @param checkPoint position ou l'on doit arriver
     * @param boat bateau actuel
     * @return une liste triée de dispositions
     */
    public List<DistanceDisposition> getIdealRepartition(Transform checkPoint, Boat boat) {


        List<BoatEntity> listOar = Arrays.stream(boat.getEntities()).filter(boatEntity -> boatEntity.getType() == BoatEntities.OAR).collect(Collectors.toList());
        int numberOfOars = listOar.size();

        List<DistanceDisposition> idealsRepartition = generateListOfDispositions(numberOfOars, boat.getPosition());

        return sortTheDisposition(idealsRepartition, checkPoint);

    }




    /**
     * Cette méthode génére les dispotions de rames possible avec le calcul de la position final du bateau
     * après que cette disposition de rames ai ramé
     * @param numberOfOars nombre de rames sur le bateau
     * @param positionBoat la position actuelle du bateau
     * @return une liste de disposition de rames non triées avec les distances finales du bateau (après avoir actionné
     *         les rames)
     */
    List<DistanceDisposition> generateListOfDispositions(int numberOfOars, Transform positionBoat) {

        List<DistanceDisposition> finalList = new ArrayList<>();

        for (int babord = 0; babord <= numberOfOars / 2; babord++) {
            for (int tribord = 0; tribord <= numberOfOars / 2; tribord++) {

                if (!(babord == 0 && tribord == 0)) {
                    double rotationAngle = Math.PI * (tribord - babord) / numberOfOars;
                    double finalAngle = positionBoat.getOrientation() + rotationAngle;

                    double speed = 165 * ((double) (babord + tribord)) / numberOfOars;

                    double distanceWeParcoursX = speed * Math.cos(finalAngle);
                    double distanceWeParcoursY = speed * Math.sin(finalAngle);

                    double finalPositionX = distanceWeParcoursX + positionBoat.getX();
                    double finalPositionY = distanceWeParcoursY + positionBoat.getY();


                    finalList.add(new DistanceDisposition(babord, tribord, new Point(finalPositionX, finalPositionY)));
                }
            }
        }

        return finalList;

    }


    /**
     * Utilisation d'un algorithme de tri par selection : objectif est de trouver la disposition de rames qui nous permettra de
     * parcourir la plus grande distance
     * @param initialList liste de dispositions initiales (cette liste doit être la liste des dispositions avec
     *                    des positions qui sont les positions du bateau après le ramage des rames.
     * @param checkPoint endroit ou l'on souhaite arriver
     * @return
     */
    List<DistanceDisposition> sortTheDisposition(List<DistanceDisposition> initialList, Transform checkPoint) {

        DistanceDisposition[] list = new DistanceDisposition[initialList.size()];
        initialList.toArray(list);

        for (int i = 0; i < list.length - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < list.length; j++)
            {
                boolean condX = checkPoint.getX() - list[j].getFinalPositionOnTheMap().getX() < checkPoint.getX() - list[index].getFinalPositionOnTheMap().getX();
                boolean condY = checkPoint.getY() - list[j].getFinalPositionOnTheMap().getY() < checkPoint.getY() - list[index].getFinalPositionOnTheMap().getY();
                if (condX && condY){
                    index = j;
                }
            }

            DistanceDisposition min = list[index];
            list[index] = list[i];
            list[i] = min;
        }

        return Arrays.asList(list.clone());
    }

}