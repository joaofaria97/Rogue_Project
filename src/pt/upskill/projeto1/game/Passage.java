package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Element;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.objects.Obstacle;
import pt.upskill.projeto1.rogue.utils.Position;

public abstract class Passage extends Element implements Obstacle {
    private int passageNumber;
    private int toPassageNumber;
    private int toRoomNumber;
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

    public void setPassageNumber(int passageNumber) {
        this.passageNumber = passageNumber;
    }

    public int getToPassageNumber() {
        return toPassageNumber;
    }

    public void setToPassageNumber(int toPassageNumber) {
        this.toPassageNumber = toPassageNumber;
    }

    public int getToRoomNumber() {
        return toRoomNumber;
    }

    public void setToRoomNumber(int toRoomNumber) {
        this.toRoomNumber = toRoomNumber;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
