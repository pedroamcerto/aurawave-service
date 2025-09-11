package com.aurawave.dao;

import com.aurawave.core.exception.NotFoundException;
import com.aurawave.domain.model.Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class WarehouseDaoTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement ps;

    @Mock
    private PreparedStatement psGenKeys;

    @Mock
    private ResultSet rs;

    @Mock
    private ResultSet rsKeys;

    @InjectMocks
    private WarehouseDao dao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dao = new WarehouseDao();
        try {
            var f = WarehouseDao.class.getDeclaredField("connection");
            f.setAccessible(true);
            f.set(dao, connection);
        } catch (Exception e) {
            fail(e);
        }
    }

    private Warehouse wh(Long id, String name, String address) {
        Warehouse w = new Warehouse();
        w.setId(id);
        w.setName(name);
        w.setAddress(address);
        return w;
    }

    @Test
    void testCreateShouldReturnGeneratedId() throws Exception {
        when(connection.prepareStatement(anyString(), any(String[].class))).thenReturn(psGenKeys);
        when(psGenKeys.executeUpdate()).thenReturn(1);
        when(psGenKeys.getGeneratedKeys()).thenReturn(rsKeys);
        when(rsKeys.next()).thenReturn(true);
        when(rsKeys.getLong(1)).thenReturn(42L);

        Long id = dao.create(wh(null, "Main WH", "Av. Central, 100"));

        assertEquals(42L, id);
        verify(psGenKeys).setString(eq(1), eq("Main WH"));
        verify(psGenKeys).setString(eq(2), eq("Av. Central, 100"));
        verify(psGenKeys).executeUpdate();
        verify(psGenKeys).getGeneratedKeys();
    }

    @Test
    void testCreateShouldWrapSqlExceptions() throws Exception {
        when(connection.prepareStatement(anyString(), any(String[].class))).thenReturn(psGenKeys);
        when(psGenKeys.executeUpdate()).thenThrow(new SQLException("boom"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> dao.create(wh(null, "A", "B")));
        assertTrue(ex.getMessage().contains("Erro na criação do almoxarifado"));
    }

    @Test
    void testUpdateShouldSucceedWhenRowsAffected() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        assertDoesNotThrow(() -> dao.update(7L, wh(null, "X", "Y")));
        verify(ps).executeUpdate();
    }

    @Test
    void testUpdateShouldThrowNotFoundWhenZeroRows() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        assertThrows(NotFoundException.class, () -> dao.update(7L, wh(null, "X", "Y")));
    }

    @Test
    void testGetByIdShouldMapRow() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getLong("ID")).thenReturn(5L);
        when(rs.getString("NAME")).thenReturn("West WH");
        when(rs.getString("ADDRESS")).thenReturn("Av. Oeste, 300");
        when(rs.getTimestamp("CREATED_DATE")).thenReturn(Timestamp.valueOf("2025-01-01 10:00:00"));
        when(rs.getTimestamp("MODIFY_DATE")).thenReturn(Timestamp.valueOf("2025-01-02 10:00:00"));

        var out = dao.getById(5L);

        assertEquals(5L, out.getId());
        assertEquals("West WH", out.getName());
        assertEquals("Av. Oeste, 300", out.getAddress());
        assertNotNull(out.getCreatedDate());
        assertNotNull(out.getModifyDate());
    }

    @Test
    void testGetByIdShouldThrowNotFoundWhenNoRow() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        assertThrows(NotFoundException.class, () -> dao.getById(1L));
    }

    @Test
    void testGetAllShouldReturnList() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true, true, false);
        when(rs.getLong("ID")).thenReturn(1L, 2L);
        when(rs.getString("NAME")).thenReturn("A", "B");
        when(rs.getString("ADDRESS")).thenReturn("Rua A, 1", "Rua B, 2");
        when(rs.getTimestamp("CREATED_DATE")).thenReturn(null, null);
        when(rs.getTimestamp("MODIFY_DATE")).thenReturn(null, null);

        var list = dao.getAll();

        assertEquals(2, list.size());
        assertEquals(1L, list.get(0).getId());
        assertEquals("A", list.get(0).getName());
        assertEquals("Rua B, 2", list.get(1).getAddress());
    }

    @Test
    void testDeleteShouldSucceedWhenRowsAffected() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        assertDoesNotThrow(() -> dao.delete(9L));
        verify(ps).executeUpdate();
    }

    @Test
    void testDeleteShouldThrowNotFoundWhenZeroRows() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        assertThrows(NotFoundException.class, () -> dao.delete(9L));
    }

    @Test
    void testExistsByIdShouldReturnTrueWhenRowExists() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);

        boolean exists = dao.existsById(7L);

        assertTrue(exists);
        verify(ps).setLong(1, 7L);
    }

    @Test
    void testExistsByIdShouldReturnFalseWhenNoRow() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        boolean exists = dao.existsById(7L);

        assertFalse(exists);
    }
}
