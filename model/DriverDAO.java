package model;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DriverDAO {
    private static final String CSV_FILE = "data\\drivers.csv";
    private List<Driver> drivers;

    public DriverDAO() {
        drivers = new ArrayList<>();
        loadDrivers();
    }

    private void loadDrivers() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                try {
                    Driver driver = new Driver(
                        data[0],
                        Driver.DriverType.valueOf(data[1]),
                        LocalDate.parse(data[2], formatter)
                    );
                    driver.setStatus(Driver.LicenseStatus.valueOf(data[3]));
                    drivers.add(driver);
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid data: " + Arrays.toString(data));
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Missing data in line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDrivers() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_FILE))) {
            for (Driver driver : drivers) {
                pw.println(String.format("%s,%s,%s,%s",
                    driver.getLicenseNumber(),
                    driver.getType(),
                    driver.getBirthDate().format(formatter),
                    driver.getStatus()
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Driver findByLicenseNumber(String licenseNumber) {
        return drivers.stream()
            .filter(d -> d.getLicenseNumber().equals(licenseNumber))
            .findFirst()
            .orElse(null);
    }

    public Map<Driver.DriverType, Integer> getDriverTypeStats() {
        Map<Driver.DriverType, Integer> stats = new EnumMap<>(Driver.DriverType.class);
        for (Driver.DriverType type : Driver.DriverType.values()) {
            stats.put(type, 0);
        }
        for (Driver driver : drivers) {
            stats.put(driver.getType(), stats.get(driver.getType()) + 1);
        }
        return stats;
    }

    public Map<Driver.LicenseStatus, Map<Driver.DriverType, Integer>> getStatusStats() {
        Map<Driver.LicenseStatus, Map<Driver.DriverType, Integer>> stats = new EnumMap<>(Driver.LicenseStatus.class);
        for (Driver.LicenseStatus status : Driver.LicenseStatus.values()) {
            stats.put(status, new EnumMap<>(Driver.DriverType.class));
            for (Driver.DriverType type : Driver.DriverType.values()) {
                stats.get(status).put(type, 0);
            }
        }
        for (Driver driver : drivers) {
            stats.get(driver.getStatus()).put(driver.getType(),
                stats.get(driver.getStatus()).get(driver.getType()) + 1);
        }
        return stats;
    }
}
