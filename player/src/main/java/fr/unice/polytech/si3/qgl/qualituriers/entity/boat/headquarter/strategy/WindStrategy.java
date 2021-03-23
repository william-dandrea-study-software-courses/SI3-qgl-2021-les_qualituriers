package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.SailBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.BoatPathFinding;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.HeadquarterUtil;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;

import java.util.List;
import java.util.Optional;

public class WindStrategy {

    private Boat boat;
    private Transform goal;
    private List<Marin> sailors;
    private GameInfo gameInfo;

    public WindStrategy(Boat boat, Transform goal, List<Marin> sailors, GameInfo gameInfo) {
        this.boat = boat;
        this.goal = goal;
        this.sailors = sailors;
        this.gameInfo = gameInfo;
    }



    /**
     * Cette méthode va déplacer le marin sur la voile, ou bien, va deplacer le marin le plus proche de la voile
     * @param marin le marin que l'on souhaite deplacer sur la voile
     * @return l'action si il peut se deplacer, sinon il renvoie Optional.empty()
     */
    public Optional<Action> initOneSailorOnTheSail(Marin marin) {

        Optional<BoatEntity> potentialSail = HeadquarterUtil.getSail(boat);

        if (potentialSail.isPresent()) {

            SailBoatEntity sail = (SailBoatEntity) potentialSail.get();

            BoatPathFinding boatPathFinding = new BoatPathFinding(sailors, boat, marin.getId(), sail.getPosition());
            Point objective = boatPathFinding.generateClosestPoint();

            return HeadquarterUtil.generateMovingAction(marin.getId(), marin.getX(), marin.getY(), (int) objective.getX(), (int) objective.getY());

        }

        return Optional.empty();
    }
}
