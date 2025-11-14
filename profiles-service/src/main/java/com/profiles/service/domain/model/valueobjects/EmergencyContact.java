package com.profiles.service.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public class EmergencyContact {
    private String fullName;
    private String phone;

    public EmergencyContact() {}

    public EmergencyContact(String fullName, String phone) {
        this.fullName = fullName;
        this.phone = phone;
    }

    public String getFullName() { return fullName; }

    public String getPhone() { return phone; }
}
