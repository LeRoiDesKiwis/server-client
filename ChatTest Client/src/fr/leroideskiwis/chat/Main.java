package fr.leroideskiwis.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Random;
import java.util.Scanner;

public class Main implements Runnable{

    private Scanner scan = new Scanner(System.in);
    private InputStream input;
    private OutputStream out;
    private Socket socket;
    private int id;

    public Main() throws IOException {

        System.out.print("adresse ip : ");

        String tmp = scan.nextLine();

        String ip = tmp.split(":")[0];
        int port = Integer.parseInt(tmp.split(":")[1]);

        System.out.println("Connexion...");

        socket = new Socket(ip, port);

        input = socket.getInputStream();
        out = socket.getOutputStream();
        id = new Random().nextInt(9999);

        new Thread(new InputThread(), "input").start();

    }

    public static void main(String... args){

        try {
            new Thread(new Main(), "client").start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while(socket == null){}

        while (true) {

            System.out.print(">> ");

            if (scan.hasNextLine()) {
                try {

                    out.write(("[Client-"+id+"] "+scan.nextLine()).getBytes());

                    out.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Message envoyé !");

        }

    }

    private class InputThread implements Runnable {

        @Override
        public void run() {

            try {

                while (true) {

                    if (input.available() > 0) {

                        StringBuilder builder = new StringBuilder();
                        int c = input.read();

                        while(c != -1){

                            builder.append((char)c);

                            if(input.available() == 0) break;

                            c = input.read();

                        }

                        System.out.println(builder.toString());

                    }

                }

            }catch(Exception e){e.printStackTrace();}

        }
    }
}
