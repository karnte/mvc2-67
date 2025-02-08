package view;

import model.Driver;
import controller.DriverController;
import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class GeneralDriverView extends JFrame {
    private DriverController controller;
    private Driver currentDriver;
    private JLabel statusLabel;
    private JButton testButton;
    private JButton finishedButton;

    public GeneralDriverView(DriverController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("GENERAL DRIVER");
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel infoPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        statusLabel = new JLabel();
        testButton = new JButton("Test Performance");
        finishedButton = new JButton("Finished");

        infoPanel.add(new JLabel("ID:"));
        infoPanel.add(new JLabel());
        infoPanel.add(new JLabel("TYPE:"));
        infoPanel.add(new JLabel("GENERAL DRIVER"));
        infoPanel.add(new JLabel("Date of Birth:"));
        infoPanel.add(new JLabel());
        infoPanel.add(new JLabel("Status:"));
        infoPanel.add(statusLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(testButton);
        // buttonPanel.add(finishedButton);

        testButton.addActionListener(e -> {
            if (testButton.getText().equals("Test Performance")) {
                System.out.println("test finished");
                this.dispose();
            } else {
                testButton.setText("Test Performance");
            }
        });

        finishedButton.addActionListener(e -> onFinishedButtonPressed());

        add(infoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    public void displayDriver(Driver driver) {
        this.currentDriver = driver;
        ((JLabel)((JPanel)getContentPane().getComponent(0)).getComponent(1))
            .setText(driver.getLicenseNumber());

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = driver.getBirthDate().format(dateFormatter);
        ((JLabel)((JPanel)getContentPane().getComponent(0)).getComponent(5))
            .setText(formattedDate);

        statusLabel.setText(driver.getStatus().toString());
        
        testButton.setEnabled(driver.getStatus() == Driver.LicenseStatus.NORMAL);
    }

    private void onFinishedButtonPressed() {
        this.dispose();
    }
}