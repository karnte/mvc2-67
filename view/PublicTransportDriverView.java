package view;

import model.Driver;
import controller.DriverController;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class PublicTransportDriverView extends JFrame {
    private DriverController controller;
    private Driver currentDriver;
    private JLabel statusLabel;
    private JLabel complaintsLabel;
    private JButton trainingButton;
    private JButton testButton;

    public PublicTransportDriverView(DriverController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Public Transport Driver Information");
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel infoPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        statusLabel = new JLabel();
        complaintsLabel = new JLabel();
        trainingButton = new JButton("Training");
        testButton = new JButton("Performance Test");

        infoPanel.add(new JLabel("License Number:"));
        infoPanel.add(new JLabel());
        infoPanel.add(new JLabel("Type:"));
        infoPanel.add(new JLabel("Public Transport Driver"));
        infoPanel.add(new JLabel("Birth Date:"));
        infoPanel.add(new JLabel());
        infoPanel.add(new JLabel("Status:"));
        infoPanel.add(statusLabel);
        infoPanel.add(new JLabel("Number of Complaints:"));
        infoPanel.add(complaintsLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(trainingButton);
        buttonPanel.add(testButton);

        trainingButton.addActionListener(e -> {
            if (trainingButton.getText().equals("Training")) {
                trainingButton.setText("End Training");
            } else {
                trainingButton.setText("Training");
                currentDriver.setStatus(Driver.LicenseStatus.NORMAL);
                currentDriver.setComplaints(0);
                controller.updateDriver(currentDriver);
                updateUI();
            }
        });

        testButton.addActionListener(e -> {
            toggleButtonText(testButton, "Performance Test", "End Test");
            if (testButton.getText().equals("Performance Test")) {
                this.dispose(); // Close the view
            }
        });

        add(infoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void toggleButtonText(JButton button, String startText, String endText) {
        if (button.getText().equals(startText)) {
            button.setText(endText);
        } else {
            button.setText(startText);
        }
    }

    public void displayDriver(Driver driver) {
        this.currentDriver = driver;
        ((JLabel)((JPanel)getContentPane().getComponent(0)).getComponent(1))
            .setText(driver.getLicenseNumber());
        ((JLabel)((JPanel)getContentPane().getComponent(0)).getComponent(5))
            .setText(driver.getBirthDate().toString());
        statusLabel.setText(driver.getStatus().toString());

        // Randomly generate complaints between 0 and 10
        Random random = new Random();
        int complaints = random.nextInt(11);
        currentDriver.setComplaints(complaints);
        complaintsLabel.setText(String.valueOf(complaints));

        updateUI();
    }

    private void updateUI() {
        boolean isSuspended = currentDriver.getComplaints() > 5;
        if (isSuspended) {
            currentDriver.setStatus(Driver.LicenseStatus.SUSPENDED);
            controller.updateDriver(currentDriver);
        }

        trainingButton.setVisible(isSuspended);
        testButton.setVisible(!isSuspended || currentDriver.getStatus() == Driver.LicenseStatus.NORMAL);
        testButton.setEnabled(currentDriver.getStatus() == Driver.LicenseStatus.NORMAL);
    }
}