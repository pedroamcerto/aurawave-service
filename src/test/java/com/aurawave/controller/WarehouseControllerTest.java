package com.aurawave.controller;

import com.aurawave.dto.warehouseDto.WarehouseRequestDto;
import com.aurawave.dto.warehouseDto.WarehouseResponseDto;
import com.aurawave.service.WarehouseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = WarehouseController.class)
class WarehouseControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private WarehouseService service;

    @InjectMocks
    private WarehouseController controller;

    private WarehouseRequestDto req() {
        return new WarehouseRequestDto("Main WH", "Av. Central, 100");
    }

    private WarehouseResponseDto resp(long id, String name) {
        return new WarehouseResponseDto(
                id,
                name,
                "Av. Central, 100",
                LocalDateTime.of(2025,1,1,10,0),
                LocalDateTime.of(2025,1,2,11,0)
        );
    }

    @Test
    void testCreateShouldReturn201AndBody() throws Exception {
        var dto = req();
        var out = resp(1L, "Main WH");
        when(service.create(any(WarehouseRequestDto.class))).thenReturn(out);

        mvc.perform(post("/api/warehouses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Main WH"));

        verify(service).create(any(WarehouseRequestDto.class));
    }

    @Test
    void testUpdateShouldReturn200AndBody() throws Exception {
        var dto = req();
        var out = resp(7L, "Updated WH");
        when(service.update(eq(7L), any(WarehouseRequestDto.class))).thenReturn(out);

        mvc.perform(put("/api/warehouses/{id}", 7)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.name").value("Updated WH"));

        verify(service).update(eq(7L), any(WarehouseRequestDto.class));
    }

    @Test
    void testGetByIdShouldReturn200AndBody() throws Exception {
        var out = resp(3L, "West WH");
        when(service.getById(3L)).thenReturn(out);

        mvc.perform(get("/api/warehouses/{id}", 3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("West WH"));

        verify(service).getById(3L);
    }

    @Test
    void testGetAllShouldReturn200AndList() throws Exception {
        var r1 = resp(1L, "A");
        var r2 = resp(2L, "B");
        when(service.getAll()).thenReturn(List.of(r1, r2));

        mvc.perform(get("/api/warehouses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(service).getAll();
    }

    @Test
    void testDeleteShouldReturn204() throws Exception {
        mvc.perform(delete("/api/warehouses/{id}", 9))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(service).delete(9L);
    }
}
