package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.HeadquarterUtil;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;

import java.util.List;


/**
 * Les consigne du jeu nous donnent une formule poru la rotaton du bateau : PIx<diff rame tribord - rame bâbord>/<nombre total de rames>
 * En gros, le bateau ne peux tourner que d'un certain nombre d'angle (donné par cette formule).
 * Cette classe a pour objectif d'aller trouver la différence <diff rame tribord - rame bâbord> de rames sur le bateau
 * afin que le bateau puisse tourner du bon angle.
 * Ici, on ne se préocupe pas du nombre de rame qu'il faut pour ramer, on veut juste connaitre la différence.
 *
 * Ensuite, cette classe génére aussi l'angle que devrai avoir le gouvernail pour pouvoir affiner l'angle de rotation du bateau
 *
 * EXEMPLE : On veut un angle de PI/4 pour atteindre le CheckPoint, nous avons 6 rames sur le bateau.
 * => Le bateau, du a sa disposition de rame, ne peux tourner que de PI / 6, en ayant 1 rame active de plus a tribord qu'a babord
 * ====> La méthode differenceOfOarsForGoingSomewhere() va donc retourner 1 (la différence de rames actives nécessaire pour tourner)
 * ====> La méthode differenceOfAngleForTheRudder() va donc retourner PI / 4 - PI/6 = PI/12
 *
 * @author williamdandrea
 */
public class DifferenceOfOarsForGoingSomewhere {

    private Boat boat;
    private Transform finalPoint;


    public DifferenceOfOarsForGoingSomewhere(Boat boat, Transform finalPoint) {
        this.boat = boat;
        this.finalPoint = finalPoint;
    }

    /**
     * On appelle cette méthode pour récupérer la différence de marin qu'il nous faut entre le tribord et le babord
     * Cette valeur retourne la différence de rames qu'il faut sur le bateu pour tourner jusqu'au checkpoint
     * @return nombreDeRameTrbord - nombreDeRameBabord
     */
    public int differenceOfOarsForGoingSomewhere() {

        return calculTheDifferenceOfOars();
    }

    /**
     * Cette méthode génére l'angle que devra voir le gouvernail pour pouvoir tourner exactement a l'angle voulu
     * @return l'angle que devra avoir le gouvernail pour pouvoir tourner
     */
    public double differenceOfAngleForTheRudder() {


        double phiThatTheBoatCanTurn = Math.PI * calculTheDifferenceOfOars() / HeadquarterUtil.getListOfOars(boat).size();

        double differenceOfAngleThatTheRudderWillTurn = boat.getPosition().getAngleToSee(finalPoint) - phiThatTheBoatCanTurn;

        if (differenceOfAngleThatTheRudderWillTurn >= Config.MAX_ANGLE_FOR_RUDDER) {differenceOfAngleThatTheRudderWillTurn = Config.MAX_ANGLE_FOR_RUDDER;}
        if (differenceOfAngleThatTheRudderWillTurn <= -Config.MAX_ANGLE_FOR_RUDDER) {differenceOfAngleThatTheRudderWillTurn = -Config.MAX_ANGLE_FOR_RUDDER;}

        return differenceOfAngleThatTheRudderWillTurn;
    }


    private int calculTheDifferenceOfOars() {

        double angleBetweenTheBoatAndHisDirection = boat.getPosition().getAngleToSee(finalPoint);

        if (angleBetweenTheBoatAndHisDirection >= Config.MAX_ANGLE_TURN_FOR_SAILORS) {angleBetweenTheBoatAndHisDirection =  Config.MAX_ANGLE_TURN_FOR_SAILORS;}
        if (angleBetweenTheBoatAndHisDirection <= -Config.MAX_ANGLE_TURN_FOR_SAILORS) {angleBetweenTheBoatAndHisDirection =  -Config.MAX_ANGLE_TURN_FOR_SAILORS;}

        // differenceOfSailors = rameActiveTribord - rameActiveBabord
        double differenceOfSailorsDouble = angleBetweenTheBoatAndHisDirection * HeadquarterUtil.getListOfOars(boat).size() / Math.PI;
        int differenceOfSailors = (int) differenceOfSailorsDouble;
        return differenceOfSailors;
    }


}
