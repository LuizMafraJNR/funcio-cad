package com.example.application.dto;

import java.time.LocalDate;
import java.util.Objects;

public class FuncionarioDTO{
    private Integer id;
    private String nome;
    private Integer rg;
    private String cargo;
    private Double salario;
    private LocalDate dataNascimento;
    private LocalDate dataAdmissao;
    private String status;
    private String departamento;

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Integer getRg() {
        return rg;
    }

    public void setRg(Integer rg) {
        this.rg = rg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FuncionarioDTO that = (FuncionarioDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(rg, that.rg) && Objects.equals(cargo, that.cargo) && Objects.equals(salario, that.salario) && Objects.equals(dataNascimento, that.dataNascimento) && Objects.equals(dataAdmissao, that.dataAdmissao) && Objects.equals(status, that.status) && Objects.equals(departamento, that.departamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, rg, cargo, salario, dataNascimento, dataAdmissao, status, departamento);
    }
}
