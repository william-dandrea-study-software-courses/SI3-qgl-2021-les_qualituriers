package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboatsecond;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.OarBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboatsecond.utils.DistanceDisposition;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Moving;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * important : faire un appel a getMarins après avoir appeler la méthode principale pour actualiser les marins
 * sur le bateau
 */

public class MoveBoatDistanceStrategy {


    private final Boat boat;
    private final List<Disposition> listOfDispositions;
    private List<Marin> sailors;

    public MoveBoatDistanceStrategy(Boat boat, List<Disposition> listOfDispositions, Marin[] sailors) {
        this.boat = boat;
        this.listOfDispositions = listOfDispositions;
        this.sailors = Arrays.asList(sailors.clone());
    }

    public Marin[] getSailors() { return (Marin[]) sailors.toArray(new Marin[0]); }

    public List<Action> moveBoat() {
        return moveBoatIntern();
    }


    private List<Action> moveBoatIntern() {

        List<Action> finalListAction = new ArrayList<>();

        for (Disposition disposition : listOfDispositions) {

            System.out.println(disposition.toString());
            int missingNumberOfSailorsOnOarAtBabord = disposition.getBabordOar() - getListOfSailorsOnBabordOars().size();
            System.out.println("missingNumberOfSailorsOnOarAtBabord :" + missingNumberOfSailorsOnOarAtBabord );
            if (missingNumberOfSailorsOnOarAtBabord > 0) {

                System.out.println("=> getListOfSailorsOnAnyOar().size()" + getListOfSailorsOnAnyOar().size());
                System.out.println("=> (disposition.getTribordOar() - getListOfSailorsOnTribordOars().size())" + (disposition.getTribordOar() - getListOfSailorsOnTribordOars().size()));
                if (getListOfSailorsOnAnyOar().size() + (disposition.getTribordOar() - getListOfSailorsOnTribordOars().size()) >= missingNumberOfSailorsOnOarAtBabord) {

                    for (Marin marinLibre : getListOfSailorsOnAnyOar()) {
                        List<BoatEntity> freeOarsPlaceAtBabord = getListOfBabordOars().stream().filter(oar -> sailors.stream().anyMatch(marin -> marin.getX() == oar.getX() && marin.getY() == oar.getY())).collect(Collectors.toList());
                        Optional<BoatEntity> oarWeWillMove = freeOarsPlaceAtBabord.stream().findAny();
                        if (oarWeWillMove.isPresent()) {
                            finalListAction.add(generateMovingAction(marinLibre.getId(), marinLibre.getX(), marinLibre.getY(), oarWeWillMove.get().getX(), oarWeWillMove.get().getY()));
                            marinLibre.setX(oarWeWillMove.get().getX()); marinLibre.setY(oarWeWillMove.get().getY());
                            //freeOarsPlaceAtBabord.remove(oarWeWillMove.get());
                        }
                    }

                    if (disposition.getTribordOar() - getListOfSailorsOnTribordOars().size() > 0) {
                        for (int i = 0; i < disposition.getTribordOar() - getListOfSailorsOnTribordOars().size(); i++) {
                            List<BoatEntity> freeOarsPlaceAtBabord = getListOfBabordOars().stream().filter(oar -> sailors.stream().anyMatch(marin -> marin.getX() == oar.getX() && marin.getY() == oar.getY())).collect(Collectors.toList());

                            Optional<BoatEntity> oarWeWillMove = freeOarsPlaceAtBabord.stream().findAny();
                            Optional<Marin> marin = getListOfSailorsOnTribordOars().stream().findAny();
                            if (marin.isPresent() && oarWeWillMove.isPresent()) {
                                finalListAction.add(generateMovingAction(marin.get().getId(), marin.get().getX(), marin.get().getY(), oarWeWillMove.get().getX(), oarWeWillMove.get().getY()));
                                marin.get().setX(oarWeWillMove.get().getX()); marin.get().setY(oarWeWillMove.get().getY());

                            }
                        }
                    }


                }
            }



            int missingNumberOfSailorsOnOarAtTribord = disposition.getTribordOar() - getListOfSailorsOnTribordOars().size();
            System.out.println("missingNumberOfSailorsOnOarAtTribord : " + missingNumberOfSailorsOnOarAtTribord);
            if (missingNumberOfSailorsOnOarAtTribord > 0) {

                List<BoatEntity> freeOarsPlaceAtTribord = getListOfTribordOars().stream().filter(oar -> sailors.stream().anyMatch(marin -> marin.getX() == oar.getX() && marin.getY() == oar.getY())).collect(Collectors.toList());
                System.out.println("=> getListOfSailorsOnAnyOar().size() : " + getListOfSailorsOnAnyOar().size());
                System.out.println("=> (disposition.getBabordOar() - getListOfSailorsOnBabordOars().size()) : " + (disposition.getBabordOar() - getListOfSailorsOnBabordOars().size()));
                if (getListOfSailorsOnAnyOar().size() + (disposition.getBabordOar() - getListOfSailorsOnBabordOars().size()) >= missingNumberOfSailorsOnOarAtTribord) {

                    for (Marin marinLibre : getListOfSailorsOnAnyOar()) {
                        Optional<BoatEntity> oarWeWillMove = freeOarsPlaceAtTribord.stream().findAny();
                        if (oarWeWillMove.isPresent()) {
                            finalListAction.add(generateMovingAction(marinLibre.getId(), marinLibre.getX(), marinLibre.getY(), oarWeWillMove.get().getX(), oarWeWillMove.get().getY()));
                            marinLibre.setX(oarWeWillMove.get().getX()); marinLibre.setY(oarWeWillMove.get().getY());
                            freeOarsPlaceAtTribord.remove(oarWeWillMove.get());
                        }
                    }
                }


                if (disposition.getBabordOar() - getListOfSailorsOnBabordOars().size() > 0) {
                    for (int i = 0; i < disposition.getBabordOar() - getListOfSailorsOnBabordOars().size(); i++) {
                        Optional<BoatEntity> oarWeWillMove = freeOarsPlaceAtTribord.stream().findAny();
                        Optional<Marin> marin = getListOfSailorsOnBabordOars().stream().findAny();
                        if (marin.isPresent() && oarWeWillMove.isPresent()) {
                            finalListAction.add(generateMovingAction(marin.get().getId(), marin.get().getX(), marin.get().getY(), oarWeWillMove.get().getX(), oarWeWillMove.get().getY()));
                            marin.get().setX(oarWeWillMove.get().getX()); marin.get().setY(oarWeWillMove.get().getY());
                            freeOarsPlaceAtTribord.remove(oarWeWillMove.get());

                        }
                    }
                }
            }

            if (disposition.getBabordOar() - getListOfSailorsOnBabordOars().size() <= 0 && disposition.getTribordOar() - getListOfSailorsOnTribordOars().size() <= 0) {
                return finalListAction;
            } else {
                finalListAction.clear();
            }
        }

        return null;

    }















