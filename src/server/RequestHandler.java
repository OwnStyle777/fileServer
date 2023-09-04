package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;

public class RequestHandler implements Runnable, HashMapMethods, FileServer {
    private final HashMap<Integer, String> listOfFiles;
    private final HashMapMethods hashMapMethods;
    private final FileServer fileServer;
    private volatile boolean shouldExit = false;
    private final static String address = "127.0.0.1";
    private final static int port = 8080;
    Integer ID;
    private static final String enterFileName = "Enter name of the file: ";
    private static final String request = "The request was sent.";
    String fileName;


    public RequestHandler(HashMap<Integer, String> listOfFiles, HashMapMethods hashMapMethods, FileServer fileServer) {
        this.listOfFiles = listOfFiles;
        this.hashMapMethods = hashMapMethods;
        this.fileServer = fileServer;
    }

    @Override
    public void run() {

        while (!shouldExit) {
            try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {

                Socket socket = server.accept();

                try {
                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                    String clientChoose = input.readUTF();
                    System.out.println(clientChoose);

                    boolean requestDone = false;
                    while (!requestDone) {
                        hashMapMethods.loadHashMapFromFile(listOfFiles);
                        ID = Collections.max(listOfFiles.keySet()) + 1;

                        switch (clientChoose) {
                            case "1":
                                output.writeUTF("Do you want to get the file by name or by id (1 - name, 2 - id): ");

                                String nameOrId = input.readUTF();
                                System.out.println(nameOrId);

                                if (nameOrId.equals("1")) {
                                    output.writeUTF("Enter name: ");

                                    String name = input.readUTF();
                                    System.out.println(name);
                                    if (fileServer.isThisServerFileExist(name)) {

                                        output.writeUTF("The file was downloaded! Specify a name for it: ");
                                        System.out.println("The file was downloaded! Specify a name for it: ");

                                        String hddFileName = input.readUTF();
                                        System.out.println(hddFileName);

                                        output.writeUTF("File saved on the hard drive!");
                                        System.out.println("File saved on the hard drive!");
                                        fileServer.getByName(hddFileName, name);
                                        requestDone = true;
                                    } else {
                                        output.writeUTF("The response says that this file is not found!");
                                        requestDone = true;
                                    }

                                } else if (nameOrId.equals("2")) {
                                    output.writeUTF("Enter id: ");

                                    int Id = input.readInt();
                                    System.out.println(Id);
                                    output.writeUTF(request);
                                    output.writeBoolean((listOfFiles.containsKey(Id)));
                                    if (listOfFiles.containsKey(Id)) {

                                        output.writeUTF("The file was downloaded! Specify a name for it: ");
                                        System.out.println("The file was downloaded! Specify a name for it: ");

                                        String hddFileName = input.readUTF();
                                        System.out.println(hddFileName);

                                        output.writeUTF("File saved on the hard drive!");
                                        System.out.println("File saved on the hard drive!");
                                        fileServer.getById(hddFileName, Id, listOfFiles);
                                        requestDone = true;

                                    } else {
                                        output.writeUTF("The response says that this file is not found!");
                                        System.out.println("The response says that this file is not found!");
                                        requestDone = true;
                                    }
                                }

                                break;
                            case "2":
                                output.writeUTF(enterFileName);
                                System.out.println(enterFileName);
                                fileName = input.readUTF();
                                System.out.println(fileName);

                                if (fileServer.isThisClientFileExist(fileName)) {

                                    output.writeUTF("Enter name of the file to be saved on server:");
                                    System.out.println("Enter name of the file to be saved on server:");
                                    String serverFileName1 = input.readUTF();
                                    System.out.println(serverFileName1);

                                    output.writeUTF(request);
                                    System.out.println(request);
                                    if (fileServer.put(fileName, serverFileName1)) {
                                        if (serverFileName1.equals("")) {
                                            hashMapMethods.addToHashMap(listOfFiles, fileName, ID);
                                        } else {
                                            hashMapMethods.addToHashMap(listOfFiles, serverFileName1, ID);
                                        }
                                        output.writeUTF("Response says that file is saved! ID = " + ID);
                                        requestDone = true;
                                    }
                                } else {
                                    output.writeUTF("The response says that this file is not found!");
                                    requestDone = true;

                                }
                                break;
                            case "3":
                                output.writeUTF("Do you want to get the file by name or by id (1 - name, 2 - id): ");
                                System.out.println("Do you want to get the file by name or by id (1 - name, 2 - id): ");

                                String nameOrId1 = input.readUTF();
                                System.out.println(nameOrId1);


                                if (nameOrId1.equals("1")) {
                                    output.writeUTF("Enter name: ");
                                    System.out.println("Enter name: ");

                                    fileName = input.readUTF();
                                    System.out.println(fileName);
                                    output.writeUTF(request);
                                    System.out.println(request);

                                    if (isThisServerFileExist(fileName)) {

                                        fileServer.deleteByName(fileName);
                                        output.writeUTF("The response says that the file was successfully deleted!");
                                        requestDone = true;
                                    } else {
                                        output.writeUTF("The response says that this file is not found!");
                                        requestDone = true;


                                    }
                                } else if (nameOrId1.equals("2")) {
                                    output.writeUTF("Enter Id: ");
                                    System.out.println("Enter Id: ");

                                    int Id = input.readInt();
                                    System.out.println(fileName);
                                    output.writeUTF(request);
                                    System.out.println(request);
                                    output.writeBoolean(listOfFiles.containsKey(Id));
                                    if (listOfFiles.containsKey(Id)) {

                                        fileServer.deleteById(Id, listOfFiles);
                                        output.writeUTF("The response says that the file was successfully deleted!");
                                        requestDone = true;
                                    } else {
                                        output.writeUTF("The response says that this file is not found!");
                                        requestDone = true;

                                    }
                                }
                                break;
                            case "exit":
                                output.writeUTF(request);
                                socket.close();
                                shouldExit = true;
                                System.out.println(shouldExit);
                                requestDone = true;
                                break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
