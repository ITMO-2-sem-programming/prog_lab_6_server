package ru.itmo.commands;

import ru.itmo.classes.MusicBand;
import java.util.TreeMap;

/**
 *
 */
public class ClearCommand extends Command {

    public static String syntaxDescription =
            "\nCommand: clear" +
            "\nDescription: Deletes all the keys and values (clears he collection)." +
            "\nNumber of arguments: 0\n";


    public static String execute(TreeMap<Integer, MusicBand> collection) {

        collection.clear();

        return null;
    }

}
