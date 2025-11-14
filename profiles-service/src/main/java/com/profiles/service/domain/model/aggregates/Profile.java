package com.profiles.service.domain.model.aggregates;

import com.profiles.service.domain.model.commands.CreateProfileCommand;
import com.profiles.service.domain.model.valueobjects.EmergencyContact;
import com.profiles.service.domain.model.valueobjects.Gender;
import com.profiles.service.domain.model.valueobjects.PersonName;
import com.profiles.service.domain.model.valueobjects.PhoneNumber;
import com.profiles.service.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

/**
 * UserProfile aggregate root.
 * Manages general user profile information (patient, doctor, manager).
 */
@Entity
public class Profile extends AuditableAbstractAggregateRoot<Profile> {

    // User reference identifier (not a foreign key)
    private Long userId;

    @Embedded
    private PersonName name;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Embedded
    private PhoneNumber phoneNumber;

    private String email;

    private String city;

    @Embedded
    private EmergencyContact emergencyContact;

    private Long primaryDoctorId; // nullable

    public Profile() {}

    public Profile(CreateProfileCommand command) {
        this.userId = command.userId();
        this.name = new PersonName(command.firstName(), command.lastName());
        this.birthDate = command.birthDate();
        this.gender = command.gender();
        this.phoneNumber = new PhoneNumber(command.phoneNumber());
        this.email = command.email();
        this.city = command.city();
        this.emergencyContact = command.emergencyContact();
        this.primaryDoctorId = null; // default at creation
    }

    // Getters

    public Long getUserId() { return userId; }

    public String getFullName() { return name.getFullName(); }

    public LocalDate getBirthDate() { return birthDate; }

    public Gender getGender() { return gender; }

    public String getPhoneNumber() { return phoneNumber.getPhoneNumber(); }

    public String getEmail() { return email; }

    public String getCity() { return city; }

    public EmergencyContact getEmergencyContact() { return emergencyContact; }

    public Long getPrimaryDoctorId() { return primaryDoctorId; }

    // Update operations

    public void updateName(String firstName, String lastName) {
        this.name = new PersonName(firstName, lastName);
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = new PhoneNumber(phoneNumber);
    }

    public void updateCity(String city) { this.city = city; }

    public void updatePrimaryDoctor(Long doctorId) { this.primaryDoctorId = doctorId; }

    public void updateBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public void updateGender(Gender gender) { this.gender = gender; }

    public void updateEmail(String email) { this.email = email; }

    public void updateEmergencyContact(EmergencyContact emergencyContact) { this.emergencyContact = emergencyContact; }

}
