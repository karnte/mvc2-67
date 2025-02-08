package model;

import java.time.LocalDate;
import java.time.Period;

public class Driver {
    private String licenseNumber;
    private DriverType type;
    private LocalDate birthDate;
    private LicenseStatus status;
    private int complaints;  // For public transport drivers

    public enum DriverType {
        GENERAL, NOVICE, PUBLIC
    }

    public enum LicenseStatus {
        NORMAL, EXPIRED, SUSPENDED
    }

    public Driver(String licenseNumber, DriverType type, LocalDate birthDate) {
        this.licenseNumber = licenseNumber;
        this.type = type;
        this.birthDate = birthDate;
        this.status = LicenseStatus.NORMAL;
        this.complaints = 0;
    }

    // Getters and setters
    public String getLicenseNumber() { return licenseNumber; }
    public DriverType getType() { return type; }
    public void setType(DriverType type) { this.type = type; }
    public LocalDate getBirthDate() { return birthDate; }
    public LicenseStatus getStatus() { return status; }
    public void setStatus(LicenseStatus status) { this.status = status; }
    public int getComplaints() { return complaints; }
    public void setComplaints(int complaints) { this.complaints = complaints; }

    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public boolean validateAge() {
        int age = getAge();
        switch (type) {
            case GENERAL:
                if (age < 16) {
                    status = LicenseStatus.SUSPENDED;
                    return false;
                } else if (age > 70) {
                    status = LicenseStatus.EXPIRED;
                    return false;
                }
                break;
            case NOVICE:
                if (age < 16) {
                    status = LicenseStatus.SUSPENDED;
                    return false;
                } else if (age > 50) {
                    status = LicenseStatus.EXPIRED;
                    return false;
                }
                break;
            case PUBLIC:
                if (age < 20) {
                    status = LicenseStatus.SUSPENDED;
                    return false;
                } else if (age > 60) {
                    status = LicenseStatus.EXPIRED;
                    return false;
                }
                break;
        }
        return true;
    }
}