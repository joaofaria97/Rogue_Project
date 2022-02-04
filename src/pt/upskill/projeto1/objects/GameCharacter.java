package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;

public abstract class GameCharacter extends Element {
    protected List<Direction> directions;
    private int HP;
    private int damage;

    public GameCharacter(Position position) {
        super(position);
        setDirections();
    }

    public List<Direction> getDirections() {
        return directions;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
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
        character.setHP(character.getHP() - this.damage);
//        System.out.printf("%s(HP:%d) attacks %s(HP:%d)!\n", getName(), getHP(), character.getName(), character.getHP());
    }
}