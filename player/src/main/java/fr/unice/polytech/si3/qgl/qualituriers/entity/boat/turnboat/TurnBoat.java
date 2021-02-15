package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboat;


import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.BoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboat.turnboatutils.BabordTribordAngle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Moving;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Oar;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Cette classe a pour objectif générale de faire tourner le bateau d'un certain angle.
 *
 * Cette classe doit être appelé si l'on souhaite faire bouger le bateau d'un certain angle. Nous avons plusieurs solutions :
 * - soit nous pouvons tourner d'un angle exacte (un angle donné par la formule) et ainsi, l'algorithme va aller chercher la meilleur répartition de marins
 *
 * @author D'Andrea William
 * @version 2.0 - MAJ pour WEEK3
 * @date 16 février 2021
 */
public class TurnBoat {

    private static final double ECART_DOUBLE = 0.001;

    // Initial properties
    private final Boat boat;
    private final double finalOrientationBoat;
    private final List<Marin> sailors;
    private List<BoatEntity> boatEntities;
    private Transform positionBoat;

    private List<BoatEntity> listOfOarsAtBabord;
    private List<BoatEntity> listOfOarsAtTribord;
    private List<BoatEntity> listOfOarsAtBabordWithAnySailorsOnIt = new ArrayList<>();
    private List<BoatEntity> listOfOarsAtTribordWithAnySailorsOnIt = new ArrayList<>();

    private int numberOfOars;

    private List<BabordTribordAngle> possibleAngles;

    private final List<Marin> sailorsOnOar = new ArrayList<>();
    private final List<Marin> sailorsOnOarAtTribord = new ArrayList<>();
    private final List<Marin> sailorsOnOarAtBabord = new ArrayList<>();
    private List<Marin> sailorsOnAnyEntities = new ArrayList<>();

    private BabordTribordAngle finalDispositionOfOars;



    // Final properties
    private final List<Action> actionsToDo;

    /**
     * @param finalOrientationBoat l'orientation finale que le bateau doit avoir (pas besoin de faire de calculs au préalable)
     * @param boat : le bateau actuel
     * @param sailors est la liste des marins que l'on veut utiliser pour exécuter cette action
     */
    public TurnBoat(double finalOrientationBoat, Boat boat, List<Marin> sailors) {
        this.finalOrientationBoat = finalOrientationBoat;
        this.boat = boat;
        this.sailors = sailors;

        actionsToDo = new ArrayList<>();

        actualizeAll();

    }


    public List<Action> turnBoat() {



        // Intern properties
        double differenceOfAngle = generateDifferenceOfAngle(false);

        // Si l'angle est nul, alors nous avons pas besoin de tourner
        if (differenceOfAngle == 0.0) {
            return new ArrayList<>();
        }


        possibleAngles = generateListOfPossibleAngles(0);

        if (differenceOfAngle <= -Math.PI / 2) {
            finalDispositionOfOars = selectTheGoodAngle(-Math.PI / 2);
        } else if (differenceOfAngle >= Math.PI / 2) {
            finalDispositionOfOars = selectTheGoodAngle(Math.PI / 2);
        } else {
            finalDispositionOfOars = selectTheGoodAngle(differenceOfAngle);
        }




        oar(finalDispositionOfOars.getBabord(), finalDispositionOfOars.getTribord());



        return actionsToDo;
    }


    BabordTribordAngle selectTheGoodAngle(double angle) {
        // On les regeneres pour etre sur
        possibleAngles = generateListOfPossibleAngles(0);


        BabordTribordAngle finalRepartition = new BabordTribordAngle(0,0,0);
        double smallestEcart = Math.PI / numberOfOars;



        // condition si on est autour de 0
        if (smallestEcart/2 >= angle &&  -smallestEcart/2 <= angle) {

            Optional<BabordTribordAngle> finalRepartitionTemp = possibleAngles.stream().filter(eachAngle -> eachAngle.getTribord() == eachAngle.getBabord() && eachAngle.getBabord() == numberOfOars/2).findAny();
            if (finalRepartitionTemp.isPresent()){
                finalRepartition = finalRepartitionTemp.get();
            }

        } else {
            for (BabordTribordAngle eachAngle : possibleAngles) {
                if (angle > eachAngle.getAngle() - (smallestEcart/2) && angle <= eachAngle.getAngle() + (smallestEcart/2) ) {
                    finalRepartition = generateIdealRepartitionOfOars(eachAngle.getAngle());
                    break;
                }
            }
        }


        return finalRepartition;
    }




