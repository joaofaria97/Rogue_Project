package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;

public abstract class Character extends Damageable {
    private List<Direction> directions;

    public Character(Position position) {
        super(position);
        setDirections();
    }

    public List<Direction> getDirections() {
        return directions;
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
}