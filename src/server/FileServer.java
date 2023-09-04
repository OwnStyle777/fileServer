package server;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public interface FileServer {

    default boolean getById(String hddFileName, int fileId, HashMap<Integer, String> map) throws IOException {

        String serverDirectory = "C:\\Users\\DELL\\Desktop\\MyFiles\\PlatformerTutorial-ep04\\File Server\\File Server\\task\\src\\server\\data\\";
        String hddDirectory = "C:\\Users\\DELL\\Desktop\\MyFiles\\PlatformerTutorial-ep04\\File Server\\File Server\\task\\src\\client\\data\\";

        if (map.containsKey(fileId)) {
            String serverFileName = map.get(fileId);

            if (hddFileName.equals("")) {
                hddFileName = serverFileName;
            }

            File serverFile = new File(serverDirectory + serverFileName);
            File hddFile = new File(hddDirectory + hddFileName);

            if (serverFile.exists()) {

                try (FileOutputStream fileOutputStream = new FileOutputStream(hddFile); FileInputStream fileInputStream = new FileInputStream(serverFile)) {

                    byte[] data = fileInputStream.readAllBytes();
                    fileOutputStream.write(data);
                } catch (IOException e) {
                    e.printStackTrace();

                }
                return true;
            }
        }
        return false;
    }

    default boolean isThisClientFileExist(String fileName) {
        String path = "C:\\Users\\DELL\\Desktop\\MyFiles\\PlatformerTutorial-ep04\\File Server\\File Server\\task\\src\\client\\data\\";
        File file = new File(path + fileName);
        if (file.exists()) {
            return true;
        }
        return false;

    }

    default boolean isThisServerFileExist(String fileName) {
        String path = "C:\\Users\\DELL\\Desktop\\MyFiles\\PlatformerTutorial-ep04\\File Server\\File Server\\task\\src\\server\\data\\";
        File file = new File(path + fileName);
        if (file.exists()) {
            return true;
        }
        return false;

    }

    default boolean getByName(String hddFileName, String serverFileName) throws IOException {
//
        String serverDirectory = "C:\\Users\\DELL\\Desktop\\MyFiles\\PlatformerTutorial-ep04\\File Server\\File Server\\task\\src\\server\\data\\";
        String hddDirectory = "C:\\Users\\DELL\\Desktop\\MyFiles\\PlatformerTutorial-ep04\\File Server\\File Server\\task\\src\\client\\data\\";

        File serverFile = new File(serverDirectory + serverFileName);
        if (hddFileName.equals("")) {
            hddFileName = serverFileName;
        }

        File hddFile = new File(hddDirectory + hddFileName);

        if (serverFile.exists()) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(hddFile); FileInputStream fileInputStream = new FileInputStream(serverFile)) {

                byte[] data = fileInputStream.readAllBytes();
                fileOutputStream.write(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }


    default boolean put(String name, String serverName) throws IOException {
        String hddDirectory = "C:\\Users\\DELL\\Desktop\\MyFiles\\PlatformerTutorial-ep04\\File Server\\File Server\\task\\src\\client\\data\\";
        String serverDirectory = "C:\\Users\\DELL\\Desktop\\MyFiles\\PlatformerTutorial-ep04\\File Server\\File Server\\task\\src\\server\\data\\";
        File hddFile = new File(hddDirectory + name);
        if (serverName.equals("")) {
            serverName = name;
        }
        File serverFile = new File(serverDirectory + serverName);

        if (hddFile.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(hddFile); FileOutputStream fileOutputStream = new FileOutputStream(serverFile)) {
                byte[] data = fileInputStream.readAllBytes();
                fileOutputStream.write(data);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }

        return false;

    }

    ;

    default boolean deleteByName(String fileName) throws IOException {
        String directory = "C:\\Users\\DELL\\Desktop\\MyFiles\\PlatformerTutorial-ep04\\File Server\\File Server\\task\\src\\server\\data\\";
        File file = new File(directory + fileName);

        if (!file.exists()) {
            return false;
        } else {
            return file.delete();
        }
    }
    default boolean deleteById(int Id, HashMap<Integer, String> map) throws IOException {
        String directory = "C:\\Users\\DELL\\Desktop\\MyFiles\\PlatformerTutorial-ep04\\File Server\\File Server\\task\\src\\server\\data\\";
        String serverFileName = map.get(Id);

        File file = new File(directory + serverFileName);

        if (!file.exists()) {
            return false;
        } else {
            return file.delete();
        }
    }
}

