package pt.upskill.projeto1.gui;

import pt.upskill.projeto1.rogue.utils.Position;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static pt.upskill.projeto1.game.Engine.gui;

public class StatusBar {
    private List<ImageTile> fireBalls;
    private List<ImageTile> hp;
    private List<ImageTile> items;

    public StatusBar() {
        fireBalls = new ArrayList<>();
        hp = new ArrayList<>();
        items = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Position position = new Position(i, 0);
            gui.addStatusImage(new Black(position));
            if (i < 3) fireBalls.add(new Fire(position));
            else if (i < 7) hp.add(new Green(position));
        }
        paintStatusBar();
    }

    public void paintStatusBar() {
        for (ImageTile tile : fireBalls) gui.addStatusImage(tile);
        for (ImageTile tile : hp) gui.addStatusImage(tile);
        for (ImageTile tile : items) gui.addStatusImage(tile);
    }

    public List<ImageTile> getFireBalls() {
        return fireBalls;
    }

    public void removeFireBall() {
        ImageTile fireBall = fireBalls.remove(fireBalls.size() - 1);
        fireBalls.remove(fireBall);
        gui.removeStatusImage(fireBall);
    }

    public List<ImageTile> getHp() {
        return hp;
    }

    public void setHp(List<ImageTile> hp) {
        this.hp = hp;
    }

    public List<ImageTile> getItems() {
        return items;
    }

    public void setItems(List<ImageTile> items) {
        this.items = items;
    }
}
