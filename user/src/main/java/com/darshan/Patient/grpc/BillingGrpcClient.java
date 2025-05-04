package com.darshan.Patient.grpc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BillingGrpcClient {

    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    public BillingGrpcClient(
            @Value("${billing.service.address:localhost}") String serverAddress,
            @Value("${billing.service.grpc.port:9001}") int serverPort) {

        log.info("connecting to billing service grpc at {}:{}", serverAddress, serverPort);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress, serverPort).usePlaintext().build();

        blockingStub = BillingServiceGrpc.newBlockingStub(channel);

    }

    public BillingResponse createBillingAccount(String patientId, String email, String name) {
        BillingRequest request = BillingRequest.newBuilder().setPatientId(patientId)
                .setEmail(email)
                .setName(name)
                .build();

        BillingResponse response = blockingStub.createBillingAccount(request);
        log.info("recived response from grpc{}", response);
        return response;

    }
}
