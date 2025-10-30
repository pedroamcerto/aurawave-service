package com.aurawave.dao;

import com.aurawave.core.exception.NotFoundException;
import com.aurawave.domain.interfaces.DaoInterface;
import com.aurawave.domain.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ModelDao implements DaoInterface<Model, Long> {

    @Autowired
    private Connection connection;

    @Override
    public Long create(Model model) {
        final String sql = """
            INSERT INTO MODEL (NM_MODEL, MANUFACTURER_ID_MANUFACTURER)
            VALUES (?, ?)
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"})) {
            ps.setString(1, model.getNmModel());
            ps.setLong(2, model.getManufacturerId());
            int rows = ps.executeUpdate();
            if (rows == 0) throw new SQLException("Insert falhou: nenhuma linha afetada.");

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
            throw new SQLException("Não foi possível recuperar a chave gerada (ID).");
        } catch (SQLException e) {
            if (e.getErrorCode() == 2291) {
                throw new NotFoundException("Manufacturer (manufacturerId=" + model.getManufacturerId() + ") não encontrado.");
            }
            throw new RuntimeException("Erro na criação do modelo", e);
        }
    }

    @Override
    public void update(Long id, Model model) {
        final String sql = """
            UPDATE MODEL
               SET NM_MODEL = ?, MANUFACTURER_ID_MANUFACTURER = ?
             WHERE ID = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, model.getNmModel());
            ps.setLong(2, model.getManufacturerId());
            ps.setLong(3, id);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new NotFoundException("Modelo id " + id + " não encontrado");
        } catch (SQLException e) {
            if (e.getErrorCode() == 2291) {
                throw new NotFoundException("Manufacturer (manufacturerId=" + model.getManufacturerId() + ") não encontrado.");
            }
            throw new RuntimeException("Erro ao atualizar o modelo " + id, e);
        }
    }

    @Override
    public Model getById(Long id) {
        final String sql = """
            SELECT ID, NM_MODEL, MANUFACTURER_ID_MANUFACTURER, CREATED_AT, UPDATED_AT
              FROM MODEL
             WHERE ID = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) throw new NotFoundException("Modelo id " + id + " não encontrado");
                return map(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar modelo " + id, e);
        }
    }

    @Override
    public List<Model> getAll() {
        final String sql = """
            SELECT ID, NM_MODEL, MANUFACTURER_ID_MANUFACTURER, CREATED_AT, UPDATED_AT
              FROM MODEL
             ORDER BY ID
            """;
        List<Model> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar modelos", e);
        }
    }

    @Override
    public void delete(Long id) {
        final String sql = "DELETE FROM MODEL WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new NotFoundException("Modelo id " + id + " não encontrado");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar o modelo " + id, e);
        }
    }

    private Model map(ResultSet rs) throws SQLException {
        Model m = new Model();
        m.setId(rs.getLong("ID"));
        m.setNmModel(rs.getString("NM_MODEL"));
        m.setManufacturerId(rs.getLong("MANUFACTURER_ID_MANUFACTURER"));
        Timestamp c = rs.getTimestamp("CREATED_AT");
        Timestamp mod = rs.getTimestamp("UPDATED_AT");
        m.setCreatedDate(c != null ? c.toLocalDateTime() : null);
        m.setModifyDate(mod != null ? mod.toLocalDateTime() : null);
        return m;
    }
}
