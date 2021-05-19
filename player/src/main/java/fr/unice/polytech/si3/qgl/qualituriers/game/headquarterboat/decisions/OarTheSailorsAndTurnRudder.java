package fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.decisions;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.SailBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.StreamVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntities;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.utils.AngleUtil;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Oar;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Turn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author D'Andréa William
 */
public class OarTheSailorsAndTurnRudder {

    private GameInfo gameInfo;
    private CheckPoint goal;

    // private double angleWithOar;

    public OarTheSailorsAndTurnRudder(GameInfo gameInfo, CheckPoint goal) {
        this.gameInfo = gameInfo;
        this.goal = goal;
        // angleWithOar = 0;
    }

    public List<Action> launch() {

        List<Action> actions = new ArrayList<>();

        int differenceOfSailors = getDifferenceOfOarsForGoingToDestination();
        int numberOfActiveOars = numberOfOarWeNeedToActivateForGoingToCheckpoint();


        if (differenceOfSailors == 0) {
            actions.addAll(generateOarAction(0, 0, numberOfActiveOars));
        }


        // On doit avoir differenceOfSailors marins actifs de plus a babord qu'a tribord
        if (differenceOfSailors < 0) {
            int difference = Math.abs(differenceOfSailors);
            actions.addAll(generateOarAction(difference,0, numberOfActiveOars));
        }

        // On doit avoir differenceOfSailors marins actifs de plus a Tribord qu'a babord
        if (differenceOfSailors > 0) {
            int difference = Math.abs(differenceOfSailors);
            actions.addAll(generateOarAction(0,difference, numberOfActiveOars));
        }


        // On donne maintenant l'angle de rotation restant au Rudder si il est sur le rudder
        Optional<Marin> rudderOp = gameInfo.getSailorOnRudder();
        if (rudderOp.isPresent()) {

            double phiThatTheBoatCanTurn = Math.PI * differenceOfSailors / gameInfo.getListOfOars().size();

            double differenceOfAngleThatTheRudderWillTurn = gameInfo.getShip().getPosition().getAngleToSee(goal.getPosition()) - phiThatTheBoatCanTurn;


            if (differenceOfAngleThatTheRudderWillTurn >= Config.MAX_ANGLE_FOR_RUDDER) {differenceOfAngleThatTheRudderWillTurn = Config.MAX_ANGLE_FOR_RUDDER;}
            if (differenceOfAngleThatTheRudderWillTurn <= -Config.MAX_ANGLE_FOR_RUDDER) {differenceOfAngleThatTheRudderWillTurn = -Config.MAX_ANGLE_FOR_RUDDER;}


            actions.add(new Turn(rudderOp.get().getId(), differenceOfAngleThatTheRudderWillTurn));
        }

        return actions;
    }


    /**
     * Méthode générant des actions Oar sur un certains nombres de sailors inclus dans la liste listOfSailors
     * @param numberOfSailorsToOar le nombre de sailros a faire ramer
     * @param listOfSailors la liste des mains que l'on souhaite faire ramer
     * @return la liste d'actions oar
     */
    public List<Action> oarListOfSailors(int numberOfSailorsToOar, List<Marin> listOfSailors) {

        List<Action> actions = new ArrayList<>();
        for (int i = 0; i < Math.min(numberOfSailorsToOar, listOfSailors.size()); i++) {
            actions.add(new Oar(listOfSailors.get(i).getId()));
        }
        return actions;
    }


    /**
     * Méthode ayant pour objectif de compter le nombre de marins a faire ramer pour atteindre l'objectif
     * @return le nombre de marins a faire ramer
     */
    public int numberOfOarWeNeedToActivateForGoingToCheckpoint() {

        double distanceBetweenBoatAndCheckPoint = gameInfo.getShip().getPosition().distance(goal.getPosition());
        int goodNumberOfActiveOars = gameInfo.getListOfOars().size();

        for (int i = 0; i <= gameInfo.getListOfOars().size(); i++) {

            if (generateBoatSpeed(i) <= distanceBetweenBoatAndCheckPoint) {
                goodNumberOfActiveOars = i;
            }
        }

        return goodNumberOfActiveOars;
    }


