package com.company;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private MainPanel panel;

    public MainWindow() throws HeadlessException {
        panel = new MainPanel();
        this.add(panel);
    }

    @Override
    public void paintComponents(Graphics g) {

    }
}
