package controller;

import model.*;
import view.*;
import javax.swing.*;
import java.util.Map;

public class DriverController {
    private DriverDAO driverDAO;
    private MainView mainView;
    private GeneralDriverView generalView;
    private NoviceDriverView noviceView;
    private PublicTransportDriverView publicView;
    private StatsView statsView;

    public DriverController() {
        driverDAO = new DriverDAO();
        initializeViews();
    }

    private void initializeViews() {
        mainView = new MainView(this);
        generalView = new GeneralDriverView(this);
        noviceView = new NoviceDriverView(this);
        publicView = new PublicTransportDriverView(this);
        statsView = new StatsView(this);
        mainView.setVisible(true);
    }

    public void searchDriver(String licenseNumber) {
        if (!licenseNumber.matches("[1-9]\\d{8}")) {
            JOptionPane.showMessageDialog(mainView, 
                "Invalid license number. It must be a 9-digit number starting with 1-9",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Driver driver = driverDAO.findByLicenseNumber(licenseNumber);
        if (driver == null) {
            JOptionPane.showMessageDialog(mainView,
                "Driver data not found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        driver.validateAge();
        showDriverView(driver);
    }

    private void showDriverView(Driver driver) {
        switch (driver.getType()) {
            case GENERAL:
                generalView.displayDriver(driver);
                generalView.setVisible(true);
                break;
            case NOVICE:
                noviceView.displayDriver(driver);
                noviceView.setVisible(true);
                break;
            case PUBLIC:
                publicView.displayDriver(driver);
                publicView.setVisible(true);
                break;
        }
    }

    public void updateDriver(Driver driver) {
        driverDAO.saveDrivers();
    }

    public void showStats() {
        Map<Driver.DriverType, Integer> typeStats = driverDAO.getDriverTypeStats();
        Map<Driver.LicenseStatus, Map<Driver.DriverType, Integer>> statusStats = 
            driverDAO.getStatusStats();
        statsView.displayStats(typeStats, statusStats);
        statsView.setVisible(true);
    }
}