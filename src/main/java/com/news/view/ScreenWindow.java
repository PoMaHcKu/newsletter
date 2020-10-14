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

public class ScreenWindow extends Frame implements ActionListener, WindowListener {

    private DatagramSocket socket;
    private DatagramPacket packet;
    private TextArea archiveForm;
    private TextField inputText;
    private Label archiveLabel;
    private Label newNewsLabel;
    private Button send;
    private Button exit;
    private ServerThread serverThread;

    public ScreenWindow(String header) throws HeadlessException {
        super(header);
        setSize(500, 500);
        archiveForm = new TextArea(10, 30);
        inputText = new TextField(30);
        send = new Button("Опубликовать");
        exit = new Button("Exit");
        send.addActionListener(this);
        this.addWindowListener(this);
        send.setEnabled(false);
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(gbl);
        addArchiveArea(gbl, gbc, archiveForm);
        addExitButton(gbl, gbc, exit);
        addTextField(gbl, gbc, inputText);
        addSendButton(gbl, gbc, send);

        serverThread = new ServerThread(this);
        serverThread.start();
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private void addArchiveArea(GridBagLayout gbl, GridBagConstraints gbc, TextArea ta) {
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbl.setConstraints(ta, gbc);
        add(ta);
    }

    private void addExitButton(GridBagLayout gbl, GridBagConstraints gbc, Button exit) {
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        gbc.ipadx = 10;
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbl.setConstraints(exit, gbc);
        add(exit);
    }

    private void addTextField(GridBagLayout gbl, GridBagConstraints gbc, TextField it) {
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.gridwidth = 1;
        gbl.setConstraints(it, gbc);
        add(it);
    }

    private void addSendButton(GridBagLayout gbl, GridBagConstraints gbc, Button send) {
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbl.setConstraints(send, gbc);
        add(send);
        send.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(send)) {
            String message = inputText.getText();
            archiveForm.append("\n:" + message);
            sendString(message);
        }
    }

    private void sendString(String message) {
        byte[] buff = message.getBytes();
        try {
            packet = new DatagramPacket(buff, buff.length, InetAddress.getByName("255.255.255.255"), 8800);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        setVisible(false);
        System.exit(1);
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