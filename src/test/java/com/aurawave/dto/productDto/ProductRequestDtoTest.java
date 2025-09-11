package com.aurawave.dto.productDto;

import com.aurawave.domain.enumerated.ProductStatus;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ProductRequestDtoValidationParameterizedTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    static Stream<ProductRequestDto> validDtos() {
        return Stream.of(
                new ProductRequestDto("A", LocalDateTime.now(), 1L, new BigDecimal("0.00"), ProductStatus.AVAILABLE),
                new ProductRequestDto("X".repeat(120), null, 99L, new BigDecimal("10.50"), ProductStatus.AVAILABLE),
                new ProductRequestDto("Mouse", LocalDateTime.of(2030,1,1,0,0), 5L, new BigDecimal("100.00"), ProductStatus.AVAILABLE)
        );
    }

    static Stream<ProductRequestDto> invalidNames() {
        return Stream.of(
                new ProductRequestDto(null, LocalDateTime.now(), 1L, new BigDecimal("1.00"), ProductStatus.AVAILABLE),
                new ProductRequestDto("", LocalDateTime.now(), 1L, new BigDecimal("1.00"), ProductStatus.AVAILABLE),
                new ProductRequestDto("   ", LocalDateTime.now(), 1L, new BigDecimal("1.00"), ProductStatus.AVAILABLE),
                new ProductRequestDto("X".repeat(121), LocalDateTime.now(), 1L, new BigDecimal("1.00"), ProductStatus.AVAILABLE)
        );
    }

    static Stream<ProductRequestDto> invalidWarehouseIds() {
        return Stream.of(
                new ProductRequestDto("Mouse", LocalDateTime.now(), null, new BigDecimal("1.00"), ProductStatus.AVAILABLE)
        );
    }

    static Stream<ProductRequestDto> invalidCostPrices() {
        return Stream.of(
                new ProductRequestDto("Mouse", LocalDateTime.now(), 1L, null, ProductStatus.AVAILABLE),
                new ProductRequestDto("Mouse", LocalDateTime.now(), 1L, new BigDecimal("-0.01"), ProductStatus.AVAILABLE)
        );
    }

    static Stream<ProductRequestDto> invalidStatus() {
        return Stream.of(
                new ProductRequestDto("Mouse", LocalDateTime.now(), 1L, new BigDecimal("1.00"), null)
        );
    }

    @ParameterizedTest
    @MethodSource("validDtos")
    void testValidDtosShouldHaveNoViolations(ProductRequestDto dto) {
        assertThat(validator.validate(dto)).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("invalidNames")
    void testInvalidNamesShouldViolateName(ProductRequestDto dto) {
        Set<ConstraintViolation<ProductRequestDto>> v = validator.validate(dto);
        assertThat(v).isNotEmpty();
        assertThat(v).anySatisfy(vi -> assertThat(vi.getPropertyPath().toString()).isEqualTo("name"));
    }

    @ParameterizedTest
    @MethodSource("invalidWarehouseIds")
    void testInvalidWarehouseIdShouldViolateWarehouseId(ProductRequestDto dto) {
        Set<ConstraintViolation<ProductRequestDto>> v = validator.validate(dto);
        assertThat(v).isNotEmpty();
        assertThat(v).anySatisfy(vi -> assertThat(vi.getPropertyPath().toString()).isEqualTo("warehouseId"));
    }

    @ParameterizedTest
    @MethodSource("invalidCostPrices")
    void testInvalidCostPriceShouldViolateCostPrice(ProductRequestDto dto) {
        Set<ConstraintViolation<ProductRequestDto>> v = validator.validate(dto);
        assertThat(v).isNotEmpty();
        assertThat(v).anySatisfy(vi -> assertThat(vi.getPropertyPath().toString()).isEqualTo("costPrice"));
    }

    @ParameterizedTest
    @MethodSource("invalidStatus")
    void testInvalidStatusShouldViolateStatus(ProductRequestDto dto) {
        Set<ConstraintViolation<ProductRequestDto>> v = validator.validate(dto);
        assertThat(v).isNotEmpty();
        assertThat(v).anySatisfy(vi -> assertThat(vi.getPropertyPath().toString()).isEqualTo("status"));
    }
}
