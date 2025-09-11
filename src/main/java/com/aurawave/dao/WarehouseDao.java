package com.aurawave.dao;

import com.aurawave.core.exception.NotFoundException;
import com.aurawave.domain.interfaces.WarehouseInterface;
import com.aurawave.domain.model.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WarehouseDao implements WarehouseInterface {

    @Autowired
    private Connection connection;

    @Override
    public Long create(Warehouse wh) {
        final String sql = """
            INSERT INTO WAREHOUSE (NAME, ADDRESS)
            VALUES (?, ?)
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"})) {
            ps.setString(1, wh.getName());
            ps.setString(2, wh.getAddress());
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
    public void update(Long id, Warehouse wh) {
        final String sql = """
            UPDATE WAREHOUSE
               SET NAME = ?, ADDRESS = ?
             WHERE ID = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, wh.getName());
            ps.setString(2, wh.getAddress());
            ps.setLong(3, id);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new NotFoundException("Almoxarifado id " + id + " não encontrado");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar o almoxarifado " + id, e);
        }
    }

    @Override
    public Warehouse getById(Long id) {
        final String sql = """
            SELECT ID, NAME, ADDRESS, CREATED_DATE, MODIFY_DATE
              FROM WAREHOUSE
             WHERE ID = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) throw new NotFoundException("Almoxarifado id " + id + " não encontrado");
                return map(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar almoxarifado " + id, e);
        }
    }

    @Override
    public List<Warehouse> getAll() {
        final String sql = """
            SELECT ID, NAME, ADDRESS, CREATED_DATE, MODIFY_DATE
              FROM WAREHOUSE
             ORDER BY ID
            """;
        List<Warehouse> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar almoxarifados", e);
        }
    }

    @Override
    public void delete(Long id) {
        final String sql = "DELETE FROM WAREHOUSE WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new NotFoundException("Almoxarifado id " + id + " não encontrado");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar o almoxarifado " + id, e);
        }
    }

    public boolean existsById(Long id) {
        final String sql = "SELECT 1 FROM WAREHOUSE WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência do warehouse " + id, e);
        }
    }


    private Warehouse map(ResultSet rs) throws SQLException {
        Warehouse w = new Warehouse();
        w.setId(rs.getLong("ID"));
        w.setName(rs.getString("NAME"));
        w.setAddress(rs.getString("ADDRESS"));
        Timestamp c = rs.getTimestamp("CREATED_DATE");
        Timestamp m = rs.getTimestamp("MODIFY_DATE");
        w.setCreatedDate(c != null ? c.toLocalDateTime() : null);
        w.setModifyDate(m != null ? m.toLocalDateTime() : null);
        return w;
    }
}
