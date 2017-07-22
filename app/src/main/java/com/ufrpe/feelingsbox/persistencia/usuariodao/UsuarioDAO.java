package com.ufrpe.feelingsbox.persistencia.usuariodao;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.feelingsbox.dominio.usuario.Usuario;
import com.ufrpe.feelingsbox.persistencia.DataBase;


public class UsuarioDAO {

    private DataBase dbHelper;
    private SQLiteDatabase feelingsDb;

    public UsuarioDAO(Context context) {

        dbHelper = new DataBase(context);
    }

    public Usuario getUsuario(String email, String senha) {
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DataBase.TABELA_USUARIO +
                " WHERE " + DataBase.USUARIO_EMAIL + " LIKE ? AND " +
                DataBase.USUARIO_SENHA + " LIKE ?";

        String[] argumentos = {email, senha};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        Usuario usuario = null;

        if (cursor.moveToNext()) {
            usuario = criarUsuario(cursor);
        }
        cursor.close();
        feelingsDb.close();

        return usuario;
    }

    public Usuario getUsuario(String nick) {
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DataBase.TABELA_USUARIO +
                " WHERE " + DataBase.USUARIO_NICK + " LIKE ?";

        String[] argumentos = {nick};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        Usuario usuario = null;

        if (cursor.moveToNext()) {

            usuario = criarUsuario(cursor);
        }

        cursor.close();
        feelingsDb.close();

        return usuario;
    }


    public Usuario getUsuario(int id){
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DataBase.TABELA_USUARIO +
                " WHERE " + DataBase.ID + " LIKE ?";

        String idString = Long.toString(id);
        String[] argumentos = {idString};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        Usuario usuario = null;

        if(cursor.moveToNext()){

            usuario = criarUsuario(cursor);
        }

        cursor.close();
        feelingsDb.close();

        return usuario;
    }

    public Usuario getUsuarioEmail(String email) {
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DataBase.TABELA_USUARIO +
                " WHERE " + DataBase.USUARIO_EMAIL + " LIKE ?";

        String[] argumentos = {email};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        Usuario usuario = null;

        if (cursor.moveToNext()) {

            usuario = criarUsuario(cursor);
        }

        cursor.close();
        feelingsDb.close();

        return usuario;
    }

    // Insere o Obj Usuario na TABELA_USUARIO, pegando os atributos e inserindo

    public long inserirUsuario(Usuario usuario){
        feelingsDb = dbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();

        String colunaEmail = DataBase.USUARIO_EMAIL;
        String email = usuario.getEmail();
        values.put(colunaEmail, email);

        String colunaSenha = DataBase.USUARIO_SENHA;
        String senha = usuario.getSenha();
        values.put(colunaSenha, senha);

        String colunaNick = DataBase.USUARIO_NICK;
        String nick = usuario.getNick();
        values.put(colunaNick, nick);

        String tabela = DataBase.TABELA_USUARIO;
        long id = feelingsDb.insert(tabela, null, values);

        feelingsDb.close();
        return id;
    }

    // Cursor percorre as colunas da TABELA_USUARIO e retorna um objeto Usuario

    public Usuario criarUsuario(Cursor cursor){

        String colunaId = DataBase.ID;
        int indexColunaId= cursor.getColumnIndex(colunaId);
        long id = cursor.getInt(indexColunaId);

        String colunaEmail = DataBase.USUARIO_EMAIL;
        int indexColunaEmail = cursor.getColumnIndex(colunaEmail);
        String email = cursor.getString(indexColunaEmail);

        String colunaSenha = DataBase.USUARIO_SENHA;
        int indexColunaSenha = cursor.getColumnIndex(colunaSenha);
        String senha = cursor.getString(indexColunaSenha);

        String colunaNick = DataBase.USUARIO_NICK;
        int indexColunaNick = cursor.getColumnIndex(colunaNick);
        String nick = cursor.getString(indexColunaNick);

        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setNick(nick);

        return usuario;
    }

}