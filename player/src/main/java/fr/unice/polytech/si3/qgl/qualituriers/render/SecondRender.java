package fr.unice.polytech.si3.qgl.qualituriers.render;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.RoundInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Alexandre Arcil, CLODONG Yann
 * • Un seul point de passage
 * • Le point de passage ne sera pas en face de vous : il va falloir apprendre à tourner
 * • Votre bateau sera plus grand: 4 cases de long pour 2 cases de large
 * • Votre bateau contiendra 6 rames
 * • Vos 4 marins ne seront pas forcement placés sur des rames au début de la partie : déplacez les avec l'action MOVING.
 */
public class SecondRender extends Render {

    private final CheckPoint[] checkpoints;
    private int nextCheckpoint = 0;
    private boolean firstRound = true;

    private ObjectMapper om;

    public SecondRender(GameInfo gameInfo) {
        super(gameInfo);
        om = new ObjectMapper();

        var goal = (RegattaGoal)gameInfo.getGoal();
        checkpoints = goal.getCheckPoints();
    }

    /**
     * @return the angle to reach the next checkpoint
     */
    public double getAngleToRotate() {
        var checkpoint = checkpoints[nextCheckpoint];
        return gameInfo.getShip().getPosition().getAngleToSee(checkpoint.getPosition());
    }


    @Override
    public String nextRound(RoundInfo round) throws JsonProcessingException {
        // Don't forget to change nextCheckpoint when it's reached

        // EN premier, nous allons affecter les nouvelles bonns valeurs au bateau
        gameInfo.getShip().setTransform(round.getShip().getPosition());
        gameInfo.getShip().setEntities(round.getShip().getEntities());


        CheckPoint checkPoint;
        RegattaGoal regGoal = (RegattaGoal) gameInfo.getGoal();
        CheckPoint[] checkPoints = regGoal.getCheckPoints();
        Optional<CheckPoint> checkPointOp = Arrays.stream(checkPoints).findAny();
        if (checkPointOp.isPresent()) {checkPoint = checkPointOp.get();}


        if (firstRound) {
            gameInfo.getShip().setSailors(Arrays.asList(gameInfo.getSailors().clone()));
            firstRound = false;
        }

        //gameInfo.getShip().moveBoatToAPoint(checkPoint.getPosition());


        //List<Action> actions = gameInfo.getShip().getActionsToDo();
        List<Action> actions = new ArrayList<>();
        //gameInfo.getShip().setActionsToDo(new ArrayList<Action>());





        return om.writeValueAsString(actions);
    }

}
