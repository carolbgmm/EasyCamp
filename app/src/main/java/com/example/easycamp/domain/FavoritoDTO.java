package com.example.easycamp.domain;

public class FavoritoDTO {
    private String usuarioId;
    private long campamentoId;

    // Constructor vacío requerido para Firebase
    public FavoritoDTO() {
    }

    public FavoritoDTO(String usuarioId, long campamentoId) {
        this.usuarioId = usuarioId;
        this.campamentoId = campamentoId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public long getCampamentoId() {
        return campamentoId;
    }

    public void setCampamentoId(long campamentoId) {
        this.campamentoId = campamentoId;
    }
}

