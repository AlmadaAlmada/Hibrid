package com.hibrid.poc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "animal")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String codigo;
    private String tipo;
    private String sexo;
    private String especie;
    private String raca;
    private Double peso;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "data_vacinacao")
    private LocalDate dataVacinacao;

    @Column(name = "producao_leite_diaria")
    private Double producaoLeiteDiaria;

    @Column(name = "producao_leite_mensal")
    private Double producaoLeiteMensal;

    @Column(name = "producao_media_carne")
    private Double producaoMediaCarne;

    private Integer procriacoes;

    @Column(name = "ultima_procriacao")
    private LocalDate ultimaProcriacao;

    @Column(name = "produzindo_leite")
    private Boolean produzindoLeite;

    private String destino;

    @Column(name = "usuario_id")
    private Long usuarioId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public LocalDate getDataVacinacao() {
        return dataVacinacao;
    }

    public void setDataVacinacao(LocalDate dataVacinacao) {
        this.dataVacinacao = dataVacinacao;
    }

    public Double getProducaoLeiteDiaria() {
        return producaoLeiteDiaria;
    }

    public void setProducaoLeiteDiaria(Double producaoLeiteDiaria) {
        this.producaoLeiteDiaria = producaoLeiteDiaria;
    }

    public Double getProducaoLeiteMensal() {
        return producaoLeiteMensal;
    }

    public void setProducaoLeiteMensal(Double producaoLeiteMensal) {
        this.producaoLeiteMensal = producaoLeiteMensal;
    }

    public Double getProducaoMediaCarne() {
        return producaoMediaCarne;
    }

    public void setProducaoMediaCarne(Double producaoMediaCarne) {
        this.producaoMediaCarne = producaoMediaCarne;
    }

    public Integer getProcriacoes() {
        return procriacoes;
    }

    public void setProcriacoes(Integer procriacoes) {
        this.procriacoes = procriacoes;
    }

    public LocalDate getUltimaProcriacao() {
        return ultimaProcriacao;
    }

    public void setUltimaProcriacao(LocalDate ultimaProcriacao) {
        this.ultimaProcriacao = ultimaProcriacao;
    }

    public Boolean getProduzindoLeite() {
        return produzindoLeite;
    }

    public void setProduzindoLeite(Boolean produzindoLeite) {
        this.produzindoLeite = produzindoLeite;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}

