package com.aurawave.dao;

import com.aurawave.core.exception.NotFoundException;
import com.aurawave.domain.interfaces.DaoInterface;
import com.aurawave.domain.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SessionDao implements DaoInterface<Session, Long> {

    @Autowired
    private Connection connection;

    @Override
    public Long create(Session session) {
        final String sql = """
            INSERT INTO SESSION (START, "END", WAREHOUSE_ID_WAREHOUSE, COLLABORATOR_ID_COLLABORATOR)
            VALUES (?, ?, ?, ?)
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"})) {
            ps.setTimestamp(1, session.getStart() != null ? Timestamp.valueOf(session.getStart()) : null);
            ps.setTimestamp(2, session.getEnd() != null ? Timestamp.valueOf(session.getEnd()) : null);
            ps.setLong(3, session.getWarehouseId());
            ps.setLong(4, session.getCollaboratorId());
            int rows = ps.executeUpdate();
            if (rows == 0) throw new SQLException("Insert falhou: nenhuma linha afetada.");

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
            throw new SQLException("Não foi possível recuperar a chave gerada (ID).");
        } catch (SQLException e) {
            if (e.getErrorCode() == 2291) {
                throw new NotFoundException("Warehouse ou Collaborator não encontrado (warehouseId=" + session.getWarehouseId() + ", collaboratorId=" + session.getCollaboratorId() + ")");
            }
            throw new RuntimeException("Erro na criação da sessão", e);
        }
    }

    @Override
    public void update(Long id, Session session) {
        final String sql = """
            UPDATE SESSION
               SET START = ?, "END" = ?, WAREHOUSE_ID_WAREHOUSE = ?, COLLABORATOR_ID_COLLABORATOR = ?
             WHERE ID = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setTimestamp(1, session.getStart() != null ? Timestamp.valueOf(session.getStart()) : null);
            ps.setTimestamp(2, session.getEnd() != null ? Timestamp.valueOf(session.getEnd()) : null);
            ps.setLong(3, session.getWarehouseId());
            ps.setLong(4, session.getCollaboratorId());
            ps.setLong(5, id);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new NotFoundException("Sessão id " + id + " não encontrada");
        } catch (SQLException e) {
            if (e.getErrorCode() == 2291) {
                throw new NotFoundException("Warehouse ou Collaborator não encontrado (warehouseId=" + session.getWarehouseId() + ", collaboratorId=" + session.getCollaboratorId() + ")");
            }
            throw new RuntimeException("Erro ao atualizar a sessão " + id, e);
        }
    }

    @Override
    public Session getById(Long id) {
        final String sql = """
            SELECT ID, START, "END", WAREHOUSE_ID_WAREHOUSE, COLLABORATOR_ID_COLLABORATOR, CREATED_AT, UPDATED_AT
              FROM SESSION
             WHERE ID = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) throw new NotFoundException("Sessão id " + id + " não encontrada");
                return map(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar sessão " + id, e);
        }
    }

    @Override
    public List<Session> getAll() {
        final String sql = """
            SELECT ID, START, "END", WAREHOUSE_ID_WAREHOUSE, COLLABORATOR_ID_COLLABORATOR, CREATED_AT, UPDATED_AT
              FROM SESSION
             ORDER BY ID
            """;
        List<Session> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar sessões", e);
        }
    }

    @Override
    public void delete(Long id) {
        final String sql = "DELETE FROM SESSION WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new NotFoundException("Sessão id " + id + " não encontrada");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar a sessão " + id, e);
        }
    }

    private Session map(ResultSet rs) throws SQLException {
        Session s = new Session();
        s.setId(rs.getLong("ID"));
        Timestamp start = rs.getTimestamp("START");
        Timestamp end = rs.getTimestamp("END");
        s.setStart(start != null ? start.toLocalDateTime() : null);
        s.setEnd(end != null ? end.toLocalDateTime() : null);
        s.setWarehouseId(rs.getLong("WAREHOUSE_ID_WAREHOUSE"));
        s.setCollaboratorId(rs.getLong("COLLABORATOR_ID_COLLABORATOR"));
        Timestamp c = rs.getTimestamp("CREATED_AT");
        Timestamp m = rs.getTimestamp("UPDATED_AT");
        s.setCreatedDate(c != null ? c.toLocalDateTime() : null);
        s.setModifyDate(m != null ? m.toLocalDateTime() : null);
        return s;
    }
}
