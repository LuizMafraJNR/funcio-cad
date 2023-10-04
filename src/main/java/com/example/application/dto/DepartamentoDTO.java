package com.example.application.dto;

import java.util.List;

public class DepartamentoDTO {
    private Integer id;
    private String nome;
    private String status;

    public Boolean isAtivo(){
        if (this.status.equals("Ativo")){
            return true;
        } else {
            return false;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

}