    private Action generateMovingAction(int sailorId,int initialX, int initialY, int finalX, int finalY) {
        return new Moving(sailorId, finalX - initialX, finalY - initialY);
    }



    private List<Marin> getListOfSailorsOnBabordOars() {
        List<Marin> marinsAtBabord =  sailors.stream().filter(marin -> marin.getX() == 0).collect(Collectors.toList());
        List<Marin> finalList = new ArrayList<>();
        for (Marin marin : marinsAtBabord) {
            if (getListOfBabordOars().stream().allMatch(oar -> oar.getX() == marin.getX() && oar.getY() == marin.getY())) {
                finalList.add(marin);
            }
        }
        return finalList;
    }
    private List<Marin> getListOfSailorsOnTribordOars() {
        List<Marin> marinsAtTribord = sailors.stream().filter(marin -> marin.getX() == boat.getDeck().getWidth()-1).collect(Collectors.toList());
        List<Marin> finalList = new ArrayList<>();
        for (Marin marin : marinsAtTribord) {
            if (getListOfTribordOars().stream().allMatch(oar -> oar.getX() == marin.getX() && oar.getY() == marin.getY())) {
                finalList.add(marin);
            }
        }
        return finalList;
    }

    private List<Marin> getListOfSailorsOnOars() { return Stream.concat(getListOfSailorsOnBabordOars().stream(), getListOfSailorsOnTribordOars().stream()).collect(Collectors.toList()); }
    private List<Marin> getListOfSailorsOnAnyOar() { return sailors.stream().filter(marin -> !getListOfSailorsOnOars().contains(marin)).collect(Collectors.toList()); }
    private List<BoatEntity> getListOfOars() { return Arrays.stream(boat.getEntities()).filter(OarBoatEntity.class::isInstance).collect(Collectors.toList()); }
    private List<BoatEntity> getListOfBabordOars() { return Arrays.stream(boat.getEntities()).filter(OarBoatEntity.class::isInstance).filter(oar -> ((OarBoatEntity) oar).isLeftOar()).collect(Collectors.toList()); }
    private List<BoatEntity> getListOfTribordOars() { return Arrays.stream(boat.getEntities()).filter(OarBoatEntity.class::isInstance).filter(oar -> ((OarBoatEntity) oar).isRightOar()).collect(Collectors.toList()); }

}
