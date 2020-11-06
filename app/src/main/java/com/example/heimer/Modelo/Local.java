package com.example.heimer.Modelo;

import java.io.Serializable;

public class Local implements Serializable {

    private int id;
    private String endereco;
    private String bairro;
    private String cidade;
    private int capacidade;

    public Local(int id, String endereco, String bairro, String cidade, int capacidade) {
        this.id = id;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.capacidade = capacidade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Local: " + getEndereco() + " - Cidade: " + getCidade() + " - Bairro: " + getBairro() + " - Capacidade: " + getCapacidade();
    }
}
