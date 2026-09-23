package com.cooperativa.votacao.model;


import com.cooperativa.votacao.model.enums.ResultadoVotacao;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "pauta")
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String titulo;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private ResultadoVotacao resultadoVotacao = ResultadoVotacao.AGUARDANDO_VOTACAO;

    public Pauta(){

    }

    public Pauta(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public UUID getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public ResultadoVotacao getResultadoVotacao() { return resultadoVotacao; }

    public void setResultadoVotacao(ResultadoVotacao resultadoVotacao) {
        this.resultadoVotacao = resultadoVotacao;
    }
}
