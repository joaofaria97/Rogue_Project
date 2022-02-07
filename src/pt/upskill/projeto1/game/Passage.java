package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Element;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.objects.Obstacle;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.List;

public abstract class Passage extends Element implements Obstacle {
    private final int passageNumber;
    private final int toPassageNumber;
    private final int toRoomNumber;
    private Direction leaveDirection;
    private boolean locked;

    public Passage(Position position, int passageNumber, int toPassageNumber, int toRoomNumber) {
        super(position);
        this.passageNumber = passageNumber;
        this.toPassageNumber = toPassageNumber;
        this.toRoomNumber = toRoomNumber;
        locked = false;
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

    public Direction getLeaveDirection() {
        return leaveDirection;
    }

    public void setLeaveDirection(Direction leaveDirection) {
        this.leaveDirection = leaveDirection;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        return "Passage{" +
                "passageNumber=" + passageNumber +
                '}';
    }
}
