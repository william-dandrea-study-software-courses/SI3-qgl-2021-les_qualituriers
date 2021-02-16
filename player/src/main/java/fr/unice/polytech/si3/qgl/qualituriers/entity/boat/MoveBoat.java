package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboat.TurnBoat;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Moving;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Oar;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MoveBoat extends Boat{


    public MoveBoat(int life, Transform position, String name, Deck deck, BoatEntity[] entities, Shape shape) {
        super(life, position, name, deck, entities, shape);
    }






    public List<Action> moveBoatInLine() {

        generateListSailorsOnOar();
        List<Action> actionList = new ArrayList<>();

        // On regarde combien on a de marin a droite et a gauche
        if (getSailorsOnOarAtBabord().size() > 0 && getSailorsOnOarAtTribord().size() > 0) {
            // Si on a assez de marin de chaque côté on a juste a avancer
            for (int i = 0; i < Math.min(getSailorsOnOarAtBabord().size(),getSailorsOnOarAtTribord().size()); i++) {

                actionList.add(new Oar(getSailorsOnOarAtTribord().get(i).getId()));
                actionList.add(new Oar(getSailorsOnOarAtBabord().get(i).getId()));

            }

            return actionList;
        } else {



            // Si on a pas assez a babord, on bouge un marin de tribord vers babord
            if (getSailorsOnOarAtBabord().size() == 0 && getSailorsOnOarAtTribord().size() > 1)  {

                Optional<BoatEntity> freePlace = getListOfOarsAtBabord().stream().findAny();
                Optional<Marin> moveMarin = getSailorsOnOarAtTribord().stream().findAny();

                if (moveMarin.isPresent() && freePlace.isPresent()) {
                    actionList.add(new Moving(moveMarin.get().getId(), freePlace.get().getX() - moveMarin.get().getX(), freePlace.get().getY() - moveMarin.get().getY()));
                    moveMarin.get().setX(freePlace.get().getX());
                    moveMarin.get().setY(freePlace.get().getY());
                }

                return moveBoatInLine();

            }

            // Si on a pas assez a tribord
            if (getSailorsOnOarAtTribord().size() == 0 && getSailorsOnOarAtBabord().size() > 1) {

                Optional<BoatEntity> freePlace = getListOfOarsAtTribord().stream().findAny();
                Optional<Marin> moveMarin = getSailorsOnOarAtBabord().stream().findAny();

                if (moveMarin.isPresent() && freePlace.isPresent()) {
                    actionList.add(new Moving(moveMarin.get().getId(), freePlace.get().getX() - moveMarin.get().getX(), freePlace.get().getY() - moveMarin.get().getY()));
                    moveMarin.get().setX(freePlace.get().getX());
                    moveMarin.get().setY(freePlace.get().getY());
                }

                return moveBoatInLine();

            }
        }


        return actionList;

    }





}
