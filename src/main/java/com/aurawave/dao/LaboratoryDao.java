package com.aurawave.dao;

import com.aurawave.core.exception.NotFoundException;
import com.aurawave.domain.interfaces.DaoInterface;
import com.aurawave.domain.model.Laboratory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LaboratoryDao implements DaoInterface<Laboratory, Long> {

    @Autowired
    private Connection connection;

    @Override
    public Long create(Laboratory laboratory) {
        final String sql = """
            INSERT INTO LABORATORY (NM_LABORATORY)
            VALUES (?)
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"})) {
            ps.setString(1, laboratory.getNmLaboratory());
            int rows = ps.executeUpdate();
            if (rows == 0) throw new SQLException("Insert falhou: nenhuma linha afetada.");

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
            throw new SQLException("Não foi possível recuperar a chave gerada (ID).");
        } catch (SQLException e) {
            throw new RuntimeException("Erro na criação do laboratório", e);
        }
    }

    @Override
    public void update(Long id, Laboratory laboratory) {
        final String sql = """
            UPDATE LABORATORY
               SET NM_LABORATORY = ?
             WHERE ID = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, laboratory.getNmLaboratory());
            ps.setLong(2, id);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new NotFoundException("Laboratório id " + id + " não encontrado");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar o laboratório " + id, e);
        }
    }

    @Override
    public Laboratory getById(Long id) {
        final String sql = """
            SELECT ID, NM_LABORATORY, CREATED_AT, UPDATED_AT
              FROM LABORATORY
             WHERE ID = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) throw new NotFoundException("Laboratório id " + id + " não encontrado");
                return map(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar laboratório " + id, e);
        }
    }

    @Override
    public List<Laboratory> getAll() {
        final String sql = """
            SELECT ID, NM_LABORATORY, CREATED_AT, UPDATED_AT
              FROM LABORATORY
             ORDER BY ID
            """;
        List<Laboratory> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar laboratórios", e);
        }
    }

    @Override
    public void delete(Long id) {
        final String sql = "DELETE FROM LABORATORY WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new NotFoundException("Laboratório id " + id + " não encontrado");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar o laboratório " + id, e);
        }
    }

    private Laboratory map(ResultSet rs) throws SQLException {
        Laboratory l = new Laboratory();
        l.setId(rs.getLong("ID"));
        l.setNmLaboratory(rs.getString("NM_LABORATORY"));
        Timestamp c = rs.getTimestamp("CREATED_AT");
        Timestamp m = rs.getTimestamp("UPDATED_AT");
        l.setCreatedDate(c != null ? c.toLocalDateTime() : null);
        l.setModifyDate(m != null ? m.toLocalDateTime() : null);
        return l;
    }
}
