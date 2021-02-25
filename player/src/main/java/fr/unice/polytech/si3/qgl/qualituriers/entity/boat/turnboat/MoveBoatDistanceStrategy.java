package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboat;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Moving;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Oar;
import fr.unice.polytech.si3.qgl.qualituriers.utils.logger.ILogger;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * important : faire un appel a getMarins après avoir appeler la méthode principale pour actualiser les marins
 * sur le bateau
 */

public class MoveBoatDistanceStrategy {


    // input
    private final Boat boat;
    private final List<Disposition> listOfDispositions;
    private List<Marin> sailors;

    //intern
    private Disposition finalDisposition;
    private Map<Integer, Point> initialPositionOfSailors;

    public MoveBoatDistanceStrategy(Boat boat, List<Disposition> listOfDispositions, Marin[] sailors) {
        this.boat = boat;
        this.listOfDispositions = listOfDispositions;
        this.sailors = Arrays.asList(sailors.clone());

        initialPositionOfSailors = new HashMap<>();
        for (Marin sailor : sailors) {  initialPositionOfSailors.put(sailor.getId(), new Point(sailor.getX(), sailor.getY())); }

    }

    public Marin[] getSailors() { return sailors.toArray(new Marin[0]); }

    public List<Action> moveBoat(ILogger logger) {

        List<Action> finalsAction = new ArrayList<>();
        List<Action> movingAction = moveBoatIntern(logger);

        if (movingAction != null) {
            List<Action> oarAction = oarSailors(finalDisposition);
            finalsAction.addAll(movingAction);
            finalsAction.addAll(oarAction);

        }
        return finalsAction;

    }

    private List<Action> oarSailors(Disposition finalDisposition) {

        if (finalDisposition != null) {
            List<Action> finalsAction = new ArrayList<>();
            int wantedTribordOar = finalDisposition.getTribordOar();
            int wantedBabordOar = finalDisposition.getBabordOar();

            List<Marin> availableSailorsAtBabord = getListOfSailorsOnBabordOars();
            List<Marin> availableSailorsAtTribord = getListOfSailorsOnTribordOars();

            for (int babord = 0; babord < wantedBabordOar; babord++) {
                finalsAction.add(new Oar(availableSailorsAtBabord.get(babord).getId()));
            }
            for (int tribord = 0; tribord < wantedTribordOar; tribord++) {
                finalsAction.add(new Oar(availableSailorsAtTribord.get(tribord).getId()));
            }
            return finalsAction;
        }
        return new ArrayList<>();
    }


