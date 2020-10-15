package com.news.view;

import com.news.server.ServerThread;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class WriterScreen extends Screen implements ActionListener {

    private final String ARCHIVE = "Архив новостей";
    private final String NEW_LABEL = "Новая новость";
    private final String SEND_LABEL = "Опубликовать";
    private Button send;
    private TextField inputText;
    private DatagramSocket socket;
    private ServerThread serverThread;

    public WriterScreen(String title, int port) throws HeadlessException {
        super(title, port);
        serverThread = new ServerThread(this, port);
        serverThread.start();
        outputArea = new TextArea(10, 30);
        inputText = new TextField(30);
        send = new Button(SEND_LABEL);
        send.addActionListener(this);
        addWindowListener(this);
        createArchiveLabel(gbl, gbc, new Label(ARCHIVE));
        addArchiveArea(gbl, gbc, outputArea);
        createNewNewsLabel(gbl, gbc, new Label(NEW_LABEL));
        addTextField(gbl, gbc, inputText);
        addSendButton(gbl, gbc, send);
        setVisible(true);
    }

    @Override
    public void start() {
        try {
            socket = new DatagramSocket();
            send.setEnabled(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private void createArchiveLabel(GridBagLayout gbl, GridBagConstraints gbc, Label label) {
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbl.setConstraints(label, gbc);
        add(label);
    }

    private void createNewNewsLabel(GridBagLayout gbl, GridBagConstraints gbc, Label label) {
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.gridy = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbl.setConstraints(label, gbc);
        add(label);
    }

    private void addArchiveArea(GridBagLayout gbl, GridBagConstraints gbc, TextArea ta) {
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 15, 10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        ta.setFont(new Font("Arial", Font.PLAIN, 12));
        ta.setEditable(false);
        gbl.setConstraints(ta, gbc);
        add(ta);
    }

    private void addTextField(GridBagLayout gbl, GridBagConstraints gbc, TextField it) {
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.gridy = 3;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 10, 10, 10);
        gbl.setConstraints(it, gbc);
        add(it);
    }

    private void addSendButton(GridBagLayout gbl, GridBagConstraints gbc, Button send) {
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.gridy = 4;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbl.setConstraints(send, gbc);
        add(send);
        send.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(send)) {
            String message = inputText.getText();
            if (validationMessage(message)) sendString(message);
        }
    }

    private boolean validationMessage(String message) {
        if (message.length() == 0) {
            return false;
        } else if (message.length() > 500) {
            outputArea.append("\nThe message is too long (max length is 500 characters).\n");
            return false;
        }
        inputText.setText("");
        return true;
    }

    private void sendString(String message) {
        byte[] buff = message.getBytes();
        try {
            DatagramPacket packet = new DatagramPacket(buff, buff.length, InetAddress.getByName("255.255.255.255"), port);
            socket.send(packet);
            inputText.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        socket.close();
        super.windowClosing(e);
    }
}