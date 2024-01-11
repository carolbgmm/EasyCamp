package com.example.easycamp.util.crud;



import com.example.easycamp.domain.CampamentoDTO;
import com.example.easycamp.domain.InscritosDTO;

import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class FirebaseInscritosManager {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("inscritos");
    private FirebaseCampamentoManager firebaseCampamentoManager = new FirebaseCampamentoManager();

    // Create (Agregar)
    public void agregarInscrito(InscritosDTO inscrito) {
        String id = database.push().getKey();
        if (id != null) {
            inscrito.setId(id);
            database.child(id).setValue(inscrito);
        }
    }

    // Read (Leer)
    public void obtenerInscritos(final OnDataReceivedListener<List<InscritosDTO>> listener) {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<InscritosDTO> inscritos = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    InscritosDTO inscrito = data.getValue(InscritosDTO.class);
                    if (inscrito != null) {
                        inscritos.add(inscrito);
                    }
                }
                listener.onDataReceived(inscritos);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Manejo de errores
            }
        });
    }

    // Update (Actualizar)
    public void actualizarInscrito(InscritosDTO inscrito) {
        String id = inscrito.getId();
        if (id != null) {
            database.child(id).setValue(inscrito);
        }
    }

    // Delete (Eliminar)
    public void eliminarInscrito(InscritosDTO inscrito) {
        String id = inscrito.getId();
        if (id != null) {
            database.child(id).removeValue();
        }
    }

    // Interfaz para manejar la recepción de datos
    public interface OnDataReceivedListener<T> {
        void onDataReceived(T data);
    }

    // Inscribir un hijo a un campamento
    public void inscribirHijoAlCampamento(String hijoId, String campamentoId,String padreid, OnInscripcionCompletadaListener listener) {
        if (campamentoId != null) {
            // Generar una nueva ID para la inscripción
            String inscripcionId = database.push().getKey();

            // Crear un nuevo objeto InscritosDTO con la información proporcionada
            InscritosDTO inscripcion = new InscritosDTO(inscripcionId, hijoId, campamentoId,padreid);

            // Agregar la inscripción a la base de datos
            if (inscripcionId != null) {
                database.child(inscripcionId).setValue(inscripcion, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError error, DatabaseReference ref) {
                        if (error == null) {
                            // La inscripción se realizó con éxito
                            listener.onInscripcionCompletada();
                        } else {
                            // Ocurrió un error al realizar la inscripción
                            // Manejar el error según tus necesidades
                        }
                    }
                });
            }
        } else {
            // El campamentoId es nulo, manejar la situación según tus necesidades
        }
    }

    // Obtener la lista de inscritos filtrada por la ID del padre
    public void obtenerInscritosPorIdPadre(String idPadre, OnDataReceivedListener<List<InscritosDTO>> listener) {
        Query query = database.orderByChild("idPadre").equalTo(idPadre);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<InscritosDTO> inscritos = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    InscritosDTO inscrito = data.getValue(InscritosDTO.class);
                    if (inscrito != null) {
                        inscritos.add(inscrito);
                    }
                }
                listener.onDataReceived(inscritos);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Manejo de errores
            }
        });
    }
    // Interfaz para manejar la completitud de la inscripción
    public interface OnInscripcionCompletadaListener {
        void onInscripcionCompletada();
    }

    public void obtenerCampamentosInscritosDeUsuario(String usuarioID, OnCampamentosInscritosListener listener) {
        List<CampamentoDTO> campamentosInscritos = new ArrayList<>();

        database.orderByChild("idPadre").equalTo(usuarioID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot inscritoSnapshot : snapshot.getChildren()) {
                    String campamentoID = inscritoSnapshot.child("campamentoId").getValue(String.class);

                    if (campamentoID != null) {
                        firebaseCampamentoManager.obtenerCampamentoPorID(campamentoID, new FirebaseCampamentoManager.OnCampamentoObtenidoListener() {
                            @Override
                            public void onCampamentoObtenido(CampamentoDTO campamento) {
                                if (campamento != null) {
                                    campamentosInscritos.add(campamento);

                                    if (campamentosInscritos.size() == snapshot.getChildrenCount()) {
                                        // Se han obtenido todos los campamentos inscritos
                                        listener.onCampamentosInscritosObtenidos(campamentosInscritos);
                                    }
                                }
                            }

                            @Override
                            public void onError(Exception exception) {
                                // Manejar el error según tus necesidades
                                listener.onError(exception);
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Manejo de errores
                listener.onError(error.toException());
            }
        });
    }


    public interface OnCampamentosInscritosListener {
        void onCampamentosInscritosObtenidos(List<CampamentoDTO> campamentosInscritos);
        void onError(Exception exception);
    }
}

