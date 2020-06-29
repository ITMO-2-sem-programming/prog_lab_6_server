package ru.itmo;

import ru.itmo.classes.*;
import ru.itmo.commands.*;
import ru.itmo.connection.Request;
import ru.itmo.connection.Response;


import java.io.IOException;
import java.util.*;

public class CollectionManager {

    private static String filePath;

    private static FileManager fileManager;


    public static Response executeCommand(TreeMap<Integer, MusicBand> collection, Request request) {

        Response response = new Response();

        try {
            response.setSuccessMessage(executeCommand(collection, request.getCommandName(), request.getArgumentKey(), request.getArgumentMusicBand()));
//        } catch (InvalidCommandException | IllegalArgumentException e) {
//            response.setErrorMessage(e.getMessage());
//        } catch (UnsupportedOperationException e) {
//            response.setErrorMessage("Error: Error on the server side. \n    Error info: " + e.getMessage());
//        }
        } catch (Exception e) {
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }


    public static String executeCommand(TreeMap<Integer, MusicBand> collection, String commandName, String argumentKey, MusicBand argumentMusicBand){



        switch (commandName) {
            case ("help"):
                return HelpCommand.execute(collection, argumentKey);


            case ("info"):
                if (fileManager == null)
                    return InfoCommand.execute(collection, "unknown");
                else
                    return InfoCommand.execute(collection, fileManager.getDateOfInitialisation().toString());


            case ("show"):
                return ShowCommand.execute(collection, argumentKey);


            case ("insert"):
                return InsertCommand.execute(collection, argumentKey, argumentMusicBand);


            case ("update"):
                return UpdateCommand.execute(collection, argumentKey, argumentMusicBand);


            case ("remove_key"):
                return RemoveByKeyCommand.execute(collection, argumentKey);


            case ("clear"):
                return ClearCommand.execute(collection);


            case ("save"): // Client doesn't have any access to this method

                try {
                    FileManager.saveCollectionToFile(collection, filePath);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                return null;



            case ("remove_greater"):
                return RemoveGreaterCommand.execute(collection, argumentMusicBand);


            case ("replace_if_lower"):
                return ReplaceIfLowerCommand.execute(collection, argumentKey, argumentMusicBand);


            case ("remove_lower_key"):
                return RemoveLowerKeyCommand.execute(collection, argumentKey);


            case ("remove_all_by_genre"):
                return RemoveAllByGenreCommand.execute(collection, argumentKey);


            case ("max_by_front_man"):
                return MaxByFrontManCommand.execute(collection);


            case ("filter_greater_than_singles_count"):
                return FilterGreaterThanSinglesCountCommand.execute(collection, Integer.parseInt(argumentKey));



            default: throw new UnsupportedOperationException(String.format("Error: Command '%s' isn't supported.", commandName));

        }
    }







    public static String getFilePath() {
        return filePath;
    }


    public static void setFilePath(String filePath) {
        CollectionManager.filePath = filePath;
    }


    public static FileManager getFileManager() {
        return fileManager;
    }


    public static void setFileManager(FileManager fileManager) {
        CollectionManager.fileManager = fileManager;
    }
}
