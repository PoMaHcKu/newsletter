package com.news.view;

import java.awt.*;

public class ListenerScreen extends Screen {

    private final String NEWS_LABEL = "Новости";

    public ListenerScreen(String title, int port) {
        super(title, port);
        outputArea = new TextArea(10, 30);
        createArchiveLabel(gbl, gbc, new Label(NEWS_LABEL));
        addNewsArea(gbl, gbc, outputArea);
        setVisible(true);
    }

    @Override
    public void start() {
        System.out.println();
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

    private void addNewsArea(GridBagLayout gbl, GridBagConstraints gbc, TextArea ta) {
        ta.setRows(20);
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
}