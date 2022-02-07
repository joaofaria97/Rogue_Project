package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;

import static pt.upskill.projeto1.game.Engine.statusBar;

public abstract class GameCharacter extends Element {
    protected static List<Direction> directions;
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

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setDirections() {
        directions = new ArrayList<Direction>();
        for (Direction direction : Direction.values()) {
            if (!direction.isDiagonal()) directions.add(direction);
        }
    }

    public void move(Vector2D vector2d) {
        setPosition(getPosition().plus(vector2d));
    }

    public void attack(GameCharacter character) {
        character.setHealth(character.getHealth() - this.damage);
        if (character instanceof Hero) statusBar.setHealthBar();
//        System.out.printf("%s(HP:%d) attacks %s(HP:%d)!\n", getName(), getHP(), character.getName(), character.getHP());
    }
}