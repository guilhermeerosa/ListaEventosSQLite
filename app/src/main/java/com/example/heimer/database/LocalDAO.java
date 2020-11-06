package com.example.heimer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.heimer.Modelo.Local;
import com.example.heimer.database.entity.LocalEntity;

import java.util.ArrayList;
import java.util.List;

public class LocalDAO {

    private String SQL_LISTAR_TODOS = "SELECT * FROM " + LocalEntity.TABLE_NAME;
    private DBGateway dbGateway;

    public LocalDAO(Context context){
        dbGateway = DBGateway.getInstance(context);
    }

    public boolean salvar(Local local){
        ContentValues contentValues = new ContentValues();
        contentValues.put(LocalEntity.COLUMN_NAME_ENDERECO, local.getEndereco());
        contentValues.put(LocalEntity.COLUMN_NAME_BAIRRO, local.getBairro());
        contentValues.put(LocalEntity.COLUMN_NAME_CIDADE, local.getCidade());
        contentValues.put(LocalEntity.COLUMN_NAME_CAPACIDADE, local.getCapacidade());
        if(local.getId() > 0){
            return dbGateway.getDatabase().update(LocalEntity.TABLE_NAME,
                    contentValues,
                    LocalEntity._ID + "=?",
                    new String[]{String.valueOf(local.getId())}) > 0;
        }
        return dbGateway.getDatabase().insert(LocalEntity.TABLE_NAME,
                null,
                contentValues) > 0;
    }

    public List<Local> listar(){
        List<Local> locais = new ArrayList<>();
        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_LISTAR_TODOS, null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(LocalEntity._ID));
            String endereco = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_ENDERECO));
            String bairro = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_BAIRRO));
            String cidade = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CIDADE));
            int capacidade = cursor.getInt(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CAPACIDADE));
            locais.add(new Local(id, endereco, bairro, cidade, capacidade));
        }
        cursor.close();
        return locais;
    }

    public boolean excluir(Local local){
        return dbGateway.getDatabase().delete(LocalEntity.TABLE_NAME,
                LocalEntity._ID + "=?",
                new String[]{String.valueOf(local.getId())}) > 0;
    }
}
