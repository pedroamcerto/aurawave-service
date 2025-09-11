package com.aurawave.dto.productDto;

import com.aurawave.domain.enumerated.ProductStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ProductResponseDtoTest {

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void shouldSerialize() throws Exception {
        var dto = new ProductResponseDto(
                1L,
                "Mouse",
                LocalDateTime.of(2030, 1, 1, 12, 0),
                5L,
                new BigDecimal("20.00"),
                ProductStatus.AVAILABLE,
                LocalDateTime.of(2025, 1, 1, 10, 0),
                LocalDateTime.of(2025, 1, 2, 10, 0)
        );

        String json = mapper.writeValueAsString(dto);

        assertThat(json).contains("\"id\":1");
        assertThat(json).contains("\"name\":\"Mouse\"");
        assertThat(json).contains("\"status\":\"AVAILABLE\"");
    }

    @Test
    void shouldDeserialize() throws Exception {
        String json = """
            {
              "id": 2,
              "name": "Keyboard",
              "validityDate": "2030-01-01T12:00:00",
              "warehouseId": 10,
              "costPrice": 50.00,
              "status": "AVAILABLE",
              "createdDate": "2025-01-01T10:00:00",
              "modifyDate": "2025-01-02T10:00:00"
            }
            """;

        var dto = mapper.readValue(json, ProductResponseDto.class);

        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getName()).isEqualTo("Keyboard");
        assertThat(dto.getStatus()).isEqualTo(ProductStatus.AVAILABLE);
    }
}
