package pt.upskill.projeto1.objects.Characters;

import pt.upskill.projeto1.rogue.utils.Position;

public class Bat extends Enemy {

    public Bat(Position position) {
        super(position);
        setHealth(5);
        setDamage(1);
    }

    @Override
    public String getName() {
        return "Bat";
    }
}
