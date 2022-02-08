package pt.upskill.projeto1.objects.Map;

import pt.upskill.projeto1.objects.Map.Passage;
import pt.upskill.projeto1.rogue.utils.Position;

public class DoorWay extends Passage {

    public DoorWay(Position position, int passageNumber, int toPassageNumber, int toRoomNumber) {
        super(position, passageNumber, toPassageNumber, toRoomNumber);
    }

    @Override
    public String getName() {
        return "DoorWay";
    }
}
