package fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat;

/**
 * @author D'Andréa William
 */
public class HeadQuarterControls {

    // Variable indiquant que si le nombre de sailors sur le bateau est inférieur à 2, nous n'utilisons que les rames
    // du bateau et rien d'autre
    public final static int MAX_SAILORS_ON_BOAT_FOR_USE_JUST_OAR = 2;

    // Variable donnant le nombre de marin nécessaire pour utiliser les rames et le gouvernail
    public final static int NUMBER_OF_SAILORS_FOR_USE_OAR_AND_RUDDER = 3;

    // Variable donnant le nombre minimum de marins sur le bateau pour utiliser les rames, le gouvernail, la voile et la vigie
    public final static int MIN_SAILORS_FOR_USE_OAR_RUDDER_SAIL_WATCH = 4;
}
