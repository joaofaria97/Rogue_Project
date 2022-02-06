package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.FireTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class Fire extends Element implements FireTile {

    protected Fire(Position position) {
        super(position);
    }

    @Override
    public boolean validateImpact() {
        return false;
    }

    @Override
    public String getName() {
        return "Fire";
    }
}
