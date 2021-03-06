package com.company;

import com.company.figures.FigureType;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class MainWindow extends JFrame {
    private JPanel rootPanel;
    private MainPanel mainPanel;
    private JComboBox<String> comboBox;

    private void initialiseItems() {
        for (FigureType f :
                FigureType.values()) {
            comboBox.addItem(f.toString());
        }
    }

    public MainWindow() {
        $$$setupUI$$$();
        setContentPane(rootPanel);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initialiseItems();
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.setActiveItem(Objects.requireNonNull(comboBox.getSelectedItem()).toString());
                //MainWindow.this.requestFocus();
                mainPanel.requestFocus();
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        mainPanel = new MainPanel();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(mainPanel, new GridConstraints(0, 0, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(500, 500), null, new Dimension(500, 500), 0, false));
        comboBox = new JComboBox();
        comboBox.setEnabled(true);
        rootPanel.add(comboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

}
