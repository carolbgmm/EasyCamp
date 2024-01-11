package com.example.easycamp.util.crud;
import com.example.easycamp.domain.TickDTO;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class FirebaseTickManager {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("tick");

    // Create (Agregar)
    public void agregarTick(TickDTO tick) {
        String id = database.push().getKey();
        if (id != null) {
            tick.setId(id);
            database.child(id).setValue(tick);
        }
    }

    // Read (Leer)
    public void obtenerTicks(final OnDataReceivedListener<List<TickDTO>> listener) {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<TickDTO> ticks = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    TickDTO tick = data.getValue(TickDTO.class);
                    if (tick != null) {
                        ticks.add(tick);
                    }
                }
                listener.onDataReceived(ticks);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Manejo de errores
            }
        });
    }

    // Update (Actualizar)
    public void actualizarTick(TickDTO tick) {
        String id = tick.getId();
        if (id != null) {
            database.child(id).setValue(tick);
        }
    }

    // Delete (Eliminar)
    public void eliminarTick(TickDTO tick) {
        String id = tick.getId();
        if (id != null) {
            database.child(id).removeValue();
        }
    }

    // Interfaz para manejar la recepción de datos
    public interface OnDataReceivedListener<T> {
        void onDataReceived(T data);
    }
}
