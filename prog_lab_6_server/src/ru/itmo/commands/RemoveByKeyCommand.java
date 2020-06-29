package ru.itmo.commands;

import ru.itmo.classes.MusicBand;

import java.util.TreeMap;

public class RemoveByKeyCommand extends Command {

    public static String syntaxDescription =
            "\nCommand: remove_key <key>" +
                    "\nDescription: Removes an element with the specified key." +
                    "\nNumber of arguments: 1" +
                    "\n   Argument: key (Integer)\n";


    public static String execute(TreeMap<Integer, MusicBand> collection, Integer key) {

        checkCollectionForEmptiness(collection);

        if (collection.remove(key) == null) {
            throw new IllegalArgumentException("Error: No element with such 'key' in the collection.");
        }

        return null;
    }

    public static String execute(TreeMap<Integer, MusicBand> collection, String key) {
        return execute(collection, Integer.parseInt(key));

    }
}