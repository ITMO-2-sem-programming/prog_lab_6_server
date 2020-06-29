package ru.itmo.commands;

import ru.itmo.classes.MusicBand;
import ru.itmo.personalExceptions.InvalidCommandException;

import java.util.TreeMap;

public class InsertCommand extends Command {

    public static String syntaxDescription =
            "\nCommand: insert <key> {element}" +
                    "\nDescription: Adds new element with the following key (if the key doesn't exist)." +
                    "\nNumber of arguments: 2" +
                    "\n   First argument:  key     (Integer)" +
                    "\n   Second argument: element (MusicBand)\n";


    /**
     * @param collection
     * @param key
     */
    public static String execute(TreeMap<Integer, MusicBand> collection, Integer key, MusicBand musicBand) {

        if (collection.containsKey(key))
            throw new InvalidCommandException("Error: Collection already contains musicBand with key: " + key + ". Command is impossible.");

        collection.put(key, musicBand);

        return null;
    }


    public static String execute(TreeMap<Integer, MusicBand> collection, String key, MusicBand musicBand) {
        return execute(collection, Integer.parseInt(key), musicBand);
    }
}