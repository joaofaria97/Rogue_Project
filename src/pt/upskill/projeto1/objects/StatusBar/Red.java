package pt.upskill.projeto1.objects.StatusBar;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Element;
import pt.upskill.projeto1.rogue.utils.Position;

public class Red extends Element implements ImageTile {

    public Red(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Red";
    }
}
