package fr.unice.polytech.si3.qgl.qualituriers.utils.action;

/**
 * @author Alexandre Arcil
 *
 * Correspond aux actions. Chaque enum contient le type ainsi que la class qui le représente.
 *
 * Reflexion: le type ne sera pas mieux dans le class directement ?
 */
public enum Actions {

    MOVING("MOVING", Moving.class), LIFT_SAIL("LIFT_SAIL", LiftSail.class),
    LOWER_SAIL("LOWER_SAIL", LowerSail.class), TURN("TURN", Turn.class),
    OAR("OAR", Oar.class), USE_WATCH("USE_WATCH", UseWatch.class),
    AIM("AIM", Aim.class), FIRE("FIRE", Fire.class),
    RELOAD("RELOAD", Reload.class);

    private final String type;
    private final Class<? extends Action> action;

    Actions(String type, Class<? extends Action> action) {
        this.type = type;
        this.action = action;
    }

    /**
     * Convertie un type reçu depuis le json en la class Action qui correspond.
     * @param type Le type de l'action
     * @return la class de l'Action correspond, null sinon.
     */
    public static Class<? extends Action> typeToAction(String type) {
        for (Actions value : values()) {
            if(value.type.equals(type))
                return value.action;
        }
        return null;
    }

    public String getType() {
        return type;
    }
}
