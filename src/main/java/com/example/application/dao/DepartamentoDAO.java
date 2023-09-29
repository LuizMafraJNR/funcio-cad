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
                departamento.setStatus(resultSet.getInt("status"));


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
}