    private List<Action> moveBoatIntern(ILogger logger) {

        System.out.println("IDEAL : " + listOfDispositions.get(0));

        List<Action> finalListAction = new ArrayList<>();

        for (Disposition disposition : listOfDispositions) {

            List<Point> fordiddenPointsToMove = getListOfSailorsOnOars().stream().map(sailor -> new Point(sailor.getX(), sailor.getY())).collect(Collectors.toList());

            int missingNumberOfSailorsOnOarAtBabord = disposition.getBabordOar() - getListOfSailorsOnBabordOars().size();
            if (missingNumberOfSailorsOnOarAtBabord > 0) {

                if (getListOfSailorsOnAnyOar().size() >= missingNumberOfSailorsOnOarAtBabord) {

                    for (Marin marinLibre : getListOfSailorsOnAnyOar()) {
                        List<Point> availablePoints = new ArrayList<>();

                        for (Point ramePoint : getListOfBabordOars().stream().map(oar -> new Point(oar.getX(), oar.getY())).collect(Collectors.toList())) {
                            boolean canGoToThisPoint = true;
                            for (Point forbiddenPoint : fordiddenPointsToMove) {
                                if (forbiddenPoint.getX() == ramePoint.getX() && forbiddenPoint.getY() == ramePoint.getY()) {
                                    canGoToThisPoint = false;
                                    break;
                                }
                            }
                            if (canGoToThisPoint) availablePoints.add(ramePoint);
                        }


                        Optional<Point> oarWeWillMove = availablePoints.stream().findAny();


                        if (oarWeWillMove.isPresent()) {
                            finalListAction.add(generateMovingAction(marinLibre.getId(), marinLibre.getX(), marinLibre.getY(), (int) oarWeWillMove.get().getX(), (int) oarWeWillMove.get().getY()));
                            marinLibre.setX((int) oarWeWillMove.get().getX()); marinLibre.setY((int) oarWeWillMove.get().getY());
                            fordiddenPointsToMove.add(new Point(marinLibre.getX(), marinLibre.getY()));
                        }
                    }
                }

                if (getListOfSailorsOnTribordOars().size() - disposition.getTribordOar() > 0) {
                    for (int i = 0; i < getListOfSailorsOnTribordOars().size() - disposition.getTribordOar(); i++) {

                        List<Point> availablePoints = new ArrayList<>();
                        for (Point ramePoint : getListOfBabordOars().stream().map(oar -> new Point(oar.getX(), oar.getY())).collect(Collectors.toList())) {
                            boolean canGoToThisPoint = true;
                            for (Point forbiddenPoint : fordiddenPointsToMove) {
                                if (forbiddenPoint.getX() == ramePoint.getX() && forbiddenPoint.getY() == ramePoint.getY()) {
                                    canGoToThisPoint = false;
                                    break;
                                }
                            }
                            if (canGoToThisPoint) availablePoints.add(ramePoint);
                        }

                        Optional<Point> oarWeWillMove = availablePoints.stream().findAny();
                        Optional<Marin> marin = getListOfSailorsOnBabordOars().stream().findAny();

                        if (marin.isPresent() && oarWeWillMove.isPresent()) {
                            finalListAction.add(generateMovingAction(marin.get().getId(), marin.get().getX(), marin.get().getY(), (int)oarWeWillMove.get().getX(), (int)oarWeWillMove.get().getY()));
                            marin.get().setX((int) oarWeWillMove.get().getX()); marin.get().setY((int)oarWeWillMove.get().getY());
                            fordiddenPointsToMove.add(new Point(marin.get().getX(), marin.get().getY()));

                        }
                    }
                }
            }



            int missingNumberOfSailorsOnOarAtTribord = disposition.getTribordOar() - getListOfSailorsOnTribordOars().size();
            if (missingNumberOfSailorsOnOarAtTribord > 0) {


                if (getListOfSailorsOnAnyOar().size() >= missingNumberOfSailorsOnOarAtTribord) {
                    for (Marin marinLibre : getListOfSailorsOnAnyOar()) {

                        List<Point> availablePoints = new ArrayList<>();

                        for (Point ramePoint : getListOfTribordOars().stream().map(oar -> new Point(oar.getX(), oar.getY())).collect(Collectors.toList())) {
                            boolean canGoToThisPoint = true;
                            for (Point forbiddenPoint : fordiddenPointsToMove) {
                                if (forbiddenPoint.getX() == ramePoint.getX() && forbiddenPoint.getY() == ramePoint.getY()) {
                                    canGoToThisPoint = false;
                                    break;
                                }
                            }
                            if (canGoToThisPoint) availablePoints.add(ramePoint);
                        }



                        Optional<Point> oarWeWillMove = availablePoints.stream().findAny();

                        if (oarWeWillMove.isPresent()) {

                            finalListAction.add(generateMovingAction(marinLibre.getId(), marinLibre.getX(), marinLibre.getY(), (int) oarWeWillMove.get().getX(), (int) oarWeWillMove.get().getY()));
                            //sailors.remove(marinLibre);
                            marinLibre.setX((int) oarWeWillMove.get().getX()); marinLibre.setY((int) oarWeWillMove.get().getY());
                            //sailors.add(marinLibre);
                            fordiddenPointsToMove.add(new Point(marinLibre.getX(), marinLibre.getY()));

                        }
                    }
                }



                if (getListOfSailorsOnBabordOars().size() - disposition.getBabordOar() > 0) {

                    for (int i = 0; i < getListOfSailorsOnBabordOars().size() - disposition.getBabordOar(); i++) {

                        List<Point> availablePoints = new ArrayList<>();
                        for (Point ramePoint : getListOfTribordOars().stream().map(oar -> new Point(oar.getX(), oar.getY())).collect(Collectors.toList())) {
                            boolean canGoToThisPoint = true;
                            for (Point forbiddenPoint : fordiddenPointsToMove) {
                                if (forbiddenPoint.getX() == ramePoint.getX() && forbiddenPoint.getY() == ramePoint.getY()) {
                                    canGoToThisPoint = false;
                                    break;
                                }
                            }
                            if (canGoToThisPoint) availablePoints.add(ramePoint);
                        }




                        Optional<Point> oarWeWillMove = availablePoints.stream().findAny();
                        Optional<Marin> marin = getListOfSailorsOnBabordOars().stream().findAny();

                        if (marin.isPresent() && oarWeWillMove.isPresent()) {
                            finalListAction.add(generateMovingAction(marin.get().getId(), marin.get().getX(), marin.get().getY(), (int)oarWeWillMove.get().getX(), (int)oarWeWillMove.get().getY()));
                            marin.get().setX((int) oarWeWillMove.get().getX()); marin.get().setY((int)oarWeWillMove.get().getY());
                            fordiddenPointsToMove.add(new Point(marin.get().getX(), marin.get().getY()));
                        }
                    }
                }
            }

            if (disposition.getBabordOar() - getListOfSailorsOnBabordOars().size() <= 0 && disposition.getTribordOar() - getListOfSailorsOnTribordOars().size() <= 0) {
                finalDisposition = disposition;
                System.out.println(finalDisposition);
                return finalListAction;
            }

            System.out.println("Ca passe pas");

            // On reinitialise la position des sailors
            for (Marin marin : sailors) {
                marin.setX((int)initialPositionOfSailors.get(marin.getId()).getX());
                marin.setY((int)initialPositionOfSailors.get(marin.getId()).getY());
            }
            finalListAction.clear();
        }

        return new ArrayList<>();

    }









