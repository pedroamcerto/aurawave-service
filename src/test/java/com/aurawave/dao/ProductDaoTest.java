package com.aurawave.dao;

import com.aurawave.core.exception.NotFoundException;
import com.aurawave.domain.enumerated.ProductStatus;
import com.aurawave.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProductDaoTest {

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
    private ProductDao dao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        dao = new ProductDao();
        try {
            var f = ProductDao.class.getDeclaredField("connection");
            f.setAccessible(true);
            f.set(dao, connection);
        } catch (Exception e) {
            fail(e);
        }
    }

    private Product product(Long id) {
        Product p = new Product();
        p.setId(id);
        p.setName("Mouse");
        p.setValidityDate(null);
        p.setWarehouseId(10L);
        p.setCostPrice(new BigDecimal("20.00"));
        p.setStatus(ProductStatus.AVAILABLE);
        return p;
    }

    @Test
    void testCreateShouldReturnGeneratedId() throws Exception {
        when(connection.prepareStatement(anyString(), any(String[].class))).thenReturn(psGenKeys);
        when(psGenKeys.executeUpdate()).thenReturn(1);
        when(psGenKeys.getGeneratedKeys()).thenReturn(rsKeys);
        when(rsKeys.next()).thenReturn(true);
        when(rsKeys.getLong(1)).thenReturn(123L);

        Long id = dao.create(product(null));

        assertEquals(123L, id);
        verify(psGenKeys).setString(eq(1), eq("Mouse"));
        verify(psGenKeys).setNull(eq(2), eq(Types.TIMESTAMP));
        verify(psGenKeys).setLong(eq(3), eq(10L));
        verify(psGenKeys).setBigDecimal(eq(4), eq(new BigDecimal("20.00")));
        verify(psGenKeys).setString(eq(5), eq(ProductStatus.AVAILABLE.name()));
        verify(psGenKeys).executeUpdate();
        verify(psGenKeys).getGeneratedKeys();
        verify(rsKeys).next();
    }

    @Test
    void testCreateShouldThrowNotFoundOnFkViolation2291() throws Exception {
        when(connection.prepareStatement(anyString(), any(String[].class))).thenReturn(psGenKeys);
        when(psGenKeys.executeUpdate()).thenThrow(new SQLException("FK", "state", 2291));

        assertThrows(NotFoundException.class, () -> dao.create(product(null)));
    }

    @Test
    void testCreateShouldWrapOtherSqlExceptions() throws Exception {
        when(connection.prepareStatement(anyString(), any(String[].class))).thenReturn(psGenKeys);
        when(psGenKeys.executeUpdate()).thenThrow(new SQLException("generic"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> dao.create(product(null)));
        assertTrue(ex.getMessage().contains("Erro ao criar produto"));
    }

    @Test
    void testUpdateShouldSucceedWhenRowsAffected() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        assertDoesNotThrow(() -> dao.update(7L, product(null)));
        verify(ps).executeUpdate();
    }

    @Test
    void testUpdateShouldThrowNotFoundWhenZeroRows() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        assertThrows(NotFoundException.class, () -> dao.update(7L, product(null)));
    }

    @Test
    void testUpdateShouldThrowNotFoundOnFkViolation2291() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenThrow(new SQLException("FK", "state", 2291));

        assertThrows(NotFoundException.class, () -> dao.update(7L, product(null)));
    }

    @Test
    void testGetByIdShouldMapRow() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getLong("ID")).thenReturn(9L);
        when(rs.getString("NAME")).thenReturn("Mouse");
        when(rs.getTimestamp("VALIDITY_DATE")).thenReturn(null);
        when(rs.getLong("WAREHOUSE_ID")).thenReturn(10L);
        when(rs.getBigDecimal("COST_PRICE")).thenReturn(new BigDecimal("20.00"));
        when(rs.getString("STATUS")).thenReturn("AVAILABLE");
        when(rs.getTimestamp("CREATED_DATE")).thenReturn(Timestamp.valueOf("2025-01-01 10:00:00"));
        when(rs.getTimestamp("MODIFY_DATE")).thenReturn(Timestamp.valueOf("2025-01-02 10:00:00"));

        var p = dao.getById(9L);

        assertEquals(9L, p.getId());
        assertEquals("Mouse", p.getName());
        assertEquals(10L, p.getWarehouseId());
        assertEquals(new BigDecimal("20.00"), p.getCostPrice());
        assertEquals(ProductStatus.AVAILABLE, p.getStatus());
        assertNotNull(p.getCreatedDate());
        assertNotNull(p.getModifyDate());
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
        when(rs.getTimestamp("VALIDITY_DATE")).thenReturn(null, null);
        when(rs.getLong("WAREHOUSE_ID")).thenReturn(10L, 11L);
        when(rs.getBigDecimal("COST_PRICE")).thenReturn(new BigDecimal("5.00"), new BigDecimal("6.00"));
        when(rs.getString("STATUS")).thenReturn("AVAILABLE", "EXPIRED");
        when(rs.getTimestamp("CREATED_DATE")).thenReturn(null, null);
        when(rs.getTimestamp("MODIFY_DATE")).thenReturn(null, null);

        var list = dao.getAll();

        assertEquals(2, list.size());
        assertEquals(1L, list.get(0).getId());
        assertEquals(ProductStatus.AVAILABLE, list.get(0).getStatus());
        assertEquals(2L, list.get(1).getId());
        assertEquals(ProductStatus.EXPIRED, list.get(1).getStatus());
    }

    @Test
    void testDeleteShouldSucceedWhenRowsAffected() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        assertDoesNotThrow(() -> dao.delete(5L));
        verify(ps).executeUpdate();
    }

    @Test
    void testDeleteShouldThrowNotFoundWhenZeroRows() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        assertThrows(NotFoundException.class, () -> dao.delete(5L));
    }
}
