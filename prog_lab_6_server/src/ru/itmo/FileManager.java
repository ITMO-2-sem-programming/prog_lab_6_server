package ru.itmo;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import ru.itmo.classes.MusicBand;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

public class FileManager {

    private Date dateOfInitialisation;


    /**
     * @param filePath
     * @return
     * @throws IOException
     */
    //Этот метод не static, т.к в программе можно работать с нескольким коллекциями. При загрузке коллекции конкретный CollectionManager "привязывается" к созданной коллекции.
    protected TreeMap<Integer, MusicBand> getCollectionFromFile(String filePath) throws IOException { //Добавить обработку ошибок, связанных с файлом. Создавать файл отдельно через File file = new File();

        if (filePath == null) throw new FileNotFoundException("Error: Can't resolve the file path (null).");
        File file = new File(filePath);
//        if (!file.isFile()) throw new IOException("Error: The path leads to folder, not file.");
        if (!file.exists()) throw new FileNotFoundException("Error: The file doesn't exist.");
        if (!file.canRead()) throw new SecurityException("Error: Can't read the file: Access denied.");
        if (file.length() == 0) throw new JsonSyntaxException("Error: The file is empty.");

        try (BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));) {

            StringBuilder fileData = new StringBuilder();
            String newLine;

            while ((newLine = inputStreamReader.readLine()) != null) {
                fileData.append(newLine);
            }

            Type treeMapType = new TypeToken<TreeMap<Integer, MusicBand>>()  {}.getType();

            TreeMap<Integer, MusicBand> treeMapCollection = new Gson().fromJson(fileData.toString(), treeMapType);

            setDateOfInitialisation();

            return treeMapCollection;

        } catch (JsonSyntaxException | NumberFormatException e) {
            throw new JsonSyntaxException("Error: Json syntax is incorrect. Impossible to load the collection. \nError: " + e.getMessage()); //.substring(46, e.getMessage().length())
        } catch (IOException e) {
            throw new IOException("Error: Unknown error during reading the file was occurred.");
        }
    }


    /**
     * @param treeMapCollection
     * @param filePath
     * @throws IOException
     */
    public static void saveCollectionToFile(TreeMap<Integer, MusicBand> treeMapCollection, String filePath) throws IOException { //Writing to file  Возможно, в качестве параметра можно передавать не только TreeMap, а также и Object (Процедура записи не изменится)

        if (filePath == null) throw new FileNotFoundException("Error: Can't resolve the file path (null).");

        File file = new File(filePath);
        if (file.createNewFile()) {
            System.out.printf("New file was created to save collection to. Filepath: '%s'", filePath);
        } else if (!file.canWrite()) throw new SecurityException("Error: Can't write to this file.");

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {

            fileOutputStream.write(new Gson().toJson(treeMapCollection).getBytes());

        } catch (IOException e) {
            throw new IOException("Error: Unknown error during saving collection to the file was occurred.");
        }
    }


    public Date getDateOfInitialisation() {
        return dateOfInitialisation;
    }

    private void setDateOfInitialisation() {
        this.dateOfInitialisation = new Date();
    }
}

