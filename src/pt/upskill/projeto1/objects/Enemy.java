package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.game.Passage;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.ArrayList;
import java.util.List;

import static pt.upskill.projeto1.game.Engine.currentRoom;

public abstract class Enemy extends GameCharacter implements Obstacle {
    private int chaseDistance;

    public Enemy(Position position) {
        super(position);
        chaseDistance = 3;
    }

    public int getChaseDistance() {
        return chaseDistance;
    }


    public void move() {
        Hero hero = currentRoom.getHero();

        // attack
        for (Direction direction : Direction.values()) {
            if (hero.getPosition().equals(getPosition().plus(direction.asVector()))) {
                attack(hero);
                return;
            }
        }

        if (hero.getPosition().distance(getPosition()) <= getChaseDistance()) {
            // chase
            int minDistance = currentRoom.ROOM_HEIGHT + currentRoom.ROOM_WIDTH;
            Direction minDirection = null;
            for (Direction direction : getDirections()) {
                Position nextPosition = getPosition().plus(direction.asVector());
                if (nextPosition.distance(hero.getPosition()) < minDistance && legalMove(nextPosition)) {
                    minDistance = nextPosition.distance(hero.getPosition());
                    minDirection = direction;
                }
            }
            move(minDirection.asVector());

        } else {
            // roam
            Direction direction = null;
            do {
                int rand = (int) (Math.random() * getDirections().size());
                direction = getDirections().get(rand);
            } while (!legalMove(getPosition().plus(direction.asVector())));
            move(direction.asVector());
        }
    }

    public static boolean legalMove(Position position) {
        for (Passage passage : currentRoom.getPassages()) {
            if (position.equals(passage.getPosition())) return false;
        }
//        fazer igual para itens
        return GameCharacter.legalMove(position);
    }
}
