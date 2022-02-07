package pt.upskill.projeto1.gui;

import pt.upskill.projeto1.gui.FireTile;
import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.objects.Element;
import pt.upskill.projeto1.objects.GameCharacter;
import pt.upskill.projeto1.rogue.utils.Position;

import static pt.upskill.projeto1.game.Engine.currentRoom;
import static pt.upskill.projeto1.game.Engine.gui;

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
            if (obstacle.getPosition().equals(position)) {
                if (obstacle instanceof GameCharacter) {
                    ((GameCharacter) obstacle).setHealth(0);
                    gui.removeImage(obstacle);
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public String getName() {
        return "Fire";
    }
}
