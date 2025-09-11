package com.aurawave.service;

import com.aurawave.core.exception.NotFoundException;
import com.aurawave.dao.ProductDao;
import com.aurawave.dao.WarehouseDao;
import com.aurawave.domain.enumerated.ProductStatus;
import com.aurawave.domain.model.Product;
import com.aurawave.dto.productDto.ProductRequestDto;
import com.aurawave.dto.productDto.ProductResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock private ProductDao productDao;
    @Mock private WarehouseDao warehouseDao;
    @Mock private ModelMapper mapper;

    @Spy @InjectMocks
    private ProductService service;

    AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    private ProductRequestDto dto(String name, LocalDateTime vd, Long wid, String price, ProductStatus st) {
        return new ProductRequestDto(name, vd, wid, new BigDecimal(price), st);
    }

    private Product prod(Long id, String name, LocalDateTime vd, Long wid, String price, ProductStatus st) {
        Product p = new Product();
        p.setId(id);
        p.setName(name);
        p.setValidityDate(vd);
        p.setWarehouseId(wid);
        p.setCostPrice(new BigDecimal(price));
        p.setStatus(st);
        return p;
    }

    private ProductResponseDto resp(Long id, String name, LocalDateTime vd, Long wid, String price, ProductStatus st) {
        return new ProductResponseDto(id, name, vd, wid, new BigDecimal(price), st,
                LocalDateTime.now(), LocalDateTime.now());
    }


    @Test
    void testCreateOk() {
        var dto = dto("Mouse", LocalDateTime.of(2030,1,1,12,0), 10L, "20.00", ProductStatus.AVAILABLE);
        var saved = prod(1L, "Mouse", dto.getValidityDate(), 10L, "20.00", ProductStatus.AVAILABLE);
        var expected = resp(1L, "Mouse", dto.getValidityDate(), 10L, "20.00", ProductStatus.AVAILABLE);

        when(warehouseDao.existsById(10L)).thenReturn(true);
        when(mapper.map(dto, Product.class)).thenReturn(prod(null, "Mouse", dto.getValidityDate(), 10L, "20.00", ProductStatus.AVAILABLE));
        when(productDao.create(any(Product.class))).thenReturn(1L);
        when(productDao.getById(1L)).thenReturn(saved);
        when(mapper.map(saved, ProductResponseDto.class)).thenReturn(expected);

        var out = service.create(dto);

        assertNotNull(out);
        assertEquals(1L, out.getId());
        assertEquals("Mouse", out.getName());
        verify(warehouseDao).existsById(10L);
        verify(productDao).create(any(Product.class));
        verify(productDao).getById(1L);
    }

    @Test
    void testCreateWarehouseNotFound() {
        var dto = dto("Mouse", LocalDateTime.now(), 999L, "10.00", ProductStatus.AVAILABLE);
        when(warehouseDao.existsById(999L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> service.create(dto));
        verifyNoInteractions(productDao);
    }

    @Test
    void testUpdateOk() {
        var id = 7L;
        var dto = dto("Keyboard", LocalDateTime.of(2030,1,1,0,0), 5L, "50.00", ProductStatus.AVAILABLE);
        var updated = prod(id, "Keyboard", dto.getValidityDate(), 5L, "50.00", ProductStatus.AVAILABLE);
        var expected = resp(id, "Keyboard", dto.getValidityDate(), 5L, "50.00", ProductStatus.AVAILABLE);

        when(warehouseDao.existsById(5L)).thenReturn(true);
        when(mapper.map(dto, Product.class)).thenReturn(prod(null, "Keyboard", dto.getValidityDate(), 5L, "50.00", ProductStatus.AVAILABLE));
        doNothing().when(productDao).update(eq(id), any(Product.class));
        when(productDao.getById(id)).thenReturn(updated);
        when(mapper.map(updated, ProductResponseDto.class)).thenReturn(expected);

        var out = service.update(id, dto);

        assertEquals(id, out.getId());
        assertEquals("Keyboard", out.getName());
        verify(productDao).update(eq(id), any(Product.class));
        verify(productDao).getById(id);
    }

    @Test
    void testUpdateWarehouseNotFound() {
        var dto = dto("X", LocalDateTime.now(), 111L, "5.00", ProductStatus.AVAILABLE);
        when(warehouseDao.existsById(111L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> service.update(1L, dto));
        verify(productDao, never()).update(anyLong(), any());
    }

    @Test
    void testGetByIdOk() {
        var p = prod(3L, "Headset", LocalDateTime.of(2030,1,1,0,0), 1L, "100.00", ProductStatus.AVAILABLE);
        var expected = resp(3L, "Headset", p.getValidityDate(), 1L, "100.00", ProductStatus.AVAILABLE);

        when(productDao.getById(3L)).thenReturn(p);
        when(mapper.map(p, ProductResponseDto.class)).thenReturn(expected);

        var out = service.getById(3L);

        assertEquals(3L, out.getId());
        assertEquals("Headset", out.getName());
        verify(productDao).getById(3L);
    }

    @Test
    void testGetAllOk() {
        var p1 = prod(1L, "A", LocalDateTime.now(), 1L, "10.00", ProductStatus.AVAILABLE);
        var p2 = prod(2L, "B", LocalDateTime.now(), 1L, "20.00", ProductStatus.AVAILABLE);
        var r1 = resp(1L, "A", p1.getValidityDate(), 1L, "10.00", ProductStatus.AVAILABLE);
        var r2 = resp(2L, "B", p2.getValidityDate(), 1L, "20.00", ProductStatus.AVAILABLE);

        when(productDao.getAll()).thenReturn(List.of(p1, p2));
        when(mapper.map(p1, ProductResponseDto.class)).thenReturn(r1);
        when(mapper.map(p2, ProductResponseDto.class)).thenReturn(r2);

        var out = service.getAll();

        assertEquals(2, out.size());
        assertEquals(2L, out.get(1).getId());
        verify(productDao).getAll();
    }

    @Test
    void testDeleteOk() {
        doNothing().when(productDao).delete(9L);
        service.delete(9L);
        verify(productDao).delete(9L);
    }


    @Test
    void testValidValidityProduct_IsEmptyList_NoUpdate() {
        doReturn(List.of()).when(service).getAll();

        service.validValidityProduct();

        verify(service, never()).update(anyLong(), any(ProductRequestDto.class));
        verify(productDao, never()).update(anyLong(), any());
    }

    @Test
    void testValidValidityProduct_OnlyExpiredAreUpdated() {
        var past = LocalDateTime.now().minusDays(1);
        var future = LocalDateTime.now().plusDays(5);

        var expired = new ProductResponseDto(
                101L, "Produto Expirado", past, 77L, new BigDecimal("9.99"),
                ProductStatus.AVAILABLE, LocalDateTime.now(), LocalDateTime.now());

        var ok = new ProductResponseDto(
                202L, "Produto OK", future, 88L, new BigDecimal("19.99"),
                ProductStatus.AVAILABLE, LocalDateTime.now(), LocalDateTime.now());

        doReturn(List.of(expired, ok)).when(service).getAll();

        when(warehouseDao.existsById(anyLong())).thenReturn(true);
        when(mapper.map(any(ProductRequestDto.class), eq(Product.class))).thenReturn(new Product());
        doNothing().when(productDao).update(anyLong(), any(Product.class));
        when(productDao.getById(anyLong())).thenReturn(new Product());
        when(mapper.map(any(Product.class), eq(ProductResponseDto.class)))
                .thenReturn(new ProductResponseDto());

        ArgumentCaptor<Long> idCap = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<ProductRequestDto> dtoCap = ArgumentCaptor.forClass(ProductRequestDto.class);

        service.validValidityProduct();

        verify(service, times(1)).update(idCap.capture(), dtoCap.capture());

        assertEquals(101L, idCap.getValue());
        ProductRequestDto sent = dtoCap.getValue();
        assertEquals("Produto Expirado", sent.getName());
        assertEquals(past, sent.getValidityDate());
        assertEquals(new BigDecimal("9.99"), sent.getCostPrice());
        assertEquals(77L, sent.getWarehouseId());
        assertEquals(ProductStatus.EXPIRED, sent.getStatus());
    }

    @Test
    void testValidValidityProduct_NullsAndNoValidity_NoUpdate() {
        var withoutValidity = new ProductResponseDto(
                303L, "Sem Validade", null, 55L, new BigDecimal("5.00"),
                ProductStatus.AVAILABLE, LocalDateTime.now(), LocalDateTime.now());

        when(service.getAll()).thenReturn(List.of());

        service.validValidityProduct();

        verify(service, never()).update(anyLong(), any(ProductRequestDto.class));
        verify(productDao, never()).update(anyLong(), any());
    }
}
