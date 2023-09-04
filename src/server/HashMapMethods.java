package server;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public interface HashMapMethods {
    default void addToHashMap(HashMap<Integer, String> map, String fileName, Integer ID) throws IOException {

        map.put(ID, fileName);

        try {
            saveHashMapToFile(map);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    default void saveHashMapToFile(HashMap<Integer, String> map) throws IOException {
        String directory = "D:\\DELL\\client\\data\\hashMap\\";
        String fileName = "listOfFiles.txt";
        File file = new File(directory + fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                writer.write(entry.getKey() + " " + entry.getValue());
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    default void loadHashMapFromFile(HashMap<Integer, String> hashMap) {
        String directory = "D:\\DELL\\client\\data\\hashMap\\listOfFiles.txt";
        File file = new File(directory);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    int key = Integer.parseInt(parts[0]);
                    String value = parts[1];
                    hashMap.put(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
