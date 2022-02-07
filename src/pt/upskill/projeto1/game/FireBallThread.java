package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.Explosion;
import pt.upskill.projeto1.gui.FireTile;
import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;

import static pt.upskill.projeto1.game.Engine.gui;

public class FireBallThread extends Thread {

    private Direction direction;
    private FireTile fireTile;

    public FireBallThread(Direction direction, FireTile fireTile) {
        this.direction = direction;
        this.fireTile = fireTile;
    }

    @Override
    public void run() {
        boolean control = true;
        Explosion explosion = null;
        while (control) {
            Position nextPosition = fireTile.getPosition().plus(direction.asVector());
            fireTile.setPosition(nextPosition);
            gui.addImage(fireTile);
            try {
                if (fireTile.validateImpact()) {
                    // FireBall continue
                    sleep(300);
                }else{
                    // FireBall should explode and stop is job
                    explosion = new Explosion(nextPosition);
                    gui.addImage(explosion);
                    sleep(500);
                    control = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Remove FireBall of {ImageMatrixGUI}
        gui.removeImage(fireTile);

        try {
            gui.removeImage(explosion);
        } catch (NullPointerException e) {
        }
    }
}