    /**
     * Cette méthode va permettre de trouver quel est la répartition idéale de marin qui vont ramer
     * @param angle est l'angle dont on souhaite tourner
     * @return la meilleure dispositions des rames pour entamer une rotation
     */
    BabordTribordAngle generateIdealRepartitionOfOars(double angle) {


        List<BabordTribordAngle> idealsRepartitionOfOars = possibleAngles.stream().filter( eachAngle -> eachAngle.getAngle() - ECART_DOUBLE <= angle && eachAngle.getAngle() + ECART_DOUBLE >= angle).collect(Collectors.toList());
        BabordTribordAngle finalRepartition = new BabordTribordAngle(0,0,0);


        if (!idealsRepartitionOfOars.isEmpty()) {
            finalRepartition = idealsRepartitionOfOars.stream().findFirst().get();

        }

        // Plus on a de marins qui rament, et plus notre bateau pourra aller vite. Il faut donc trouver le maximum possible
        int numberOfSailorOnOarAtTribord = sailorsOnOarAtTribord.size();
        int numberOfSailorOnOarAtBabord = sailorsOnOarAtBabord.size();
        int numberOfSailorsOnAnyEntities = sailorsOnAnyEntities.size();



        // CAS OU ANGLE TOMBE PAS SUR UNE VALEUR EXACTE //

        // On va parcourir toutes les dispositions idéales afin de trouver celle qui pourra utiliser le maximum de marin
        for (BabordTribordAngle repartition : idealsRepartitionOfOars) {




            // On regarde si on en a assez a babord et a tribord (dans ceux qui sont déjà sur des rames)
            if (repartition.getBabord() <= numberOfSailorOnOarAtBabord && repartition.getTribord() <= numberOfSailorOnOarAtTribord) {
                finalRepartition = repartition;

            } else {

                boolean anyDisposition = true;

                if (repartition.getBabord() <= numberOfSailorOnOarAtBabord && repartition.getTribord() > numberOfSailorOnOarAtTribord && numberOfSailorsOnAnyEntities >= repartition.getTribord() - numberOfSailorOnOarAtTribord) {
                    // Si on en a assez a babord mais qu'il en manque un certain nombre a tribord mais qu'on a suffisamment de marin libre pour combler le trou
                    anyDisposition = false;
                    int numberOfMissingTribordSailor = repartition.getTribord() - numberOfSailorOnOarAtTribord;

                    // On va d'abord trouver une place libre a tribord
                    finalRepartition = findBestRepartitionFromOneBoatSide(finalRepartition, numberOfSailorsOnAnyEntities, repartition, numberOfMissingTribordSailor, listOfOarsAtBabordWithAnySailorsOnIt);
                    moveSailorsOnOarWithFreeSailors(finalRepartition);




                }


                if (repartition.getTribord() <= numberOfSailorOnOarAtTribord && repartition.getBabord() > numberOfSailorOnOarAtBabord && numberOfSailorsOnAnyEntities >= repartition.getBabord() - numberOfSailorOnOarAtBabord) {
                    anyDisposition = false;

                    // Si on en a assez a tribord mais qu'il en manque un certain nombre a babord mais qu'on a suffisamment de marin libre pour combler le trou
                    int numberOfMissingBabordSailor = repartition.getBabord() - numberOfSailorOnOarAtBabord;

                    // On va d'abord trouver une place libre a tribord
                    finalRepartition = findBestRepartitionFromOneBoatSide(finalRepartition, numberOfSailorsOnAnyEntities, repartition, numberOfMissingBabordSailor, listOfOarsAtTribordWithAnySailorsOnIt);
                    moveSailorsOnOarWithFreeSailors(finalRepartition);

                }

                //finalRepartition = repartitionMin;


                // TODO : refactorer cette méthode mais faire très attentions a l'actualisation des lists listOfOarsAtTribordWithAnySailorsOnIt et listOfOarsAtBabordWithAnySailorsOnIt
                if (anyDisposition) {
                    // Si nous n'avons pas suffisamment de marin a gauche et a droite mais suffisamment de marins libres

                    System.out.println("On est la : actuellement -> " + numberOfSailorOnOarAtBabord + " : " + numberOfSailorOnOarAtTribord);
                    System.out.println(repartition);

                    // On compte combien de marin il nous manque du bon coté
                    // On regarde si on peut enlever des marins qui sont sur des rames l'autre coté et les bouger du bon coté tout en respectant la disposition
                    // Si on peut on les déplace



                    // Si on a assez de marin sur le bateau pour exécuter la disposition
                    if ((numberOfSailorOnOarAtBabord + numberOfSailorOnOarAtTribord) >= (repartition.getTribord() + repartition.getBabord())) {


                        // Si on en a pas assez a tribord
                        if (numberOfSailorOnOarAtTribord <= repartition.getTribord()) {

                            // On va bouger un marin de babord vers tribord
                            System.out.println("On va bouger un marin de babord vers tribord");


                            // On a le droit de déplacer seulement un certain nombre de marin pour respecter la repartition
                            int numberOfSailorWeCanMoveFromBabordToTribord = numberOfSailorOnOarAtBabord - repartition.getBabord();
                            System.out.println("1 : " + numberOfSailorWeCanMoveFromBabordToTribord);



                            // Maintenant on sélectionne numberOfSailorWeCanMoveFromBabordToTribord marins de babord afin de les faire bouger a tribord
                            for (Marin babordSailorWeCanMove : sailorsOnOarAtBabord) {

                                Optional<BoatEntity> destination = Optional.empty();

                                //On ne choisi que un certain nombre de marins
                                if (numberOfSailorWeCanMoveFromBabordToTribord >0) {

                                    // On parcours les potentielles destinations a tribord
                                    for (BoatEntity finalPosition : listOfOarsAtTribordWithAnySailorsOnIt) {

                                        if (babordSailorWeCanMove.canMoveTo(finalPosition.getX(), finalPosition.getY())) {

                                            destination = Optional.of(finalPosition);

                                            numberOfSailorWeCanMoveFromBabordToTribord--;
                                            break;
                                        }

                                    }

                                }

                                if (destination.isPresent()) {
                                    // On peut déplacer le marin vers cette position
                                    actionsToDo.add(new Moving(babordSailorWeCanMove.getId(), destination.get().getX() - babordSailorWeCanMove.getX(), destination.get().getY() - babordSailorWeCanMove.getY()));
                                    actionsToDo.add(new Oar(babordSailorWeCanMove.getId()));

                                    babordSailorWeCanMove.setX(destination.get().getX());
                                    babordSailorWeCanMove.setY(destination.get().getY());


                                    // ET updater listOfOarsAtTribordWithAnySailorsOnIt
                                    listOfOarsAtTribordWithAnySailorsOnIt.remove(destination.get());



                                }


                            }

                        }


                        // Si on en a pas assez a babord
                        if (numberOfSailorOnOarAtBabord <= repartition.getBabord()) {

                            // On va bouger un marin de tribord vers babord
                            System.out.println("On va bouger un marin de tribord vers babord");



                            // On a le droit de déplacer seulement un certain nombre de marin pour respecter la repartition
                            int numberOfSailorWeCanMoveFromTribordToBabord = numberOfSailorOnOarAtTribord - repartition.getTribord();
                            System.out.println("2 : " + numberOfSailorWeCanMoveFromTribordToBabord);



                            // Maintenant on sélectionne numberOfSailorWeCanMoveFromBabordToTribord marins de babord afin de les faire bouger a tribord
                            for (Marin tribordSailorWeCanMove : sailorsOnOarAtTribord) {

                                Optional<BoatEntity> destination = Optional.empty();

                                //On ne choisi que un certain nombre de marins
                                if (numberOfSailorWeCanMoveFromTribordToBabord >0) {

                                    // On parcours les potentielles destinations a tribord
                                    for (BoatEntity finalPosition : listOfOarsAtBabordWithAnySailorsOnIt) {

                                        if (tribordSailorWeCanMove.canMoveTo(finalPosition.getX(), finalPosition.getY())) {

                                            destination = Optional.of(finalPosition);

                                            numberOfSailorWeCanMoveFromTribordToBabord--;
                                            break;
                                        }

                                    }

                                }

                                if (destination.isPresent()) {
                                    // On peut déplacer le marin vers cette position
                                    actionsToDo.add(new Moving(tribordSailorWeCanMove.getId(), destination.get().getX() - tribordSailorWeCanMove.getX(), destination.get().getY() - tribordSailorWeCanMove.getY()));
                                    actionsToDo.add(new Oar(tribordSailorWeCanMove.getId()));

                                    tribordSailorWeCanMove.setX(destination.get().getX());
                                    tribordSailorWeCanMove.setY(destination.get().getY());


                                    // ET updater listOfOarsAtTribordWithAnySailorsOnIt
                                    listOfOarsAtBabordWithAnySailorsOnIt.remove(destination.get());



                                }
                            }
                        }
                    }
                }
            }



        }


        return finalRepartition;

    }


