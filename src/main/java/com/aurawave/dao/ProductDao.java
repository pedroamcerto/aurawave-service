package com.aurawave.dao;

import com.aurawave.core.exception.NotFoundException;
import com.aurawave.domain.enumerated.ProductStatus;
import com.aurawave.domain.interfaces.DaoInterface;
import com.aurawave.domain.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDao implements DaoInterface<Product, Long> {

    @Autowired
    private Connection connection;

    @Override
    public Long create(Product product) {
        final String sql = """
            INSERT INTO PRODUCT (NM_PRODUCT, VALIDITY_DATE, COST_PRICE, STATUS, WAREHOUSE_ID_WAREHOUSE)
            VALUES (?, ?, ?, ?, ?)
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"})) {
            ps.setString(1, product.getName());
            ps.setTimestamp(2, product.getValidityDate() != null ? Timestamp.valueOf(product.getValidityDate()) : null);
            ps.setBigDecimal(3, product.getCostPrice());
            ps.setString(4, product.getStatus() != null ? product.getStatus().name() : null);
            ps.setLong(5, product.getWarehouseId());
            int rows = ps.executeUpdate();
            if (rows == 0) throw new SQLException("Insert falhou: nenhuma linha afetada.");

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
            throw new SQLException("Não foi possível recuperar a chave gerada (ID).");
        } catch (SQLException e) {
            if (e.getErrorCode() == 2291) {
                throw new NotFoundException("Warehouse (warehouseId=" + product.getWarehouseId() + ") não encontrado.");
            }
            throw new RuntimeException("Erro na criação do produto", e);
        }
    }

    @Override
    public void update(Long id, Product product) {
        final String sql = """
            UPDATE PRODUCT
               SET NM_PRODUCT = ?, VALIDITY_DATE = ?, COST_PRICE = ?, STATUS = ?, WAREHOUSE_ID_WAREHOUSE = ?
             WHERE ID = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setTimestamp(2, product.getValidityDate() != null ? Timestamp.valueOf(product.getValidityDate()) : null);
            ps.setBigDecimal(3, product.getCostPrice());
            ps.setString(4, product.getStatus() != null ? product.getStatus().name() : null);
            ps.setLong(5, product.getWarehouseId());
            ps.setLong(6, id);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new NotFoundException("Produto id " + id + " não encontrado");
        } catch (SQLException e) {
            if (e.getErrorCode() == 2291) {
                throw new NotFoundException("Warehouse (warehouseId=" + product.getWarehouseId() + ") não encontrado.");
            }
            throw new RuntimeException("Erro ao atualizar o produto " + id, e);
        }
    }

    @Override
    public Product getById(Long id) {
        final String sql = """
            SELECT ID, NM_PRODUCT, VALIDITY_DATE, COST_PRICE, STATUS, WAREHOUSE_ID_WAREHOUSE, CREATED_AT, UPDATED_AT
              FROM PRODUCT
             WHERE ID = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) throw new NotFoundException("Produto id " + id + " não encontrado");
                return map(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar produto " + id, e);
        }
    }

    @Override
    public List<Product> getAll() {
        final String sql = """
            SELECT ID, NM_PRODUCT, VALIDITY_DATE, COST_PRICE, STATUS, WAREHOUSE_ID_WAREHOUSE, CREATED_AT, UPDATED_AT
              FROM PRODUCT
             ORDER BY ID
            """;
        List<Product> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar produtos", e);
        }
    }

    @Override
    public void delete(Long id) {
        final String sql = "DELETE FROM PRODUCT WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new NotFoundException("Produto id " + id + " não encontrado");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar o produto " + id, e);
        }
    }

    private Product map(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getLong("ID"));
        p.setName(rs.getString("NM_PRODUCT"));
        Timestamp validity = rs.getTimestamp("VALIDITY_DATE");
        p.setValidityDate(validity != null ? validity.toLocalDateTime() : null);
        p.setCostPrice(rs.getBigDecimal("COST_PRICE"));
        String statusStr = rs.getString("STATUS");
        p.setStatus(statusStr != null ? ProductStatus.valueOf(statusStr) : null);
        p.setWarehouseId(rs.getLong("WAREHOUSE_ID_WAREHOUSE"));
        Timestamp c = rs.getTimestamp("CREATED_AT");
        Timestamp m = rs.getTimestamp("UPDATED_AT");
        p.setCreatedDate(c != null ? c.toLocalDateTime() : null);
        p.setModifyDate(m != null ? m.toLocalDateTime() : null);
        return p;
    }
}
