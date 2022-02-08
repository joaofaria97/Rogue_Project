package pt.upskill.projeto1.objects.Map;

import pt.upskill.projeto1.game.Command;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.*;
import pt.upskill.projeto1.objects.Characters.Enemy;
import pt.upskill.projeto1.objects.Characters.Hero;
import pt.upskill.projeto1.objects.Characters.Skeleton;
import pt.upskill.projeto1.objects.Characters.Thief;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static pt.upskill.projeto1.game.Engine.*;

public class Room {
    public static final int ROOM_WIDTH = 10;
    public static final int ROOM_HEIGHT = 10;

    private int roomNumber;

    private Hero hero;
    private Position seedPosition;

    Direction lastDirection;

    private List<ImageTile> tiles;
    private List<Element> obstacles;
    private List<Enemy> enemies;
    private List<Passage> passages;
//    private List<GameCharacter> characters;

    private boolean leaving;
    private Passage leavingPassage;

    public Room(File roomFile) {
        this.roomNumber = Integer.parseInt(roomFile.getName().split("room")[1].split(".txt")[0]);

        lastDirection = Direction.RIGHT;

        tiles = new ArrayList<ImageTile>();
        obstacles = new ArrayList<Element>();
        enemies = new ArrayList<Enemy>();
        passages = new ArrayList<Passage>();

        readHeader(roomFile);
        buildMap(roomFile);
    }

    private void readHeader(File roomFile) {
        try {
            Scanner fileScanner = new Scanner(roomFile);
            while (fileScanner.hasNextLine()) {
                String fileLine = fileScanner.nextLine();
                if (fileLine.charAt(0) == '#') {
                    try {
                        String[] lineArray = fileLine.substring(2).split(" ");
                        if (lineArray.length >= 4) {
                            // passage info
                            int passageNumber = Integer.parseInt(lineArray[0]);
                            int toPassageNumber = Integer.parseInt(lineArray[3]);
                            int toRoomNumber = Integer.parseInt(lineArray[2].split("room")[1].split(".txt")[0]);
                            char ch = lineArray[1].charAt(0);
                            if (ch == 'E') passages.add(new DoorOpen(null, passageNumber, toPassageNumber, toRoomNumber));
                            if (ch == 'D') passages.add(new DoorWay(null, passageNumber, toPassageNumber, toRoomNumber));
                        } else {
                            // item info
                        }
                    } catch (StringIndexOutOfBoundsException e) {
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void buildMap(File roomFile) {
        paintFloor();
        try {
            Scanner fileScanner = new Scanner(roomFile);
            int j = 0;
            while (fileScanner.hasNextLine()) {
                String fileLine = fileScanner.nextLine();
                if (fileLine.charAt(0) != '#') {
                    for (int i = 0; i < fileLine.length(); i++) {
                        Position position = new Position(i, j);
                        char ch = fileLine.charAt(i);
                        if (ch == 'H') seedPosition = position;
                        if (ch == 'W') tiles.add(new Wall(position));
                        if (ch == 'S') tiles.add(new Skeleton(position));
                        if (ch == 'T') tiles.add(new Thief(position));

                        if (Character.isDigit(ch)) {
                            for (Passage passage : passages) {
                                if (passage.getPassageNumber() == Integer.parseInt("" + ch)) {
                                    passage.setPosition(position);
                                    tiles.add(passage);
                                };
                            }
                        }
                    }
                    j++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (ImageTile tile : tiles) {
            if (tile instanceof Obstacle) obstacles.add((Element) tile);
            if (tile instanceof Enemy) enemies.add((Enemy) tile);
        }
        setPassageDirections();
    }

    private void setPassageDirections() {
        currentRoom = this;
        for (Passage passage : passages) {
            Position position = passage.getPosition();
            for (Direction direction : Hero.getDirections()) {
                if (Hero.legalMove((position.plus(direction.asVector())))) passage.setLeaveDirection(direction.contrary());
            }
        }
    }
    private void paintFloor() {
        for (int i = 0; i < ROOM_HEIGHT; i++) {
            for (int j = 0; j < ROOM_WIDTH; j++) {
                tiles.add(new Floor(new Position(i, j)));
            }
        }
        double percentage = 0.2;
        int grassTiles = (int) (Math.random() * percentage * ROOM_HEIGHT * ROOM_WIDTH + percentage * ROOM_HEIGHT * ROOM_WIDTH);
        for (int i = 0; i < grassTiles; i++) tiles.add(new Grass(Position.random()));
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
        if (seedPosition != null) hero.setPosition(seedPosition);
        seedPosition = null;
        tiles.add(hero);
    }

    public List<ImageTile> getTiles() {
        return tiles;
    }

    public List<Element> getObstacles() {
        return obstacles;
    }

    public void setObstacles(List<Element> obstacles) {
        this.obstacles = obstacles;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Passage> getPassages() {
        return passages;
    }

    public boolean isLeaving() {
        return leaving;
    }

    public Passage getLeavingPassage() {
        return leavingPassage;
    }

    public void play(Command command) {
        if (command.getDirection() != null) moveEnemies();
        hero.control(command);

        if (hero.isLeaving()) leavingPassage = hero.getLeavingPassage();
    }

    private void moveEnemies() {
        for (Enemy enemy : getEnemies()) enemy.move();
    }
}
