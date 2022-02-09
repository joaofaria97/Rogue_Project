package pt.upskill.projeto1.objects.Items;

import pt.upskill.projeto1.rogue.utils.Position;

public class Hammer extends Item {

    public Hammer(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Hammer";
    }
}
