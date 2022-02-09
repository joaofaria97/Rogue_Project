package pt.upskill.projeto1.objects.Map;

import pt.upskill.projeto1.objects.Items.Item;
import pt.upskill.projeto1.rogue.utils.Position;

public abstract class Weapon extends Item {
    private int damageBoost;
    private int uses;

    protected Weapon(Position position) {
        super(position);
        uses = 3;
    }

    public int getDamageBoost() {
        return damageBoost;
    }

    public void setDamageBoost(int damageBoost) {
        this.damageBoost = damageBoost;
    }

    public int getUses() {
        return uses;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }
}
