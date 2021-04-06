package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter;

public class HeadQuarterConfig {


    public static final boolean USE_WIND = false;


    /**
     * Ce paramètre est un coeficient qui permet de définir à partir de combien de tour possible en ligne droite
     * nous permet de faire bouger le marin. Exemple : si ce coef est a 5, il faudra que le bateau puisse
     * faire 5 tour en ligne droite au minimum pour pouvoir faire deplacer le marin qui est sur le gouvernail vers la voile
     */
    public static final int COEFFICIENT_FOR_MOVING_SAILOR_TO_SAIL = 5;
}
