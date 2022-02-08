package pt.upskill.projeto1.objects.Characters;

import pt.upskill.projeto1.game.Command;
import pt.upskill.projeto1.game.FireBallThread;
import pt.upskill.projeto1.objects.Map.Passage;
import pt.upskill.projeto1.gui.Fire;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.LinkedList;
import java.util.Queue;

import static pt.upskill.projeto1.game.Engine.*;

public class Hero extends GameCharacter {
    public static final int MAX_HEALTH = 20;
    private int BASE_DAMAGE = 5;
    private Direction lastDirection;
    private boolean leaving;
    private Passage leavingPassage;
    private Queue<Fire> fireBalls;

    public Hero(Position position) {
        super(position);
        setHealth(MAX_HEALTH);
        setDamage(BASE_DAMAGE);
        fireBalls = new LinkedList<Fire>();
        for (int i = 0; i < 3; i++) fireBalls.add(new Fire(position));
    }

    public boolean isLeaving() {
        return leaving;
    }

    public Passage getLeavingPassage() {
        return leavingPassage;
    }

    public void control(Command command) {
        leaving = false;
        if (command.getDirection() != null) {
            lastDirection = command.getDirection();

            for (GameCharacter enemy : currentRoom.getEnemies()) {
                if (enemy.getPosition().equals(getPosition().plus(command.getDirection().asVector()))) {
                    attack(enemy);
                    return;
                }
            }
            for (Passage passage : currentRoom.getPassages()) {
                if (getPosition().plus(command.getDirection().asVector()).equals(passage.getPosition()) ||
                        (getPosition().equals(passage.getPosition()) && command.getDirection().equals(passage.getLeaveDirection()))) {
                    leaving = true;
                    leavingPassage = passage;
                    return;
                }
            }
            move(command.getDirection().asVector());
        } else {
            if (command.name().equals("FIRE")) {
                try {
                    launchFire(lastDirection);
                } catch (NullPointerException e) {
                    gui.setStatus("Já não tens bolas de fogo!");
                }
            }
        }
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