    /**
     * Cette méthode a pour objectif de faire bouger les marins libre por reussir au mieux la disposition souhaité
     * @param repartition que l'on souhaite
     */
    private void moveSailorsOnOarWithFreeSailors(BabordTribordAngle repartition) {



        int wantedBabordSailors = repartition.getBabord();
        int wantedTribordSailors = repartition.getTribord();


        while (sailorsOnOarAtBabord.size() <= wantedBabordSailors) {



            BoatEntity rameWeChoose = (listOfOarsAtBabordWithAnySailorsOnIt.stream().findFirst().isPresent()) ? listOfOarsAtBabordWithAnySailorsOnIt.stream().findFirst().get() : boatEntities.get(0);


            Marin marinWeChoose = sailorsOnAnyEntities.stream().filter(marin -> rameWeChoose.getX() - marin.getX() <= Config.MAX_MOVING_CASES_MARIN && rameWeChoose.getY() - marin.getY() <= Config.MAX_MOVING_CASES_MARIN).findAny().get();

            // On va parcourir tout les marins libres et regarder lequel est le plus loin
            for (Marin marinLibre : sailorsOnAnyEntities ) {

                int differenceOfDistanceXMarinWeChoose = rameWeChoose.getX() - marinWeChoose.getX();
                int differenceOfDistanceYMarinWeChoose = rameWeChoose.getY() - marinWeChoose.getY();

                int differenceOfDistanceXMarinLibre = rameWeChoose.getX() - marinLibre.getX();
                int differenceOfDistanceYMarinLibre = rameWeChoose.getY() - marinLibre.getY();

                // Si le marin libre est plus loin que le marin actuel, on prend celui la
                if (differenceOfDistanceXMarinLibre >= differenceOfDistanceXMarinWeChoose && differenceOfDistanceYMarinLibre >= differenceOfDistanceYMarinWeChoose && differenceOfDistanceXMarinLibre <= Config.MAX_MOVING_CASES_MARIN && differenceOfDistanceYMarinLibre <= Config.MAX_MOVING_CASES_MARIN) {
                    marinWeChoose = marinLibre;
                }
            }



            Moving marinMoving = new Moving(marinWeChoose.getId(), rameWeChoose.getX() - marinWeChoose.getX(), rameWeChoose.getY() - marinWeChoose.getY());
            actionsToDo.add(marinMoving);

            // Maintenant on bouge le marin marinWeChoose vers la rame rameWeChoose
            marinWeChoose.setX(rameWeChoose.getX());
            marinWeChoose.setY(rameWeChoose.getY());

            Oar oar = new Oar(marinMoving.getSailorId());
            actionsToDo.add(oar);

            actualizeAll();
        }



        while (sailorsOnOarAtTribord.size() <= wantedTribordSailors) {

            BoatEntity rameWeChoose = listOfOarsAtTribordWithAnySailorsOnIt.stream().findFirst().get();
            Marin marinWeChoose = sailorsOnAnyEntities.stream().filter(marin -> rameWeChoose.getX() - marin.getX() <= Config.MAX_MOVING_CASES_MARIN && rameWeChoose.getY() - marin.getY() <= Config.MAX_MOVING_CASES_MARIN).findAny().get();

            // On va parcourir tout les marins libres et regarder lequel est le plus loin
            for (Marin marinLibre : sailorsOnAnyEntities ) {

                int differenceOfDistanceXMarinWeChoose = rameWeChoose.getX() - marinWeChoose.getX();
                int differenceOfDistanceYMarinWeChoose = rameWeChoose.getY() - marinWeChoose.getY();

                int differenceOfDistanceXMarinLibre = rameWeChoose.getX() - marinLibre.getX();
                int differenceOfDistanceYMarinLibre = rameWeChoose.getY() - marinLibre.getY();

                // Si le marin libre est plus loin que le marin actuel, on prend celui la
                if (differenceOfDistanceXMarinLibre >= differenceOfDistanceXMarinWeChoose && differenceOfDistanceYMarinLibre >= differenceOfDistanceYMarinWeChoose && differenceOfDistanceXMarinLibre <= Config.MAX_MOVING_CASES_MARIN && differenceOfDistanceYMarinLibre <= Config.MAX_MOVING_CASES_MARIN) {
                    marinWeChoose = marinLibre;
                }
            }



            Moving marinMoving = new Moving(marinWeChoose.getId(), rameWeChoose.getX() - marinWeChoose.getX(), rameWeChoose.getY() - marinWeChoose.getY());
            actionsToDo.add(marinMoving);

            // Maintenant on bouge le marin marinWeChoose vers la rame rameWeChoose
            marinWeChoose.setX(rameWeChoose.getX());
            marinWeChoose.setY(rameWeChoose.getY());

            Oar oar = new Oar(marinMoving.getSailorId());
            actionsToDo.add(oar);

            actualizeAll();
        }
    }


