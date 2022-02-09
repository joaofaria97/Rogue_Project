package pt.upskill.projeto1.objects.Items;

import pt.upskill.projeto1.objects.Element;
import pt.upskill.projeto1.objects.Map.Door;
import pt.upskill.projeto1.objects.Map.Passage;
import pt.upskill.projeto1.rogue.utils.Position;

import static pt.upskill.projeto1.game.Engine.currentRoom;

public class Key extends Item {
    private int keyNumber;

    public Key(Position position, int keyNumber) {
        super(position);
        this.keyNumber = keyNumber;
    }

    public int getKeyNumber() {
        return keyNumber;
    }

    public void setKeyNumber(int keyNumber) {
        this.keyNumber = keyNumber;
    }

    public void unlockDoor() {
        for (Passage passage : currentRoom.getPassages()) {
            if (passage instanceof Door) {
                Door door = (Door) passage;
                if (door.getUnlockNumber() == getKeyNumber()) door.unlock();
            }
        }
        // drop item
        getCollected();
    }

    @Override
    public String getName() {
        return "Key";
    }
}
