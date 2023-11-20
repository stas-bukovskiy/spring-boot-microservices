package com.recordsystem.facultyservice.controller;

import com.recordsystem.facultyservice.model.Faculty;
import com.recordsystem.facultyservice.service.FacultyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(FacultyController.class)
class FacultyControllerTest {
    @MockBean
    private FacultyService facultyService;
    @MockBean
    private JwtAuthenticationFilter filter;

    @InjectMocks
    private FacultyController facultyController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        facultyController = new FacultyController(facultyService);
        mockMvc = MockMvcBuilders.standaloneSetup(facultyController).build();
    }

    @Test
    void testGetAllFaculties() throws Exception {
        // Create a list of faculties for testing
        List<Faculty> faculties = new ArrayList<>();
        faculties.add(new Faculty(1L, "Faculty 1", "Description 1"));
        faculties.add(new Faculty(2L, "Faculty 2", "Description 2"));

        // Mock the service method to return the list of faculties
        when(facultyService.getAllFaculties()).thenReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/faculty")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(faculties.size()));
    }

    @Test
    void testGetFacultyById() throws Exception {
        Long facultyId = 1L;
        Faculty faculty = new Faculty(facultyId, "Faculty 1", "Description 1");

        when(facultyService.getFacultyById(facultyId)).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/faculty/{id}", facultyId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(faculty.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(faculty.getName()));
    }

    @Test
    void testGetFacultyByIdWithInvalidId() throws Exception {
        Long facultyId = 1L;

        when(facultyService.getFacultyById(facultyId)).thenThrow(new RuntimeException("Faculty not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/faculty/{id}", facultyId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
