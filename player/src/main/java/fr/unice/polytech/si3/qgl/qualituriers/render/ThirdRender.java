package fr.unice.polytech.si3.qgl.qualituriers.render;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboat.TurnBoat;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.RoundInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
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


        double distanceRestante = Math.sqrt(distanceRestanteX * distanceRestanteX + distanceRestanteY * distanceRestanteY);
        System.out.println("======================================================================================================");
        System.out.println(distanceRestanteX);
        System.out.println(distanceRestanteY);
        System.out.println("Distance restante : " + distanceRestante);
        System.out.println("======================================================================================================");


        gameInfo.getShip().setSailors(Arrays.asList(gameInfo.getSailors().clone()));
        gameInfo.getShip().getCaptain().goTo(((RegattaGoal) gameInfo.getGoal()).getCheckPoints()[0].getPosition());

        return om.writeValueAsString(gameInfo.getShip().playTurn());





    }
}
