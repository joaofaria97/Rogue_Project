package pt.upskill.projeto1.objects.StatusBar;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Element;
import pt.upskill.projeto1.rogue.utils.Position;

public class Green extends Element implements ImageTile {

    public Green(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Green";
    }
}
