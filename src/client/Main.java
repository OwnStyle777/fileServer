package client;

import server.FileServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    static String address = "127.0.0.1";
    static int port = 8080;

    public static void main(String[] args) throws IOException {
        FileServer fileServer = new FileServer() {
        };
        try (Socket socket = new Socket(InetAddress.getByName(address), port); Scanner scanner = new Scanner(System.in)) {
            boolean exit = false;

            while (!exit) {
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                String menu = "Enter a action: (1 - get a file, 2 - save a file, 3 - delete a file):";
                System.out.println(menu);
                String userChoice = scanner.next();
                output.writeUTF(userChoice);

                if (userChoice.equals("exit")) {
                    String serverResponse = input.readUTF();
                    System.out.println(serverResponse);
                    break;

                } else if (userChoice.equals("1")) {
                    String idOrNameQuestion = input.readUTF();
                    System.out.println(idOrNameQuestion);

                    String choiceIdOrName = scanner.next();
                    output.writeUTF(choiceIdOrName);

                    if (choiceIdOrName.equals("1")) {

                        String enterTheFileName = input.readUTF();
                        System.out.println(enterTheFileName);

                        String fileName = scanner.next();
                        output.writeUTF(fileName);
                        System.out.println(fileName);

                        if (fileServer.isThisServerFileExist(fileName)) {

                            String writeHddFileName = input.readUTF();
                            System.out.println(writeHddFileName);

                            String hddFileName = scanner.next();
                            output.writeUTF(hddFileName);

                            String informationMessage = input.readUTF();
                            System.out.println(informationMessage);
                        } else {
                            String informationMessage = input.readUTF();
                            System.out.println(informationMessage);

                        }

                    } else if (choiceIdOrName.equals("2")) {
                        String enterId = input.readUTF();
                        System.out.println(enterId);

                        int Id = scanner.nextInt();
                        output.writeInt(Id);
                        System.out.println(Id);

                        String request = input.readUTF();
                        System.out.println(request);
                        boolean existsId = input.readBoolean();
                        if (existsId) {

                            String writeHddFileName = input.readUTF();
                            System.out.println(writeHddFileName);

                            String hddFileName = scanner.next();
                            output.writeUTF(hddFileName);

                            String informationMessage = input.readUTF();
                            System.out.println(informationMessage);
                        } else {
                            String response = input.readUTF();
                            System.out.println(response);
                        }
                    }

                } else if (userChoice.equals("2")) {
                    String enterFileName = input.readUTF();
                    System.out.println(enterFileName);

                    String filename = scanner.next();
                    output.writeUTF(filename);
                    if (fileServer.isThisClientFileExist(filename)) {

                        String specifyServerFileName = input.readUTF();
                        System.out.println(specifyServerFileName);
                        scanner.nextLine();
                        String serverFileName = scanner.nextLine();
                        output.writeUTF(serverFileName);

                        String response = input.readUTF();
                        System.out.println(response);
                        String messageOfCreation = input.readUTF();
                        System.out.println(messageOfCreation);
                    } else {
                        String response = input.readUTF();
                        System.out.println(response);
                    }

                } else if (userChoice.equals("3")) {

                    String enterFileName = input.readUTF();
                    System.out.println(enterFileName);
                    String idOrName = scanner.next();
                    output.writeUTF(idOrName);


                    if (idOrName.equals("1")) {
                        String enterName = input.readUTF();
                        System.out.println(enterName);

                        String fileName = scanner.next();
                        output.writeUTF(fileName);
                        String request = input.readUTF();
                        System.out.println(request);
                        if (fileServer.isThisServerFileExist(fileName)) {
                            String response = input.readUTF();
                            System.out.println(response);
                        } else {
                            String response = input.readUTF();
                            System.out.println(response);
                        }

                    } else if (idOrName.equals("2")) {
                        String enterId = input.readUTF();
                        System.out.println(enterId);

                        int Id = scanner.nextInt();
                        output.writeInt(Id);
                        String request = input.readUTF();
                        System.out.println(request);
                        boolean existsId = input.readBoolean();

                        if (existsId) {
                            String response = input.readUTF();
                            System.out.println(response);
                        } else {
                            String response = input.readUTF();
                            System.out.println(response);
                        }
                    }
                }
                exit = true;
                input.close();
                output.close();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
