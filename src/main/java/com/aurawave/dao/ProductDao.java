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
    public Long create(Product p) {
        final String sql = """
            INSERT INTO PRODUCT
                (NAME, VALIDITY_DATE, WAREHOUSE_ID, COST_PRICE, STATUS)
            VALUES
                (?, ?, ?, ?, ?)
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"})) {
            ps.setString(1, p.getName());
            if (p.getValidityDate() != null)
                ps.setTimestamp(2, Timestamp.valueOf(p.getValidityDate()));
            else
                ps.setNull(2, Types.TIMESTAMP);
            ps.setLong(3, p.getWarehouseId());
            ps.setBigDecimal(4, p.getCostPrice());
            ps.setString(5, p.getStatus().name());

            int rows = ps.executeUpdate();
            if (rows == 0) throw new SQLException("Insert falhou: nenhuma linha afetada.");

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
            throw new SQLException("Não foi possível recuperar a chave gerada (ID).");
        } catch (SQLException e) {
            if (e.getErrorCode() == 2291) {
                throw new NotFoundException("Warehouse (warehouseId=" + p.getWarehouseId() + ") não encontrado para o produto.");
            }
            throw new RuntimeException("Erro ao criar produto", e);
        }
    }

    @Override
    public void update(Long id, Product p) {
        final String sql = """
            UPDATE PRODUCT
               SET NAME = ?,
                   VALIDITY_DATE = ?,
                   WAREHOUSE_ID = ?,
                   COST_PRICE = ?,
                   STATUS = ?
             WHERE ID = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            if (p.getValidityDate() != null)
                ps.setTimestamp(2, Timestamp.valueOf(p.getValidityDate()));
            else
                ps.setNull(2, Types.TIMESTAMP);
            ps.setLong(3, p.getWarehouseId());
            ps.setBigDecimal(4, p.getCostPrice());
            ps.setString(5, p.getStatus().name());
            ps.setLong(6, id);

            int rows = ps.executeUpdate();
            if (rows == 0) throw new NotFoundException("Produto id " + id + " não encontrado");
        } catch (SQLException e) {
            if (e.getErrorCode() == 2291) {
                throw new NotFoundException("Warehouse (warehouseId=" + p.getWarehouseId() + ") não encontrado para o produto.");
            }
            throw new RuntimeException("Erro ao atualizar o produto " + id, e);
        }
    }

    @Override
    public Product getById(Long id) {
        final String sql = """
            SELECT ID, NAME, VALIDITY_DATE, WAREHOUSE_ID, COST_PRICE, STATUS,
                   CREATED_DATE, MODIFY_DATE
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
            throw new RuntimeException("Erro ao buscar produto por id " + id, e);
        }
    }

    @Override
    public List<Product> getAll() {
        final String sql = """
            SELECT ID, NAME, VALIDITY_DATE, WAREHOUSE_ID, COST_PRICE, STATUS,
                   CREATED_DATE, MODIFY_DATE
              FROM PRODUCT
             ORDER BY ID
            """;
        List<Product> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar os produtos", e);
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
        p.setName(rs.getString("NAME"));

        Timestamp v = rs.getTimestamp("VALIDITY_DATE");
        p.setValidityDate(v != null ? v.toLocalDateTime() : null);

        p.setWarehouseId(rs.getLong("WAREHOUSE_ID"));
        p.setCostPrice(rs.getBigDecimal("COST_PRICE"));

        String st = rs.getString("STATUS");
        p.setStatus(st != null ? ProductStatus.forValue(st) : null);

        Timestamp c = rs.getTimestamp("CREATED_DATE");
        Timestamp m = rs.getTimestamp("MODIFY_DATE");
        p.setCreatedDate(c != null ? c.toLocalDateTime() : null);
        p.setModifyDate(m != null ? m.toLocalDateTime() : null);
        return p;
    }
}
