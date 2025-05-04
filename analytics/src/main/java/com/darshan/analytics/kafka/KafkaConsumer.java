package com.darshan.analytics.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.google.protobuf.InvalidProtocolBufferException;

import lombok.extern.slf4j.Slf4j;
import patient.events.PatientEvent;

@Service
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "patient", groupId = "analytics")
    public void consumeEvent(byte[] event) {

        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            // logic

            log.info("recived patient event:[patientId={},patientname=[],patientEmail={}]",
                    patientEvent.getPatientId(), patientEvent.getName(), patientEvent.getEmail());
        } catch (InvalidProtocolBufferException e) {
            log.error("error in deserialising {}", e.getMessage());
        }
    }

}
