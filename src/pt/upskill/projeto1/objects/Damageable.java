package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.rogue.utils.Position;

public abstract class Damageable extends Element {
    private int HP;
    private int damage;

    public Damageable(Position position) {
        super(position);
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void attack(Damageable damageable) {
        damageable.setHP(damageable.getHP() - this.damage);
    }
}
