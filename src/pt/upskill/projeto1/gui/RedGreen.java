package pt.upskill.projeto1.gui;

import pt.upskill.projeto1.objects.Element;
import pt.upskill.projeto1.rogue.utils.Position;

public class RedGreen extends Element implements ImageTile {

    public RedGreen(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "RedGreen";
    }
}
