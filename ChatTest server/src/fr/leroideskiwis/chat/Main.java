package fr.leroideskiwis.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main implements Runnable{

    private ServerSocket server;
    private List<OutputStream> outs = new ArrayList<>();

    public List<OutputStream> getOuts() {
        return outs;
    }

    public void broadcast(String s){

        for (OutputStream out : outs) {

            try {
                out.write(s.getBytes());
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    public Main() throws Throwable{

        server = new ServerSocket(2019);

        while(true){

            Socket socket = server.accept();
            System.out.println("Un nouveau client s'est connecté ! (ip : "+socket.getInetAddress().getHostAddress()+")");

            outs.add(socket.getOutputStream());

            new Thread(new Client(socket, this), "client-"+new Random().nextInt(9999)).start();

        }

    }

    public static void main(String... args){

        try {
            new Thread(new Main(), "main-thread").start();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }

    @Override
    public void run() {



    }
}
