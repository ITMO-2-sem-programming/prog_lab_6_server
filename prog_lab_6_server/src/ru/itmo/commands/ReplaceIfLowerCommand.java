package ru.itmo.commands;

import ru.itmo.classes.MusicBand;

import java.util.TreeMap;

public class ReplaceIfLowerCommand extends Command {

    public static String syntaxDescription =
            "\nCommand: replace_if_lower <key> {element}" +
                    "\nDescription: Replaces the element of specified key with the specified element if the new one is less." +
                    "\nNumber of arguments 2: " +
                    "\n   First argument:  key     (Integer)" +
                    "\n   Second argument: element (MusicBand)\n";


    public static String execute(TreeMap<Integer, MusicBand> collection, Integer key, MusicBand musicBand) {

        checkCollectionForEmptiness(collection);

        if (!collection.containsKey(key))
            throw new IllegalArgumentException("No such key in the collection. Command is impossible.");


        if (musicBand.isLessThan(collection.get(key))) {
            collection.put(key, musicBand);
        }

        return null;
    }

    public static String execute(TreeMap<Integer, MusicBand> collection, String key, MusicBand musicBand) {
        return execute(collection, Integer.parseInt(key), musicBand);
    }

}
