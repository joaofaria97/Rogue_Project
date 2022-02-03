package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.rogue.utils.Position;

public class Wall extends Element {
    public Wall(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Wall";
    }
}
