package com.aurawave.dao;

import com.aurawave.core.exception.NotFoundException;
import com.aurawave.domain.interfaces.DaoInterface;
import com.aurawave.domain.model.Manufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ManufacturerDao implements DaoInterface<Manufacturer, Long> {

    @Autowired
    private Connection connection;

    @Override
    public Long create(Manufacturer manufacturer) {
        final String sql = """
            INSERT INTO MANUFACTURER (NM_MANUFACTURER)
            VALUES (?)
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"})) {
            ps.setString(1, manufacturer.getNmManufacturer());
            int rows = ps.executeUpdate();
            if (rows == 0) throw new SQLException("Insert falhou: nenhuma linha afetada.");

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
            throw new SQLException("Não foi possível recuperar a chave gerada (ID).");
        } catch (SQLException e) {
            throw new RuntimeException("Erro na criação do fabricante", e);
        }
    }

    @Override
    public void update(Long id, Manufacturer manufacturer) {
        final String sql = """
            UPDATE MANUFACTURER
               SET NM_MANUFACTURER = ?
             WHERE ID = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, manufacturer.getNmManufacturer());
            ps.setLong(2, id);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new NotFoundException("Fabricante id " + id + " não encontrado");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar o fabricante " + id, e);
        }
    }

    @Override
    public Manufacturer getById(Long id) {
        final String sql = """
            SELECT ID, NM_MANUFACTURER, CREATED_AT, UPDATED_AT
              FROM MANUFACTURER
             WHERE ID = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) throw new NotFoundException("Fabricante id " + id + " não encontrado");
                return map(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar fabricante " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        final String sql = """
            SELECT ID, NM_MANUFACTURER, CREATED_AT, UPDATED_AT
              FROM MANUFACTURER
             ORDER BY ID
            """;
        List<Manufacturer> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar fabricantes", e);
        }
    }

    @Override
    public void delete(Long id) {
        final String sql = "DELETE FROM MANUFACTURER WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new NotFoundException("Fabricante id " + id + " não encontrado");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar o fabricante " + id, e);
        }
    }

    private Manufacturer map(ResultSet rs) throws SQLException {
        Manufacturer m = new Manufacturer();
        m.setId(rs.getLong("ID"));
        m.setNmManufacturer(rs.getString("NM_MANUFACTURER"));
        Timestamp c = rs.getTimestamp("CREATED_AT");
        Timestamp mod = rs.getTimestamp("UPDATED_AT");
        m.setCreatedDate(c != null ? c.toLocalDateTime() : null);
        m.setModifyDate(mod != null ? mod.toLocalDateTime() : null);
        return m;
    }
}
