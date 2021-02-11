package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatutils.*;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatutils.BabordTribordAngle;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatutils.HowTurn;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Moving;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Oar;
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
 */
public class Boat {

    private int life;
    private Transform transform;
    private final String name;
    private final Deck deck;
    private BoatEntity[] entities;
    private final Shape shape;
    private List<Action> actionsToDo;

    @JsonIgnore
    private Captain captain;
    @JsonIgnore
    private Foreman foreman;
    private List<Marin> sailors;

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

        this.captain = new Captain(this);
        this.foreman = new Foreman(this);
    }

    public String getName() {
        return name;
    }
    public int getLife() { return life; }
    public Transform getTransform() { return transform; }
    public Deck getDeck() { return deck; }
    public BoatEntity[] getEntities() { return entities; }

    public List<Action> getActionsToDo() {
        return actionsToDo;
    }

    public Shape getShape() {
        return shape;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public void setEntities(BoatEntity[] entities) {
        this.entities = entities;
    }

    public void setSailors(List<Marin> sailors) {
        this.sailors = sailors;
        this.foreman.setSailors(sailors);
    }

    @JsonIgnore
    public List<Marin> getSailors() {
        if(sailors == null) return new ArrayList<>();
        return List.copyOf(sailors);
    }

    @JsonIgnore
    public Foreman getForeman() {
        return foreman;
    }

    public void setActionsToDo(List<Action> actionsToDo) {
        this.actionsToDo = actionsToDo;
    }

    public void moveBoatToAPoint(Transform transformFinal) {


        //System.out.println("================================================================================================");
        // On verifie si on est deja dans le checkpoint



        Point pointFinal = transformFinal.getPoint();
        double angleWeWantToTurn = transform.getAngleToSee(pointFinal);
        System.out.println("=============== : " + angleWeWantToTurn);
        turnBoat(angleWeWantToTurn);

    }




    /**
     *
     * Cette methode permet de créer une rotation si le bateau a besoin de tourner, ou de ramer tout droit
     * Cette methode genere les actions BougerMarin mais aussi RAMER
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
    void turnBoat(double finaleOrientationOfTheBoat) {

        // We retrieve the oars (and their positions) who are positioned on the Boat



        List<BoatEntity> listOfOars = Arrays.stream(entities).filter(boatEntity -> boatEntity.type.equals(BoatEntities.OAR) ).collect(Collectors.toList());
        int numberOfOars = listOfOars.size();


        // We calculate the angle between the actual orientation of the boat and the wanted orientation
        double actualOrientationOfTheBoat = transform.getOrientation();
        // FALSE : double differenceOfAngle = finaleOrientationOfTheBoat - actualOrientationOfTheBot;
        double differenceOfAngle = finaleOrientationOfTheBoat;




        // We check if we have a number pair or impair of oar, if it is impair, we have one more possible angle
        int numberOfAnglesPossibles  = (numberOfOars % 2 != 0) ? (numberOfOars / 2) + 1: (numberOfOars / 2);

        // This map represent the angle (Double) we have if we have (Integer - babord) oar left and (Integer - tribort) oar right (of the boat)
        // First integer : number of oar at babord (left)
        // Second integer : number of oar at tribord (right)
        List<BabordTribordAngle> possibleAngles = permutationsOfAngle(numberOfAnglesPossibles, numberOfOars);
        System.out.println(possibleAngles.toString());



        // We try to find the good angles in the possibleAngle list

        double differenceWeAccept = 0.0001;
        double smallestEcartPossible = (Math.PI * 1 / numberOfOars);
        HowTurn actionsForTurning = findTheGoodAngle(differenceOfAngle, possibleAngles, differenceWeAccept);


        System.out.println(differenceOfAngle);


        if (differenceOfAngle <= smallestEcartPossible /2 && differenceOfAngle >= -smallestEcartPossible/2) {
            //On va tout droit
            generateActionForMoveInLine(differenceWeAccept, numberOfOars, numberOfAnglesPossibles);

        } else {
            // Now we watch if we have the good number of oar from each side
            if (differenceOfAngle >= Math.PI/2 - differenceWeAccept) {
                generateActionsForRotation(new BabordTribordAngle(0,0,Math.PI/2), possibleAngles, differenceWeAccept);
            } else {

                if (differenceOfAngle <= -Math.PI/2 + differenceWeAccept ) {
                    generateActionsForRotation(new BabordTribordAngle(0,0,-Math.PI/2), possibleAngles, differenceWeAccept);

                } else {
                    if (actionsForTurning.getCantDoAngle() >= 0 - differenceWeAccept && actionsForTurning.getCantDoAngle() <= 0 + differenceWeAccept) {
                        // do what is write in the actionsForTurning.getCanDoAngle

                        //for (BabordTribordAngle actualAngle: actionsForTurning.getCanDoAngle()) {
                        //generateActionsForRotation(actualAngle, possibleAngles, differenceWeAccept);
                        //}
                        generateActionsForRotation(actionsForTurning.getCanDoAngle().stream().findFirst().get(), possibleAngles, differenceWeAccept);

                    } else {




                        // Si on a pas le bon angle, il est donc nécessaire de tourner vers l'angle le plus proche et ensuite de
                        // calculer une trajectoire
                        System.out.println("Pas le bon angle MOMO");

                        // Nous allons dans un premier temps essayer de trouver l'angle le plus proche (inferieur)


                        System.out.println("smallest : " + smallestEcartPossible);
                        System.out.println("What we want : " + differenceOfAngle);
                        BabordTribordAngle dispositionWePrivilege = possibleAngles.stream().filter(angle -> angle.getTribord() == angle.getBabord()).findAny().get();;


                        for (BabordTribordAngle actualAngle: possibleAngles) {
                            System.out.println(actualAngle.toString());


                            // Pour angleWeWant Positif : Si actualAngle appartient a (angleWeWant - (smallestEcartPossible / 2)) On peut prendre en compte ce babordTribordAngle
                            // Pour angleWeWAnt negatif : si actualAngle appartient a (angleWeWant + (smallestEcartPossible / 2)) On peut ...

                            if (differenceOfAngle > 0) {
                                if (differenceOfAngle - actualAngle.getAngle() <= smallestEcartPossible) {

                                    dispositionWePrivilege = actualAngle;

                                }
                            }

                            if (differenceOfAngle < 0) {
                                if (differenceOfAngle - actualAngle.getAngle() >= -smallestEcartPossible) {

                                    dispositionWePrivilege = actualAngle;

                                }
                            }
                        }

                        generateActionsForRotation(dispositionWePrivilege, possibleAngles, differenceWeAccept);
                        System.out.println(dispositionWePrivilege.toString());
                        System.out.println(actionsToDo);

                    }
                }

            }


        }

        //System.out.println(actionsToDo);


    }


    // ==================================== Methods For turnBoat ==================================== //


    private void generateActionForMoveInLine(double differenceWeAccept, int numberOfOars, int numberOfAnglesPossibles) {


        List<BabordTribordAngle> possibleAngles = new ArrayList<>();

        for (int bab = 1; bab <= numberOfAnglesPossibles; bab++) {
            for (int tri = 1; tri <= numberOfAnglesPossibles ; tri++) {
                if (bab + tri <= numberOfOars) {
                    //PI * <diff rame tribord - rame bâbord>/<nombre total de rames>
                    int diff = tri - bab;

                    double angle = (diff == 0) ? 0 : Math.PI * diff / numberOfOars ;
                    possibleAngles.add(new BabordTribordAngle(bab,tri,angle));

                    System.out.println(bab + " " + tri + " " + diff + " " + angle);
                }
            }
        }

        generateActionsForRotation(new BabordTribordAngle(0,0,0), possibleAngles, differenceWeAccept );

    }


    private void generateActionsForRotation(BabordTribordAngle angle, List<BabordTribordAngle> possibleAngles, double differenceWeAccept) {


        double wantedAngle = angle.getAngle();

        /**
         * Dans un premier temps, nous generons toutes les variables qui pourrons nous etre utile par la suite
         */
        List<BoatEntity> listOfOars = Arrays.stream(entities).filter(boatEntity -> boatEntity.type.equals(BoatEntities.OAR) ).collect(Collectors.toList());
        List<BoatEntity> listOfOarsAtLeft = listOfOars.stream().filter(boatEntity -> boatEntity.y == 0).collect(Collectors.toList());
        List<BoatEntity> listOfOarsAtRight = listOfOars.stream().filter(boatEntity -> boatEntity.y == deck.getWidth()-1).collect(Collectors.toList());
        int numberOfBabordOar = listOfOarsAtLeft.size();
        int numberOfTribordOar = listOfOarsAtRight.size();

        List<Marin> sailorsOnOarAtTribord = new ArrayList<>();
        List<Marin> sailorsOnOarAtBabord = new ArrayList<>();


        for (Marin sailor: sailors) {
            for(BoatEntity rightEntity : listOfOarsAtRight) {
                if (rightEntity.x == sailor.getX() && rightEntity.y == sailor.getY()) {
                    sailorsOnOarAtTribord.add(sailor);
                }
            }
            for(BoatEntity leftEntity : listOfOarsAtLeft) {
                if (leftEntity.x == sailor.getX() && leftEntity.y == sailor.getY()) {
                    sailorsOnOarAtBabord.add(sailor);
                }
            }
        }


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
        System.out.println(goal.toString());


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
                generateOarAction(goal.getBabord(), goal.getTribord(), sailorsOnOarAtBabord, sailorsOnOarAtTribord);


            } else {
                /**
                 * Mais si nous avons pas suffisament a babord ou tribord, il nous faut bouger un marin
                 */

                if (sailorsOnOarAtBabord.size() < goal.getBabord()) {
                    // Mais sinon, nous devons bouger un marin
                    System.out.println("Babord toute !");

                    moveSailorsAtBabord(goal.getBabord(), goal.getTribord(), sailorsOnOarAtTribord, sailorsOnOarAtBabord);
                } else {
                    // Si nous avons suffisament a babord, il nous suffit de ramer
                }


                if (sailorsOnOarAtTribord.size() < goal.getTribord()) {
                    // Mais sinon, nous devons bouger un marin
                    System.out.println("Tribord toute !");

                    moveSailorsAtTribord(goal.getBabord(), goal.getTribord(), sailorsOnOarAtTribord, sailorsOnOarAtBabord);
                } else {
                    // Si nous avons suffisament a tribord, il nous suffit de ramer
                }

                generateOarAction(goal.getBabord(), goal.getTribord(), sailorsOnOarAtBabord(), sailorsOnOarAtTribord());
                //System.out.println(actionsToDo);

            }
        }
    }


    List<Marin> sailorsOnOarAtBabord() {

        List<BoatEntity> listOfOars = Arrays.stream(entities).filter(boatEntity -> boatEntity.type.equals(BoatEntities.OAR) ).collect(Collectors.toList());
        List<BoatEntity> listOfOarsAtLeft = listOfOars.stream().filter(boatEntity -> boatEntity.y == 0).collect(Collectors.toList());
        List<Marin> sailorsOnOarAtBabord = new ArrayList<>();

        for (Marin sailor: sailors) {
            for(BoatEntity leftEntity : listOfOarsAtLeft) {
                if (leftEntity.x == sailor.getX() && leftEntity.y == sailor.getY()) {
                    sailorsOnOarAtBabord.add(sailor);
                }
            }
        }

        return sailorsOnOarAtBabord;
    }

    List<Marin> sailorsOnOarAtTribord() {

        List<BoatEntity> listOfOars = Arrays.stream(entities).filter(boatEntity -> boatEntity.type.equals(BoatEntities.OAR) ).collect(Collectors.toList());
        List<BoatEntity> listOfOarsAtTribord = listOfOars.stream().filter(boatEntity -> boatEntity.y == deck.getWidth()-1).collect(Collectors.toList());
        List<Marin> sailorsOnOarAtTribord = new ArrayList<>();

        for (Marin sailor: sailors) {
            for(BoatEntity rightEntity : listOfOarsAtTribord) {
                if (rightEntity.x == sailor.getX() && rightEntity.y == sailor.getY()) {
                    sailorsOnOarAtTribord.add(sailor);
                }
            }
        }
        return sailorsOnOarAtTribord;
    }





    /**
     * Cette methode a pour objectif de créer l'action RAMER pour :
     * @param numberOfOarWeWantToActivateAtBabord rames a babord
     * @param numberOfOarWeWantToActivateAtTribord rames a tribord
     */
    private void generateOarAction(int numberOfOarWeWantToActivateAtBabord, int numberOfOarWeWantToActivateAtTribord, List<Marin> sailorsOnOarAtBabord, List<Marin> sailorsOnOarAtTribord) {

        System.out.println(sailorsOnOarAtBabord + " : " + sailorsOnOarAtTribord);
        System.out.println(numberOfOarWeWantToActivateAtBabord + " : " + numberOfOarWeWantToActivateAtTribord);

        for (int i = 0; i < numberOfOarWeWantToActivateAtBabord; i++) {
            actionsToDo.add(new Oar(sailorsOnOarAtBabord.get(i).getId()));
        }
        for (int i = 0; i < numberOfOarWeWantToActivateAtTribord; i++) {
            actionsToDo.add(new Oar(sailorsOnOarAtTribord.get(i).getId()));
        }
        System.out.println(actionsToDo.toString());
    }

    /**
     * Nous devons récolter des marins qui se situent au centre du bateau ou a tribord et les déplacer a babord
     */
    public void moveSailorsAtBabord(int goalBabord, int goalTribord, List<Marin> sailorsOnOarAtTribord, List<Marin> sailorsOnOarAtBabord) {


        /**
         * Nous allons d'abord rechercher les rames inutilisées a babord (c'est-a-dire une case a babord ou nous avons
         * une rame mais pas de marins)
         */


        List<Point> freeOarPlaceAtBabord = freeOarPlaceAtBabord(sailors);

        /**
         * Cherchons d'abord un marin qui ne se situe pas sur une rame (c'est a dire qu'il se situe sur une case vide)
         * ensuite un marin qui ne se situe pas a babord : Un marin qui ne se situe pas a babord à comme
         * coordonnées : (toute la hauteur du bateau  ;  [1 : +inf])
         */

        System.out.println("========================================================");
        System.out.println("Nous avons actuellement " + sailorsOnOarAtBabord.size() + " marins a babors et " + sailorsOnOarAtTribord.size() + " marins a tribord");
        System.out.println("Nous allons essayer de placer " + goalBabord + " marins a babord et " + goalTribord + " marins a tribord");
        System.out.println("========================================================");

        List<Point> emplacementWhereAnyBoatEntities = parcelsWhereAnyEntitiesArePresent();
        System.out.println("Emplacement ou il n'y a aucune rame : " + emplacementWhereAnyBoatEntities.toString());
        System.out.println("Place OAR inutilisées a babord: " + freeOarPlaceAtBabord);

        // Nous allons essayer de trouver un marin qui ne se situe pas sur une rame (c'est a dire qu'il se situe sur une case vide)
        List<Marin> sailorsWhoAreSituatedOnAnyBotEntities = new ArrayList<>();

        for (Point point : emplacementWhereAnyBoatEntities) {
            if (marinIsPresentOnOneParcel((int) point.getX(), (int) point.getY())) {
                Optional<Marin> marin = findTheSailorOnTheBoat((int) point.getX(), (int) point.getY());
                if (marin.isPresent()) {
                    sailorsWhoAreSituatedOnAnyBotEntities.add(marin.get());
                }

            }
        }

        System.out.println("Nous avons un marin potentiel a l'emplacement (situé sur une case vide) : " + sailorsWhoAreSituatedOnAnyBotEntities.toString());

        // Nous allons chercher les marins qui se situent sur une rame a tribord
        // => sailorsOnOarAtTribord

        System.out.println("Nous avons un marin potentiel a l'emplacement (situé sur une rame a tribord) : " + sailorsOnOarAtTribord.toString());



        /**
         * Ce que nous devons faire maintenant, c'est prendre les marins se situant sur une case vide et les bouger sur
         * une rame a babord. Si nous avons pas suffisament de marins libres, il nous est nécessaire de bouger un marin
         * qui se situe sur une rame a tribord
         */

        // Nous devons d'abord connaitre le nombre de marins que nous souhaitons a babord :
        int wantedSailorAtBabord = goalBabord - sailorsOnOarAtBabord.size();

        // Verifions si nous en avons assez dans la liste des marins qui ne se trouve sur aucune BoatEntities
        if (wantedSailorAtBabord <= sailorsWhoAreSituatedOnAnyBotEntities.size()) {
            // Nous avons ici suffisament de marins sur des places libres pour entammer une rotation
            System.out.println("Suffisament de marins dans des places libres, bougeons les marins !");
            moveListOfSailors(sailorsWhoAreSituatedOnAnyBotEntities, freeOarPlaceAtBabord);

        } else {
            // Nous sommes obliger de piocher encore quelques marins dans la liste des marins a tribord
            System.out.println("Pas assez de marins dans des places libres, enlevons des marins qui se situent sur des rames a tribord ...");
            List<Point> residualsPoints = moveListOfSailors(sailorsWhoAreSituatedOnAnyBotEntities, freeOarPlaceAtBabord);
            moveListOfSailors(sailorsOnOarAtTribord, residualsPoints);
        }

        System.out.println(actionsToDo.toString());
    }

    public void moveSailorsAtTribord(int goalBabord, int goalTribord, List<Marin> sailorsOnOarAtTribord, List<Marin> sailorsOnOarAtBabord) {

        /**
         * Nous allons d'abord rechercher les rames inutilisées a babord (c'est-a-dire une case a babord ou nous avons
         * une rame mais pas de marins)
         */


        List<Point> freeOarPlaceAtTribord = freeOarPlaceAtTribord();

        /**
         * Cherchons d'abord un marin qui ne se situe pas sur une rame (c'est a dire qu'il se situe sur une case vide)
         * ensuite un marin qui ne se situe pas a babord : Un marin qui ne se situe pas a babord à comme
         * coordonnées : (toute la hauteur du bateau  ;  [1 : +inf])
         */

        System.out.println("========================================================");
        System.out.println("Nous avons actuellement " + sailorsOnOarAtBabord.size() + " marins a babors et " + sailorsOnOarAtTribord.size() + " marins a tribord");
        System.out.println("Nous allons essayer de placer " + goalBabord + " marins a babord et " + goalTribord + " marins a tribord");
        System.out.println("========================================================");

        List<Point> emplacementWhereAnyBoatEntities = parcelsWhereAnyEntitiesArePresent();
        System.out.println("Emplacement ou il n'y a aucune rame : " + emplacementWhereAnyBoatEntities.toString());
        System.out.println("Place OAR inutilisées a babord: " + freeOarPlaceAtTribord);

        // Nous allons essayer de trouver un marin qui ne se situe pas sur une rame (c'est a dire qu'il se situe sur une case vide)
        List<Marin> sailorsWhoAreSituatedOnAnyBotEntities = new ArrayList<>();

        for (Point point : emplacementWhereAnyBoatEntities) {
            if (marinIsPresentOnOneParcel((int) point.getX(), (int) point.getY())) {
                Optional<Marin> marin = findTheSailorOnTheBoat((int) point.getX(), (int) point.getY());
                if (marin.isPresent()) {
                    sailorsWhoAreSituatedOnAnyBotEntities.add(marin.get());
                }

            }
        }


        System.out.println("Nous avons un marin potentiel a l'emplacement (situé sur une case vide) : " + sailorsWhoAreSituatedOnAnyBotEntities.toString());

        // Nous allons chercher les marins qui se situent sur une rame a tribord
        // => sailorsOnOarAtBabord

        System.out.println("Nous avons un marin potentiel a l'emplacement (situé sur une rame a babord) : " + sailorsOnOarAtBabord.toString());

        /**
         * Ce que nous devons faire maintenant, c'est prendre les marins se situant sur une case vide et les bouger sur
         * une rame a babord. Si nous avons pas suffisament de marins libres, il nous est nécessaire de bouger un marin
         * qui se situe sur une rame a tribord
         */

        // Nous devons d'abord connaitre le nombre de marins que nous souhaitons a babord :
        int wantedSailorAtTribord = goalTribord - sailorsOnOarAtTribord.size();

        // Verifions si nous en avons assez dans la liste des marins qui ne se trouve sur aucune BoatEntities
        if (wantedSailorAtTribord <= sailorsWhoAreSituatedOnAnyBotEntities.size()) {
            // Nous avons ici suffisament de marins sur des places libres pour entammer une rotation
            System.out.println("Suffisament de marins dans des places libres, bougeons les marins !");
            moveListOfSailors(sailorsWhoAreSituatedOnAnyBotEntities, freeOarPlaceAtTribord);

        } else {
            // Nous sommes obliger de piocher encore quelques marins dans la liste des marins a tribord
            System.out.println("Pas assez de marins dans des places libres, enlevons des marins qui se situent sur des rames a tribord ...");
            List<Point> residualsPoints = moveListOfSailors(sailorsWhoAreSituatedOnAnyBotEntities, freeOarPlaceAtTribord);
            moveListOfSailors(sailorsOnOarAtBabord, residualsPoints);
        }

        System.out.println(actionsToDo.toString());

    }



    /**
     * Cette méhode a pour but de faire bouger les marins présent dans la liste en input en destination des emplacements
     * fourni egalement en input
     * et genere des actions
     *
     * @return list de point restant (qui permettron a une autre fonction de deplacer les marins ici)
     */

    private List<Point> moveListOfSailors(List<Marin> sailorsWeWantToMove, List<Point> destinations) {

        int maxOfTheFor = 0;
        if (sailorsWeWantToMove.size() > destinations.size()) {
            maxOfTheFor = destinations.size();
        } else {
            maxOfTheFor = sailorsWeWantToMove.size();
        }
        int i = 0;
        for (; i < maxOfTheFor; i++) {

            Point destination = destinations.get(i);
            Marin marin = sailorsWeWantToMove.get(i);
            Point trajectoire = calculateTheTrajectoire(marin.getX(), marin.getY(), (int) destination.getX(), (int) destination.getY());
            Action action = new Moving(marin.getId(), (int) trajectoire.getX(), (int) trajectoire.getY());
            actionsToDo.add(action);

            marin.setX((int) destination.getX());
            marin.setY((int) destination.getY());

        }

        return destinations.subList(i, destinations.size());
    }

    /**
     * Cette méthode permet de trouver la trajectoire pour atteindre une destination. On a un point de depart, un
     * point d'arrivée, et cette methode nous dit combien de cases nous devons nous deplacer;
     */

    private Point calculateTheTrajectoire(int xStart, int yStart, int xFinal, int yFinal) {
        return new Point(xFinal - xStart, yFinal - yStart);
    }


    /**
     * Cette methode permet, a parti d'une coordonéee x et y, de trouver le marin qui se situe a cette position
     */
    private Optional<Marin> findTheSailorOnTheBoat(int x, int y) {

        for (Marin marin : sailors) {
            if (marin.getX() == x && marin.getY() == y) {
                return Optional.of(marin);
            }
        }
        return Optional.empty();

    }

    /**
     * Cette methode a pour objectif de trouver les places ou nous avons un canon mais aucun marin
     * @param sailors
     * @return
     */
    List<Point> freeOarPlaceAtBabord(List<Marin> sailors) {
        List<Point> finalList = new ArrayList<>();

        // On parcours toutes les rames (de babords) a la recherche d'un endroit ou nous n'avons pas de marin
        for (BoatEntity rame : entities) {
            if (rame.y == 0) {

                if (!marinIsPresentOnOneParcel(rame.x, rame.y)) {
                    finalList.add(new Point(rame.x, rame.y));
                }

            }
        }

        return finalList;
    }

    /**
     * Cette methode a pour objectif de trouver les places ou nous avons un canon mais aucun marin
     * @return
     */
    List<Point> freeOarPlaceAtTribord() {
        List<Point> finalList = new ArrayList<>();

        // On parcours toutes les rames (de babords) a la recherche d'un endroit ou nous n'avons pas de marin
        for (BoatEntity rame : entities) {
            if (rame.y == deck.getWidth()-1) {

                if (!marinIsPresentOnOneParcel(rame.x, rame.y)) {
                    finalList.add(new Point(rame.x, rame.y));
                }

            }
        }

        return finalList;
    }



    /**
     * Cette fonction va retourner une liste avec toutes les cases du bateau qui ne possede aucun canon (plus generalement, aucune entités
     */
    public List<Point> parcelsWhereAnyEntitiesArePresent() {

        //Arrays.stream(entities).forEach(enti -> System.out.println(enti.toString()));
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







    private boolean marinIsPresentOnOneParcel(int x, int y) {
        for ( Marin marin : sailors) {
            if (marin.getX() == x && marin.getY() == y) {
                return true;
            }
        }
        return false;
    }



    /**
     * Cette méthode a pour objectif de regarder si, dans le boat actuelle, si on a le nombre souhaité de marins a babord
     * @param wantedNumberOfSailorsAtBabord
     * @return true si on a le bon nombre a babord, false sinon
     */
    private boolean watchIfWeHaveTheGoodNumberOfSailorsAtBabord(int wantedNumberOfSailorsAtBabord) {

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
     * @return true si on a le bon nombre a tribord, false sinon
     */
    private boolean watchIfWeHaveTheGoodNumberOfSailorsAtTribord(int wantedNumberOfSailorsAtTribord) {

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

                    System.out.println(bab + " " + tri + " " + diff + " " + angle);
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
                System.out.println(babTriAng);
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