    /**
     * Cette méthode va faire ramer (si il y a bien le bon nombre de rame) :
     * @param babordOars rames a babord
     * @param tribordOars rames a tribord
     * Et va donc actualiser la liste d'actions
     */
    private void oar(int babordOars, int tribordOars) {

        actualizeListSailorsOnOar();

        if (sailorsOnOarAtBabord.size() >= babordOars) {
            for (int i = 0; i < babordOars; i++) {
                Marin marin = sailorsOnOarAtBabord.get(i);
                if (verifyIfSailorActuallyOar(marin.getId())) {
                    actionsToDo.add(new Oar(marin.getId()));
                }

            }
        }

        if (sailorsOnOarAtTribord.size() >= tribordOars) {
            for (int i = 0; i < tribordOars; i++) {
                Marin marin = sailorsOnOarAtTribord.get(i);
                if (verifyIfSailorActuallyOar(marin.getId())) {
                    actionsToDo.add(new Oar(marin.getId()));
                }
            }
        }


    }


    /**
     * Cette méthode permet de vérifier si un marin n'est pas déjà entrain de ramer
     * @param id du marin que l'on souhaite verifier
     * @return true si le marin n'est pas actuellement entrain de ramer, false sinon
     */
    private boolean verifyIfSailorActuallyOar(int id) {
        for (Action action : actionsToDo) {
            if ((action instanceof Oar) && action.getSailorId() == id) {
                return false;
            }
        }
        return true;
    }


