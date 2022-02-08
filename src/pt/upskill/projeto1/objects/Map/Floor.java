package pt.upskill.projeto1.objects.Map;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Element;
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
