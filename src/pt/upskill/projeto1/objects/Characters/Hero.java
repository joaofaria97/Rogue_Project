package pt.upskill.projeto1.objects.Characters;

import pt.upskill.projeto1.game.Command;
import pt.upskill.projeto1.game.FireBallThread;
import pt.upskill.projeto1.objects.Element;
import pt.upskill.projeto1.objects.Items.GoodMeat;
import pt.upskill.projeto1.objects.Items.Item;
import pt.upskill.projeto1.objects.Items.Key;
import pt.upskill.projeto1.objects.Map.Door;
import pt.upskill.projeto1.objects.Map.Hole;
import pt.upskill.projeto1.objects.Map.Passage;
import pt.upskill.projeto1.gui.Fire;
import pt.upskill.projeto1.objects.Map.Weapon;
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

    private int damage;

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
            if (item instanceof GoodMeat) {
                setHealth(MAX_HEALTH);
                item.getCollected();
                return;
            }
            if (items[i] == null) {
                items[i] = item;
                item.getCollected();
                statusBar.updateItems();
                return;
            }
        }
    }

    public void control(Command command) {
        if (command.getDirection() != null) {
            --score;
            lastDirection = command.getDirection();
            for (GameCharacter enemy : currentRoom.getEnemies()) {
                if (enemy.getPosition().equals(getPosition().plus(command.getDirection().asVector()))) {
                    for (int i = 0; i < getItems().length; i++) { //Item item : getItems()) {
                        Item item = getItems()[i];
                        if (item instanceof Weapon) {
                            Weapon weapon = (Weapon) item;
                            int uses = weapon.getUses();
                            weapon.setUses(uses - 1);
                            if (uses - 1 == 0) {
                                dropItem(i);
                                item.vanish();
                                setDamage(damage - weapon.getDamageBoost());
                            }
                        }
                    }
                    attack(enemy);
                    return;
                }
            }
            for (Passage passage : currentRoom.getPassages()) {
                if ((getPosition().equals(passage.getPosition()) || getPosition().plus(command.getDirection().asVector()).equals(passage.getPosition()))) {
                    if (passage instanceof Door) {
                        Door door = (Door) passage;
                        for (Item item : getItems()) {
                            if (item instanceof Key) {
                                Key key = (Key) item;
                                if (key.getKeyNumber() == door.getUnlockNumber()) key.unlockDoor();
                            }
                        }
                    }
                    if (passage instanceof Hole) {
                        System.out.println("JOGO FINALIZADO");
                        System.out.println("SCORE: " + score);
                        System.exit(0);
                    }
                }
            }
            move(command.getDirection().asVector());
            for (Item item : currentRoom.getItems()) {
                if (getPosition().equals(item.getPosition())) {
                    if (item instanceof Weapon) setDamage(damage + (((Weapon) item).getDamageBoost()));
                    collect(item);
                    break;
                }
            }
        } else {
            if (command.name().equals("FIRE")) {
                try {
                    launchFire();
                } catch (NullPointerException e) {
                    gui.setStatus("Já não tens bolas de fogo!");
                }
            }
            if (command.name().contains("DROP")) {
                int number = Integer.parseInt(command.name().split("DROP_ITEM")[1]);
                dropItem(number);
            }
        }
        statusBar.setStatusTiles();
    }

    public void launchFire() {
        Fire fireBall = fireBalls.poll();
        fireBall.setPosition(getPosition());
        FireBallThread fireBallThread = new FireBallThread(lastDirection, fireBall);
        fireBallThread.start();
    }

    public void dropItem(int index) {
        index--;
        try {
            Item item = items[index];

            int i = 0;
            for (; i < getDirections().size(); i++) {
                if (getDirections().get(i).equals(lastDirection)) break;
            }

            Position nextPosition = getPosition().plus(lastDirection.asVector());
            while (!Enemy.legalMove(nextPosition)) {
                nextPosition = getPosition().plus(getDirections().get(i % getDirections().size()).asVector());
                i++;
                if (i >= 2 * getDirections().size()) return;
            }

            item.setPosition(nextPosition);
            item.getDropped();
            items[index] = null;
        } catch (NullPointerException e) {
        }
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