    /**
     * Cette méthode va aller chercher la meilleure répartition possible lorsque l'on a suffisamment de marins positionnées
     * D'un cote du bateau mais pas suffisamment dans l'autre. Il va donc aller chercher si on peut déplacer les marins libres
     * sur les rames ou il manque des marins afin de réaliser la répartition avec le plus de rames
     * @param finalRepartition la valeur de sortie actuelle -> qui évoluera
     * @param numberOfSailorsOnAnyEntities nombre de marins qui ne sont sur aucune rames
     * @param repartition repartition actuelle de rames (tribordBabordAngle)
     * @param numberOfMissingSailor combien nous voulons de sailors
     * @param listOfOarsWithAnySailorsOnIt liste des rames ou il n'y a aucun marin dessus
     * @return une repartition qui optimise les déplacement des marins
     */
    private BabordTribordAngle findBestRepartitionFromOneBoatSide(BabordTribordAngle finalRepartition, int numberOfSailorsOnAnyEntities, BabordTribordAngle repartition, int numberOfMissingSailor, List<BoatEntity> listOfOarsWithAnySailorsOnIt) {

        if (numberOfMissingSailor <= numberOfSailorsOnAnyEntities) {





            // On parcours tout les marins libre et on regarde si ils peuvent tous se déplacer
            for (Marin marinLibre : sailorsOnAnyEntities) {

                int maxDistanceX = 0;   //Config.MAX_MOVING_CASES_MARIN;
                int maxDistanceY = 0;   //Config.MAX_MOVING_CASES_MARIN;

                for (BoatEntity rameLibre : listOfOarsWithAnySailorsOnIt) {
                    // Nous allons optimiser la distance, c'est a dire que nous allons utiliser le marin
                    // avec la plus grande distance de déplacement permise

                    int differenceOfX = rameLibre.getX() - marinLibre.getX();
                    int differenceOfY = rameLibre.getY() - marinLibre.getY();

                    // Si la distance est supérieur a celle d'avant mais inférieur a la distance max qu'un marin peut faire, on a une nouvelle disposition
                    if (differenceOfX >= maxDistanceX && differenceOfY >= maxDistanceY && differenceOfX <= Config.MAX_MOVING_CASES_MARIN && differenceOfY <= Config.MAX_MOVING_CASES_MARIN) {
                        maxDistanceX = differenceOfX; maxDistanceY = differenceOfY;

                        finalRepartition = repartition;
                        

                    }
                }
            }
        }


        return finalRepartition;
    }






