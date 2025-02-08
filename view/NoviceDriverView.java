package view;

import model.Driver;
import controller.DriverController;
import javax.swing.*;
import java.awt.*;

public class NoviceDriverView extends JFrame {
    private DriverController controller;
    private Driver currentDriver;
    private JLabel statusLabel;
    private JButton writtenTestButton;
    private JButton practicalTestButton;
    private boolean writtenTestCompleted = false;
    private boolean practicalTestCompleted = false;

    public NoviceDriverView(DriverController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Novice Driver Information");
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel infoPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        statusLabel = new JLabel();
        writtenTestButton = new JButton("Written Test");
        practicalTestButton = new JButton("Practical Test");

        infoPanel.add(new JLabel("License Number:"));
        infoPanel.add(new JLabel());
        infoPanel.add(new JLabel("Type:"));
        infoPanel.add(new JLabel("Novice Driver"));
        infoPanel.add(new JLabel("Birth Date:"));
        infoPanel.add(new JLabel());
        infoPanel.add(new JLabel("Status:"));
        infoPanel.add(statusLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(writtenTestButton);
        buttonPanel.add(practicalTestButton);

        writtenTestButton.addActionListener(e -> handleWrittenTestButton());
        practicalTestButton.addActionListener(e -> handlePracticalTestButton());

        add(infoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void handleWrittenTestButton() {
        if (!writtenTestCompleted) {
            System.out.println("Written Test started");
            writtenTestCompleted = true;
        } else {
            System.out.println("Written Test finished");
        }
        checkIfTestsCompleted();
    }

    private void handlePracticalTestButton() {
        if (!practicalTestCompleted) {
            System.out.println("Practical Test started");
            practicalTestCompleted = true;
        } else {
            System.out.println("Practical Test finished");
        }
        checkIfTestsCompleted();
    }

    private void checkIfTestsCompleted() {
        if (writtenTestCompleted && practicalTestCompleted) {
            currentDriver.setType(Driver.DriverType.GENERAL);
            controller.updateDriver(currentDriver);
            this.dispose();
        }
    }

    public void displayDriver(Driver driver) {
        this.currentDriver = driver;
        ((JLabel)((JPanel)getContentPane().getComponent(0)).getComponent(1))
            .setText(driver.getLicenseNumber());
        ((JLabel)((JPanel)getContentPane().getComponent(0)).getComponent(5))
            .setText(driver.getBirthDate().toString());
        statusLabel.setText(driver.getStatus().toString());
        
        boolean enableButtons = driver.getStatus() == Driver.LicenseStatus.NORMAL;
        writtenTestButton.setEnabled(enableButtons);
        practicalTestButton.setEnabled(enableButtons);
    }
}