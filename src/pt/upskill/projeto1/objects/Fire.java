package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.FireTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class Fire implements FireTile {
    private Position position;
    private boolean impact;

    public Fire() {}

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isImpact() {
        return impact;
    }

    public void setImpact(boolean impact) {
        this.impact = impact;
    }

    @Override
    public boolean validateImpact() {
        return impact;
    }

    @Override
    public String getName() {
        return "Fire";
    }
}
