package com.news.view;

import com.news.socket.MessagesTransmitter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriterScreen extends Screen implements ActionListener {

    private final String ARCHIVE = "Архив новостей";
    private final String NEW_LABEL = "Новая новость";
    private final String SEND_LABEL = "Опубликовать";
    private Button send;
    private TextField inputText;
    private MessagesTransmitter transmitter;

    public WriterScreen(String title, int port) throws HeadlessException {
        super(title);
        transmitter = new MessagesTransmitter(port);
        outputArea = new TextArea(10, 30);
        inputText = new TextField(30);
        send = new Button(SEND_LABEL);
        send.addActionListener(this);
        createArchiveLabel(gbl, gbc, new Label(ARCHIVE));
        addArchiveArea(gbl, gbc, outputArea);
        createNewNewsLabel(gbl, gbc, new Label(NEW_LABEL));
        addTextField(gbl, gbc, inputText);
        addSendButton(gbl, gbc, send);
        setVisible(true);
    }

    public void start() {
        send.setEnabled(true);
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
        ta.setFont(new Font("Arial", Font.PLAIN, 14));
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        if (e.getSource().equals(send)) {
            String message = inputText.getText();
            if (transmitter.validationMessage(message)) {
                transmitter.sendMessage(message + "\n");
                outputArea.append(dateFormat.format(new Date()) + " -> " + message + "\n");
                inputText.setText("");
            } else {
                outputArea.append("\nMessage isn't correct. Message mustn't be empty and message length must be less than 500.\n");
            }
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        transmitter.close();
        super.windowClosing(e);
    }
}