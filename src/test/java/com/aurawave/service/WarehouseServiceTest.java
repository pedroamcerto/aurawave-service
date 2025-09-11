package com.aurawave.service;

import com.aurawave.dao.WarehouseDao;
import com.aurawave.domain.model.Warehouse;
import com.aurawave.dto.warehouseDto.WarehouseRequestDto;
import com.aurawave.dto.warehouseDto.WarehouseResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class WarehouseServiceTest {

    @Mock
    private WarehouseDao warehouseDao;

    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private WarehouseService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private WarehouseRequestDto request(String name, String address) {
        return new WarehouseRequestDto(name, address);
    }

    private Warehouse entity(Long id, String name, String address, LocalDateTime created, LocalDateTime modified) {
        Warehouse w = new Warehouse();
        w.setId(id);
        w.setName(name);
        w.setAddress(address);
        w.setCreatedDate(created);
        w.setModifyDate(modified);
        return w;
    }

    private WarehouseResponseDto response(Long id, String name, String address, LocalDateTime created, LocalDateTime modified) {
        return new WarehouseResponseDto(id, name, address, created, modified);
    }

    @Test
    void testCreateOk() {
        var dto = request("Main WH", "Av. Central, 100");
        var toPersist = entity(null, "Main WH", "Av. Central, 100", null, null);
        var saved = entity(1L, "Main WH", "Av. Central, 100",
                LocalDateTime.of(2025,1,1,10,0),
                LocalDateTime.of(2025,1,2,11,0));
        var expected = response(1L, "Main WH", "Av. Central, 100",
                saved.getCreatedDate(), saved.getModifyDate());

        when(mapper.map(dto, Warehouse.class)).thenReturn(toPersist);
        when(warehouseDao.create(toPersist)).thenReturn(1L);
        when(warehouseDao.getById(1L)).thenReturn(saved);
        when(mapper.map(saved, WarehouseResponseDto.class)).thenReturn(expected);

        var out = service.create(dto);

        assertNotNull(out);
        assertEquals(1L, out.getId());
        assertEquals("Main WH", out.getName());
        assertEquals("Av. Central, 100", out.getAddress());
        assertEquals(saved.getCreatedDate(), out.getCreatedDate());
        assertEquals(saved.getModifyDate(), out.getModifyDate());

        verify(mapper).map(dto, Warehouse.class);
        verify(warehouseDao).create(toPersist);
        verify(warehouseDao).getById(1L);
        verify(mapper).map(saved, WarehouseResponseDto.class);
    }

    @Test
    void testUpdateOk() {
        var id = 7L;
        var dto = request("Updated WH", "Rua B, 200");
        var toPersist = entity(null, "Updated WH", "Rua B, 200", null, null);
        var updated = entity(id, "Updated WH", "Rua B, 200",
                LocalDateTime.of(2025,2,1,9,0),
                LocalDateTime.of(2025,2,2,10,0));
        var expected = response(id, "Updated WH", "Rua B, 200",
                updated.getCreatedDate(), updated.getModifyDate());

        when(mapper.map(dto, Warehouse.class)).thenReturn(toPersist);
        doNothing().when(warehouseDao).update(id, toPersist);
        when(warehouseDao.getById(id)).thenReturn(updated);
        when(mapper.map(updated, WarehouseResponseDto.class)).thenReturn(expected);

        var out = service.update(id, dto);

        assertEquals(id, out.getId());
        assertEquals("Updated WH", out.getName());
        assertEquals("Rua B, 200", out.getAddress());
        assertEquals(updated.getCreatedDate(), out.getCreatedDate());
        assertEquals(updated.getModifyDate(), out.getModifyDate());

        verify(warehouseDao).update(id, toPersist);
        verify(warehouseDao).getById(id);
        verify(mapper).map(updated, WarehouseResponseDto.class);
    }

    @Test
    void testGetByIdOk() {
        var entity = entity(3L, "West WH", "Av. Oeste, 300",
                LocalDateTime.of(2025,3,1,8,0),
                LocalDateTime.of(2025,3,2,8,30));
        var expected = response(3L, "West WH", "Av. Oeste, 300",
                entity.getCreatedDate(), entity.getModifyDate());

        when(warehouseDao.getById(3L)).thenReturn(entity);
        when(mapper.map(entity, WarehouseResponseDto.class)).thenReturn(expected);

        var out = service.getById(3L);

        assertEquals(3L, out.getId());
        assertEquals("West WH", out.getName());
        assertEquals("Av. Oeste, 300", out.getAddress());
        assertEquals(entity.getCreatedDate(), out.getCreatedDate());
        assertEquals(entity.getModifyDate(), out.getModifyDate());

        verify(warehouseDao).getById(3L);
        verify(mapper).map(entity, WarehouseResponseDto.class);
    }

    @Test
    void testGetAllOk() {
        var e1 = entity(1L, "A", "Rua A, 1",
                LocalDateTime.of(2025,4,1,10,0),
                LocalDateTime.of(2025,4,2,10,0));
        var e2 = entity(2L, "B", "Rua B, 2",
                LocalDateTime.of(2025,5,1,11,0),
                LocalDateTime.of(2025,5,2,11,0));
        var r1 = response(1L, "A", "Rua A, 1", e1.getCreatedDate(), e1.getModifyDate());
        var r2 = response(2L, "B", "Rua B, 2", e2.getCreatedDate(), e2.getModifyDate());

        when(warehouseDao.getAll()).thenReturn(List.of(e1, e2));
        when(mapper.map(e1, WarehouseResponseDto.class)).thenReturn(r1);
        when(mapper.map(e2, WarehouseResponseDto.class)).thenReturn(r2);

        var out = service.getAll();

        assertEquals(2, out.size());
        assertEquals(1L, out.get(0).getId());
        assertEquals("Rua A, 1", out.get(0).getAddress());
        assertEquals(2L, out.get(1).getId());
        assertEquals("Rua B, 2", out.get(1).getAddress());

        verify(warehouseDao).getAll();
        verify(mapper).map(e1, WarehouseResponseDto.class);
        verify(mapper).map(e2, WarehouseResponseDto.class);
    }

    @Test
    void testDeleteOk() {
        doNothing().when(warehouseDao).delete(9L);

        service.delete(9L);

        verify(warehouseDao).delete(9L);
    }
}
