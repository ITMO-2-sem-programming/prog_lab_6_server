package ru.itmo.commands;

import ru.itmo.classes.MusicBand;

import java.util.TreeMap;


public class RemoveGreaterCommand extends Command {

    public static String syntaxDescription =
                    "\nCommand: remove_greater {element}" +
                    "\nDescription: Removes all the element greater than the specified one." +
                    "\nNumber of arguments: 1" +
                    "\n   Argument: element (MusicBand)\n";


    public static String execute(TreeMap<Integer, MusicBand> collection, MusicBand musicBand) {

        checkCollectionForEmptiness(collection);

        collection.values().removeIf(x -> x.isMoreThan(musicBand));

        return null;

    }

}
