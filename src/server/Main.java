package server;

import java.util.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Main implements FileServer,HashMapMethods {

private static HashMap<Integer, String> listOfFiles = new HashMap<>();
private  static final FileServer fileServer = new FileServer() {};
private static final HashMapMethods hashMapMethods = new HashMapMethods() {};


    public static void main(String[] args)   {

        String serverStarted = "Server started!";
        System.out.println(serverStarted);

        Runnable requestHandler = new RequestHandler(listOfFiles, hashMapMethods, fileServer);

        Thread thread = new Thread(requestHandler);
        thread.start();

    }
}
