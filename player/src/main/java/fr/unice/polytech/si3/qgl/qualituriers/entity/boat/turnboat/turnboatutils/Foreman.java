package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatutils;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.*;

import java.util.*;

/**
 * @author CLODONG Yann
 */
public class Foreman {
    private final Boat boat;

    private final Map<BoatEntity, Marin> posts = new HashMap<>();

    private final List<OarBoatEntity> rightOars = new ArrayList<>();
    private final List<OarBoatEntity> leftOars = new ArrayList<>();
    private int maxSpeed;

    private List<Marin> availableSailors;

    private int speed = 0;
    private int bend = 0;

    public Foreman(Boat boat) {
        this.boat = boat;

        for(var ent: boat.getEntities()) {
            posts.put(ent, null);

            switch (ent.getType()) {
                case OAR:
                    var oar = (OarBoatEntity)ent;
                    if(oar.isLeftOar()) leftOars.add(oar);
                    else rightOars.add(oar);
                    break;
            }
        }

        maxSpeed = Math.min(rightOars.size(), leftOars.size());
    }

    /**
     * Définie quelle marin le contre-maitre peut utiliser
     * @param sailors: les marins
     */
    public void setSailors(Collection<Marin> sailors) {
        for(var post: posts.entrySet()) {
            unAssignSailorToOar((OarBoatEntity)post.getKey());
        }
        availableSailors = new ArrayList<>(sailors);
        int humanSpeedCapacity = availableSailors.size() / 2;
        if(humanSpeedCapacity < maxSpeed)
            maxSpeed = humanSpeedCapacity;
    }

    int getNumberRightRowingSailors() {
        return (int)rightOars.stream().map(posts::get).filter(Objects::nonNull).count();
    }

    int getNumberLeftRowingSailors() {
        int res = (int)leftOars.stream().map(posts::get).filter(Objects::nonNull).count();
        return res;
    }

    private Optional<Marin> nearestAvailable(BoatEntity entity) {
        if(entity == null) throw new NullPointerException("Entity was null.");
        return availableSailors.stream().min((a, b) -> {
            if(a != null && b != null)
                return Double.compare(a.getPosition().substract(entity.getPosition()).length(), b.getPosition().substract(entity.getPosition()).length());
            return Double.compare(a == null ? Double.MAX_VALUE : a.getPosition().substract(entity.getPosition()).length(), b == null ? Double.MAX_VALUE : b.getPosition().substract(entity.getPosition()).length());
        });
    }

    private int getNumberOfLeftOarsman() {
        if(bend < 0) return speed + bend;
        else return speed;
    }

    private int getNumberOfRightOarsman() {
        if(bend > 0) return speed - bend;
        else return speed;
    }

    public void decide() {
        Optional<OarBoatEntity> oar;

        // fired oarsman if the side is too fast
        while(getNumberOfLeftOarsman() < getNumberLeftRowingSailors() && (oar = getFilteredOar(true, false)).isPresent())
            unAssignSailorToOar(oar.get());
        while(getNumberOfRightOarsman() < getNumberRightRowingSailors() && (oar = getFilteredOar(true, true)).isPresent())
            unAssignSailorToOar(oar.get());

        // if there is sailors available
        if(availableSailors.size() > 0)  {
            // hire oarsman if the side is too slow
            while(getNumberOfLeftOarsman() > getNumberLeftRowingSailors() && (oar = getFilteredOar(false, false)).isPresent())
                assignSailorToOar(oar.get());
            while(getNumberOfRightOarsman() > getNumberRightRowingSailors() && (oar = getFilteredOar(false, true)).isPresent())
                assignSailorToOar(oar.get());
        }
    }

    private Optional<OarBoatEntity> getFilteredOar(boolean used, boolean isRight) {
        if(isRight)
            return rightOars.stream()
                .filter(o -> o.getType() == BoatEntities.OAR && (posts.get(o) != null) == used)
                .findAny();
        else
            return leftOars.stream()
                    .filter(o -> o.getType() == BoatEntities.OAR && (posts.get(o) != null) == used)
                    .findAny();
    }

    void assignSailorToOar(OarBoatEntity oar) {
        if(posts.get(oar) != null) return;
        var avSailor = nearestAvailable(oar);
        if(avSailor.isEmpty()) return;


        posts.replace(oar, avSailor.get());
        avSailor.get().assignTo(oar);
        avSailor.get().resume();
        availableSailors.remove(avSailor.get());
    }

    void unAssignSailorToOar(OarBoatEntity oar) {
        if(posts.get(oar) == null) return;
        posts.get(oar).unAssign();
        posts.get(oar).pause();
        posts.remove(oar);
        availableSailors.add(posts.get(oar));
    }

    Marin getSailorAtPost(BoatEntity post) {
        if(post == null || !posts.containsKey(post)) return null;
        return posts.get(post);
    }

    /**
     * Définie la vitesse du bateau en ligne droite
     * @param oarsman: le nombre de rameur par cotés
     */
    public void setSpeed(int oarsman) {
        if(oarsman < 0) oarsman = 0;
        else if(oarsman > maxSpeed) oarsman = maxSpeed;

        speed = oarsman;
    }

    /**
     * Définie la vitesse du bateau en ligne droite
     * @param percentage: Le bateau ira a percentage% de l'allure maximal
     */
    public void setSpeed(double percentage) {
        speed = (int)(percentage * (double)maxSpeed);
    }

    /**
     * Definie le virage du bateaux à gauche
     * @param oarsman: Le nombre de rameurs qui arrete de ramer
     */
    public void setBendLeft(int oarsman) {
        if(oarsman < 0) oarsman = 0;
        else if(oarsman > maxSpeed) oarsman = maxSpeed;
        bend = -oarsman;
    }

    /**
     * Definie le virage du bateaux à gauche
     * @param percentage (0 -> 1): La proportion rameurs qui arrete de ramer
     */
    public void setBendLeft(double percentage) {
        setBendLeft((int)(percentage * (double)maxSpeed));
    }

    /**
     * Definie le virage du bateaux a droite
     * @param oarsman: Le nombre de rameurs qui arrete de ramer
     */
    public void setBendRight(int oarsman) {
        if(oarsman < 0) oarsman = 0;
        else if(oarsman > maxSpeed) oarsman = maxSpeed;
        bend = oarsman;
    }

    /**
     * Definie le virage du bateaux à droite
     * @param percentage (0 -> 1): La proportion rameurs qui arrete de ramer
     */
    public void setBendRight(double percentage) {
        setBendRight((int)(percentage * (double)maxSpeed));
    }

    /**
     * Reset le virage: le bateau va tout droit
     */
    public void resetBend() {
        bend = 0;
    }


}
