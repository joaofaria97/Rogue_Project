package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.Explosion;
import pt.upskill.projeto1.gui.FireTile;
import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.Serializable;

import static pt.upskill.projeto1.game.Engine.*;

public class FireBallThread extends Thread implements Serializable {

    private Direction direction;
    private FireTile fireTile;

    public FireBallThread(Direction direction, FireTile fireTile) {
        this.direction = direction;
        this.fireTile = fireTile;
        System.out.println(currentRoom.getTiles().size());
    }

    @Override
    public void run() {
        boolean control = true;
        Explosion explosion = null;
        gui.addImage(fireTile);

        while (control) {

            Position nextPosition = fireTile.getPosition().plus(direction.asVector());
            fireTile.setPosition(nextPosition);

            try {
                if (fireTile.validateImpact()) {
                    // FireBall continue
                    sleep(300);
                }else{
                    // FireBall should explode and stop is job
                    explosion = new Explosion(nextPosition);
                    gui.addImage(explosion);
                    sleep(500);
                    gui.removeImage(explosion);
                    gui.removeImage(fireTile);
                    control = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}