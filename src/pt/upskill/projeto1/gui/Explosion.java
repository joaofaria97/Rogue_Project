package pt.upskill.projeto1.gui;

import pt.upskill.projeto1.objects.Element;
import pt.upskill.projeto1.rogue.utils.Position;

public class Explosion extends Element {

    public Explosion(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Explosion";
    }
}
