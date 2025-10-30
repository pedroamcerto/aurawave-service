package com.aurawave.dao;

import com.aurawave.core.exception.NotFoundException;
import com.aurawave.domain.interfaces.DaoInterface;
import com.aurawave.domain.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EventDao implements DaoInterface<Event, Long> {

    @Autowired
    private Connection connection;

    @Override
    public Long create(Event event) {
        final String sql = """
            INSERT INTO EVENT (EVENT_TYPE, EVENT, ITEM_ID_ITEM)
            VALUES (?, ?, ?)
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"})) {
            ps.setString(1, event.getEventType());
            ps.setString(2, event.getEvent());
            ps.setLong(3, event.getItemId());
            int rows = ps.executeUpdate();
            if (rows == 0) throw new SQLException("Insert falhou: nenhuma linha afetada.");

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
            throw new SQLException("Não foi possível recuperar a chave gerada (ID).");
        } catch (SQLException e) {
            if (e.getErrorCode() == 2291) {
                throw new NotFoundException("Item (itemId=" + event.getItemId() + ") não encontrado.");
            }
            throw new RuntimeException("Erro na criação do evento", e);
        }
    }

    @Override
    public void update(Long id, Event event) {
        final String sql = """
            UPDATE EVENT
               SET EVENT_TYPE = ?, EVENT = ?, ITEM_ID_ITEM = ?
             WHERE ID = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, event.getEventType());
            ps.setString(2, event.getEvent());
            ps.setLong(3, event.getItemId());
            ps.setLong(4, id);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new NotFoundException("Evento id " + id + " não encontrado");
        } catch (SQLException e) {
            if (e.getErrorCode() == 2291) {
                throw new NotFoundException("Item (itemId=" + event.getItemId() + ") não encontrado.");
            }
            throw new RuntimeException("Erro ao atualizar o evento " + id, e);
        }
    }

    @Override
    public Event getById(Long id) {
        final String sql = """
            SELECT ID, EVENT_TYPE, EVENT, ITEM_ID_ITEM, CREATED_AT, UPDATED_AT
              FROM EVENT
             WHERE ID = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) throw new NotFoundException("Evento id " + id + " não encontrado");
                return map(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar evento " + id, e);
        }
    }

    @Override
    public List<Event> getAll() {
        final String sql = """
            SELECT ID, EVENT_TYPE, EVENT, ITEM_ID_ITEM, CREATED_AT, UPDATED_AT
              FROM EVENT
             ORDER BY ID
            """;
        List<Event> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar eventos", e);
        }
    }

    @Override
    public void delete(Long id) {
        final String sql = "DELETE FROM EVENT WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new NotFoundException("Evento id " + id + " não encontrado");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar o evento " + id, e);
        }
    }

    private Event map(ResultSet rs) throws SQLException {
        Event e = new Event();
        e.setId(rs.getLong("ID"));
        e.setEventType(rs.getString("EVENT_TYPE"));
        e.setEvent(rs.getString("EVENT"));
        e.setItemId(rs.getLong("ITEM_ID_ITEM"));
        Timestamp c = rs.getTimestamp("CREATED_AT");
        Timestamp m = rs.getTimestamp("UPDATED_AT");
        e.setCreatedDate(c != null ? c.toLocalDateTime() : null);
        e.setModifyDate(m != null ? m.toLocalDateTime() : null);
        return e;
    }
}
