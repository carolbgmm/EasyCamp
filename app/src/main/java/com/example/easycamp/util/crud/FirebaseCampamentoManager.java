package com.example.easycamp.util.crud;

import com.example.easycamp.domain.CampamentoDTO;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class FirebaseCampamentoManager {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("campamentos");

    // Create (Agregar)
    public void agregarCampamento(CampamentoDTO campamento) {
        String id = database.push().getKey();
        if (id != null) {
            campamento.setId(id);
            database.child(id).setValue(campamento);
        }
    }

    // Read (Leer)
    public void obtenerCampamentos(final OnCampamentosObtenidosListener listener) {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<CampamentoDTO> campamentos = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    CampamentoDTO campamento = data.getValue(CampamentoDTO.class);
                    if (campamento != null) {
                        campamentos.add(campamento);
                    }
                }
                listener.onCampamentosObtenidos(campamentos);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Manejo de errores
                listener.onError(error.toException());
            }
        });
    }
    public interface OnCampamentosObtenidosListener {
        void onCampamentosObtenidos(List<CampamentoDTO> campamentos);

        void onError(Exception exception);
    }

    // Update (Actualizar)
    public void actualizarCampamento(CampamentoDTO campamento) {
        String id = campamento.getId();
        if (id != null) {
            database.child(id).setValue(campamento);
        }
    }

    // Delete (Eliminar)
    public void eliminarCampamento(CampamentoDTO campamento) {
        String id = campamento.getId();
        if (id != null) {
            database.child(id).removeValue();
        }
    }

    // Interfaz para manejar la recepción de datos
    public interface OnDataReceivedListener<T> {
        void onDataReceived(T data);
    }

    public void obtenerCampamentoPorID(String campamentoID, OnCampamentoObtenidoListener listener) {
        DatabaseReference campamentoRef = database.child("campamentos").child(campamentoID);

        campamentoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CampamentoDTO campamento = dataSnapshot.getValue(CampamentoDTO.class);

                if (campamento != null) {
                    listener.onCampamentoObtenido(campamento);
                } else {
                    listener.onCampamentoObtenido(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejo de errores
                listener.onError(databaseError.toException());
            }
        });
    }

    public interface OnCampamentoObtenidoListener {
        void onCampamentoObtenido(CampamentoDTO campamento);
        void onError(Exception exception);


    }

    public void obtenerCampamentosConFiltro(String idUsuario, String filtro, OnCampamentosConFiltroListener listener) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("campamentos");

        // Aplicar filtro para obtener campamentos con el nombre que contiene la cadena de búsqueda
        Query query = database.orderByChild("nombre");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<CampamentoDTO> campamentosFiltrados = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    CampamentoDTO campamento = data.getValue(CampamentoDTO.class);
                    if (campamento != null && cumpleConFiltro(campamento, filtro)) {
                        campamentosFiltrados.add(campamento);
                    }
                }
                listener.onCampamentosConFiltroObtenidos(campamentosFiltrados);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                listener.onError(error.toException());
            }
        });
    }

    private boolean cumpleConFiltro(CampamentoDTO campamento, String filtro) {
       return campamento.getNombre().contains(filtro);
    }

    public interface OnCampamentosConFiltroListener {
        void onCampamentosConFiltroObtenidos(List<CampamentoDTO> campamentosFiltrados);
        void onError(Exception exception);
    }


}

