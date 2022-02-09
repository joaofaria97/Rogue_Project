package pt.upskill.projeto1.objects.Map;

import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;

public class Hole extends Passage {

    public Hole(Position position, int passageNumber, int toPassageNumber, int toRoomNumber) {
        super(position, passageNumber, toPassageNumber, toRoomNumber);
    }

    @Override
    public String getName() {
        return "Black";
    }
}
