package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.*;
import pt.upskill.projeto1.objects.Characters.Hero;
import pt.upskill.projeto1.objects.Map.Passage;
import pt.upskill.projeto1.objects.Map.Room;
import pt.upskill.projeto1.objects.StatusBar.StatusBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
                if (currentRoom.isLeaving()) changeRoom(currentRoom.getExit());
            }
        }
    }

    public void changeRoom(Passage exit) {
        hero = currentRoom.getHero();
        hero.leaveRoom();

        gui.clearImages();

        for (Room room : rooms) {
            if (room.getRoomNumber() == exit.getToRoomNumber()) {
                currentRoom = room;
                tiles = currentRoom.getTiles();
            }
        }
        for (Passage passage : currentRoom.getPassages()) {
            if (passage.getPassageNumber() == exit.getToPassageNumber()) hero.setPosition(passage.getPosition());
        }
        currentRoom.setHero(hero);
        gui.newImages(tiles);
    }

    public static void main(String[] args){
        Engine engine = new Engine();
        engine.init();
    }
}
