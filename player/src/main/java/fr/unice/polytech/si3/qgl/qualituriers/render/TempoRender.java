package fr.unice.polytech.si3.qgl.qualituriers.render;

import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.RoundInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.NewHeadQuarter;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.helpers.IDrawer;
import fr.unice.polytech.si3.qgl.qualituriers.utils.logger.ILogger;
import fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding.*;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TempoRender extends Render {

    public static boolean ON_ENGINE = false;
    public static IDrawer SeaDrawer;
    public NewHeadQuarter headQuarter;
    protected final ILogger logger;


    private PathfindingStore store;

    public TempoRender(GameInfo gameInfo, ILogger logger) {
        super(gameInfo, logger);
        headQuarter = new NewHeadQuarter(gameInfo);
        this.store = new PathfindingStore();
        this.logger = logger;
    }

    int currentCheckpointIndex = 0;
    CheckPoint intermediareCheckpoint = null;
    public List<Action> nextRoundAlternative(RoundInfo round) {

        // Récupération des checkpoints
        var checkpoints = ((RegattaGoal) gameInfo.getGoal()).getCheckPoints();

        // Est-ce que tout les checkpoints sont complété ?
        if (checkpoints.length <= currentCheckpointIndex) return new ArrayList<>();

        // Mise à jour du gameInfo
        gameInfo.getShip().setPosition(round.getShip().getPosition());
        gameInfo.getShip().setEntities(round.getShip().getEntities());
        gameInfo.setSeaEntities(round.getVisibleEntities());
        gameInfo.getShip().setSailors(Arrays.asList(gameInfo.getSailors()));

        // Check other boats distances
        MainPathfinding pathfinding = new MainPathfinding();

        // Vérification si le checkpoint actuel est validé
        var currentCheckpoint = checkpoints[currentCheckpointIndex];
        if (Collisions.isColliding(currentCheckpoint.getPositionableShape(), gameInfo.getShip().getPositionableShape())) {
            //Mise à jour du nouveau checkpoint
            currentCheckpointIndex++;
            if (currentCheckpointIndex >= checkpoints.length) return new ArrayList<>();
            currentCheckpoint = checkpoints[currentCheckpointIndex];
        }

        if (round.getVisibleEntities() != null) {
            // Mapping of obstacles to PositionnalShape

            var pos = pathfinding.getNextCheckpoint(new PathfindingContext(
                    gameInfo.getShip().getPosition(),
                    Arrays.asList(round.getVisibleEntities()),
                    currentCheckpoint.getPosition(),
                    this.store
            ));
            intermediareCheckpoint = new CheckPoint(new Transform(pos, 0), new Circle(100));
        } else {
            intermediareCheckpoint = currentCheckpoint;
        }

        // Calcul des action a effectuer pour atteindre l'étape
        assert intermediareCheckpoint != null;

        List<Action> actions = headQuarter.playTurn(intermediareCheckpoint);
        logger.log(generateResultInfos(intermediareCheckpoint, actions));


        return actions;
    }



    @Override
    public List<Action> nextRound(RoundInfo round)  {
        return nextRoundAlternative(round);

    }



    String generateResultInfos(CheckPoint checkPoint, List<Action> actions) {
        return "TURN : " + gameInfo.getNumberOfTurn() + "\nREMAINING DISTANCE : " + generateDistanceRestante(checkPoint) + "\nACTIONS : " + actions;
    }

    double generateDistanceX(CheckPoint checkPoint) {
        return checkPoint.getPosition().getX() - gameInfo.getShip().getPosition().getX();
    }

    double generateDistanceY(CheckPoint checkPoint) {
        return checkPoint.getPosition().getY() - gameInfo.getShip().getPosition().getY();
    }

    double generateDistanceRestante(CheckPoint checkPoint) {
        return Math.sqrt(generateDistanceX(checkPoint) * generateDistanceX(checkPoint) + generateDistanceY(checkPoint) * generateDistanceY(checkPoint));
    }
}
