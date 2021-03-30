package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PathfindingStoreTest {

    private PathfindingStore pathfindingStore;
    private PathfindingResult calculatedPath;

    @BeforeEach
    void init() {
        pathfindingStore = new PathfindingStore();
        calculatedPath = new PathfindingResult();
    }

    @Test
    void setCalculatedPathTest(){
        pathfindingStore.setCalculatedPath(calculatedPath);
        assertEquals(calculatedPath, pathfindingStore.getCalculatedPath());
    }

    @Test
    void setCurrentNodeToReachTest(){
        pathfindingStore.setCurrentNodeToReach(3);
        assertEquals(3, pathfindingStore.getCurrentNodeToReach());
    }
}
