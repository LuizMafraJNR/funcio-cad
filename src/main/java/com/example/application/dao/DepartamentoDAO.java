package com.example.application.dao;

import com.example.application.connection.ConnectionMyDataBase;
import com.example.application.dto.DepartamentoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDAO {

    public static List<DepartamentoDTO> listarDepartamentos() throws SQLException {
        List<DepartamentoDTO> departamentos = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionMyDataBase.getConnection();
            String sql = "SELECT * FROM departamento";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                DepartamentoDTO departamento = new DepartamentoDTO();
                departamento.setId(resultSet.getInt("id"));
                departamento.setNome(resultSet.getString("nome"));
                departamento.setStatus(resultSet.getString("status"));


                departamentos.add(departamento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao tentar listar os funcionarios");
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        return departamentos;
    }

    public static void inserirDepartamento(DepartamentoDTO departamento) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionMyDataBase.getConnection();
            String sql = "INSERT INTO departamento (nome, status) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, departamento.getNome());
            preparedStatement.setString(2, departamento.getStatus());
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public static DepartamentoDTO buscarDepartamento(Integer id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DepartamentoDTO departamento = new DepartamentoDTO();

        try {
            connection = ConnectionMyDataBase.getConnection();
            String sql = "SELECT * FROM departamento WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                departamento.setId(resultSet.getInt("id"));
                departamento.setNome(resultSet.getString("nome"));
                departamento.setStatus(resultSet.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao tentar listar os funcionarios");
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        return departamento;
    }

    public static void atualizarDepartamento(DepartamentoDTO departamento) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionMyDataBase.getConnection();
            String sql = "UPDATE departamento SET nome = ?, status = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, departamento.getNome());
            preparedStatement.setString(2, departamento.getStatus());
            preparedStatement.setInt(3, departamento.getId());
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public static void deletarDepartamento(Integer id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionMyDataBase.getConnection();
            String sql = "DELETE FROM departamento WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

}
