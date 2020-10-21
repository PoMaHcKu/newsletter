package com.news.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class MessagesTransmitter {

    private DatagramSocket socket;
    private final int port;
    private String address;

    public MessagesTransmitter(int port) {
        this.port = port;
        initSocket();
    }

    public MessagesTransmitter(int port, String address) {
        this.port = port;
        this.address = address;
        initSocket();
    }

    private void initSocket() {
        try {
            socket = new DatagramSocket();
            socket.setBroadcast(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public boolean validationMessage(String message) {
        return !message.isEmpty() && message.length() <= 500;
    }

    public void sendMessage(String message) {
        byte[] buff = message.getBytes();
        String address = this.address == null ? "255.255.255.255" : this.address;
        try {
            DatagramPacket packet = new DatagramPacket(buff, buff.length, InetAddress.getByName(address), port);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        socket.close();
    }
}