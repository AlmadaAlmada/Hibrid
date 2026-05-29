package com.hibrid.poc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "predicao")
public class Predicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vaca_id")
    private Long vacaId;

    @Column(name = "vaca_nome")
    private String vacaNome;

    @Column(name = "semen_id")
    private Long semenId;

    @Column(name = "semen_codigo")
    private String semenCodigo;

    @Column(name = "score_genetico")
    private Integer scoreGenetico;

    @Column(name = "producao_leite_estimada")
    private Double producaoLeiteEstimada;

    @Column(name = "ganho_peso_estimado")
    private Double ganhoPesoEstimado;

    @Column(name = "indice_fertilidade")
    private Integer indiceFertilidade;

    @Column(name = "tipo_cruzamento")
    private String tipoCruzamento;

    @Column(name = "resultado_previsto")
    private String resultadoPrevisto;

    @Column(name = "recomendacao", length = 1000)
    private String recomendacao;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVacaId() {
        return vacaId;
    }

    public void setVacaId(Long vacaId) {
        this.vacaId = vacaId;
    }

    public String getVacaNome() {
        return vacaNome;
    }

    public void setVacaNome(String vacaNome) {
        this.vacaNome = vacaNome;
    }

    public Long getSemenId() {
        return semenId;
    }

    public void setSemenId(Long semenId) {
        this.semenId = semenId;
    }

    public String getSemenCodigo() {
        return semenCodigo;
    }

    public void setSemenCodigo(String semenCodigo) {
        this.semenCodigo = semenCodigo;
    }

    public Integer getScoreGenetico() {
        return scoreGenetico;
    }

    public void setScoreGenetico(Integer scoreGenetico) {
        this.scoreGenetico = scoreGenetico;
    }

    public Double getProducaoLeiteEstimada() {
        return producaoLeiteEstimada;
    }

    public void setProducaoLeiteEstimada(Double producaoLeiteEstimada) {
        this.producaoLeiteEstimada = producaoLeiteEstimada;
    }

    public Double getGanhoPesoEstimado() {
        return ganhoPesoEstimado;
    }

    public void setGanhoPesoEstimado(Double ganhoPesoEstimado) {
        this.ganhoPesoEstimado = ganhoPesoEstimado;
    }

    public Integer getIndiceFertilidade() {
        return indiceFertilidade;
    }

    public void setIndiceFertilidade(Integer indiceFertilidade) {
        this.indiceFertilidade = indiceFertilidade;
    }

    public String getTipoCruzamento() {
        return tipoCruzamento;
    }

    public void setTipoCruzamento(String tipoCruzamento) {
        this.tipoCruzamento = tipoCruzamento;
    }

    public String getResultadoPrevisto() {
        return resultadoPrevisto;
    }

    public void setResultadoPrevisto(String resultadoPrevisto) {
        this.resultadoPrevisto = resultadoPrevisto;
    }

    public String getRecomendacao() {
        return recomendacao;
    }

    public void setRecomendacao(String recomendacao) {
        this.recomendacao = recomendacao;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}

