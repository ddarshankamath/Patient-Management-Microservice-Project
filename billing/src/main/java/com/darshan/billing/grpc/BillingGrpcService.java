package com.darshan.billing.grpc;

import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@Slf4j
public class BillingGrpcService extends BillingServiceImplBase {
    @Override
    public void createBillingAccount(billing.BillingRequest billingRequest,
            StreamObserver<BillingResponse> responseObserver) {
        log.info("Create billing Account request received{}", billingRequest.toString());

        // logic

        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId("12345")
                .setStatus("active")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
