package com.cooperativa.votacao.model;

import com.cooperativa.votacao.model.enums.TipoVoto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.UUID;

@Entity
@Table(name = "voto", uniqueConstraints = { @UniqueConstraint(columnNames = {"pauta_id", "associadoId"}) })
public class Voto {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "pauta_id")
    private Pauta pauta;
    private String associadoId;
    @Enumerated(EnumType.STRING)
    private TipoVoto voto;

    public Voto() {}

    public Voto(Pauta pauta, String associadoId, TipoVoto voto) {
        this.pauta = pauta;
        this.associadoId = associadoId;
        this.voto = voto;
    }

    public UUID getId() {
        return id;
    }

    public Pauta getPauta() {
        return pauta;
    }

    public String getAssociadoId() {
        return associadoId;
    }

    public TipoVoto getVoto() {
        return voto;
    }
}
