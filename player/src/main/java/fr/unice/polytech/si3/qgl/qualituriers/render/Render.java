package fr.unice.polytech.si3.qgl.qualituriers.render;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.RoundInfo;

/**
 * Représente un rendu.
 * Il sera testé avec comme script d'exécution: https://github.com/mathiascouste/qgl-2021/blob/master/project/DELIVERY_PROCESS.md
 * Les interfaces JSON: https://github.com/mathiascouste/qgl-2021/blob/master/project/TECHNICAL_SPECS.md
 * @author Alexandre Arcil
 */
public abstract class Render {

    protected final GameInfo gameInfo;

    public Render(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    /**
     * Appelé à chaque tour de jeu, il faut décider des actions à faire.
     * @param round Les informations donné par le moteur de jeu
     * @return L'object qui sera envoyé comme réponse
     * TODO: changer le type de retour ? Object est très imprécis et sujet à problème
     */
    public abstract String nextRound(RoundInfo round) throws JsonProcessingException;

}
