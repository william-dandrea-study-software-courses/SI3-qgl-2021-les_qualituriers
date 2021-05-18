package fr.unice.polytech.si3.qgl.qualituriers.utils;

import fr.unice.polytech.si3.qgl.qualituriers.render.TempoRender;
import fr.unice.polytech.si3.qgl.qualituriers.utils.helpers.IDrawer;
import fr.unice.polytech.si3.qgl.qualituriers.utils.helpers.IShapeDraw;
import fr.unice.polytech.si3.qgl.qualituriers.utils.logger.SeaDrawer;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.awt.*;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SeaDrawerTest {

    @AfterAll
    static void setNull() {
        TempoRender.SeaDrawer = null;
    }

    @Test
    public void drawPolygonTest() {
        ArgumentCaptor<PositionablePolygon> posPolyCapt = ArgumentCaptor.forClass(PositionablePolygon.class);
        ArgumentCaptor<Color> colorCapt = ArgumentCaptor.forClass(Color.class);
        IDrawer drawer = mock(IDrawer.class);

        assertDoesNotThrow(() -> SeaDrawer.drawPolygon(null, null));
        PositionablePolygon polygon = new PositionablePolygon(new Rectangle(2, 2, 0), new Transform(5, 5, 0));
        Color color = Color.BLACK;
        TempoRender.SeaDrawer = drawer;

        SeaDrawer.drawPolygon(polygon, color);
        verify(drawer).drawFuturPolygon(posPolyCapt.capture(), colorCapt.capture());
        assertEquals(polygon, posPolyCapt.getValue());
        assertEquals(color, colorCapt.getValue());
    }

    @Test
    public void drawLineTest() {
        ArgumentCaptor<Point> startCapt = ArgumentCaptor.forClass(Point.class);
        ArgumentCaptor<Point> endCapt = ArgumentCaptor.forClass(Point.class);
        ArgumentCaptor<Color> colorCapt = ArgumentCaptor.forClass(Color.class);
        IDrawer drawer = mock(IDrawer.class);

        assertDoesNotThrow(() -> SeaDrawer.drawLine(null, null, null));
        assertNull(SeaDrawer.drawLine(null, null, null));

        Point start = new Point(0, 0);
        Point end = new Point(1, 1);
        Color color = Color.BLACK;
        TempoRender.SeaDrawer = drawer;

        IShapeDraw draw = SeaDrawer.drawLine(start, end, color);
        verify(drawer).drawFuturLine(startCapt.capture(), endCapt.capture(), colorCapt.capture());
        assertEquals(start, startCapt.getValue());
        assertEquals(end, endCapt.getValue());
        assertEquals(color, colorCapt.getValue());
        assertNotNull(draw);
    }

    @Test
    public void drawCircleTest() {
        ArgumentCaptor<PositionableCircle> posCircleCapt = ArgumentCaptor.forClass(PositionableCircle.class);
        ArgumentCaptor<Color> colorCapt = ArgumentCaptor.forClass(Color.class);
        IDrawer drawer = mock(IDrawer.class);

        assertDoesNotThrow(() -> SeaDrawer.drawCircle(null, null));
        PositionableCircle circle = new PositionableCircle(new Circle(5), new Transform(5, 5, 0));
        Color color = Color.BLACK;
        TempoRender.SeaDrawer = drawer;

        SeaDrawer.drawCircle(circle, color);
        verify(drawer).drawFuturFilledCircle(posCircleCapt.capture(), colorCapt.capture());
        assertEquals(circle, posCircleCapt.getValue());
        assertEquals(color, colorCapt.getValue());
    }

    @Test
    public void drawShapeTest() {
        ArgumentCaptor<PositionablePolygon> posPolyCapt = ArgumentCaptor.forClass(PositionablePolygon.class);
        ArgumentCaptor<Color> colorCapt = ArgumentCaptor.forClass(Color.class);
        IDrawer drawer = mock(IDrawer.class);

        assertDoesNotThrow(() -> SeaDrawer.drawShape(null, null));
        PositionablePolygon polygon = new PositionablePolygon(new Rectangle(2, 2, 0), new Transform(5, 5, 0));
        Color color = Color.BLACK;
        TempoRender.SeaDrawer = drawer;

        SeaDrawer.drawShape(polygon, color);
        verify(drawer).drawFuturShape(posPolyCapt.capture(), colorCapt.capture());
        assertEquals(polygon, posPolyCapt.getValue());
        assertEquals(color, colorCapt.getValue());
    }

    @Test
    public void drawPinTest() {
        ArgumentCaptor<Point> posCapt = ArgumentCaptor.forClass(Point.class);
        ArgumentCaptor<Color> colorCapt = ArgumentCaptor.forClass(Color.class);
        IDrawer drawer = mock(IDrawer.class);

        assertDoesNotThrow(() -> SeaDrawer.drawPin(null, null));
        Point point = new Point(0, 0);
        Color color = Color.BLACK;
        TempoRender.SeaDrawer = drawer;

        SeaDrawer.drawPin(point, color);
        verify(drawer).drawFuturPin(posCapt.capture(), colorCapt.capture());
        assertEquals(point, posCapt.getValue());
        assertEquals(color, colorCapt.getValue());
    }

}
