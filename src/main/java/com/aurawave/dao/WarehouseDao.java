package com.aurawave.dao;

import com.aurawave.core.exception.NotFoundException;
import com.aurawave.domain.interfaces.DaoInterface;
import com.aurawave.domain.model.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WarehouseDao implements DaoInterface<Warehouse, Long> {

    @Autowired
    private Connection connection;

    @Override
    public Long create(Warehouse warehouse) {
        final String sql = """
            INSERT INTO WAREHOUSE (NM_WAREHOUSE, LABORATORY_ID_LABORATORY)
            VALUES (?, ?)
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"})) {
            ps.setString(1, warehouse.getName());
            ps.setLong(2, warehouse.getLaboratoryId());
            int rows = ps.executeUpdate();
            if (rows == 0) throw new SQLException("Insert falhou: nenhuma linha afetada.");

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
            throw new SQLException("Não foi possível recuperar a chave gerada (ID).");
        } catch (SQLException e) {
            throw new RuntimeException("Erro na criação do almoxarifado", e);
        }
    }

    @Override
    public void update(Long id, Warehouse warehouse) {
        final String sql = """
            UPDATE WAREHOUSE
               SET NM_WAREHOUSE = ?, LABORATORY_ID_LABORATORY = ?
             WHERE ID = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, warehouse.getName());
            ps.setLong(2, warehouse.getLaboratoryId());
            ps.setLong(3, id);

            int rows = ps.executeUpdate();
            if (rows == 0) throw new NotFoundException("Almoxarifado com ID " + id + " não encontrado.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro na atualização do almoxarifado", e);
        }
    }

    @Override
    public void delete(Long id) {
        final String sql = "DELETE FROM WAREHOUSE WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new NotFoundException("Almoxarifado com ID " + id + " não encontrado.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar almoxarifado", e);
        }
    }

    @Override
    public Warehouse getById(Long id) {
        final String sql = """
            SELECT ID, NM_WAREHOUSE, LABORATORY_ID_LABORATORY, CREATED_AT, UPDATED_AT
              FROM WAREHOUSE
             WHERE ID = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
            throw new NotFoundException("Almoxarifado com ID " + id + " não encontrado.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar almoxarifado", e);
        }
    }

    @Override
    public List<Warehouse> getAll() {
        final String sql = """
            SELECT ID, NM_WAREHOUSE, LABORATORY_ID_LABORATORY, CREATED_AT, UPDATED_AT
              FROM WAREHOUSE
            """;
        List<Warehouse> warehouses = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                warehouses.add(mapRow(rs));
            }
            return warehouses;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar almoxarifados", e);
        }
    }

    public boolean existsById(Long id) {
        final String sql = "SELECT COUNT(*) FROM WAREHOUSE WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência do almoxarifado", e);
        }
    }

    private Warehouse mapRow(ResultSet rs) throws SQLException {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(rs.getLong("ID"));
        warehouse.setName(rs.getString("NM_WAREHOUSE"));
        warehouse.setLaboratoryId(rs.getLong("LABORATORY_ID_LABORATORY"));
        warehouse.setCreatedDate(rs.getTimestamp("CREATED_AT").toLocalDateTime());
        warehouse.setModifyDate(rs.getTimestamp("UPDATED_AT").toLocalDateTime());
        return warehouse;
    }
}
