package pt.upskill.projeto1.objects.Characters;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Element;
import pt.upskill.projeto1.objects.Map.Door;
import pt.upskill.projeto1.objects.Map.Passage;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;

import static pt.upskill.projeto1.game.Engine.*;

public abstract class GameCharacter extends Element {
    public static List<Direction> directions;
    public List<Direction> charDirections;
    private int health;
    private int damage;

    public GameCharacter(Position position) {
        super(position);
        setDirections();
    }

    public static List<Direction> getDirections() {
        return directions;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setDirections() {
        directions = new ArrayList<Direction>();
        for (Direction direction : Direction.values()) {
            if (!direction.isDiagonal()) directions.add(direction);
        }

        charDirections = new ArrayList<Direction>();
        for (Direction direction : Direction.values()) {
            if (!direction.isDiagonal()) charDirections.add(direction);
        }
    }

    public void setDirections(List<Direction> directions) {
        this.directions = directions;
    }

    public List<Direction> getCharDirections() {
        return charDirections;
    }

    public void setCharDirections(List<Direction> charDirections) {
        this.charDirections = charDirections;
    }

    public static boolean legalMove(Position position) {
        if (position.getX() < 0 || position.getX() >= currentRoom.ROOM_WIDTH) return false;
        if (position.getY() < 0 || position.getY() >= currentRoom.ROOM_HEIGHT) return false;
        for (Element obstacle : currentRoom.getObstacles()) {
            if (position.equals(obstacle.getPosition())) {
                return false;
            }
        }
        for (Passage passage : currentRoom.getPassages()) {
            if (passage instanceof Door) {
                if (position.equals(passage.getPosition()) && (((Door) passage).isLocked())) return false;
            }
        }
        return true;
    }


    public void move(Vector2D vector2d) {
        if (legalMove(getPosition().plus(vector2d))) setPosition(getPosition().plus(vector2d));
    }

    public void attack(GameCharacter character) {
        character.takeDamage(damage);
    }

    public void takeDamage(int damage) {
        setHealth(getHealth() - damage);
        if (getHealth() <= 0) die();
    }

    public void die() {
        List<ImageTile> tiles = currentRoom.getTiles();
        tiles.remove(this);
        currentRoom.setTiles(tiles);

        List<Element> obstacles = currentRoom.getObstacles();
        obstacles.remove(this);
        currentRoom.setObstacles(obstacles);

        List<Enemy> enemies = currentRoom.getEnemies();
        enemies.remove(this);
        currentRoom.setEnemies(enemies);

        gui.removeImage(this);

        if (this instanceof Enemy) score += 20;
    }
}