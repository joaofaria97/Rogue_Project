package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.ArrayList;
import java.util.List;

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
        List<Direction> directions= new ArrayList<Direction>();
        for (Direction direction : Direction.values()) {
            if (direction.isDiagonal()) directions.add(direction);
        }
        setDirections(directions);
    }
}
