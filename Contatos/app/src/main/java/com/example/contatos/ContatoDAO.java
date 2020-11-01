package com.example.contatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ContatoDAO {

    private Banco banco;
    private SQLiteDatabase db;
    private String columns[] = {Contato.id,Contato.nome1,Contato.nome2,Contato.tel1,Contato.tel2,
            Contato.email,Contato.empresa,Contato.genero,Contato.aniversario};

    public ContatoDAO(Context context) {
        banco = new Banco(context);
    }

    public long inserirContato(Contato c){
        ContentValues values = new ContentValues();
        db = banco.getWritableDatabase();
        values.put(Contato.nome1,c.getPrimeiroNome());
        values.put(Contato.nome2,c.getSegundoNome());
        values.put(Contato.tel1,c.getTelefoneCelular());
        values.put(Contato.tel2,c.getTelefoneFixo());
        values.put(Contato.email,c.getEmail());
        values.put(Contato.empresa,c.getEmpresa());
        values.put(Contato.genero,c.getGenero());
        values.put(Contato.aniversario,c.getDtAniversario());
        return db.insert(Contato.table,null,values);
    }

    public int FavoritarContato(int idContato){
        db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contato.favorito,1);
        return db.update(Contato.table,values,Contato.id+" = ?",new String[]{String.valueOf(idContato)});
    }

    public int DesfavoritarContato(int idContato){
        db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contato.favorito,0);
        return db.update(Contato.table,values,Contato.id+" = ?",new String[]{String.valueOf(idContato)});
    }

    public int DeletarContato(int idContato){
        db = banco.getWritableDatabase();
        return db.delete(Contato.table,Contato.id+" = "+idContato,null);
    }
    public int AtualizarContato(Contato c){
        db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contato.nome1,c.getPrimeiroNome());
        values.put(Contato.nome2,c.getSegundoNome());
        values.put(Contato.tel1,c.getTelefoneCelular());
        values.put(Contato.tel2,c.getTelefoneFixo());
        values.put(Contato.email,c.getEmail());
        values.put(Contato.empresa,c.getEmpresa());
        values.put(Contato.genero,c.getGenero());
        values.put(Contato.aniversario,c.getDtAniversario());

        return db.update(Contato.table,values,Contato.id+" = "+c.getIdContato(),null);
    }

    public List<Contato> ListarFavoritos(){
        List<Contato> contatoes = new ArrayList<>();
        db = banco.getReadableDatabase();
        Cursor cursor = db.query(Contato.table, columns,Contato.favorito+" = 1",null,null,null,null);
        while (cursor.moveToNext()){
            Contato contato = new Contato(cursor.getString(1),cursor.getString(3));
            contato.setIdContato(cursor.getInt(0));
            contato.setSegundoNome(cursor.getString(2));
            contato.setTelefoneFixo(cursor.getString(4));
            contato.setEmail(cursor.getString(5));
            contato.setEmpresa(cursor.getString(6));
            contato.setGenero(cursor.getString(7));
            contato.setDtAniversario(cursor.getString(8));
            contatoes.add(contato);
        }
        return contatoes;
    }

    public List<Contato> ListarContatos(){
        List<Contato> contatos = new ArrayList<>();
        db = banco.getReadableDatabase();
      Cursor cursor = db.query(Contato.table,columns,null,null,null,
              null,null,null);
      while (cursor.moveToNext()){
          Contato contato = new Contato(cursor.getString(1),cursor.getString(3));
          contato.setIdContato(cursor.getInt(0));
          contato.setSegundoNome(cursor.getString(2));
          contato.setTelefoneFixo(cursor.getString(4));
          contato.setEmail(cursor.getString(5));
          contato.setEmpresa(cursor.getString(6));
          contato.setGenero(cursor.getString(7));
          contato.setDtAniversario(cursor.getString(8));
          contatos.add(contato);
      }
      return contatos;
    }

}