    /**
     * Les consignes du jeu disent que le bateau, pour une certaine quantité de rames, ne peut faire qu'un certains
     * nombre d'angles. Ce nombre d'angle est défini par la formule :
     *                  PI * <diff rame tribord - rame bâbord>/<nombre total de rames>
     * Cette méthode calcul donc toutes les combinaisons d'angles possibles avec le nombre de rame qu'il est nécessaire
     * pour tourner
     * @param startIndex nombre minimum de rames de chaque coté
     * @return la liste de tout les angles possibles avec le nombre de rame qu'il faut de chaque coté pour tourner
     */
    List<BabordTribordAngle> generateListOfPossibleAngles(int startIndex) {

        List<BabordTribordAngle> angles = new ArrayList<>();

        if (!boatEntities.isEmpty()) {
            if (numberOfOars % 2 == 0) {

                int numberOfAnglesPossibles = numberOfOars / 2;
                for (int bab = startIndex; bab <= numberOfAnglesPossibles; bab++) {
                    for (int tri = startIndex; tri <= numberOfAnglesPossibles ; tri++) {

                        // On sors le cas ou bab = 0 et tri = 0 car si personne ne rame ni a droite, ni a gauche, on avance pas
                        if (bab + tri <= numberOfOars && bab + tri != 0) {
                            //PI * <diff rame tribord - rame bâbord>/<nombre total de rames>
                            int diff = tri - bab;
                            double angle = (diff == 0) ? 0 : Math.PI * diff / numberOfOars ;
                            angles.add(new BabordTribordAngle(bab,tri,angle));

                        }

                    }
                }
            } else
                throw new IllegalArgumentException("ERREUR moteur de jeu : Impossible d'avoir un nombre impair de rames");
        }


        return angles;
    }

    /**
     * Cette méthode actualise la variable differenceOfAngle qui a pour but de connaitre de quel angle le bateau doit
     * tourner
     * @param privilegeNegativeAngle true si on privilégie un angle négatif pour un demi-tour, false si on préfère avoir
     *                               un angle positif lors d'un demi-tour (angle = PI)
     */
    double generateDifferenceOfAngle(boolean privilegeNegativeAngle) {

        double localDifferenceOfAngle = minimizationAngleAlgorithm(finalOrientationBoat - positionBoat.getOrientation());

        if (privilegeNegativeAngle && (localDifferenceOfAngle == Math.PI || localDifferenceOfAngle == -Math.PI)) {
            localDifferenceOfAngle = -Math.PI;
        }

        return localDifferenceOfAngle;
    }

    /**
     * Cet algorithme est un algorithme de minimisation d'un angle qui va calculer l'équivalent d'un angle en une plus
     * petite valeur
     * @param angle que l'on souhaite potentiellement réduire
     * @return angle réduit
     */
    private double minimizationAngleAlgorithm(double angle) {
        if (angle > 0.0) {
            while (angle > Math.PI) {
                angle = angle - 2 * Math.PI;
            }
        } else {
            while (angle < -Math.PI) {
                angle = angle + 2 * Math.PI;
            }
        }
        return angle;
    }


