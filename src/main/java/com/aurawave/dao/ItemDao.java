package com.aurawave.dao;

import com.aurawave.core.exception.NotFoundException;
import com.aurawave.domain.interfaces.DaoInterface;
import com.aurawave.domain.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemDao implements DaoInterface<Item, Long> {

    @Autowired
    private Connection connection;

    @Override
    public Long create(Item item) {
        final String sql = """
            INSERT INTO ITEM (NM_ITEM, STATUS, MODEL_ID_MODEL, BATCH_ID_BATCH)
            VALUES (?, ?, ?, ?)
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"})) {
            ps.setString(1, item.getNmItem());
            ps.setString(2, item.getStatus());
            ps.setLong(3, item.getModelId());
            ps.setLong(4, item.getBatchId());
            int rows = ps.executeUpdate();
            if (rows == 0) throw new SQLException("Insert falhou: nenhuma linha afetada.");

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
            throw new SQLException("Não foi possível recuperar a chave gerada (ID).");
        } catch (SQLException e) {
            if (e.getErrorCode() == 2291) {
                throw new NotFoundException("Model ou Batch não encontrado (modelId=" + item.getModelId() + ", batchId=" + item.getBatchId() + ")");
            }
            throw new RuntimeException("Erro na criação do item", e);
        }
    }

    @Override
    public void update(Long id, Item item) {
        final String sql = """
            UPDATE ITEM
               SET NM_ITEM = ?, STATUS = ?, MODEL_ID_MODEL = ?, BATCH_ID_BATCH = ?
             WHERE ID = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, item.getNmItem());
            ps.setString(2, item.getStatus());
            ps.setLong(3, item.getModelId());
            ps.setLong(4, item.getBatchId());
            ps.setLong(5, id);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new NotFoundException("Item id " + id + " não encontrado");
        } catch (SQLException e) {
            if (e.getErrorCode() == 2291) {
                throw new NotFoundException("Model ou Batch não encontrado (modelId=" + item.getModelId() + ", batchId=" + item.getBatchId() + ")");
            }
            throw new RuntimeException("Erro ao atualizar o item " + id, e);
        }
    }

    @Override
    public Item getById(Long id) {
        final String sql = """
            SELECT ID, NM_ITEM, STATUS, MODEL_ID_MODEL, BATCH_ID_BATCH, CREATED_AT, UPDATED_AT
              FROM ITEM
             WHERE ID = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) throw new NotFoundException("Item id " + id + " não encontrado");
                return map(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar item " + id, e);
        }
    }

    @Override
    public List<Item> getAll() {
        final String sql = """
            SELECT ID, NM_ITEM, STATUS, MODEL_ID_MODEL, BATCH_ID_BATCH, CREATED_AT, UPDATED_AT
              FROM ITEM
             ORDER BY ID
            """;
        List<Item> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar itens", e);
        }
    }

    @Override
    public void delete(Long id) {
        final String sql = "DELETE FROM ITEM WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new NotFoundException("Item id " + id + " não encontrado");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar o item " + id, e);
        }
    }

    private Item map(ResultSet rs) throws SQLException {
        Item i = new Item();
        i.setId(rs.getLong("ID"));
        i.setNmItem(rs.getString("NM_ITEM"));
        i.setStatus(rs.getString("STATUS"));
        i.setModelId(rs.getLong("MODEL_ID_MODEL"));
        i.setBatchId(rs.getLong("BATCH_ID_BATCH"));
        Timestamp c = rs.getTimestamp("CREATED_AT");
        Timestamp m = rs.getTimestamp("UPDATED_AT");
        i.setCreatedDate(c != null ? c.toLocalDateTime() : null);
        i.setModifyDate(m != null ? m.toLocalDateTime() : null);
        return i;
    }
}
