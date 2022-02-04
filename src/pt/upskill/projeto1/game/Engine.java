package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Floor;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.rogue.utils.Position;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Engine {

    private Room room;
    private Hero hero;
    private List<ImageTile> tiles;

    public void init(){
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        hero = new Hero(null);
        room = new Room("rooms/room0.txt", hero);

        tiles = room.getTiles();
        System.out.println(tiles);
        gui.setEngine(this);
        gui.newImages(tiles);
        gui.go();

        gui.setStatus("O jogo começou!");

        while (true){
            gui.update();
        }
    }

    public void notify(int keyPressed){
        for (Command command : Command.values()) {
            if (command.getKeyCode() == keyPressed) room.play(command);
        }
    }

    public static void main(String[] args){
        Engine engine = new Engine();
        engine.init();
    }

}
