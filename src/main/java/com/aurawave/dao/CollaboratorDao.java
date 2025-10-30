package com.aurawave.dao;

import com.aurawave.core.exception.NotFoundException;
import com.aurawave.domain.interfaces.DaoInterface;
import com.aurawave.domain.model.Collaborator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CollaboratorDao implements DaoInterface<Collaborator, Long> {

    @Autowired
    private Connection connection;

    @Override
    public Long create(Collaborator collaborator) {
        final String sql = """
            INSERT INTO COLLABORATOR (NM_COLLABORATOR, COD_COMPANY, CPF)
            VALUES (?, ?, ?)
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"})) {
            ps.setString(1, collaborator.getNmCollaborator());
            ps.setString(2, collaborator.getCodCompany());
            ps.setString(3, collaborator.getCpf());
            int rows = ps.executeUpdate();
            if (rows == 0) throw new SQLException("Insert falhou: nenhuma linha afetada.");

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
            throw new SQLException("Não foi possível recuperar a chave gerada (ID).");
        } catch (SQLException e) {
            throw new RuntimeException("Erro na criação do colaborador", e);
        }
    }

    @Override
    public void update(Long id, Collaborator collaborator) {
        final String sql = """
            UPDATE COLLABORATOR
               SET NM_COLLABORATOR = ?, COD_COMPANY = ?, CPF = ?
             WHERE ID = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, collaborator.getNmCollaborator());
            ps.setString(2, collaborator.getCodCompany());
            ps.setString(3, collaborator.getCpf());
            ps.setLong(4, id);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new NotFoundException("Colaborador id " + id + " não encontrado");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar o colaborador " + id, e);
        }
    }

    @Override
    public Collaborator getById(Long id) {
        final String sql = """
            SELECT ID, NM_COLLABORATOR, COD_COMPANY, CPF, CREATED_AT, UPDATED_AT
              FROM COLLABORATOR
             WHERE ID = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) throw new NotFoundException("Colaborador id " + id + " não encontrado");
                return map(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar colaborador " + id, e);
        }
    }

    @Override
    public List<Collaborator> getAll() {
        final String sql = """
            SELECT ID, NM_COLLABORATOR, COD_COMPANY, CPF, CREATED_AT, UPDATED_AT
              FROM COLLABORATOR
             ORDER BY ID
            """;
        List<Collaborator> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar colaboradores", e);
        }
    }

    @Override
    public void delete(Long id) {
        final String sql = "DELETE FROM COLLABORATOR WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new NotFoundException("Colaborador id " + id + " não encontrado");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar o colaborador " + id, e);
        }
    }

    private Collaborator map(ResultSet rs) throws SQLException {
        Collaborator c = new Collaborator();
        c.setId(rs.getLong("ID"));
        c.setNmCollaborator(rs.getString("NM_COLLABORATOR"));
        c.setCodCompany(rs.getString("COD_COMPANY"));
        c.setCpf(rs.getString("CPF"));
        Timestamp created = rs.getTimestamp("CREATED_AT");
        Timestamp modified = rs.getTimestamp("UPDATED_AT");
        c.setCreatedDate(created != null ? created.toLocalDateTime() : null);
        c.setModifyDate(modified != null ? modified.toLocalDateTime() : null);
        return c;
    }
}
