package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Hero;

public abstract class Passage implements ImageTile {
    private Hero hero;
    private int fromRoom;
    private int toRoom;
}
