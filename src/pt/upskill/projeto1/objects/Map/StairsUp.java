package pt.upskill.projeto1.objects.Map;

import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;

public class StairsUp extends Passage {

    public StairsUp(Position position, int passageNumber, int toPassageNumber, int toRoomNumber) {
        super(position, passageNumber, toPassageNumber, toRoomNumber);
        setExitDirection();
    }

    public void setExitDirection() {
        super.setExitDirection(Direction.LEFT);
    }

    @Override
    public String getName() {
        return "StairsUp";
    }
}
