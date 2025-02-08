package view;

import model.Driver;
import javax.swing.*;

import controller.DriverController;

import java.awt.*;
import java.util.Map;

public class StatsView extends JFrame {
    private JTextArea statsArea;

    public StatsView(DriverController controller) {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Driver Statistics");
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        statsArea = new JTextArea(20, 40);
        statsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(statsArea);

        add(scrollPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    public void displayStats(
        Map<Driver.DriverType, Integer> typeStats,
        Map<Driver.LicenseStatus, Map<Driver.DriverType, Integer>> statusStats
    ) {
        StringBuilder sb = new StringBuilder();
        sb.append("Number of drivers by type:\n");
        sb.append("========================\n");
        for (Map.Entry<Driver.DriverType, Integer> entry : typeStats.entrySet()) {
            sb.append(String.format("%s: %d\n", 
                formatDriverType(entry.getKey()), entry.getValue()));
        }
        
        sb.append("\nDriver status by type:\n");
        sb.append("========================\n");
        for (Map.Entry<Driver.LicenseStatus, Map<Driver.DriverType, Integer>> statusEntry 
            : statusStats.entrySet()) {
            sb.append(String.format("\n%s:\n", formatLicenseStatus(statusEntry.getKey())));
            for (Map.Entry<Driver.DriverType, Integer> typeEntry 
                : statusEntry.getValue().entrySet()) {
                sb.append(String.format("  %s: %d\n",
                    formatDriverType(typeEntry.getKey()), typeEntry.getValue()));
            }
        }
        
        statsArea.setText(sb.toString());
    }

    private String formatDriverType(Driver.DriverType type) {
        switch (type) {
            case GENERAL: return "General";
            case NOVICE: return "Novice";
            case PUBLIC: return "Public Transport";
            default: return type.toString();
        }
    }

    private String formatLicenseStatus(Driver.LicenseStatus status) {
        switch (status) {
            case NORMAL: return "Normal";
            case EXPIRED: return "Expired";
            case SUSPENDED: return "Suspended";
            default: return status.toString();
        }
    }
}