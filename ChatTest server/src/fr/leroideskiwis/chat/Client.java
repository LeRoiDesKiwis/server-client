package fr.leroideskiwis.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {

    private Socket socket;
    private OutputStream out;
    private InputStream in;
    private Main main;

    public Client(Socket socket, Main main) throws IOException {
        this.socket = socket;
        this.out = socket.getOutputStream();
        this.in = socket.getInputStream();
        this.main = main;
    }

    @Override
    public void run() {

        while(true){

            try {
                if(in.available() > 0){

                    StringBuilder builder = new StringBuilder();
                    int c = in.read();

                    while(c != -1){
                        builder.append((char)c);

                        if(in.available() == 0) break;

                        c = in.read();
                    }

                    System.out.println("Message broadcasté : "+builder.toString());

                    main.broadcast(builder.toString());

                }
            } catch (IOException e) {
                main.getOuts().remove(out);
                System.out.println("Un client s'est déconnecté !");
            }

        }

    }

}
