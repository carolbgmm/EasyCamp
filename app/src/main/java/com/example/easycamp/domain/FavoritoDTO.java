package com.example.easycamp.domain;


public class FavoritoDTO {

    private String id;  // Nuevo atributo
    private String usuarioId;
    private String campamentoId;

    // Constructor vacío requerido para Firebase
    public FavoritoDTO() {
    }

    public FavoritoDTO(String id, String usuarioId, String campamentoId) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.campamentoId = campamentoId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getCampamentoId() {
        return campamentoId;
    }

    public void setCampamentoId(String campamentoId) {
        this.campamentoId = campamentoId;
    }
}
