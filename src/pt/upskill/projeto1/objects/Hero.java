package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.game.FireBallThread;
import pt.upskill.projeto1.gui.Fire;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.LinkedList;
import java.util.Queue;

import static pt.upskill.projeto1.game.Engine.statusBar;

public class Hero extends GameCharacter {
    private Queue<Fire> fireBalls;

    public Hero(Position position) {
        super(position);
        setHP(20);
        setDamage(3);
        fireBalls = new LinkedList<Fire>();
        for (int i = 0; i < 3; i++) fireBalls.add(new Fire(position));
    }

    public Queue<Fire> getFireBalls() {
        return fireBalls;
    }

    public void launchFire(Direction direction) {
        Fire fireBall = fireBalls.poll();
        fireBall.setPosition(getPosition());
        FireBallThread fireBallThread = new FireBallThread(direction, fireBall);
        fireBallThread.run();
    }

    @Override
    public String getName() {
        return "Hero";
    }
}
