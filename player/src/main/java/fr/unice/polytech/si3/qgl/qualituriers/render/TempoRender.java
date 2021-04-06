package fr.unice.polytech.si3.qgl.qualituriers.render;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.ReefVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.RoundInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.helpers.IDrawer;
import fr.unice.polytech.si3.qgl.qualituriers.utils.logger.ILogger;
import fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding.*;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Segment;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TempoRender extends Render {

    public static boolean ON_ENGINE = false;
    public static IDrawer SeaDrawer;

    private CheckPoint currentCheckPoint;
    private int checkPointCounter = 0;
    private PathfindingStore store;

    public TempoRender(GameInfo gameInfo, ILogger logger) {
        super(gameInfo, logger);
        CheckPoint[] listCheckPoint = ((RegattaGoal) gameInfo.getGoal()).getCheckPoints();
        currentCheckPoint = listCheckPoint[0];
        this.store = new PathfindingStore();
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

        // Vérification si le checkpoint actuel est validé
        var currentCheckpoint = checkpoints[currentCheckpointIndex];
        if (Collisions.isColliding(currentCheckpoint.getPositionableShape(), gameInfo.getShip().getPositionableShape())) {
            //Mise à jour du nouveau checkpoint
            currentCheckpointIndex++;
            if (currentCheckpointIndex >= checkpoints.length) return new ArrayList<>();
            currentCheckpoint = checkpoints[currentCheckpointIndex];
        }
        MainPathfinding pathfinding = new MainPathfinding();

        // Mapping of obstacles to PositionnalShape
        List<PositionableShape<? extends Shape>> obstacles = new ArrayList<>();
        if (gameInfo.getSeaEntities() != null) {
            Arrays.stream(gameInfo.getSeaEntities())
                    .filter(vde -> vde instanceof ReefVisibleDeckEntity)
                    .map(VisibleDeckEntity::getPositionableShape)
                    .forEach(obstacles::add);

            //boolean raycast = Collisions.raycastPolygon(new Segment(checkpoints[1].getPosition().getPoint(), checkpoints[2].getPosition().getPoint()), Config.BOAT_MARGIN * 2, obstacles.stream().map(PositionableShape::getCircumscribedPolygon), true);


            intermediareCheckpoint = pathfinding.getNextCheckpoint(new PathfindingContext(
                    gameInfo.getShip(),
                    obstacles,
                    currentCheckpoint,
                    this.store
            ));
            if(intermediareCheckpoint == null || Collisions.isColliding(intermediareCheckpoint.getPositionableShape(), this.gameInfo.getShip().getPositionableShape())) {

                int i = 0;
            }
        } else {
            intermediareCheckpoint = currentCheckpoint;
        }
        /*var old = intermediareCheckpoint;
        intermediareCheckpoint = currentCheckpoint;
        /*pathfinding.getNextCheckpoint(new PathfindingContext(
                gameInfo.getShip(),
                obstacles,
                currentCheckpoint,
                this.store
        ));*/
        // Calcul des action a effectuer pour atteindre l'étape


        assert intermediareCheckpoint != null;
        List<Action> actions = gameInfo.getShip().moveBoatDistanceStrategy2(intermediareCheckpoint.getPosition(), this.gameInfo);

        double distanceRestanteX = intermediareCheckpoint.getPosition().getX() - gameInfo.getShip().getPosition().getX();
        double distanceRestanteY = intermediareCheckpoint.getPosition().getY() - gameInfo.getShip().getPosition().getY();

        double distanceRestante = Math.sqrt(distanceRestanteX * distanceRestanteX + distanceRestanteY * distanceRestanteY);
        System.out.println("======================================================================================================");
        System.out.println("| " + distanceRestanteX);
        System.out.println("| " + distanceRestanteY);
        System.out.println("| " + "Distance restante : " + distanceRestante);
        System.out.println("| " + "Chechpoint : " + intermediareCheckpoint.getPosition().getPoint());
        System.out.println("| " + actions);
        System.out.println("======================================================================================================");

        System.out.println(Collisions.isColliding(intermediareCheckpoint.getPositionableShape(), this.gameInfo.getShip().getPositionableShape()));

        return actions;
    }



    @Override
    public List<Action> nextRound(RoundInfo round)  {
        return nextRoundAlternative(round);
/*

        int numberOfCheckPoints = ((RegattaGoal)gameInfo.getGoal()).getCheckPoints().length;

        gameInfo.getShip().setPosition(round.getShip().getPosition());
        gameInfo.getShip().setEntities(round.getShip().getEntities());

        gameInfo.getShip().setSailors(Arrays.asList(gameInfo.getSailors()));


        PositionableShape<? extends Shape> checkpointsShape = currentCheckPoint.getPositionableShape();
        PositionableShape<? extends Shape> boatShape = gameInfo.getShip().getPositionableShape();

        double distanceRestanteX = currentCheckPoint.getPosition().getX() - gameInfo.getShip().getPosition().getX();
        double distanceRestanteY = currentCheckPoint.getPosition().getY() - gameInfo.getShip().getPosition().getY();

        double distanceRestante = Math.sqrt(distanceRestanteX * distanceRestanteX + distanceRestanteY * distanceRestanteY);
        System.out.println("======================================================================================================");
        System.out.println("| " + distanceRestanteX);
        System.out.println("| " + distanceRestanteY);
        System.out.println("| " + "Distance restante : " + distanceRestante);

        if (Collisions.isColliding(checkpointsShape, boatShape) && checkPointCounter == numberOfCheckPoints - 1) {
            return new ArrayList<>();
        }

        if (Collisions.isColliding(checkpointsShape, boatShape) && checkPointCounter < numberOfCheckPoints-1) {
            checkPointCounter++;
            currentCheckPoint = ((RegattaGoal)gameInfo.getGoal()).getCheckPoints()[checkPointCounter];
        }

        List<Action> actions = gameInfo.getShip().moveBoatDistanceStrategy2(currentCheckPoint.getPosition(), this.gameInfo);
        System.out.println("| " + actions);
        System.out.println("======================================================================================================");

        System.out.println(Arrays.toString(gameInfo.getSailors()));
        System.out.println(Arrays.toString(gameInfo.getShip().getEntities()));

        return actions;*/

    }



    /*
    @Override
    public List<Action> nextRound(RoundInfo round) throws JsonProcessingException {


        if (gameInfo.getSeaEntities() != null) {
            List<PositionableShape<? extends Shape>> obstacles = new ArrayList<>();
            for (VisibleDeckEntity entity: gameInfo.getSeaEntities()) {
                obstacles.add(entity.getPositionableShape());
            }

            var checkpoints = Arrays.asList((((RegattaGoal)gameInfo.getGoal()).getCheckPoints()));

            MainPathfinding mainPathfinding = new MainPathfinding();
            PathfindingContext pathfindingContext = new PathfindingContext(gameInfo.getShip(), obstacles,checkpoints);
            currentCheckPoint = mainPathfinding.getNextCheckpoint(pathfindingContext);
        }


        int numberOfCheckPoints = ((RegattaGoal)gameInfo.getGoal()).getCheckPoints().length;

        gameInfo.getShip().setPosition(round.getShip().getPosition());
        gameInfo.getShip().setEntities(round.getShip().getEntities());

        gameInfo.getShip().setSailors(Arrays.asList(gameInfo.getSailors()));


        PositionableShape<? extends Shape> checkpointsShape = currentCheckPoint.getPositionableShape();
        PositionableShape<? extends Shape> boatShape = gameInfo.getShip().getPositionableShape();


        double distanceRestanteX = currentCheckPoint.getPosition().getX() - gameInfo.getShip().getPosition().getX();
        double distanceRestanteY = currentCheckPoint.getPosition().getY() - gameInfo.getShip().getPosition().getY();


        double distanceRestante = Math.sqrt(distanceRestanteX * distanceRestanteX + distanceRestanteY * distanceRestanteY);
        System.out.println("======================================================================================================");
        System.out.println(distanceRestanteX);
        System.out.println(distanceRestanteY);
        System.out.println("Distance restante : " + distanceRestante);
        System.out.println("======================================================================================================");


        System.out.println("WIND     : " + gameInfo.getWind());
        System.out.println("ENTITIES : " + Arrays.toString(gameInfo.getSeaEntities()));


        if (Collisions.isColliding(checkpointsShape, boatShape) && checkPointCounter == numberOfCheckPoints - 1) {
            return new ArrayList<>();
        }

        if (Collisions.isColliding(checkpointsShape, boatShape) && checkPointCounter < numberOfCheckPoints-1) {
            checkPointCounter++;
            currentCheckPoint = ((RegattaGoal)gameInfo.getGoal()).getCheckPoints()[checkPointCounter];
        }



        List<Action> actions = gameInfo.getShip().moveBoatDistanceStrategy2(currentCheckPoint.getPosition(), this.gameInfo,this.logger);
        System.out.println(actions);
        return actions;
    }
    */
}
