package pt.upskill.projeto1.gui;

import pt.upskill.projeto1.objects.Element;
import pt.upskill.projeto1.objects.Characters.Enemy;
import pt.upskill.projeto1.objects.Map.Passage;
import pt.upskill.projeto1.rogue.utils.Position;

import static pt.upskill.projeto1.game.Engine.currentRoom;
import static pt.upskill.projeto1.game.Engine.gui;

public class Fire extends Element implements FireTile {

    public Fire(Position position) {
        super(position);
    }

    @Override
    public boolean validateImpact() {
        boolean impact = true;
        Enemy target = null;
        for (Element obstacle : currentRoom.getObstacles()) {
            if (obstacle.getPosition().equals(getPosition())) {
                if (obstacle instanceof Enemy) {
                    target = (Enemy) obstacle;
                }
                impact = false;
            }
        }

        try {
            target.die();
        } catch (NullPointerException e) {
        }

        for (Passage passage : currentRoom.getPassages()) {
            if (passage.getPosition().equals(getPosition())) impact = false;
        }
        return impact;
    }

    @Override
    public String getName() {
        return "Fire";
    }
}