    void actualizeAll() {

        getListOfOars();
        getListOfTribordOars();
        getListOfBabordOars();

        getListOfSailorsOnBabordOars();
        getListOfSailorsOnTribordOars();
        getListOfSailorsOnOars();
        getListOfSailorsOnAnyOar();


    }





    private Action generateMovingAction(int sailorId,int initialX, int initialY, int finalX, int finalY) {
        return new Moving(sailorId, finalX - initialX, finalY - initialY);
    }



    private List<Marin> getListOfSailorsOnBabordOars() {

        List<Marin> marinsAtBabord =  sailors.stream().filter(marin -> marin.getY() == 0).collect(Collectors.toList());
        List<Marin> finalList = new ArrayList<>();
        for (Marin marin : marinsAtBabord) {
            if (getListOfBabordOars().stream().anyMatch(oar -> oar.getX() == marin.getX() && oar.getY() == marin.getY())) {
                finalList.add(marin);
            }
        }
        return finalList;
    }
    private List<Marin> getListOfSailorsOnTribordOars() {

        List<Marin> marinsAtTribord = sailors.stream().filter(marin -> marin.getY() == boat.getDeck().getWidth()-1).collect(Collectors.toList());
        List<Marin> finalList = new ArrayList<>();
        for (Marin marin : marinsAtTribord) {
            if (getListOfTribordOars().stream().anyMatch(oar -> oar.getX() == marin.getX() && oar.getY() == marin.getY())) {
                finalList.add(marin);
            }
        }
        return finalList;
    }

    private List<Marin> getListOfSailorsOnOars() { return Stream.concat(getListOfSailorsOnBabordOars().stream(), getListOfSailorsOnTribordOars().stream()).collect(Collectors.toList()); }
    private List<Marin> getListOfSailorsOnAnyOar() { return sailors.stream().filter(marin -> !getListOfSailorsOnOars().contains(marin)).collect(Collectors.toList()); }
    private List<BoatEntity> getListOfOars() { return Arrays.stream(boat.getEntities()).filter(oar -> oar.getType() == BoatEntities.OAR).collect(Collectors.toList()); }
    private List<BoatEntity> getListOfBabordOars() { return Arrays.stream(boat.getEntities()).filter(oar -> oar.getType() == BoatEntities.OAR && oar.getY() == 0).collect(Collectors.toList()); }
    private List<BoatEntity> getListOfTribordOars() { return Arrays.stream(boat.getEntities()).filter(oar -> oar.getType() == BoatEntities.OAR && oar.getY() == boat.getDeck().getWidth()-1).collect(Collectors.toList()); }


}
