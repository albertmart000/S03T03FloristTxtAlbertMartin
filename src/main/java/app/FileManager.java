package app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Florist;

import java.io.*;

public class FileManager {

    public static boolean createFile(String fileName) {
        File file = new File ("./data/" + fileName + ".txt");
        boolean fileIsCreated = false;
        if(!file.exists()){
            try{
                System.out.println("\nNo existeix cap arxiu amb aquest nom. Fem un de nou.");
                file.createNewFile();
                System.out.println("L'arxiu " + fileName + ".txt se ha creat correctament.");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else fileIsCreated = true;
        return fileIsCreated;
    }

    public static void writeFile(String fileName, Florist florist){
        BufferedWriter bufferedWriter;
        FileWriter fileWriter;
        GsonBuilder gsonBuilder = new GsonBuilder();
        String dataToJson = gsonBuilder.setPrettyPrinting().create().toJson(florist);
        try {
            fileWriter = new FileWriter("./data/" + fileName + ".txt");
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(dataToJson);
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("L'arxiu no està disponible.");
            System.out.println(e.getMessage());
        }
    }

    public static Florist readFile(String fileName) throws RuntimeException {
        StringBuilder stringBuilder = null;
        File file = new File("./data/" + fileName + ".txt");
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        String dataFromJson = null;
        if (stringBuilder != null) {
            dataFromJson = stringBuilder.toString();
        }
        Florist florist;
        Gson gson = new Gson();
        florist = gson.fromJson(dataFromJson, Florist.class);
        return florist;
    }
}
