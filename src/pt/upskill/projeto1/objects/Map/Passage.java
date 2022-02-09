package pt.upskill.projeto1.objects.Map;

import pt.upskill.projeto1.objects.Element;
import pt.upskill.projeto1.objects.Obstacle;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;

public abstract class Passage extends Element{
    private final int passageNumber;
    private final int toPassageNumber;
    private final int toRoomNumber;
    private Direction exitDirection;

    public Passage(Position position, int passageNumber, int toPassageNumber, int toRoomNumber) {
        super(position);
        this.passageNumber = passageNumber;
        this.toPassageNumber = toPassageNumber;
        this.toRoomNumber = toRoomNumber;
    }

    public int getPassageNumber() {
        return passageNumber;
    }

    public int getToPassageNumber() {
        return toPassageNumber;
    }

    public int getToRoomNumber() {
        return toRoomNumber;
    }

    public Direction getExitDirection() {
        return exitDirection;
    }

    public void setExitDirection(Direction exitDirection) {
        this.exitDirection = exitDirection;
    }
}
