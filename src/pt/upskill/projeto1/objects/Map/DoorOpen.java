package pt.upskill.projeto1.objects.Map;

import pt.upskill.projeto1.objects.Map.Passage;
import pt.upskill.projeto1.rogue.utils.Position;

public class DoorOpen extends Passage {

    public DoorOpen(Position position, int passageNumber, int toPassageNumber, int toRoomNumber) {
        super(position, passageNumber, toPassageNumber, toRoomNumber);
    }

    @Override
    public String getName() {
        return "DoorOpen";
    }
}
