package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.exceptions.MovingSailorException;
import fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.decisions.SailorMission;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Moving;

import java.util.Arrays;
import java.util.Objects;

import static fr.unice.polytech.si3.qgl.qualituriers.Config.MAX_MOVING_CASES_MARIN;

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
    private SailorMission sailorMission;

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

    public void setPosition(int x, int y) { this.x = x; this.y = y;}


    public void setSailorMission(SailorMission internSailorMission) {
        this.sailorMission = internSailorMission;
    }

    public SailorMission getSailorMission() {
        return sailorMission;
    }

    public boolean canMoveTo(int xFinal, int yFinal, Boat boat) {
        return (Math.abs(xFinal - x) + Math.abs(yFinal - y)) <= MAX_MOVING_CASES_MARIN
                && xFinal <= boat.getDeck().getLength()-1 && xFinal >= 0
                && yFinal  <= boat.getDeck().getWidth()-1 && yFinal >= 0;

    }

    @Override
    public String toString() {
        return "Marin : " +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", name=" + name +
                ", mission : " + sailorMission +
                "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Marin)) return false;
        var castedObj = (Marin)obj;
        return castedObj.id == id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y, name);
    }


    /**
     * @param boat sur lequel on veut regarder s'il y a des marins dessus
     * @param boatEntities le type de boatEntities sur lequel on veut regader si il y a, ou non, le marin actuel
     * @return true si le marin est sur une de ces boatEntities, false sinon
     */
    public boolean isOn(BoatEntities boatEntities, Boat boat) {
        return Arrays.stream(boat.getEntities()).filter(boatEntity -> boatEntity.getType() == boatEntities).anyMatch(boatEntity ->
                boatEntity.getX() == this.x && boatEntity.getX() == this.y
        );
    }









    public Action generateMovingAction(Point destination, Boat boat) {

        if (!canMoveTo((int) destination.getX(), (int) destination.getY(), boat)) {
            throw new MovingSailorException(id);
        }

        Action action =  new Moving(id, (int) destination.getX() - this.x, (int) destination.getY() - this.y);

        this.x = (int) destination.getX();
        this.y = (int) destination.getY();

        return action;
    }











}
