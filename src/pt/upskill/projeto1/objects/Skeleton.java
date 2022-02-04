package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.rogue.utils.Position;

public class Skeleton extends Enemy {

    public Skeleton(Position position) {
        super(position);
        setHP(5);
        setDamage(1);
    }

    @Override
    public String getName() {
        return "Skeleton";
    }

    @Override
    public int getChaseDistance() {
        return 3;
    }
}
