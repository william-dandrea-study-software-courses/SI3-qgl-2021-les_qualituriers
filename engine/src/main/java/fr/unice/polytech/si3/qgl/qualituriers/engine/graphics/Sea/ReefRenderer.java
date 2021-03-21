package fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Sea;

import fr.unice.polytech.si3.qgl.qualituriers.engine.TurnConfig;
import fr.unice.polytech.si3.qgl.qualituriers.engine.races.Race;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.ReefVisibleDeckEntity;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReefRenderer {

    private final Race race;
    public ReefRenderer(Race race) {
        this.race = race;
    }

    public void draw(MyCanvas canvas) {
        for (ReefVisibleDeckEntity reef : this.getReefs())
            canvas.drawShape(reef.getPositionableShape(), this.canSee(reef) ? Color.BLACK : Color.LIGHT_GRAY);
    }

    private List<ReefVisibleDeckEntity> getReefs() {
        if(this.race.getEntities() == null) return new ArrayList<>();
        return Arrays.stream(Objects.requireNonNull(this.race.getEntities()))
                .filter(entity -> entity instanceof ReefVisibleDeckEntity)
                .map(entity -> (ReefVisibleDeckEntity) entity)
                .collect(Collectors.toList());
    }

    private boolean canSee(ReefVisibleDeckEntity reef) {
        return reef.getPositionableShape().getTransform().distanceWithoutSquare(this.race.getBoat().getPosition()) <= Math.pow(TurnConfig.FIELD_VISION, 2);
    }

}