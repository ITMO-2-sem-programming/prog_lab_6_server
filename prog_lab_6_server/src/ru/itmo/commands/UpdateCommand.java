package ru.itmo.commands;

import ru.itmo.classes.MusicBand;
import ru.itmo.personalExceptions.InvalidCommandException;

import java.util.TreeMap;

public class UpdateCommand extends Command {

    public static String syntaxDescription =
            "\nCommand: update <id> {element}" +
                    "\nDescription: Updates value of the element with following 'ID'." +
                    "\nNumber of arguments: 2" +
                    "\n   First argument:  id      (Integer)" +
                    "\n   Second argument: element (MusicBand)\n";


    public static String execute(TreeMap<Integer, MusicBand> collection, Integer key, MusicBand musicBand) {

        checkCollectionForEmptiness(collection);

        if (!collection.containsKey(key))
            throw new InvalidCommandException("Error: No element with such 'ID' in the collection.");

        collection.put(key, musicBand);

        return null;

    }


    public static String execute(TreeMap<Integer, MusicBand> collection, String key, MusicBand musicBand) {
        return execute(collection, Integer.parseInt(key), musicBand);
    }
}
