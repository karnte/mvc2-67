package view;

import controller.DriverController;
import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private DriverController controller;
    private JTextField licenseField;
    private JButton searchButton;
    private JButton statsButton;

    public MainView(DriverController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Driver System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel label = new JLabel("Input Driver ID:");
        licenseField = new JTextField(15);
        searchButton = new JButton("search");
        statsButton = new JButton("stats");

        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(label, gbc);

        gbc.gridx = 1;
        mainPanel.add(licenseField, gbc);

        gbc.gridx = 2;
        mainPanel.add(searchButton, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        mainPanel.add(statsButton, gbc);

        add(mainPanel, BorderLayout.CENTER);

        searchButton.addActionListener(e -> 
            controller.searchDriver(licenseField.getText()));
        statsButton.addActionListener(e -> controller.showStats());

        pack();
        setLocationRelativeTo(null);
    }
}