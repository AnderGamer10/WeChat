package com.example.wechat.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Mensaje.class}, version = 1)
public abstract class DBMensaje extends RoomDatabase {
    public abstract MensajeDAO mensajeDAO();

    private static volatile DBMensaje INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static DBMensaje getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DBMensaje.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    DBMensaje.class, "word_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

