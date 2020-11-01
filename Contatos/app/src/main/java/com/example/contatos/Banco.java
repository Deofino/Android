package com.example.contatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Banco extends SQLiteOpenHelper {

    private static final String name = "dbAgendaContatos";
    private static final int version = 1;

    public Banco(@Nullable Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Contato.table+"(" +
                Contato.id+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contato.nome1+" VARCHAR (30) NOT NULL," +
                Contato.nome2+" VARCHAR (30)," +
                Contato.tel1+" VARCHAR(15) NOT NULL," +
                Contato.tel2+" VARCHAR (15)," +
                Contato.email+" VARCHAR (100)," +
                Contato.empresa+" VARCHAR(35)," +
                Contato.genero+" VARCHAR (20)," +
                Contato.aniversario+" DATE," +
                Contato.favorito+" CHAR(1))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Contato.table);
        onCreate(db);
    }
}
