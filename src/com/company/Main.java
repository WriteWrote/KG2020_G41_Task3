package com.company;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        MainWindow frame = new MainWindow();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setVisible(true);
        /*MainForm form = new MainForm();
        form.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        form.setSize(1000, 800);
        form.setVisible(true);
*/
    }
}
