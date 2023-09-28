package com.example.application.dto;

import java.util.List;

public class DepartamentoDTO {
    private Integer id;
    private String nome;
    private Integer status;

    public Boolean isAtivo(){
        if (this.status == 1){
            return true;
        } else {
            return false;
        }
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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
