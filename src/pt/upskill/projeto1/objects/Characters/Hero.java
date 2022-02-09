package pt.upskill.projeto1.objects.Characters;

import pt.upskill.projeto1.game.Command;
import pt.upskill.projeto1.game.FireBallThread;
import pt.upskill.projeto1.objects.Element;
import pt.upskill.projeto1.objects.Items.Item;
import pt.upskill.projeto1.objects.Items.Key;
import pt.upskill.projeto1.objects.Map.Door;
import pt.upskill.projeto1.objects.Map.Passage;
import pt.upskill.projeto1.gui.Fire;
import pt.upskill.projeto1.objects.StatusBar.StatusBar;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static pt.upskill.projeto1.game.Engine.*;

public class Hero extends GameCharacter {
    public static final int MAX_HEALTH = 20;
    private int BASE_DAMAGE = 5;

    private Direction lastDirection;

    private Queue<Fire> fireBalls;
    private Item[] items;

    public Hero(Position position) {
        super(position);
        setHealth(MAX_HEALTH);
        setDamage(BASE_DAMAGE);
        lastDirection = Direction.RIGHT;

        fireBalls = new LinkedList<Fire>();
        for (int i = 0; i < 3; i++) fireBalls.add(new Fire(position));

        items = new Item[3];
    }

    public int getBASE_DAMAGE() {
        return BASE_DAMAGE;
    }

    public void setBASE_DAMAGE(int BASE_DAMAGE) {
        this.BASE_DAMAGE = BASE_DAMAGE;
    }

    public Direction getLastDirection() {
        return lastDirection;
    }

    public void setLastDirection(Direction lastDirection) {
        this.lastDirection = lastDirection;
    }

    public Queue<Fire> getFireBalls() {
        return fireBalls;
    }

    public void setFireBalls(Queue<Fire> fireBalls) {
        this.fireBalls = fireBalls;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public void collect(Item item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = item;
                item.getCollected();
            }
        }
    }

    public void control(Command command) {
        if (command.getDirection() != null) {
            lastDirection = command.getDirection();
            for (GameCharacter enemy : currentRoom.getEnemies()) {
                if (enemy.getPosition().equals(getPosition().plus(command.getDirection().asVector()))) {
                    attack(enemy);
                    return;
                }
            }
            for (Passage passage : currentRoom.getPassages()) {
                if ((getPosition().equals(passage.getPosition()) || getPosition().plus(command.getDirection().asVector()).equals(passage.getPosition())) && passage instanceof Door) {
                    Door door = (Door) passage;
                    for (Item item : getItems()) {
                        if (item instanceof Key) {
                            Key key = (Key) item;
                            if (key.getKeyNumber() == door.getUnlockNumber()) key.unlockDoor();
                        }
                    }
                }
            }
            move(command.getDirection().asVector());
            for (Item item : currentRoom.getItems()) {
                if (getPosition().equals(item.getPosition())) {
                    collect(item);
                    break;
                }
            }
        } else {
            if (command.name().equals("FIRE")) {
                try {
                    launchFire(lastDirection);
                } catch (NullPointerException e) {
                    gui.setStatus("Já não tens bolas de fogo!");
                }
            }
        }
        statusBar.setStatusTiles();
    }

    public void launchFire(Direction direction) {
        Fire fireBall = fireBalls.poll();
        fireBall.setPosition(getPosition());
        FireBallThread fireBallThread = new FireBallThread(direction, fireBall);
        fireBallThread.run();
        gui.removeImage(fireBall);
    }

    public void leaveRoom() {
        currentRoom.setLeaving(false);
        currentRoom.setExit((Passage) null);
    }
    @Override
    public String getName() {
        return "Hero";
    }
}
