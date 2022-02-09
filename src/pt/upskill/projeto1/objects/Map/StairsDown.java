package pt.upskill.projeto1.objects.Map;

import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;

public class StairsDown extends Passage {

    public StairsDown(Position position, int passageNumber, int toPassageNumber, int toRoomNumber) {
        super(position, passageNumber, toPassageNumber, toRoomNumber);
        setExitDirection();
    }

    public void setExitDirection() {
        super.setExitDirection(Direction.RIGHT);
    }
    @Override
    public String getName() {
        return "StairsDown";
    }
}
