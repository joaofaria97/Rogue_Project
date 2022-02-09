package pt.upskill.projeto1.objects.Items;

import pt.upskill.projeto1.rogue.utils.Position;

public class Sword extends Item {

    public Sword(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Sword";
    }
}
