package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.ArrayList;

public class Thief extends Enemy {

    public Thief(Position position) {
        super(position);
        setHealth(8);
        setDamage(2);
    }

    @Override
    public String getName() {
        return "Thief";
    }

    @Override
    public void setDirections() {
        directions = new ArrayList<Direction>();
        for (Direction direction : Direction.values()) {
            if (direction.isDiagonal()) directions.add(direction);
        }
    }
}
