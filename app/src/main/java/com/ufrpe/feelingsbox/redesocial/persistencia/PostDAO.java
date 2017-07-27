package com.ufrpe.feelingsbox.redesocial.persistencia;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.feelingsbox.infra.DataBase;
import com.ufrpe.feelingsbox.redesocial.dominio.Post;


public class PostDAO {
    private DataBase dbHelper;
    private SQLiteDatabase feelingsDb;

    public PostDAO(Context context){
        dbHelper = new DataBase(context);
    }

    // Passa o id do post e retorna o post

    public Post getPostId(long id){
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DataBase.TABELA_POST +
                " WHERE " + DataBase.ID + " LIKE ?";

        String idString = Long.toString(id);
        String[] argumentos = {idString};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        Post post = null;

        if(cursor.moveToNext()){

            post = criarPost(cursor); //FALTAR CRIAR O MÉTODO CRIAR POST
        }

        cursor.close();
        feelingsDb.close();

        return post;
    }

    // Cria objeto post passando o cursor, retorna objeto post criado

    public Post criarPost(Cursor cursor){

        String colunaId = DataBase.ID;
        int indexColunaId= cursor.getColumnIndex(colunaId);
        long id = cursor.getInt(indexColunaId);

        String colunaUserId = DataBase.POST_USER_ID;
        int indexColunaUserId = cursor.getColumnIndex(colunaUserId);
        long idUsuario = cursor.getInt(indexColunaUserId);

        String colunaTexto = DataBase.POST_TEXTO;
        int indexColunaTexto = cursor.getColumnIndex(colunaTexto);
        String texto = cursor.getString(indexColunaTexto);

        String colunaDataHora = DataBase.POST_DATAHORA;
        int indexColunaDataHora = cursor.getColumnIndex(colunaDataHora);
        String datahora = cursor.getString(indexColunaDataHora);

        Post post = new Post();
        post.setId(id);
        post.setTexto(texto);
        post.setIdUsuario(idUsuario);

        return post;
    }

    //Insere objeto Post na TABELA_POST, retorna id do post

    public long inserirPost(Post post){
        feelingsDb = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String colunaTexto =  DataBase.POST_TEXTO;
        String texto = post.getTexto();
        values.put(colunaTexto, texto);

        String colunaIdUsuario = DataBase.POST_USER_ID;
        long idUser = post.getIdUsuario();
        values.put(colunaIdUsuario, idUser);

        String colunaDataHora = DataBase.POST_DATAHORA;
        String dataHora = post.getDataHora();
        values.put(colunaDataHora,dataHora);

        String tabela = DataBase.TABELA_POST;

        long id = feelingsDb.insert(tabela, null, values);

        feelingsDb.close();

        return id;

    }

}


