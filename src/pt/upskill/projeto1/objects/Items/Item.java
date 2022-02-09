package pt.upskill.projeto1.objects.Items;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Characters.Enemy;
import pt.upskill.projeto1.objects.Characters.Hero;
import pt.upskill.projeto1.objects.Element;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.List;

import static pt.upskill.projeto1.game.Engine.*;

public abstract class Item extends Element {

    protected Item(Position position) {
        super(position);
    }

    public void getCollected() {
        List<ImageTile> tiles = currentRoom.getTiles();
        tiles.remove(this);
        currentRoom.setTiles(tiles);

        List<Item> items = currentRoom.getItems();
        items.remove(this);
        currentRoom.setItems(items);

        gui.removeImage(this);
    }

    public void getDropped() {
        List<ImageTile> tiles = currentRoom.getTiles();
        tiles.add(this);
        currentRoom.setTiles(tiles);

        List<Item> items = currentRoom.getItems();
        items.add(this);
        currentRoom.setItems(items);

        gui.addImage(this);
    }
}
