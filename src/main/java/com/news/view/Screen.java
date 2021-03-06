package com.news.view;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public abstract class Screen extends Frame implements WindowListener {

    protected GridBagLayout gbl;
    protected GridBagConstraints gbc;
    protected TextArea outputArea;

    public Screen(String title) {
        setSize(500, 400);
        gbl = new GridBagLayout();
        gbc = new GridBagConstraints();
        setLayout(gbl);
        setTitle(title);
        addWindowListener(this);
    }

    public TextArea getOutputArea() {
        return outputArea;
    }

    public abstract void start();

    @Override
    public void windowClosing(WindowEvent e) {
        setVisible(false);
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
}