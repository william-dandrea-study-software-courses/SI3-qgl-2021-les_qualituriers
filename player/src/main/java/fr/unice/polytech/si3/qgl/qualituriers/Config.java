package fr.unice.polytech.si3.qgl.qualituriers;

import fr.unice.polytech.si3.qgl.qualituriers.utils.AngleUtil;

/**
 * Cette classe décrit permet de renseigner les différentes valeurs donné par l'énoncé, notamment les formules des vitesses,
 * des limites de marins ...
 * Nous avons fait le choix d'y implanter des méthodes (pour la vitesse notamment) car ces méthodes sont directement liées
 * aux consignes de l'énoncé.
 */
public class Config {

    private Config() {}

    public static final int MAX_MOVING_CASES_MARIN = 5;

    public static final double MAX_ANGLE_FOR_RUDDER = Math.PI / 4;
    public static final double MAX_ANGLE_TURN_FOR_SAILORS = Math.PI/2;

    /**
     * Epsilon pour comparer des valeurs flottantes
     */
    public static final double EPSILON = 10E-9D;









    /**
     * Renseigne la vitesse du bateau sur une partie. Exemple : le bateau fera 27,5km sur une partie
     * @param totalOarsOnTheBoat le nombre de rames total sur le bateau
     * @param activeOars le nombre de rames actives sur le bateau
     * @return la vitesse linéaire du bateau
     */
    public static double linearSpeedOar(int totalOarsOnTheBoat, int activeOars) {
        if (totalOarsOnTheBoat > 0 && activeOars > 0 && activeOars <= totalOarsOnTheBoat) {
            return 165 * activeOars / (double) totalOarsOnTheBoat;
        } throw new IllegalArgumentException((activeOars > totalOarsOnTheBoat)?"On ne peux pas avoir un nombre de rames actives supérieur au nombre de rames total ":"On ne peux pas avoir de valeurs nulles");
    }


    /**
     * Renseigne la vitesse du vent que l'on devra ajouter à la vitesse des rames
     * @param numberOfOpenSails le nombre de voiles ouvertes
     * @param totalSailsOnBoat le nombre de voiles sur le bateau
     * @param windSpeed la vitesse du vent
     * @param orientationBoat l'orientation du bateau
     * @param orientationWind l'orientation du vent
     * @return la valeur de la vitesse du vent
     */
    public static double linearSpeedWind(int numberOfOpenSails, int totalSailsOnBoat, double windSpeed, double orientationBoat, double orientationWind) {

        if (numberOfOpenSails <= totalSailsOnBoat) {
            double value = (double) (numberOfOpenSails / totalSailsOnBoat) * windSpeed;
            value = value * Math.cos(AngleUtil.differenceBetweenTwoAngle(orientationWind, orientationBoat));
            return value;
        } throw new IllegalArgumentException("Vous devez entrez un nombre de rame valide \n numberOfOpenSails " + numberOfOpenSails + "\n totalSailsOnBoat " + totalSailsOnBoat );

    }




}
