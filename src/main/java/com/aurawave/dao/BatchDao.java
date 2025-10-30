package com.aurawave.dao;

import com.aurawave.core.exception.NotFoundException;
import com.aurawave.domain.interfaces.DaoInterface;
import com.aurawave.domain.model.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BatchDao implements DaoInterface<Batch, Long> {

    @Autowired
    private Connection connection;

    @Override
    public Long create(Batch batch) {
        final String sql = """
            INSERT INTO BATCH (NM_BATCH, SUPPLIER_ID_SUPPLIER)
            VALUES (?, ?)
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"})) {
            ps.setString(1, batch.getNmBatch());
            ps.setLong(2, batch.getSupplierId());
            int rows = ps.executeUpdate();
            if (rows == 0) throw new SQLException("Insert falhou: nenhuma linha afetada.");

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
            throw new SQLException("Não foi possível recuperar a chave gerada (ID).");
        } catch (SQLException e) {
            if (e.getErrorCode() == 2291) {
                throw new NotFoundException("Supplier (supplierId=" + batch.getSupplierId() + ") não encontrado.");
            }
            throw new RuntimeException("Erro na criação do lote", e);
        }
    }

    @Override
    public void update(Long id, Batch batch) {
        final String sql = """
            UPDATE BATCH
               SET NM_BATCH = ?, SUPPLIER_ID_SUPPLIER = ?
             WHERE ID = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, batch.getNmBatch());
            ps.setLong(2, batch.getSupplierId());
            ps.setLong(3, id);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new NotFoundException("Lote id " + id + " não encontrado");
        } catch (SQLException e) {
            if (e.getErrorCode() == 2291) {
                throw new NotFoundException("Supplier (supplierId=" + batch.getSupplierId() + ") não encontrado.");
            }
            throw new RuntimeException("Erro ao atualizar o lote " + id, e);
        }
    }

    @Override
    public Batch getById(Long id) {
        final String sql = """
            SELECT ID, NM_BATCH, SUPPLIER_ID_SUPPLIER, CREATED_AT, UPDATED_AT
              FROM BATCH
             WHERE ID = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) throw new NotFoundException("Lote id " + id + " não encontrado");
                return map(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar lote " + id, e);
        }
    }

    @Override
    public List<Batch> getAll() {
        final String sql = """
            SELECT ID, NM_BATCH, SUPPLIER_ID_SUPPLIER, CREATED_AT, UPDATED_AT
              FROM BATCH
             ORDER BY ID
            """;
        List<Batch> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar lotes", e);
        }
    }

    @Override
    public void delete(Long id) {
        final String sql = "DELETE FROM BATCH WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new NotFoundException("Lote id " + id + " não encontrado");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar o lote " + id, e);
        }
    }

    private Batch map(ResultSet rs) throws SQLException {
        Batch b = new Batch();
        b.setId(rs.getLong("ID"));
        b.setNmBatch(rs.getString("NM_BATCH"));
        b.setSupplierId(rs.getLong("SUPPLIER_ID_SUPPLIER"));
        Timestamp c = rs.getTimestamp("CREATED_AT");
        Timestamp m = rs.getTimestamp("UPDATED_AT");
        b.setCreatedDate(c != null ? c.toLocalDateTime() : null);
        b.setModifyDate(m != null ? m.toLocalDateTime() : null);
        return b;
    }
}
