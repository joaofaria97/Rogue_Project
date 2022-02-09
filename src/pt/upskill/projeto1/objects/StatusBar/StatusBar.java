package pt.upskill.projeto1.objects.StatusBar;

import pt.upskill.projeto1.gui.*;
import pt.upskill.projeto1.objects.Characters.Hero;
import pt.upskill.projeto1.objects.Items.Item;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static pt.upskill.projeto1.game.Engine.currentRoom;
import static pt.upskill.projeto1.game.Engine.gui;

public class StatusBar {
    private List<ImageTile> statusTiles;
    private List<ImageTile> fireBar;
    private List<ImageTile> healthBar;
    private List<ImageTile> itemBar;

    public StatusBar() {
        statusTiles = new ArrayList<>();
        fireBar = new ArrayList<>();
        healthBar = new ArrayList<>();
        itemBar = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Position position = new Position(i, 0);
            gui.addStatusImage(new Black(position));
            if (i >= 3 && i < 7) gui.addStatusImage(new Red(position));
        }
        setStatusTiles();
    }

    public void setStatusTiles() {
        gui.clearStatus();

        updateFire();
        updateHealth();
        updateItems();
        statusTiles.addAll(fireBar);
        statusTiles.addAll(healthBar);
        statusTiles.addAll(itemBar);

        for (ImageTile tile : statusTiles) gui.addStatusImage(tile);
    }

    public void updateFire() {
        for (int i = 0; i < currentRoom.getHero().getFireBalls().size(); i++) {
            fireBar.add(new Fire(new Position(i, 0)));
        }
    }

    public void updateHealth() {
        int offset = 3;
        int health = currentRoom.getHero().getHealth() / 10;
        int i = 0;
        for (; i < health; i += 2)
            healthBar.add(new Green(new Position(i / 2 + offset, 0)));
        if (health % 2 != 0)
            healthBar.add(new RedGreen(new Position((i - 1) / 2 + offset, 0)));
    }

    public void updateItems() {
        int offset = 7;
        int i = 0;
        for (Item item : currentRoom.getHero().getItems()) {
            if (item != null) item.setPosition(new Position(i + offset, 0));
            i++;
        }
    }



}
