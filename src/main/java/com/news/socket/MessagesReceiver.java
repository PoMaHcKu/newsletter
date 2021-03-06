package com.news.socket;

import com.news.view.Screen;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessagesReceiver extends Thread {

    private Screen chatWindow;
    private DatagramSocket socket;
    private int port;

    public MessagesReceiver(Screen screen, int port) {
        this.port = port;
        this.chatWindow = screen;
    }

    private String receiveMessage(DatagramSocket socket) {
        byte[] buf = new byte[1024];
        DatagramPacket packet;
        try {
            packet = new DatagramPacket(buf, 1024);
            socket.receive(packet);
            return new String(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {
        try {
            socket = new DatagramSocket(port);
            while (true) {
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                String message = format.format(new Date()) + " -> " + receiveMessage(socket);
                chatWindow.getOutputArea().append(message + "\n");
            }
        } catch (SocketException e) {
            System.out.println("Server socket couldn't be opened");
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }
}