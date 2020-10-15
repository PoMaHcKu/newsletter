package com.news.view;

import com.news.server.ServerThread;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenWindow extends Frame implements ActionListener, WindowListener {

    private final String NEWSLETTER = "Отправка новостей";
    private final String ARCHIVE = "Архив новостей";
    private final String NEW_LABEL = "Новая новость";
    private final String SEND_LABEL = "Опубликовать";
    private Button send;
    private TextArea archiveForm;
    private TextField inputText;
    private DatagramSocket socket;
    private ServerThread serverThread;

    public ScreenWindow() throws HeadlessException {
        setSize(500, 400);
        archiveForm = new TextArea(10, 30);
        inputText = new TextField(30);
        send = new Button(SEND_LABEL);
        send.addActionListener(this);
        addWindowListener(this);
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(gbl);
        setTitle(NEWSLETTER);
        createArchiveLabel(gbl, gbc, new Label(ARCHIVE));
        addArchiveArea(gbl, gbc, archiveForm);
        createNewNewsLabel(gbl, gbc, new Label(NEW_LABEL));
        addTextField(gbl, gbc, inputText);
        addSendButton(gbl, gbc, send);
        setVisible(true);
        serverThread = new ServerThread(this);
        serverThread.start();
        try {
            socket = new DatagramSocket();
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
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            archiveForm.append("\n" + format.format(new Date()) + " -> " + message);
            sendString(message);
        }
    }

    private void sendString(String message) {
        byte[] buff = message.getBytes();
        try {
            DatagramPacket packet = new DatagramPacket(buff, buff.length,
                    InetAddress.getByName("255.255.255.255"), 8800);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        setVisible(false);
        socket.close();
        System.exit(0);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    public Button getSend() {
        return send;
    }

    public TextArea getArchiveForm() {
        return archiveForm;
    }
}