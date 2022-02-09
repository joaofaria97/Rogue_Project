package pt.upskill.projeto1.objects.Map;

import pt.upskill.projeto1.game.Command;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.*;
import pt.upskill.projeto1.objects.Characters.*;
import pt.upskill.projeto1.objects.Items.*;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static pt.upskill.projeto1.game.Engine.*;

public class Room implements Serializable {
    public static final int ROOM_WIDTH = 10;
    public static final int ROOM_HEIGHT = 10;

    private final int roomNumber;

    private Hero hero;
    private Position seedPosition;
    private Direction lastDirection;

    private boolean checkPoint;
    private List<ImageTile> tiles;
    private List<Element> obstacles;
    private List<Enemy> enemies;
    private List<Passage> passages;
    private List<Item> items;

    private boolean leaving;
    private Passage exit;

    // Constructor

    public Room(File roomFile) {
        this.roomNumber = Integer.parseInt(roomFile.getName().split("room")[1].split(".txt")[0]);

        lastDirection = Direction.RIGHT;

        checkPoint = false;
        tiles = new ArrayList<ImageTile>();
        obstacles = new ArrayList<Element>();
        enemies = new ArrayList<Enemy>();
        passages = new ArrayList<Passage>();
        items = new ArrayList<Item>();

        readHeader(roomFile);
        buildMap(roomFile);
        setPassageDirections();
    }

    private void readHeader(File roomFile) {
        try {
            Scanner fileScanner = new Scanner(roomFile);
            while (fileScanner.hasNextLine()) {
                String fileLine = fileScanner.nextLine();
                if (fileLine.charAt(0) == '#') {
                    try {
                        String[] lineArray = fileLine.substring(2).split(" ");
                        if (fileLine.contains("checkpoint")) setCheckPoint(true);
                        if (lineArray.length >= 4) {
                            // passage info
                            int passageNumber = Integer.parseInt(lineArray[0]);
                            int toPassageNumber = Integer.parseInt(lineArray[3]);
                            int toRoomNumber = Integer.parseInt(lineArray[2].split("room")[1].split(".txt")[0]);
                            int unlockNumber = 0;
                            if (lineArray.length == 5) {
                                unlockNumber = Integer.parseInt(lineArray[4].split("key")[1]);
                            }

                            char ch = lineArray[1].charAt(0);
                            if (ch == 'E') passages.add(new DoorWay(null, passageNumber, toPassageNumber, toRoomNumber));
                            if (ch == 'D') passages.add(new Door(null, passageNumber, toPassageNumber, toRoomNumber, unlockNumber));
                            if (ch == '^') passages.add(new StairsUp(null, passageNumber, toPassageNumber, toRoomNumber));
                            if (ch == 'v') passages.add(new StairsDown(null, passageNumber, toPassageNumber, toRoomNumber));
                            if (ch == 'o') passages.add(new Hole(null, passageNumber, toPassageNumber, toRoomNumber));

                        } else if (lineArray.length == 2) {
                            // key info
                            int keyNumber = Integer.parseInt(lineArray[1].split("key")[1]);
                            items.add(new Key(null, keyNumber));
                        }
                    } catch (StringIndexOutOfBoundsException e) {
                    }

                } else break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
                        if (ch == 'G') tiles.add(new BadGuy(position));
                        if (ch == 'B') tiles.add(new Bat(position));

                        if (ch == 'm') tiles.add(new GoodMeat(position));
                        if (ch == 's') tiles.add(new Sword(position));
                        if (ch == 'h') tiles.add(new Hammer(position));
                        if (ch == 'k') {
                            for (Element item : items) {
                                if (item instanceof Key) {
                                    item.setPosition(position);
                                    tiles.add(item);
                                }
                            }
                        }
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
            if (tile instanceof Item && !(tile instanceof Key)) items.add((Item) tile);
        }
    }

    private void setPassageDirections() {
        currentRoom = this;
        for (Passage passage : passages) {
            Position position = passage.getPosition();
            for (Direction direction : Hero.getDirections()) {
                if (Hero.legalMove((position.plus(direction.asVector())))) passage.setExitDirection(direction.contrary());
            }
        }
    }

    // Getters

    public int getRoomNumber() {
        return roomNumber;
    }

    public Hero getHero() {
        return hero;
    }

    public Position getSeedPosition() {
        return seedPosition;
    }

    public Direction getLastDirection() {
        return lastDirection;
    }

    public boolean isCheckPoint() {
        return checkPoint;
    }

    public List<ImageTile> getTiles() {
        return tiles;
    }

    public List<Element> getObstacles() {
        return obstacles;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Passage> getPassages() {
        return passages;
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean isLeaving() {
        return leaving;
    }

    public Passage getExit() {
        return exit;
    }

    // Setters

    public void setHero(Hero hero) {
        this.hero = hero;
        if (seedPosition != null) hero.setPosition(seedPosition);
        seedPosition = null;
        tiles.add(hero);
    }

    public void setSeedPosition(Position seedPosition) {
        this.seedPosition = seedPosition;
    }

    public void setLastDirection(Direction lastDirection) {
        this.lastDirection = lastDirection;
    }

    public void setCheckPoint(boolean checkPoint) {
        this.checkPoint = checkPoint;
    }

    public void setTiles(List<ImageTile> tiles) {
        this.tiles = tiles;
    }

    public void setObstacles(List<Element> obstacles) {
        this.obstacles = obstacles;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public void setPassages(List<Passage> passages) {
        this.passages = passages;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setLeaving(boolean leaving) {
        this.leaving = leaving;
    }

    public void setExit(Passage exit) {
        this.exit = exit;
    }

    // Control methods

    public void play(Command command) {
        if (command.getDirection() != null) {
            moveEnemies();
            setExit(command.getDirection());
        }
        hero.control(command);
    }

    private void moveEnemies() {
        for (Enemy enemy : getEnemies()) enemy.move();
    }

    public void setExit(Direction direction) {
        for (Passage passage : getPassages()) {
            if (hero.getPosition().equals(passage.getPosition()) && direction.equals(passage.getExitDirection())) {
                setLeaving(true);
                setExit(passage);
            }
        }
    }
}
