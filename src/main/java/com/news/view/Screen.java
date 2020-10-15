package com.news.view;

import java.awt.*;
import java.awt.event.ActionListener;

public abstract class Screen extends Frame implements ActionListener {

    protected GridBagLayout gbl;
    protected GridBagConstraints gbc;
    protected TextArea outputArea;

    public Screen(String title) throws HeadlessException {
        setSize(500, 400);
        gbl = new GridBagLayout();
        gbc = new GridBagConstraints();
        setLayout(gbl);
        setTitle(title);
    }

    public TextArea getOutputArea() {
        return outputArea;
    }
}