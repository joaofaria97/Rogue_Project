package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.*;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Engine {

    public Hero hero;
    public static ImageMatrixGUI gui;
    public static Room currentRoom;
    public static StatusBar statusBar;

    private List<Room> rooms;
    private List<ImageTile> tiles;

    public void init(){
        gui = ImageMatrixGUI.getInstance();
        hero = new Hero(null);

        rooms = new ArrayList<Room>();
        File roomDirectory = new File("rooms");
        File[] roomFiles = roomDirectory.listFiles();

        for (File roomFile : roomFiles) {
            rooms.add(new Room(roomFile));
        }

        currentRoom = rooms.get(0);
        tiles = currentRoom.getTiles();

        currentRoom.setHero(hero);
//        for(Room room : rooms) {
//            System.out.printf("Room %d\n", room.getRoomNumber());
//            System.out.println(room.getPassages());
//        }
        statusBar = new StatusBar();

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
            if (command.getKeyCode() == keyPressed) {
                currentRoom.play(command);
                if (currentRoom.getHero().isLeaving()) changeRoom(currentRoom.getHero().getLeavingPassage());
            }
        }
    }

    public void changeRoom(Passage leavingPassage) {
        Room nextRoom = null;
        hero = currentRoom.getHero();

        for (Room room : rooms) {
            if (room.getRoomNumber() == leavingPassage.getToRoomNumber()) {
                nextRoom = room;
                tiles = room.getTiles();
            }
        }

        for (Passage passage : nextRoom.getPassages()) {
            if (passage.getPassageNumber() == leavingPassage.getToPassageNumber()) hero.setPosition(passage.getPosition());
        }

        nextRoom.setHero(hero);
        currentRoom = nextRoom;
        gui.newImages(tiles);
    }

    public static void main(String[] args){
        Engine engine = new Engine();
        engine.init();
    }

}
