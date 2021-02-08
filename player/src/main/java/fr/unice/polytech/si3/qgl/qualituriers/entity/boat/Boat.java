package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatutils.BabordTribordAngle;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatutils.BoatPosition;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatutils.HowTurn;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Moving;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import org.w3c.dom.ls.LSOutput;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Cette classe a pour objectif de décrire un bateau, le bateau pourra bouger mais aussi faire plusieurs actions
 * en fonction de l'emplacement des entity
 *
 * @author williamdandrea, Alexandre Arcil
 * @author CLODONG Yann
 * TODO : 28/01/2020 : essayer de revoir le constructeur pour parser des json node plutot que chaques field un par un.
 */
public class Boat {

    private final int life;
    private final Transform transform;
    private final String name;
    private final Deck deck;
    private final BoatEntity[] entities;
    private final Shape shape;
    private List<Action> actionsToDo;

    @JsonCreator
    public Boat(@JsonProperty("life") int life, @JsonProperty("position") Transform position,
                @JsonProperty("name") String name, @JsonProperty("deck") Deck deck,
                @JsonProperty("entities") BoatEntity[] entities, @JsonProperty("shape") Shape shape) {
        this.life = life;
        this.transform = position;
        this.name = name;
        this.deck = deck;
        this.entities = entities;
        this.shape = shape;
        this.actionsToDo = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public int getLife() { return life; }
    public Transform getTransform() { return transform; }
    public Deck getDeck() { return deck; }
    public BoatEntity[] getEntities() { return entities; }

    public Shape getShape() {
        return shape;
    }


    /**
     *Gauche  Droite  Rotation
     * 0	    0	    0
     * 0	    1	    PI/4
     * 0	    2	    PI/2
     * 1	    0	    -PI/4
     * 1	    1	    0
     * 1	    2	    PI/4
     * 2	    0	    -PI/2
     * 2	    1	    -PI/4
     * 2	    2	    0
     *
     * Strategie :
     * - Regarder ou sont les marins
     * -
     * @param finaleOrientationOfTheBoat
     */
    void turnBoat(double finaleOrientationOfTheBoat, Marin sailors[]) {

        // We retrieve the oars (and their positions) who are positioned on the Boat
        List<BoatEntity> listOfOars = Arrays.stream(entities).filter(boatEntity -> boatEntity.type.equals(BoatEntities.OAR) ).collect(Collectors.toList());
        List<BoatEntity> listOfOarsAtLeft = listOfOars.stream().filter(boatEntity -> boatEntity.y == 0).collect(Collectors.toList());
        List<BoatEntity> listOfOarsAtRight = listOfOars.stream().filter(boatEntity -> boatEntity.y == deck.getWidth()-1).collect(Collectors.toList());
        int numberOfOars = listOfOars.size();
        int numberOfLeftOars = listOfOarsAtLeft.size();
        int numberOfRightOars = listOfOarsAtRight.size();

        // We calculate the angle between the actual orientation of the boat and the wanted orientation
        double actualOrientationOfTheBoat = transform.getOrientation();
        double differenceOfAngle = finaleOrientationOfTheBoat - actualOrientationOfTheBoat;

        // We watch if we have sailors positioned on the oar
        List<Marin> sailorsOnOarAtRight = new ArrayList<>();
        List<Marin> sailorsOnOarAtLeft = new ArrayList<>();

        for (Marin sailor: sailors) {
            for(BoatEntity rightEntity : listOfOarsAtRight) {
                if (rightEntity.x == sailor.getX() && rightEntity.y == sailor.getY()) {
                    sailorsOnOarAtRight.add(sailor);
                }
            }
            for(BoatEntity leftEntity : listOfOarsAtLeft) {
                if (leftEntity.x == sailor.getX() && leftEntity.y == sailor.getY()) {
                    sailorsOnOarAtLeft.add(sailor);
                }
            }
        }


        // We check if we have a number pair or impair of oar, if it is impair, we have one more possible angle
        int numberOfAnglesPossibles  = (numberOfOars % 2 != 0) ? (numberOfOars / 2) + 1: (numberOfOars / 2);

        // This map represent the angle (Double) we have if we have (Integer - babord) oar left and (Integer - tribort) oar right (of the boat)
        // First integer : number of oar at babord (left)
        // Second integer : number of oar at tribord (right)
        List<BabordTribordAngle> possibleAngles = permutationsOfAngle(numberOfAnglesPossibles, numberOfOars);
        //System.out.println(possibleAngles.toString());



        // We try to find the good angles in the possibleAngle list

        double differenceWeAccept = 0.0001;
        HowTurn actionsForTurning = findTheGoodAngle(differenceOfAngle, possibleAngles, differenceWeAccept);


        //System.out.println(differenceOfAngle);

        // Now we watch if we have the good number of oar from each side

        if (actionsForTurning.getCantDoAngle() >= 0 - differenceWeAccept && actionsForTurning.getCantDoAngle() <= 0 + differenceWeAccept) {
            // do what is write in the actionsForTurning.getCanDoAngle

            for (BabordTribordAngle actualAngle: actionsForTurning.getCanDoAngle()) {
                generateActionsForRotation(actualAngle, possibleAngles, sailors, numberOfLeftOars, numberOfRightOars, sailorsOnOarAtRight, sailorsOnOarAtLeft, differenceWeAccept);
            }

        } else {
            // do what is write in the actionsForTurning.getCanDoAngle and calculate the best trajectory
        }

    }


    // ==================================== Methods For turnBoat ==================================== //


    private void generateActionsForRotation(BabordTribordAngle angle, List<BabordTribordAngle> possibleAngles , Marin sailors[], int numberOfBabordOar, int numberOfTribordOar, List<Marin> sailorsOnOarAtTribord, List<Marin> sailorsOnOarAtBabord, double differenceWeAccept) {

        double wantedAngle = angle.getAngle();
        System.out.println("Nous entrons dans la fonction generateAction et nous voulons un angle de : " + wantedAngle);


        /** Dans un premier temps, nous allons chercher le nombre de rame qu'il nous faut a babord et a tribord pour
         *  réaliser l'angle voulu
         */

        List<BabordTribordAngle> idealsRepartitionOfOars = possibleAngles.stream().filter(eachAngle ->  eachAngle.getAngle() - differenceWeAccept <= wantedAngle && eachAngle.getAngle() + differenceWeAccept >= wantedAngle ).collect(Collectors.toList());

        // Il est évident que moins il y a de rame, plus il sera facile de realiser l'objectif, nous allons donc filtrer avec
        // pour avoir le plus petit total de rames et le mettre dans un objet a part entiere

        BabordTribordAngle goal = idealsRepartitionOfOars.stream().findFirst().get();

        /**
         * Maintenant que nous avons notre objectif de repartition, il nous faut regarder sur le bateau si nous avons
         * le bon nombre de rames du bon cote
         */


        if (numberOfBabordOar >= goal.getBabord() && numberOfTribordOar >= goal.getTribord()) {

            /**
             * Si nous avons le bon nombre de rame, il est nécessaire de regarder si nous avons le bon nombre de marins
             * de chaques côtés
             */

            if (sailorsOnOarAtBabord.size() >= goal.getBabord() && sailorsOnOarAtTribord.size() >= goal.getTribord()) {
                /**
                 * Si nous avons suffisament a tribord et a babord, il nous suffit de ramer sur les bonnes rames
                 */
                System.out.println("Nous avons suffisament de marins de chaque cote, RAMONS !");


            } else {
                /**
                 * Mais si nous avons pas suffisament a babord ou tribord, il nous faut bouger un marin
                 */

                if (sailorsOnOarAtBabord.size() >= goal.getBabord()) {
                    // Si nous avons suffisament a babord, il nous suffit de ramer

                } else {
                    // Mais sinon, nous devons bouger un marin
                    System.out.println("Babord toute !");

                    moveSailorsAtBabord(Arrays.asList(sailors.clone()), goal.getBabord(), goal.getTribord(), sailorsOnOarAtTribord, sailorsOnOarAtBabord);
                }



                if (sailorsOnOarAtTribord.size() >= goal.getTribord()) {
                    // Si nous avons suffisament a tribord, il nous suffit de ramer

                } else {
                    // Mais sinon, nous devons bouger un marin
                    System.out.println("Tribord toute !");

                    moveSailorsAtTribord();
                }

            }
        }
    }

    /**
     * Nous devons récolter des marins qui se situent au centre du bateau ou a tribord et les déplacer a babord
     */
    public void moveSailorsAtBabord(List<Marin> sailors, int goalBabord, int goalTribord, List<Marin> sailorsOnOarAtTribord, List<Marin> sailorsOnOarAtBabord) {

        /**
         * Cherchons d'abord un marin qui ne se situe pas sur une rame (c'est a dire qu'il se situe sur une case vide)
         * ensuite un marin qui ne se situe pas a babord : Un marin qui ne se situe pas a babord à comme
         * coordonnées : (toute la hauteur du bateau  ;  [1 : +inf])
         */

        System.out.println("Nous avons actuellement " + sailorsOnOarAtBabord.size() + " marins a babors et " + sailorsOnOarAtTribord.size() + " marins a tribord");
        System.out.println("Nous allons essayer de placer " + goalBabord + " marins a babord et " + goalTribord + " marins a tribord");


        List<Point> emplacementWhereAnyBoatEntities = parcelsWhereAnyEntitiesArePresent();

        // Nous allons essayer de voir si nous pouvons trouver un marin sur cette emplacement





    }

    public void moveSailorsAtTribord() {

    }

    /**
     * Cette fonction va retourner une liste avec toutes les cases du bateau qui ne possede aucun canon (plus generalement, aucune entités
     */
    public List<Point> parcelsWhereAnyEntitiesArePresent() {

        Arrays.stream(entities).forEach(enti -> System.out.println(enti.toString()));
        List<Point> finalList = new ArrayList<>();

        // Grace aux deux boucles suivantes, nous parcourons les parcelles du bateau
        for (int x = 0; x < deck.getLength(); x++) {
            for (int y = 0; y < deck.getWidth(); y++) {

                boolean haveEntity = false;

                // Il nous faut maintenant parcourir toutes les entites pour voir si il y a une entitée a l'emplacement x : y
                for (BoatEntity entity : entities) {

                    // Si il n'y pas d'entitées en cette parcel, on peut l'ajouter a la liste final
                    if (entity.y == y && entity.x == x) {
                        haveEntity = true;
                        break;
                    }
                }

                if (haveEntity == false) {
                    finalList.add(new Point(x,y));
                }
            }
        }

        return finalList;
    }







    private boolean marinIsPresentOnOneParcel(List<Marin> marins, int x, int y) {
        for ( Marin marin : marins) {
            if (marin.getX() == x && marin.getY() == y) {
                return true;
            }
        }
        return false;
    }



    /**
     * Cette méthode a pour objectif de regarder si, dans le boat actuelle, si on a le nombre souhaité de marins a babord
     * @param wantedNumberOfSailorsAtBabord
     * @param sailors
     * @return true si on a le bon nombre a babord, false sinon
     */
    private boolean watchIfWeHaveTheGoodNumberOfSailorsAtBabord(int wantedNumberOfSailorsAtBabord, List<Marin> sailors) {

        List<BoatEntity> listOfOars = Arrays.stream(entities).filter(boatEntity -> boatEntity.type.equals(BoatEntities.OAR) ).collect(Collectors.toList());
        List<BoatEntity> listOfOarsAtLeft = listOfOars.stream().filter(boatEntity -> boatEntity.y == 0).collect(Collectors.toList());
        // We watch if we have sailors positioned on the oar
        List<Marin> sailorsOnOarAtLeft = new ArrayList<>();

        for (Marin sailor: sailors) {
            for(BoatEntity leftEntity : listOfOarsAtLeft) {
                if (leftEntity.x == sailor.getX() && leftEntity.y == sailor.getY()) {
                    sailorsOnOarAtLeft.add(sailor);
                }
            }
        }

        return sailorsOnOarAtLeft.size() >= wantedNumberOfSailorsAtBabord;
    }

    /**
     * Cette méthode a pour objectif de regarder si, dans le boat actuelle, si on a le nombre souhaité de marins a tribord
     * @param wantedNumberOfSailorsAtTribord
     * @param sailors
     * @return true si on a le bon nombre a tribord, false sinon
     */
    private boolean watchIfWeHaveTheGoodNumberOfSailorsAtTribord(int wantedNumberOfSailorsAtTribord, List<Marin> sailors) {

        List<BoatEntity> listOfOars = Arrays.stream(entities).filter(boatEntity -> boatEntity.type.equals(BoatEntities.OAR) ).collect(Collectors.toList());
        List<BoatEntity> listOfOarsAtRight = listOfOars.stream().filter(boatEntity -> boatEntity.y == deck.getWidth()-1).collect(Collectors.toList());
        // We watch if we have sailors positioned on the oar
        List<Marin> sailorsOnOarAtRight = new ArrayList<>();

        for (Marin sailor: sailors) {
            for(BoatEntity rightEntity : listOfOarsAtRight) {
                if (rightEntity.x == sailor.getX() && rightEntity.y == sailor.getY()) {
                    sailorsOnOarAtRight.add(sailor);
                }
            }
        }

        return sailorsOnOarAtRight.size() >= wantedNumberOfSailorsAtTribord;



    }








    /**
     * This list represent the angle (Double) we have if we have (Integer - babord) oar left and (Integer - tribort) oar right (of the boat)
     * First integer : number of oar at babord (left)
     * Second integer : number of oar at tribord (right)
     * @param numberOfAnglesPossibles
     * @param numberOfOars
     * @return
     */
    private List<BabordTribordAngle> permutationsOfAngle(int numberOfAnglesPossibles, int numberOfOars) {

        List<BabordTribordAngle> possibleAngles = new ArrayList<>();

        for (int bab = 0; bab <= numberOfAnglesPossibles; bab++) {
            for (int tri = 0; tri <= numberOfAnglesPossibles ; tri++) {
                if (bab + tri <= numberOfOars) {
                    //PI * <diff rame tribord - rame bâbord>/<nombre total de rames>
                    int diff = tri - bab;

                    double angle = (diff == 0) ? 0 : Math.PI * diff / numberOfOars ;
                    possibleAngles.add(new BabordTribordAngle(bab,tri,angle));

                    //System.out.println(bab + " " + tri + " " + diff + " " + angle);
                }
            }
        }
        return possibleAngles;
    }


    /**
     * This method will find how we need to move the boat, for example, we need to do 3 PI/2 rotation and one rotation of
     * PI/4. But the boat with 6 oar can do PI/2 rotation but not PI/4 rotation. So, if we have an angle that we can
     * do, so howTurn.cantDoAngle == 0
     * @param differenceOfAngle what is the wanted direction of the boat
     * @param possibleAngles list of posibles angles we can do (this value depend of how many oar or the shape of the boat)
     * @param differenceWeAccept tolerence with the double operation
     * @return HowTurn with how many rotation of PI/2 we need to do AND the angle we can't do with PI/2
     */
    private HowTurn findTheGoodAngle(double differenceOfAngle, List<BabordTribordAngle> possibleAngles, double differenceWeAccept) {

        List<BabordTribordAngle> rotActionToDoForTurn = new ArrayList<>();

        // Si angle négatif
        if (differenceOfAngle < 0) {

            while (differenceOfAngle <= -Math.PI/2) {
                if (differenceOfAngle <= -(Math.PI / 2)) {

                    for (BabordTribordAngle babTriAng : possibleAngles) {

                        if (babTriAng.getAngle() == -(Math.PI / 2)) {
                            rotActionToDoForTurn.add(babTriAng);
                        }

                    }
                    differenceOfAngle = differenceOfAngle + (Math.PI / 2);
                }
            }

        } else if (differenceOfAngle > 0){
            // Cas ou angle > O

            while (differenceOfAngle >= Math.PI/2) {

                if (differenceOfAngle >= (Math.PI / 2)) {

                    for (BabordTribordAngle babTriAng : possibleAngles) {
                        if (babTriAng.getAngle() == (Math.PI / 2)) {
                            rotActionToDoForTurn.add(babTriAng);
                        }
                    }
                    differenceOfAngle = differenceOfAngle - (Math.PI / 2);

                }

            }
        }

        //System.out.println("Hello");
        for (BabordTribordAngle babTriAng : possibleAngles) {

            double min = differenceOfAngle - differenceWeAccept;
            double max = differenceOfAngle + differenceWeAccept;

            if ( babTriAng.getAngle() >= min && babTriAng.getAngle() <= max) {
                // TODO : we can optimize here for check what is the good element to do
                rotActionToDoForTurn.add(babTriAng);
                //System.out.println(babTriAng);
                differenceOfAngle = differenceOfAngle - babTriAng.getAngle();
                break;
            }
        }

        if (differenceOfAngle >= 0 - differenceWeAccept && differenceOfAngle <= 0 + differenceWeAccept) {
            differenceOfAngle = 0;
        }

        return new HowTurn(rotActionToDoForTurn, differenceOfAngle);
    }





    /**
     * This method move the sailor at a certain position, this method also verify if we can move the sailor at this
     * position. Template of the JSON output:
     * {
     *         "sailorId": 1,
     *         "type": "MOVING",
     *         "xdistance": 0,
     *         "ydistance": 0
     * }
     * @param sailor
     * @param xDistance
     * @param yDistance
     * @return
     */
    public Optional<String> moveSailorSomewhere(Marin sailor, int xDistance, int yDistance) {

        Deck boatDeck = deck;
        int deckY = boatDeck.getLength();   // Hauteur du bateau
        int deckX = boatDeck.getWidth();    // Largeur du bateau

        if (xDistance > deckX || yDistance > deckY) {
            return Optional.empty();
        }

        ObjectMapper objectMapper = new ObjectMapper();

        Moving moving = new Moving(sailor.getId() ,xDistance, yDistance);
        String stringMoving = null;

        try {
            stringMoving = objectMapper.writeValueAsString(moving);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (stringMoving != null) {
            return Optional.of(stringMoving);
        }
        return Optional.empty();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Boat)) return false;
        var castedObj = (Boat)obj;
        return castedObj.name == name;
    }











}
