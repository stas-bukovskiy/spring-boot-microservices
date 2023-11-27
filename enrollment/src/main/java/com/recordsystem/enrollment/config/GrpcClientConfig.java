package com.recordsystem.enrollment.config;

import com.recordsystem.faculty.v1.DisciplineServiceGrpc;
import com.recordsystem.user.v1.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    @Bean
    @Qualifier("authManagedChannel")
    ManagedChannel authManagedChannel(@Value("${grpc.client.auth}") String target) {
        return ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();
    }

    @Bean
    @Qualifier("disciplineManagedChannel")
    ManagedChannel disciplineManagedChannel(@Value("${grpc.client.discipline}") String target) {
        return ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();
    }

    @Bean
    UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub(@Qualifier("authManagedChannel") ManagedChannel authManagedChannel) {
        return UserServiceGrpc.newBlockingStub(authManagedChannel);
    }

    @Bean
    DisciplineServiceGrpc.DisciplineServiceBlockingStub disciplineServiceBlockingStub(@Qualifier("disciplineManagedChannel") ManagedChannel disciplineManagedChannel) {
        return DisciplineServiceGrpc.newBlockingStub(disciplineManagedChannel);
    }

}
