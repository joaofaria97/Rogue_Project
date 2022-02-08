package pt.upskill.projeto1.objects.Map;

import pt.upskill.projeto1.objects.Element;
import pt.upskill.projeto1.objects.Obstacle;
import pt.upskill.projeto1.rogue.utils.Position;

public class Wall extends Element implements Obstacle {
    public Wall(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Wall";
    }
}
