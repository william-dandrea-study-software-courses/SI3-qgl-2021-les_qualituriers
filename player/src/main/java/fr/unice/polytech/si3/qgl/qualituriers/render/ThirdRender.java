package fr.unice.polytech.si3.qgl.qualituriers.render;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboat.TurnBoat;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.RoundInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.PositionableShape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThirdRender extends Render{



    private CheckPoint[] listCheckPoint;
    private CheckPoint currentCheckPoint;
    private ObjectMapper om;

    public ThirdRender(GameInfo gameInfo) {
        super(gameInfo);

        om = new ObjectMapper();
        listCheckPoint = ((RegattaGoal) gameInfo.getGoal()).getCheckPoints();
        currentCheckPoint = listCheckPoint[0];
    }

    @Override
    public String nextRound(RoundInfo round) throws JsonProcessingException {

        gameInfo.getShip().setTransform(round.getShip().getPosition());
        gameInfo.getShip().setEntities(round.getShip().getEntities());

        List<Action> finalsActions = new ArrayList<>();

        PositionableShape<Shape> checkpointsShape = new PositionableShape<>(currentCheckPoint.getShape(), currentCheckPoint.getPosition());
        PositionableShape<Shape> boatShape = new PositionableShape<>(gameInfo.getShip().getShape(), gameInfo.getShip().getPosition());


        double distanceRestanteX = currentCheckPoint.getPosition().getX() - gameInfo.getShip().getPosition().getX();
        double distanceRestanteY = currentCheckPoint.getPosition().getY() - gameInfo.getShip().getPosition().getY();

        System.out.println(distanceRestanteX);
        System.out.println(distanceRestanteY);
        System.out.println(gameInfo.getShip().getPosition().getOrientation());


        //double angle = Math.atan(distanceRestanteY/distanceRestanteX) + gameInfo.getShip().getTransform().getOrientation();

        double checkPointSize = 40;
        if (currentCheckPoint.getShape() instanceof Circle) {
            checkPointSize = (((Circle) currentCheckPoint.getShape()).getRadius()) / 2;
        }
        System.out.println(checkPointSize);








        //if (!Collisions.isColliding(checkpointsShape, boatShape)) {
        if (Math.abs(distanceRestanteX) >= checkPointSize && Math.abs(distanceRestanteY) >= checkPointSize) {


            // Calculer l'angle
            double angle = gameInfo.getShip().getPosition().getAngleToSee(currentCheckPoint.getPosition());

            if (angle == 0.0) {
                //finalsActions = gameInfo.getShip().moveBoatToAPoint(currentCheckPoint.getPosition());
                TurnBoat turnBoat = new TurnBoat(angle, gameInfo.getShip(), Arrays.asList(gameInfo.getSailors()));
                finalsActions = turnBoat.moveBoatInLine();
            } else {
                TurnBoat turnBoat = new TurnBoat(angle, gameInfo.getShip(), Arrays.asList(gameInfo.getSailors()));
                finalsActions = turnBoat.turnBoat();
                System.out.println("Salut");
            }
            // verifier si on a atteint le checkpoint : si oui : si ya plus de checpoints apres s'arreter, sinon prendre le nouveau checkpoint

        }


        return om.writeValueAsString(finalsActions);
    }
}
