package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.*;
import pt.upskill.projeto1.objects.Characters.Hero;
import pt.upskill.projeto1.objects.Map.Passage;
import pt.upskill.projeto1.objects.Map.Room;
import pt.upskill.projeto1.objects.StatusBar.StatusBar;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Engine implements Serializable {

    public static int score;

    public Hero hero;
    public static ImageMatrixGUI gui;
    public static Room currentRoom;
    public static StatusBar statusBar;
    public static Object checkPoint;

    private List<Room> rooms;
    private List<ImageTile> tiles;

    public void init(){
        gui = ImageMatrixGUI.getInstance();
        hero = new Hero(null);
        checkPoint = null;
        rooms = new ArrayList<Room>();
        File roomDirectory = new File("rooms");
        File[] roomFiles = roomDirectory.listFiles();

        score = 100;

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
        gui.setStatus("SCORE: " + score);
        if (hero.getHealth() <= 0) {
            System.out.println("MORRESTE");
            System.exit(0);
        }
        if (score <= 0) {
            System.out.println("NÃO SABES JOGAR");
            System.out.println("SCORE: " + score);
            System.exit(0);
        }
    }

    public void changeRoom(Passage exit) {
        hero = currentRoom.getHero();
        hero.leaveRoom();

        gui.clearImages();

        for (Room room : rooms) {
            if (room.getRoomNumber() == exit.getToRoomNumber()) {
                currentRoom = room;
                if (currentRoom.isCheckPoint()) {
                    checkPoint = PipedDeepCopy.copy(this);
                    currentRoom.setCheckPoint(false);
                }
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
