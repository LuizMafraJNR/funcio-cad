package com.example.application.dao;

import com.example.application.connection.ConnectionMyDataBase;
import com.example.application.dto.FuncionarioDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {
    static Connection connection = null;
    static PreparedStatement preparedStatement = null;

    public static void adicionarFuncionario(FuncionarioDTO funcionarioDTO) throws SQLException {
        try{
        connection = ConnectionMyDataBase.getConnection();
        String sql = """    
                INSERT INTO funcionario(nome, rg,cargo,salario,dataNascimento,dataAdmissao,status)
                VALUES (?,?,?,?,?,?,?);
                """;
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, funcionarioDTO.getNome());
        preparedStatement.setInt(2, funcionarioDTO.getRg());
        preparedStatement.setString(3, funcionarioDTO.getCargo());
        preparedStatement.setDouble(4, funcionarioDTO.getSalario());
        preparedStatement.setDate(5, Date.valueOf(funcionarioDTO.getDataNascimento()));
        preparedStatement.setDate(6, Date.valueOf(funcionarioDTO.getDataAdmissao()));
        preparedStatement.setString(7, funcionarioDTO.getStatus());
        preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null){
                preparedStatement.close();
            }
        }
    }

    public static void atualizaFuncionario(FuncionarioDTO funcionarioDTO) throws SQLException{
        try {
        connection = ConnectionMyDataBase.getConnection();
        String sql = """
                UPDATE funcionario SET nome = ?, rg = ?, cargo = ?, salario = ?, dataNascimento = ?, dataAdmissao = ?, status = ? WHERE id = ?
                """;

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, funcionarioDTO.getNome());
        preparedStatement.setInt(2, funcionarioDTO.getRg());
        preparedStatement.setString(3, funcionarioDTO.getCargo());
        preparedStatement.setDouble(4, funcionarioDTO.getSalario());
        preparedStatement.setDate(5, Date.valueOf(funcionarioDTO.getDataNascimento()));
        preparedStatement.setDate(6, Date.valueOf(funcionarioDTO.getDataAdmissao()));
        preparedStatement.setString(7, funcionarioDTO.getStatus());
        preparedStatement.setInt(8, funcionarioDTO.getId());
        preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null){
                preparedStatement.close();
            }
        }
    }

    public static List<FuncionarioDTO> listarFuncionarios() throws SQLException {
        List<FuncionarioDTO> funcionarios = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionMyDataBase.getConnection();
            String sql = "SELECT * FROM funcionario";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                FuncionarioDTO funcionario = new FuncionarioDTO();
                funcionario.setNome(resultSet.getString("nome"));
                funcionario.setId(resultSet.getInt("id"));
                funcionario.setRg(resultSet.getInt("rg"));
                funcionario.setCargo(resultSet.getString("cargo"));
                funcionario.setSalario(resultSet.getDouble("salario"));

                // Converte as datas do banco de dados para GregorianCalendar
                LocalDate dataNascimento = resultSet.getDate("dataNascimento").toLocalDate();
                funcionario.setDataNascimento(dataNascimento);

                LocalDate dataAdmissao = resultSet.getDate("dataAdmissao").toLocalDate();
                funcionario.setDataAdmissao(dataAdmissao);

                funcionario.setStatus(resultSet.getString("status"));

                funcionarios.add(funcionario);
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Erro ao tentar listar os funcionarios");
        }
        finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        return funcionarios;
    }

    public static FuncionarioDTO buscarFuncionarioPorId(Integer id, FuncionarioDTO funcionarioDTO) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionMyDataBase.getConnection();
            String sql = "SELECT * FROM funcionario WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return funcionarioDTO;
            } else {
                return null; // Funcionário não encontrado
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public static void excluirFuncionario(Integer id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionMyDataBase.getConnection();
            String sql = "DELETE FROM funcionario WHERE id = ?";
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
