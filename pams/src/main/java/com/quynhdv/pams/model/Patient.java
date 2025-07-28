package com.quynhdv.pams.model;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Patient {
    private String firstName, lastName, contactNumber, email, mailingAddress;
    private LocalDate dateOfBirth;

    public Patient(String firstName, String lastName, String contactNumber, String email, String mailingAddress,
            LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.mailingAddress = mailingAddress;
        this.dateOfBirth = dateOfBirth;
    }

    @JsonIgnore
    public int getAge() {
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public String getDateOfBirth() {
        return dateOfBirth.toString();
    }

    @Override
    public String toString() {
        return "Patient{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", email='" + email + '\'' +
                ", mailingAddress='" + mailingAddress + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
