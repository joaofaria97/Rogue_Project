package pt.upskill.projeto1.gui;

import pt.upskill.projeto1.objects.Element;
import pt.upskill.projeto1.objects.Characters.Enemy;
import pt.upskill.projeto1.rogue.utils.Position;

import static pt.upskill.projeto1.game.Engine.currentRoom;

public class Fire extends Element implements FireTile {
//    private Position position;

    public Fire(Position position) {
        super(position);
//        this.position = position;
    }

    @Override
    public boolean validateImpact() {
        for (Element obstacle : currentRoom.getObstacles()) {
            if (obstacle.getPosition().equals(getPosition())) {
                if (obstacle instanceof Enemy) {
                    Enemy enemy = (Enemy) obstacle;
                    enemy.die();
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
