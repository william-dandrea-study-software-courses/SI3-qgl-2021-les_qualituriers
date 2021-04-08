package fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Sea.futur;

import fr.unice.polytech.si3.qgl.qualituriers.utils.helpers.IDrawer;
import fr.unice.polytech.si3.qgl.qualituriers.utils.helpers.IShapeDraw;

import java.awt.*;

public abstract class FuturDraw implements IShapeDraw {

    private boolean destroyed;

    public abstract void draw(IDrawer drawer, Graphics g);

    @Override
    public void destroy() {
        this.destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
