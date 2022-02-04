package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.rogue.utils.Position;

public abstract class Enemy extends Character {
    int chaseDistance;

    public Enemy(Position position) {
        super(position);
    }

    public int getChaseDistance() {
        return chaseDistance;
    }

    public void setChaseDistance(int chaseDistance) {
        this.chaseDistance = chaseDistance;
    }
}
