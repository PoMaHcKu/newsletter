package com.news.server;

import com.news.view.ScreenWindow;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServerThread extends Thread {

    private ScreenWindow chatWindow;
    private DatagramSocket socket;

    public ServerThread(ScreenWindow screenWindow) {
        this.chatWindow = screenWindow;
    }

    private String receiveMessage(DatagramSocket socket) {
        byte[] buf = new byte[512];
        DatagramPacket packet;
        try {
            packet = new DatagramPacket(buf, 512);
            socket.receive(packet);
            return new String(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {
        chatWindow.getSend().setEnabled(true);
        try {
            socket = new DatagramSocket(8800);
            while (true) {
                chatWindow.getArchiveForm().append("\n-->" + receiveMessage(socket));
            }
        } catch (SocketException e) {
            System.out.println("Server socket couldn't be opened");
            socket.close();
            this.interrupt();
            e.printStackTrace();
        }
    }
}