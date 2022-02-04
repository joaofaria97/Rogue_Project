package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.*;
import pt.upskill.projeto1.objects.GameCharacter;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Room {
    private final int ROOM_WIDTH = 10;
    private final int ROOM_HEIGHT = 10;

    private int roomNumber;
    private String fileName;

    private Hero hero;
    private Position seedPosition;

    private List<ImageTile> tiles;
    private List<Element> obstacles;
    private List<Enemy> enemies;

    private List<Passage> passages;
    private Passage exit;

    public Room(String fileName, int roomNumber) {
        this.roomNumber = roomNumber;
        this.fileName = fileName;

        tiles = new ArrayList<ImageTile>();
        obstacles = new ArrayList<Element>();
        enemies = new ArrayList<Enemy>();
        passages = new ArrayList<Passage>();

        try {
            Scanner fileScanner = new Scanner(new File(fileName));
            readHeader(fileScanner);
            buildMap(fileScanner);
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

    private void readHeader(Scanner fileScanner) {
        while (fileScanner.hasNextLine()) {
            String fileLine = fileScanner.nextLine();
            if (fileLine.charAt(0) == '#') {
                if (fileLine.length() > 1) {
                    String[] passageInfo = fileLine.substring(2).split(" ");
                    int toRoom = Integer.parseInt(passageInfo[2].split("room")[1].split(".txt")[0]);
                    int passageNumber = Integer.parseInt(passageInfo[0]);
                    int toPassage = Integer.parseInt(passageInfo[3]);
                    if (passageInfo[1].equals("E")) passages.add(new DoorOpen(null, passageNumber, toRoom, toPassage));
                }
            }
        }
    }

    private void buildMap(Scanner fileScanner) {
        paintFloor();
        int j = 0;
        while (fileScanner.hasNextLine()) {
            String fileLine = fileScanner.nextLine();
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
        for (ImageTile tile : tiles) {
            if (tile instanceof Obstacle) obstacles.add((Element) tile);
            if (tile instanceof Enemy) enemies.add((Enemy) tile);
        }
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
        if (seedPosition != null) hero.setPosition(seedPosition);
        tiles.add(hero);
    }

    public List<ImageTile> getTiles() {
        return tiles;
    }

    public void setTiles(List<ImageTile> tiles) {
        this.tiles = tiles;
    }

    public void play(Command command) {
        controlHero(command);
        clearDead();
        controlEnemies();
        clearDead();
    }

    private void controlHero(Command command) {
        if (command.getDirection() != null) {
            Position nextPosition = hero.getPosition().plus(command.getDirection().asVector());
            if (passage(nextPosition)) {

            }
            if (legalMove(nextPosition)) hero.move(command.getDirection().asVector());
            for (Enemy enemy : enemies) {
                if (nextPosition.equals(enemy.getPosition())) hero.attack(enemy);
            }
        } else {
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

    private boolean passage(Position position) {
        boolean isPassage = false;
        for (Passage passage : passages) {
            if (passage.getPosition().equals(position)) isPassage = true;
        }
        return isPassage;
    }

    private void clearDead() {
        List<Enemy> dead = new ArrayList<Enemy>();
        for (Enemy enemy : enemies) {
            if (enemy.getHP() <= 0) {
                dead.add(enemy);
                ImageMatrixGUI.getInstance().removeImage(enemy);
                System.out.println("Thief image gone");
            }
        }
        tiles.removeAll(dead);
        enemies.removeAll(dead);
        obstacles.removeAll(dead);
    }
}
