package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.FireTile;
import pt.upskill.projeto1.rogue.utils.Position;

import static pt.upskill.projeto1.game.Engine.currentRoom;

public class Fire implements FireTile {
    private Position position;

    public Fire(Position position) {
        this.position = position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean validateImpact() {
        for (Element obstacle : currentRoom.getObstacles()) {
            if (obstacle.getPosition().equals(position)) return false;
        }
        return true;
    }

    @Override
    public String getName() {
        return "Fire";
    }
}
