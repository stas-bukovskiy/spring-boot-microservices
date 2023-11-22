package com.recordsystem.enrollment.controller;

import com.recordsystem.enrollment.dto.EnrollmentRequest;
import com.recordsystem.enrollment.entity.EnrollmentState;
import com.recordsystem.enrollment.service.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.*;

class EnrollmentControllerTest {

    @InjectMocks
    private EnrollmentController enrollmentController;

    @Mock
    private EnrollmentService enrollmentService;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(enrollmentController).build();
    }

    @Test
    void testGetState() {
        Flux<EnrollmentState> enrollmentStates = Flux.just(EnrollmentState.OPEN, EnrollmentState.CLOSED);
        when(enrollmentService.enrollmentState()).thenReturn(enrollmentStates);

        webTestClient.get()
                .uri("/api/v1/enrollment/state")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(String.class);

        verify(enrollmentService, times(1)).enrollmentState();
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testSetState() {
        EnrollmentState state = EnrollmentState.OPEN;
        doNothing().when(enrollmentService).setState(state);

        webTestClient.post()
                .uri("/api/v1/enrollment/state")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(state.name()), String.class)
                .exchange()
                .expectStatus().isOk();

        verify(enrollmentService, times(1)).setState(state);
    }

    @Test
    void testEnrollOnCourse() {
        EnrollmentRequest request = new EnrollmentRequest(12L, 101L);
        doNothing().when(enrollmentService).enrollOnCourse(request);

        webTestClient.post()
                .uri("/api/v1/enrollment/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), EnrollmentRequest.class)
                .exchange()
                .expectStatus().isOk();

        verify(enrollmentService, times(1)).enrollOnCourse(request);
    }
}
