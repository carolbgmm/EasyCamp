package com.example.easycamp.domain;



public class InscritosDTO {

    private String id;
    private String hijoId;
    private String campamentoId;
    private String idPadre; // Nuevo atributo

    // Constructor vacío necesario para Firebase
    public InscritosDTO() {
    }

    public InscritosDTO(String id, String hijoId, String campamentoId, String idPadre) {
        this.id = id;
        this.hijoId = hijoId;
        this.campamentoId = campamentoId;
        this.idPadre = idPadre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHijoId() {
        return hijoId;
    }

    public void setHijoId(String hijoId) {
        this.hijoId = hijoId;
    }

    public String getCampamentoId() {
        return campamentoId;
    }

    public void setCampamentoId(String campamentoId) {
        this.campamentoId = campamentoId;
    }

    public String getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(String idPadre) {
        this.idPadre = idPadre;
    }
}
