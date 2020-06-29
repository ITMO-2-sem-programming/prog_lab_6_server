package ru.itmo.commands;

import ru.itmo.classes.MusicBand;

import java.util.ArrayList;
import java.util.TreeMap;

public class RemoveLowerKeyCommand extends Command {

    public static String syntaxDescription =
            "Command: remove_lower_key <key>" +
                    "\nDescription: Removes all the elements, which key is less than the specified one." +
                    "\nNumber of arguments: 1" +
                    "\n   Argument: key (Integer)\n";




    /**
     * @param collection
     * @param key
     */
    public static String execute(TreeMap<Integer, MusicBand> collection, Integer key) {

        checkCollectionForEmptiness(collection);

        collection.values().removeIf(x -> x.getId() < key);

        return null;
    }

    public static String execute(TreeMap<Integer, MusicBand> collection, String key) {
        return execute(collection, Integer.parseInt(key));
    }
}
