package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Floor;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.objects.Wall;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Room {
    private String fileName;
    private Hero hero;
    private List<ImageTile> tiles;

    public Room(String fileName, Hero hero) {
        this.fileName = fileName;
        this.hero = hero;
        this.hero.setPosition(new Position(4, 3));
        buildMap();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public List<ImageTile> getTiles() {
        return tiles;
    }

    public void setTiles(List<ImageTile> tiles) {
        this.tiles = tiles;
    }

    private void paintFloor() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                tiles.add(new Floor(new Position(i, j)));
            }
        }
    }
    private void buildMap() {
        tiles = new ArrayList<ImageTile>();
        paintFloor();

        try {
            Scanner fileScanner = new Scanner(new File(fileName));
            int j = 0;
            while (fileScanner.hasNextLine()) {
                String fileLine = fileScanner.nextLine();
                for (int i = 0; i < fileLine.length(); i++) {
                    Position position = new Position(i, j);
                    if (fileLine.charAt(i) == 'W') tiles.add(new Wall(position));
                }
                j++;
            }
            tiles.add(hero);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void play(Command command) {
        if (command.getDirection() != null) hero.move(command.getDirection().asVector());
    }
}
