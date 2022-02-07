package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.Fire;
import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.*;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static pt.upskill.projeto1.game.Engine.*;

public class Room {
    private final int ROOM_WIDTH = 10;
    private final int ROOM_HEIGHT = 10;

    private int roomNumber;

    private Hero hero;
    private Position seedPosition;

    Direction lastDirection;

    private List<ImageTile> tiles;
    private List<Element> obstacles;
    private List<Enemy> enemies;
    private List<Passage> passages;

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
                    if (fileLine.length() > 1) {
                        String[] passageInfo = fileLine.substring(2).split(" ");
                        int passageNumber = Integer.parseInt(passageInfo[0]);
                        int toPassageNumber = Integer.parseInt(passageInfo[3]);
                        int toRoomNumber = Integer.parseInt(passageInfo[2].split("room")[1].split(".txt")[0]);
                        char ch = passageInfo[1].charAt(0);
                        if (ch == 'E') passages.add(new DoorOpen(null, passageNumber, toPassageNumber, toRoomNumber));
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
                                Direction leaveDirection = j == ROOM_HEIGHT - 1 ? Direction.DOWN : Direction.UP;
                                passage.setLeaveDirection(leaveDirection);
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
        gui.setStatus("Sala " + currentRoom.getRoomNumber());
        if (command.getDirection() != null) {
            controlEnemies();
//            clearDead();
        }
        controlHero(command);
        clearDead();
    }

    private void controlHero(Command command) {
        leaving = false;
        leavingPassage = null;
        if (command.getDirection() != null) {
            lastDirection = command.getDirection();

            Position nextPosition = hero.getPosition().plus(command.getDirection().asVector());

            for (Passage passage : passages) {
                if ((passage.getPosition().equals(hero.getPosition()) && command.getDirection().equals(passage.getLeaveDirection()))
                        || passage.getPosition().equals(nextPosition)) {
                    leaving = true;
                    leavingPassage = passage;
                    return;
                }
            }
            for (Enemy enemy : enemies) {
                if (nextPosition.equals(enemy.getPosition())) hero.attack(enemy);
            }
            if (legalMove(nextPosition)) hero.move(command.getDirection().asVector());

        } else {
            if (command.name().equals("FIRE")) {
                try {
                    statusBar.removeFireBall();
                    hero.launchFire(lastDirection);
                } catch (NullPointerException e) {
                    gui.setStatus("Já não tens bolas de fogo!");
                }
            }
//            disparar
//            collectibles
        }

    }

    private void controlEnemies() {
        Position heroPosition = hero.getPosition();

        for (Enemy enemy : enemies) {
            List<Direction> directions = enemy.getDirections();
            Direction nextDirection = null;
            Position nextPosition = null;

            if (enemy.getPosition().distance(heroPosition) <= enemy.getChaseDistance()) {
                // Chase
                int minDistance = ROOM_HEIGHT + ROOM_WIDTH;
                for (Direction direction : directions) {
                    nextPosition = enemy.getPosition().plus(direction.asVector());

                    if (nextPosition.equals(heroPosition) || enemy.getPosition().distance(heroPosition) == 1) {
                        enemy.attack(hero);
                        break;
                    }
                    else if (nextPosition.distance(hero.getPosition()) < minDistance && legalMove(nextPosition)) {
                        nextDirection = direction;
                        minDistance = nextPosition.distance(hero.getPosition());
                    }
                }

            } else {
                // Roam
                do {
                    int rand = (int) (Math.random() * directions.size());
                    nextDirection = directions.get(rand);
                    nextPosition = enemy.getPosition().plus(nextDirection.asVector());
                } while (!legalMove(nextPosition));
            }
            try {
                enemy.move(nextDirection.asVector());
            } catch (NullPointerException e) {
            }
        }
    }

    private boolean legalMove(Position position) {
        boolean move = true;
        for (Element obstacle : obstacles) {
            if (position.equals(obstacle.getPosition())) move = false;
        }
        return move;
    }

    private void clearDead() {
        List<Enemy> dead = new ArrayList<Enemy>();
        for (Enemy enemy : enemies) {
            if (enemy.getHP() <= 0) {
                dead.add(enemy);
                ImageMatrixGUI.getInstance().removeImage(enemy);
            }
        }
        tiles.removeAll(dead);
        enemies.removeAll(dead);
        obstacles.removeAll(dead);
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                '}';
    }
}
