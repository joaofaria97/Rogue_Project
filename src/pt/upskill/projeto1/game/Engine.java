package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Hero;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Engine {

    private Room currentRoom;
    private List<Room> rooms;
    private Hero hero;
    private List<ImageTile> tiles;
    private ImageMatrixGUI gui;

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
        currentRoom.setHero(hero);
        tiles = currentRoom.getTiles();

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
                if (currentRoom.getLeaveRoom() != null) changeRoom(currentRoom.getLeaveRoom());
            }
        }
    }

    public void changeRoom(Passage transition) {
        Room nextRoom = null;
        hero = currentRoom.getHero();

        for (Room room : rooms) {
            if (room.getRoomNumber() == transition.getToRoomNumber()) {
                nextRoom = room;
                tiles = room.getTiles();
            }
        }

        for (Passage passage : nextRoom.getPassages()) {
            if (passage.getPassageNumber() == transition.getToPassageNumber()) hero.setPosition(passage.getPosition());
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
