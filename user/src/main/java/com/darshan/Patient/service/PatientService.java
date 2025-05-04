package com.darshan.Patient.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.darshan.Patient.dto.PatientRequestDTO;
import com.darshan.Patient.dto.PatientResponseDTO;
import com.darshan.Patient.exception.EmailAlredyExistsException;
import com.darshan.Patient.mapper.PatientMapper;
import com.darshan.Patient.models.Patient;
import com.darshan.Patient.repository.PatientRepository;
import com.darshan.Patient.exception.PatientNotFoundException;
import com.darshan.Patient.grpc.BillingGrpcClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final BillingGrpcClient billingServiceGrpcClient;
    private final com.darshan.Patient.kafka.kafkaProducer kafkaProducer;

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponseDTO> patientResponseDTOs = patients.stream().map(patient -> PatientMapper.toDTO(patient))
                .toList();

        return patientResponseDTOs;
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlredyExistsException(
                    " a patient with this email id alredy exists" + patientRequestDTO.getEmail());
        }
        Patient newPatient = patientRepository.save(
                PatientMapper.toModel(patientRequestDTO));

        billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(), newPatient.getEmail().toString(),
                newPatient.getName().toString());
        kafkaProducer.sendEvent(newPatient);

        return PatientMapper.toDTO(newPatient);

    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("patient not found with ID: " + id));

        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlredyExistsException(
                    " a patient with this email id alredy exists" + patientRequestDTO.getEmail());
        }

        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);

        return PatientMapper.toDTO(updatedPatient);

    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }

}
