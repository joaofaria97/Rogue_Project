package pt.upskill.projeto1.objects.Items;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Characters.Enemy;
import pt.upskill.projeto1.objects.Element;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.List;

import static pt.upskill.projeto1.game.Engine.currentRoom;
import static pt.upskill.projeto1.game.Engine.gui;

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
}
