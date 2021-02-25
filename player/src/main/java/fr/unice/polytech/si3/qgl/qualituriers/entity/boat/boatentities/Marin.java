package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Moving;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Oar;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe represente un marin qui pourra donner des ordres a différents protagonistes present sur le bateau
 *
 * @author williamdandrea
 * @author CLODONG Yann
 */
public class Marin {

    private final int id;
    private int x;
    private int y;
    private final String name;

    private BoatEntity assignment;
    private boolean paused = false;

    public Marin(@JsonProperty("id")int id, @JsonProperty("x")int x, @JsonProperty("y")int y, @JsonProperty("name")String name) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public Point getPosition() {
        return new Point(x, y);
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public void setX( int x ) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean canMoveTo(int xFinal, int yFinal) {
        return (Math.abs(x - xFinal) <= Config.MAX_MOVING_CASES_MARIN && Math.abs(y - yFinal) <= Config.MAX_MOVING_CASES_MARIN);
    }

    @Override
    public String toString() {
        return "Marin{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Marin)) return false;
        var castedObj = (Marin)obj;
        return castedObj.id == id;
    }

    public void assignTo(BoatEntity entity) {
        this.assignment = entity;
    }

    public void unAssign() {
        this.assignment = null;
    }

    boolean onAssignment() {
        if(assignment == null) return true;
        return x == assignment.x && y == assignment.y;
    }

    private Moving getMovingAction() {
        Point dest = assignment.getPosition();
        Point pos = getPosition();

        Point dist = Moving.clamp(dest.substract(pos));

        x += (int)dist.getX();
        y += (int)dist.getY();

        return new Moving(id, (int)dist.getX(), (int)dist.getY());
    }

    private Action getAssignmentAction() {
        if(assignment == null) return null;

        switch(assignment.type) {
            case OAR: return new Oar(id);
        }
        return null;
    }

    public List<Action> actionDoneDuringTurn() {
        List<Action> actions = new ArrayList<>();

        // Go on assignment
        if(!onAssignment()) {
            actions.add(getMovingAction());
        }

        // If I arrived or if I am on assignment
        if(!paused && Moving.canMove(getPosition(), assignment.getPosition())) {
            var act = getAssignmentAction();
            if(act != null)
                actions.add(act);
        }

        return actions;
    }

    public void pause() {
        this.paused = true;
    }

    public void resume() {
        this.paused = false;
    }
}
