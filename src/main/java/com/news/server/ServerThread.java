package com.news.server;

import com.news.view.ScreenWindow;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerThread extends Thread {

    private ScreenWindow chatWindow;
    private DatagramSocket socket;

    public ServerThread(ScreenWindow screenWindow) {
        this.chatWindow = screenWindow;
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
            socket = new DatagramSocket(8800);
            while (true) {
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                String message = "\n" + format.format(new Date()) + " -> " + receiveMessage(socket);
                chatWindow.getArchiveForm().append(message);
            }
        } catch (SocketException e) {
            System.out.println("Server socket couldn't be opened");
            socket.close();
            e.printStackTrace();
            System.exit(-1);
        }
    }
}