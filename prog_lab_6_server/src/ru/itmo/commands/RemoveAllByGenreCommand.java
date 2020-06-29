package ru.itmo.commands;

import ru.itmo.classes.MusicBand;
import ru.itmo.classes.MusicGenre;

import java.util.TreeMap;

public class RemoveAllByGenreCommand extends Command {

    public static String syntaxDescription =
            "\nCommand: remove_all_by_genre <genre>" +
                    "\nDescription: Removes all the elements, which field 'genre' value matches the specified." +
                    "\nNumber of arguments: 1" +
                    "\n   Argument: genre (MusicGenre)\n";


    /**
     * @param collection
     * @param genre
     */
    public static String execute(TreeMap<Integer, MusicBand> collection, MusicGenre genre) {

        checkCollectionForEmptiness(collection);

        collection.values().removeIf(x -> x.getGenre() == genre);
        return null;
    }


    public static String execute(TreeMap<Integer, MusicBand> collection, String genre) {
        return execute(collection, MusicGenre.valueOf(genre.toUpperCase()));
    }

}