    /**
     * Cette méthode permet de connaitre la vitesse du bateau sur sa position actuelle
     * @param numberOfActiveOars nombre de rames actives
     * @return la vitesse du bateau a l'instant t
     */
    public double generateBoatSpeed(int numberOfActiveOars) {

        // Vitesse avec les rames
        double speedBoatOar = 165.0 *  ((double) Math.min(numberOfActiveOars, gameInfo.getListOfOars().size()) / (double) gameInfo.getListOfOars().size());

        // Vitesse avec vent
        double speedBoatWind = 0;
        Optional<BoatEntity> sailOp = gameInfo.getShip().getSail();
        if (gameInfo.getWind() != null && sailOp.isPresent()) {
            SailBoatEntity sail = (SailBoatEntity) sailOp.get();
            double numberOfSails = gameInfo.getListOfSail().size();
            double numberOfOpenSails = (double) gameInfo.getListOfSail().stream().filter(sailIn -> ((SailBoatEntity) sailIn).isOpened()).count();

            if (gameInfo.getWind().getStrength() != 0.0 && sail.isOpened())

                speedBoatWind = (numberOfSails / numberOfOpenSails) * gameInfo.getWind().getStrength() * Math.cos(AngleUtil.differenceBetweenTwoAngle(gameInfo.getShip().getPosition().getOrientation(), gameInfo.getWind().getOrientation()));
        }

        // Si on est dans un courant
        double speedBoatStream = 0;
        for (VisibleDeckEntity seaEntity : gameInfo.getSeaEntities()) {

            // Si l'entity est un stream et que le bateau est dans le stream
            if (seaEntity.getType() == VisibleDeckEntities.STREAM) {
                if (Collisions.isColliding(seaEntity.getPositionableShape(), gameInfo.getShip().getPositionableShape())) {

                    double angleBetweenStreamAndBoat = AngleUtil.differenceBetweenTwoAngle(gameInfo.getShip().getPosition().getOrientation(), seaEntity.getPosition().getOrientation());

                    // On est dans le même sens que le courant, ca nous pousse
                    if (angleBetweenStreamAndBoat < Math.PI / 2) {
                        speedBoatStream = ((StreamVisibleDeckEntity) seaEntity).getStrength();
                    }

                    // On est dans le sens inverse du courant, ca nous ralenti
                    if (angleBetweenStreamAndBoat > Math.PI / 2) {
                        speedBoatStream = -((StreamVisibleDeckEntity) seaEntity).getStrength();
                    }
                }
            }

        }


        return speedBoatOar + speedBoatWind + speedBoatStream;
    }



    /**
     * Cette méthode retourne la différence du nombre de marin qui dovent ramer que nous devons avoir entre abbord et tribord
     *      1) > 0 : On doit avoir differenceOfSailors marins actifs de plus a Tribord qu'a babord
     *      2) < 0 : On doit avoir differenceOfSailors marins actifs de plus a babord qu'a tribord
     *      3) = 0 : On doit avoir le même nombre de marins actifs de chaque côté
     */
    public int getDifferenceOfOarsForGoingToDestination() {

        double angleBetweenTheBoatAndHisDirection = gameInfo.getShip().getPosition().getAngleToSee(goal.getPosition());

        if (angleBetweenTheBoatAndHisDirection >= Config.MAX_ANGLE_TURN_FOR_SAILORS) {
            angleBetweenTheBoatAndHisDirection = Config.MAX_ANGLE_TURN_FOR_SAILORS;
        }

        if (angleBetweenTheBoatAndHisDirection <= -Config.MAX_ANGLE_TURN_FOR_SAILORS) {
            angleBetweenTheBoatAndHisDirection = -Config.MAX_ANGLE_TURN_FOR_SAILORS;
        }

        // differenceOfSailors = rameActiveTribord - rameActiveBabord
        double differenceOfSailorsDouble = angleBetweenTheBoatAndHisDirection * gameInfo.getListOfOars().size() / Math.PI;
        return (int) differenceOfSailorsDouble;
    }






    /**
     * Cette méthode va générer la liste d'action pour un certain nombre de marin a droite et a gauche, cette méthode va
     * essayer de faire ramer le plus de marin possible en ayant l'ecart entre babord et tribord
     * @param babord nombre de marin min a babord
     * @param tribord nombre de marin min a tribord
     * @return liste d'action a faire pour avancer
     */
    List<Action> generateOarAction(int babord, int tribord, int optimumNumberOfOars) {

        int numberOfOarsOnTheBoat = gameInfo.getListOfOars().size();

        List<Marin> listOfBabordSailors = gameInfo.getListOfSailorsOnBabordOars();
        List<Marin> listOfTribordSailors = gameInfo.getListOfSailorsOnTribordOars();


        int increment = 1;

        while (increment <= numberOfOarsOnTheBoat) {

            if (!(babord + tribord < numberOfOarsOnTheBoat
                    && babord + tribord < optimumNumberOfOars-1
                    && babord < listOfBabordSailors.size()
                    && tribord < listOfTribordSailors.size()
            )) {
                break;
            } else {
                babord += 1;
                tribord += 1;
            }

            increment++;
        }

        // angleWithOar = Math.PI * (tribord - babord) / gameInfo.getListOfOars().size();

        List<Action> finalActions = new ArrayList<>();
        finalActions.addAll(oarListOfSailors(tribord, listOfTribordSailors));
        finalActions.addAll(oarListOfSailors(babord, listOfBabordSailors));

        return finalActions;
    }


    public void setGameInfo(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    public void setGoal(CheckPoint goal) {
        this.goal = goal;
    }
}
