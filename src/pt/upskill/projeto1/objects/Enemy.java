package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.rogue.utils.Position;

public abstract class Enemy extends GameCharacter implements Obstacle {
    private int chaseDistance;

    public Enemy(Position position) {
        super(position);
        chaseDistance = 4;
    }

    public int getChaseDistance() {
        return chaseDistance;
    }

    public void setChaseDistance(int chaseDistance) {
        this.chaseDistance = chaseDistance;
    }
}
