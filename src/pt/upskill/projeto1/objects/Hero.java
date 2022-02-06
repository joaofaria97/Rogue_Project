package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.List;
import java.util.Queue;

public class Hero extends GameCharacter {
    private Queue<Fire> fireBalls;
    public Hero(Position position) {
        super(position);
        setHP(20);
        setDamage(3);
        for (int i = 0; i < 3; i++) fireBalls.add(new Fire());
    }

    @Override
    public String getName() {
        return "Hero";
    }
}
