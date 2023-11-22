package com.recordsystem.enrollment.config;

import com.recordsystem.user.v1.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    @Bean
    ManagedChannel managedChannel(@Value("${grpc.client.authority}") String target) {
        return ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();
    }

    @Bean
    UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub(ManagedChannel managedChannel) {
        return UserServiceGrpc.newBlockingStub(managedChannel);
    }

}
