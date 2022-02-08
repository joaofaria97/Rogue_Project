package pt.upskill.projeto1.objects.StatusBar;

import pt.upskill.projeto1.gui.*;
import pt.upskill.projeto1.objects.Characters.Hero;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.ArrayList;
import java.util.List;

import static pt.upskill.projeto1.game.Engine.currentRoom;
import static pt.upskill.projeto1.game.Engine.gui;

public class StatusBar {
    private List<ImageTile> fireBar;
    private List<ImageTile> healthBar;
    private List<ImageTile> itemBar;

    public StatusBar() {
        fireBar = new ArrayList<>();
        healthBar = new ArrayList<>();
        itemBar = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Position position = new Position(i, 0);
            gui.addStatusImage(new Black(position));
            if (i < 3) {
                Fire fire = new Fire(position);
                fireBar.add(fire);
                gui.addStatusImage(fire);
            }
            else if (i < 7) {
                gui.addStatusImage(new Red(position));
            }
        }
        setHealthBar();
    }

    public void removeFireBall() {
        try {
            ImageTile fireBall = fireBar.remove(fireBar.size() - 1);
            fireBar.remove(fireBall);
            gui.removeStatusImage(fireBall);
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    public void setHealthBar() {
        System.out.println(healthBar);
        for (ImageTile healthBlock : healthBar) {
            gui.removeStatusImage(healthBlock);
        }
        healthBar.clear();

        int currentHealth = currentRoom.getHero().getHealth();
        double healthPercentage = (double) currentHealth / Hero.MAX_HEALTH;
        double barPercentage = 0.25;
        for (int i = 0; i < 4; i++) {
            Position position = new Position(i + 3, 0);
            if (healthPercentage >= barPercentage) {
                Green greenBlock = new Green(position);
                healthBar.add(greenBlock);
                gui.addStatusImage(greenBlock);
            } else if (healthPercentage >= barPercentage - 0.25){
                RedGreen redGreen = new RedGreen(position);
                healthBar.add(redGreen);
                gui.addStatusImage(redGreen);
            }
            barPercentage += 0.25;
        }
    }
}
