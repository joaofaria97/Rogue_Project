package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class Floor extends Element {
    public Floor(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Floor";
    }
}
