package com.darshan.Patient.dto;

import com.darshan.Patient.dto.validator.CreatePatientValidationGroup;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatientRequestDTO {

    @NotBlank
    @Size(max = 100, message = "name Cannot exceed 100 charecter")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "address is required")
    private String address;

    @NotBlank(message = "dob is required")
    private String dateOfBirth;

    @NotBlank(groups = CreatePatientValidationGroup.class, message = " registered date is reuired")
    private String registeredDate;

}
