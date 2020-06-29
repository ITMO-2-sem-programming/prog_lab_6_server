package ru.itmo;

import ru.itmo.classes.*;

import ru.itmo.connection.*;


import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class Main {

    static int numberOfRequestsToSaveTheCollection;
    static String filePath;

    public static void main(String[] args) throws IOException {

//        String filePath = System.getenv("COLLECTION_PATH");
//        if (filePath == null) {
//            System.out.println("Error: Env var 'COLLECTION_PATH' doesn't exist.");
//            return;
//        }
        filePath = "collection.json";
        TreeMap<Integer, MusicBand> collectionFromFile;


        FileManager fileManager;
//        TreeMap<Integer, MusicBand> collectionFromFile = new TreeMap<>();
        try {


            fileManager = new FileManager();
            collectionFromFile = fileManager.getCollectionFromFile(filePath);

            checkCollectionForKeysIDsIdentity(collectionFromFile);

            checkCollectionForFieldsValues(collectionFromFile);

            System.out.println("Collection was loaded successfully!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }


        CollectionManager.setFileManager(fileManager); //Is needed only for printing collection's DateOfInitialisation when running command "info
        CollectionManager.setFilePath(filePath);

        numberOfRequestsToSaveTheCollection = 8;
        run(collectionFromFile);
    }

    public static void run(TreeMap<Integer, MusicBand> collection) {

        ConnectionManager connectionManager = new ConnectionManager(61531);
        Request request;
        Response response;
        int numberOfRequests = 0;
        while (true) {
            try {
                request = (Request) Serializer.toObject(connectionManager.receive());
                numberOfRequests += 1;
                response = CollectionManager.executeCommand(collection, request);
                System.out.println("C name:" + request.getCommandName() +
                        "\nKey:" + request.getArgumentKey() +
                        "\nMisBand:" + request.getArgumentMusicBand());



                connectionManager.send(Serializer.toByteArray(response));

            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }


















    public static void checkCollectionForKeysIDsIdentity(TreeMap<Integer, MusicBand> collection) {
        for (Map.Entry<Integer, MusicBand> entry: collection.entrySet()) {
            if (!entry.getKey().equals(entry.getValue().getId())) {
                throw new IllegalArgumentException(String.format("Error: Key isn't identical to ID. Element: key: '%s', id: '%s'", entry.getKey(), entry.getValue().getId()));
            }
        }
    }


    public static void checkCollectionForFieldsValues(TreeMap<Integer, MusicBand> collection) {
        for (Integer key: collection.keySet()) {
            try {
                MusicBand musicBand = new MusicBand();
                Person person = new Person();
                Coordinates coordinates = new Coordinates();
                Location location = new Location();

                MusicBand musicBandParsed = collection.get(key);
                Coordinates coordinatesParsed = musicBandParsed.getCoordinates();
                Person personParsed = musicBandParsed.getFrontMan();

                if (personParsed == null) person = null;
                else {
                    Location locationParsed = personParsed.getLocation();
                    location.setX(locationParsed.getX());
                    location.setY(locationParsed.getY());
                    location.setName(locationParsed.getName());

                    person.setName(personParsed.getName());
                    person.setHeight(personParsed.getHeight());
                    person.setHeirColor(personParsed.getHeirColor());
                    person.setNationality(personParsed.getNationality());
                    person.setLocation(location);
                }

                coordinates.setX(coordinatesParsed.getX());
                coordinates.setY(coordinatesParsed.getY());

                musicBand.setId(musicBandParsed.getId());
                musicBand.setName(musicBandParsed.getName());
                musicBand.setCoordinates(coordinates);
                musicBand.setNumberOfParticipants(musicBandParsed.getNumberOfParticipants());
                musicBand.setSinglesCount(musicBandParsed.getSinglesCount());
                musicBand.setGenre(musicBandParsed.getGenre());
                musicBand.setFrontMan(person);


            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(String.format("Error: Element with key: '%s'\n%s", key, e.getMessage()));
            }
        }
    }

    // For testing the Method "checkCollectionForFieldsValues" productivity
    //    checkCollectionForFieldsValues(generateHugeCollection(900000));
//            System.out.println("(Java) : I'm ready!");
    public static TreeMap<Integer, MusicBand> generateHugeCollection(Integer numberOfBands) {
        TreeMap<Integer, MusicBand> collection = new TreeMap<>();

        for (int i = 0; i < numberOfBands; i++) {
            MusicBand beatles = new MusicBand(i, "Beatles", new Coordinates(161, 16), 7, 3, MusicGenre.PROGRESSIVE_ROCK, new Person("John Lennon", 195L, Color.RED, Country.GERMANY, new Location(1, 2, "Scene")));
            collection.put(i, beatles);
        }
        return collection;
    }


}


