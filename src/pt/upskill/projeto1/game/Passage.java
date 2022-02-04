package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Element;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.objects.Obstacle;
import pt.upskill.projeto1.rogue.utils.Position;

public abstract class Passage extends Element implements Obstacle {
    private int passageNumber;
    private int toRoom;
    private int toPassage;
    private Hero hero;

    public Passage(Position position, int passageNumber, int toRoom, int toPassage) {
        super(position);
        this.passageNumber = passageNumber;
        this.toRoom = toRoom;
        this.toPassage = toPassage;
    }

    public int getPassageNumber() {
        return passageNumber;
    }

    public void setPassageNumber(int passageNumber) {
        this.passageNumber = passageNumber;
    }

    public int getToRoom() {
        return toRoom;
    }

    public void setToRoom(int toRoom) {
        this.toRoom = toRoom;
    }

    public int getToPassage() {
        return toPassage;
    }

    public void setToPassage(int toPassage) {
        this.toPassage = toPassage;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    @Override
    public String toString() {
        return "Passage{" +
                "passageNumber=" + passageNumber +
                ", toRoom=" + toRoom +
                ", toPassage=" + toPassage +
                '}';
    }
}
