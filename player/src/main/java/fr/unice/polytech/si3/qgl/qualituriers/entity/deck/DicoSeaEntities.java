package fr.unice.polytech.si3.qgl.qualituriers.entity.deck;

import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;

public class DicoSeaEntities {

    private final int index;
    private final VisibleDeckEntity seaEntity;

    public DicoSeaEntities(int index, VisibleDeckEntity seaEntity) {
        this.index = index;
        this.seaEntity = seaEntity;
    }

    public int getIndex() {
        return index;
    }

    public VisibleDeckEntity getSeaEntity() {
        return seaEntity;
    }
}
