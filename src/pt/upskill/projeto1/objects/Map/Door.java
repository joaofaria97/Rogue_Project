package pt.upskill.projeto1.objects.Map;

import pt.upskill.projeto1.objects.Items.Key;
import pt.upskill.projeto1.objects.Map.Passage;
import pt.upskill.projeto1.rogue.utils.Position;

public class Door extends Passage {
    private boolean locked;
    private int unlockNumber;
    private String name;

    public Door(Position position, int passageNumber, int toPassageNumber, int toRoomNumber, int unlockNumber) {
        super(position, passageNumber, toPassageNumber, toRoomNumber);
        this.unlockNumber = unlockNumber;
        setLocked(unlockNumber != 0);
        setName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setName() {
        String door = isLocked() ? "DoorClosed" : "DoorOpen";
        setName(door);
    }

    public int getUnlockNumber() {
        return unlockNumber;
    }

    public void setUnlockNumber(int unlockNumber) {
        this.unlockNumber = unlockNumber;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
        setName();
    }

    public void lock() {
        setLocked(true);
    }

    public void unlock() {
        setLocked(false);
    }

    @Override
    public String getName() {
        return name;
    }
}
