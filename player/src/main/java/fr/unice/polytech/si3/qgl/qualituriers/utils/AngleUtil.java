package fr.unice.polytech.si3.qgl.qualituriers.utils;

/**
 * @author Yann Clodong
 */
public class AngleUtil {


    /**
     * Calcul la différence entre 2 angles
     * @param angleFinal l'angle final
     * @param angleInit l'angle initial
     * @return la différence entre 2 angles
     */
    public static double differenceBetweenTwoAngle(double angleInit, double angleFinal) {
        return modAngle(angleFinal- angleInit);
    }

    /**
     * Ramène n'importe quel angle dans l'intervalle ]-π, π]
     * @param angle: angle
     * @return Angle
     */
    public static double modAngle(double angle) {
        if(angle <= Math.PI && angle > -Math.PI) return angle;
        if(angle > Math.PI) return modAngle(angle - 2 * Math.PI);
        if(angle <= Math.PI) return modAngle(angle + 2 * Math.PI);
        return 0;
    }

}
