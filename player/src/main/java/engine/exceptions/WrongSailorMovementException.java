package engine.exceptions;

public class WrongSailorMovementException extends RuntimeException {
    public WrongSailorMovementException(int sailorId, int distanceX, int distanceY) {
        super("The sailor " + sailorId + " can't move of " + distanceX + "; " + distanceY);
    }
}
