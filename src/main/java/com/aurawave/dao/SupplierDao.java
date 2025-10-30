package com.aurawave.dao;

import com.aurawave.core.exception.NotFoundException;
import com.aurawave.domain.interfaces.DaoInterface;
import com.aurawave.domain.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SupplierDao implements DaoInterface<Supplier, Long> {

    @Autowired
    private Connection connection;

    @Override
    public Long create(Supplier supplier) {
        final String sql = """
            INSERT INTO SUPPLIER (NM_SUPPLIER, CPF)
            VALUES (?, ?)
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"})) {
            ps.setString(1, supplier.getNmSupplier());
            ps.setString(2, supplier.getCpf());
            int rows = ps.executeUpdate();
            if (rows == 0) throw new SQLException("Insert falhou: nenhuma linha afetada.");

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
            throw new SQLException("Não foi possível recuperar a chave gerada (ID).");
        } catch (SQLException e) {
            throw new RuntimeException("Erro na criação do fornecedor", e);
        }
    }

    @Override
    public void update(Long id, Supplier supplier) {
        final String sql = """
            UPDATE SUPPLIER
               SET NM_SUPPLIER = ?, CPF = ?
             WHERE ID = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, supplier.getNmSupplier());
            ps.setString(2, supplier.getCpf());
            ps.setLong(3, id);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new NotFoundException("Fornecedor id " + id + " não encontrado");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar o fornecedor " + id, e);
        }
    }

    @Override
    public Supplier getById(Long id) {
        final String sql = """
            SELECT ID, NM_SUPPLIER, CPF, CREATED_AT, UPDATED_AT
              FROM SUPPLIER
             WHERE ID = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) throw new NotFoundException("Fornecedor id " + id + " não encontrado");
                return map(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar fornecedor " + id, e);
        }
    }

    @Override
    public List<Supplier> getAll() {
        final String sql = """
            SELECT ID, NM_SUPPLIER, CPF, CREATED_AT, UPDATED_AT
              FROM SUPPLIER
             ORDER BY ID
            """;
        List<Supplier> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar fornecedores", e);
        }
    }

    @Override
    public void delete(Long id) {
        final String sql = "DELETE FROM SUPPLIER WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new NotFoundException("Fornecedor id " + id + " não encontrado");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar o fornecedor " + id, e);
        }
    }

    private Supplier map(ResultSet rs) throws SQLException {
        Supplier s = new Supplier();
        s.setId(rs.getLong("ID"));
        s.setNmSupplier(rs.getString("NM_SUPPLIER"));
        s.setCpf(rs.getString("CPF"));
        Timestamp c = rs.getTimestamp("CREATED_AT");
        Timestamp m = rs.getTimestamp("UPDATED_AT");
        s.setCreatedDate(c != null ? c.toLocalDateTime() : null);
        s.setModifyDate(m != null ? m.toLocalDateTime() : null);
        return s;
    }
}
