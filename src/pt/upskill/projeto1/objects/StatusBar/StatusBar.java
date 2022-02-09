package pt.upskill.projeto1.objects.StatusBar;

import pt.upskill.projeto1.gui.*;
import pt.upskill.projeto1.objects.Characters.Hero;
import pt.upskill.projeto1.objects.Items.Item;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static pt.upskill.projeto1.game.Engine.currentRoom;
import static pt.upskill.projeto1.game.Engine.gui;

public class StatusBar implements Serializable {
    private List<ImageTile> statusTiles;
    private List<ImageTile> fireBar;
    private List<ImageTile> healthBar;
    private List<ImageTile> itemBar;

    public StatusBar() {
        statusTiles = new ArrayList<>();
        fireBar = new ArrayList<>();
        healthBar = new ArrayList<>();
        itemBar = new ArrayList<>();

        setStatusTiles();
    }

    public void paintBackground() {
        for (int i = 0; i < 10; i++) {
            Position position = new Position(i, 0);
            gui.addStatusImage(new Black(position));
        }
    }

    public void setStatusTiles() {
        gui.clearStatus();
        statusTiles.clear();

        paintBackground();
        updateFire();
        updateHealth();
        updateItems();
        statusTiles.addAll(fireBar);
        statusTiles.addAll(healthBar);
        statusTiles.addAll(itemBar);

        for (ImageTile tile : statusTiles) gui.addStatusImage(tile);
    }

    public void updateFire() {
        fireBar.clear();
        for (int i = 0; i < currentRoom.getHero().getFireBalls().size(); i++) {
            fireBar.add(new Fire(new Position(i, 0)));
        }
    }

    public void updateHealth() {
        healthBar.clear();
        int offset = 3;
        int health = currentRoom.getHero().getHealth();
        int healthPercentage = (int) ((double) health / currentRoom.getHero().MAX_HEALTH * 8);

        int i = 0;
        for (; i < healthPercentage; i += 2)
            healthBar.add(new Green(new Position(i / 2 + offset, 0)));
        if (healthPercentage % 2 != 0)
            healthBar.add(new RedGreen(new Position((i - 1) / 2 + offset, 0)));
        for (; i < 8; i += 2)
            healthBar.add(new Red(new Position(i / 2 + offset, 0)));
    }

    public void updateItems() {
        itemBar.clear();
        int offset = 7;
        int i = 0;
        for (Item item : currentRoom.getHero().getItems()) {
            if (item != null) {
                item.setPosition(new Position(i + offset, 0));
                itemBar.add(item);
            }
            i++;
        }
    }



}
