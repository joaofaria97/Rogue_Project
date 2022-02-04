package pt.upskill.projeto1.game;

import pt.upskill.projeto1.rogue.utils.Direction;

import java.awt.event.KeyEvent;

public enum Command {
    MOVE_UP (KeyEvent.VK_UP, Direction.UP),
    MOVE_DOWN (KeyEvent.VK_DOWN, Direction.DOWN),
    MOVE_LEFT (KeyEvent.VK_LEFT, Direction.LEFT),
    MOVE_RIGHT (KeyEvent.VK_RIGHT, Direction.RIGHT),
    DROP_ITEM1 (KeyEvent.VK_1, null),
    DROP_ITEM2 (KeyEvent.VK_2, null),
    DROP_ITEM3 (KeyEvent.VK_3, null),
    FIRE (KeyEvent.VK_SPACE, null);

    private int keyCode;
    private Direction direction;

    Command(int keyCode, Direction direction) {
        this.keyCode = keyCode;
        this.direction = direction;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public Direction getDirection() {
        return direction;
    }
}
