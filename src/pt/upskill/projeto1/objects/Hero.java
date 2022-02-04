package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.List;

public class Hero extends GameCharacter {
    public Hero(Position position) {
        super(position);
        setHP(20);
        setDamage(3);
    }

    @Override
    public String getName() {
        return "Hero";
    }
}