    /**
     * Cette méthode va actualiser les variables sailorsOnOar / sailorsOnOarAtBabord / sailorsOnOarAtTribord en 
     * recherchant le nombre de marin qui sont actuellement positionnés sur les rames
     */
    private void actualizeListSailorsOnOar() {




        for (Marin sailor: sailors) {

            for(BoatEntity rightEntity : listOfOarsAtTribord) {
                if (rightEntity.getX() == sailor.getX() && rightEntity.getY() == sailor.getY()) {

                    sailorsOnOarAtTribord.add(sailor);
                    sailorsOnOar.add(sailor);
                }
            }


            for(BoatEntity leftEntity : listOfOarsAtBabord) {
                if (leftEntity.getX() == sailor.getX() && leftEntity.getY() == sailor.getY()) {

                    sailorsOnOarAtBabord.add(sailor);
                    sailorsOnOar.add(sailor);
                }
            }
        }
    }





    /**
     * Cette méthode permet d'actualiser les 2 listes : listOfOarsAtBabordWithAnySailorsOnIt & listOfOarsAtTribordWithAnySailorsOnIt
     * qui sont les listes des rames a babord/tribord ou aucun marin n'est situé dessus.
     */
    private List<BoatEntity> generateListOfOarsWithAnySailorOnIt(List<BoatEntity> listOfOars, List<Marin> sailorsOnOar) {

        Set<BoatEntity> temporaireBabord = new HashSet<>();
        for (BoatEntity rameBabord : listOfOars) {
            boolean same = false;
            for (Marin marin : sailorsOnOar) {

                if (marin.getX() == rameBabord.getX() && marin.getY() == rameBabord.getY()) {
                    same = true;
                    break;
                }

            }
            if (!same) temporaireBabord.add(rameBabord);
        }

        return new ArrayList<>(temporaireBabord);
    }




    /**
     * WARNING : NE PAS TOUCHER A L'ORDRE DES APPELS DANS CETTE FONCTION !
     */

    private void actualizeAll() {


        boatEntities = Arrays.asList(boat.getEntities().clone());
        positionBoat = boat.getTransform();

        if (!boatEntities.contains(null)) {
            List<BoatEntity> listOfOars = boatEntities.stream().filter(boatEntity -> boatEntity.getType().equals(BoatEntities.OAR)).collect(Collectors.toList());
            numberOfOars = listOfOars.size();

            listOfOarsAtBabord = listOfOars.stream().filter(boatEntity -> boatEntity.getY() == 0).collect(Collectors.toList());
            listOfOarsAtTribord = listOfOars.stream().filter(boatEntity -> boatEntity.getY() == boat.getDeck().getWidth()-1).collect(Collectors.toList());



            actualizeListSailorsOnOar();

            sailorsOnAnyEntities = sailors.stream().filter(marin -> !sailorsOnOar.contains(marin)).collect(Collectors.toList());


            listOfOarsAtBabordWithAnySailorsOnIt = generateListOfOarsWithAnySailorOnIt(listOfOarsAtBabord, sailorsOnOarAtBabord);
            listOfOarsAtTribordWithAnySailorsOnIt = generateListOfOarsWithAnySailorOnIt(listOfOarsAtTribord, sailorsOnOarAtTribord);



        }


        possibleAngles = new ArrayList<>();


    }





























































    public double getFinalOrientationBoat() { return finalOrientationBoat; }



    public Boat getBoat() {
        return boat;
    }

    public List<BoatEntity> getBoatEntities() {
        return boatEntities;
    }



    public List<BoatEntity> getListOfOarsAtBabord() {
        return listOfOarsAtBabord;
    }









    public List<Marin> getSailorsOnOar() {
        return sailorsOnOar;
    }

    public List<Marin> getSailorsOnOarAtTribord() {
        return sailorsOnOarAtTribord;
    }

    public List<Marin> getSailorsOnOarAtBabord() {
        return sailorsOnOarAtBabord;
    }

    public List<Marin> getSailorsOnAnyEntities() {
        return sailorsOnAnyEntities;
    }

    public BabordTribordAngle getFinalDispositionOfOars() {
        return finalDispositionOfOars;
    }
}
