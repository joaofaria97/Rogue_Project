package pt.upskill.projeto1.objects.Map;

import pt.upskill.projeto1.objects.Element;
import pt.upskill.projeto1.rogue.utils.Position;

public class Grass extends Element {
    public Grass(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Grass";
    }
}
