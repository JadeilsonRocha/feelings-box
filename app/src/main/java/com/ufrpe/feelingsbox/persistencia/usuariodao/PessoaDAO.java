package com.ufrpe.feelingsbox.persistencia.usuariodao;

import com.ufrpe.feelingsbox.dominio.usuario.Pessoa;
import com.ufrpe.feelingsbox.dominio.usuario.Usuario;

import com.ufrpe.feelingsbox.persistencia.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class PessoaDAO {

    private DataBase dbHelper;
    private UsuarioDAO usuarioDAO;
    private SQLiteDatabase feelingsDb;


    public PessoaDAO(Context context){

        dbHelper = new DataBase(context);
        usuarioDAO = new UsuarioDAO(context);
    }

    // Cursor percorre as colunas da TABELA_PESSOA e cria Objeto Pessoa

    public Pessoa criarPessoa(Cursor cursor){

        String colunaNome = DataBase.PESSOA_NOME;
        int indexColunaNome = cursor.getColumnIndex(colunaNome);
        String nome = cursor.getString(indexColunaNome);

        String colunaSexo = DataBase.PESSOA_SEXO;
        int indexColunaSexo = cursor.getColumnIndex(colunaSexo);
        String sexo = cursor.getString(indexColunaSexo);

        String colunaId = DataBase.ID;
        int indexColunaId = cursor.getColumnIndex(colunaId);
        long id  = cursor.getInt(indexColunaId);

        String colunaDataNasc = DataBase.PESSOA_DATANASC;
        int indexColunaDataNasc = cursor.getColumnIndex(colunaDataNasc);
        String data = cursor.getString(indexColunaDataNasc);

        String colunaUsuarioId =  DataBase.PESSOA_USER_ID;
        int indexColunaUsuarioId = cursor.getColumnIndex(colunaUsuarioId);
        int idUsuario = cursor.getInt(indexColunaUsuarioId);

        Usuario usuario = usuarioDAO.getUsuario(idUsuario);
        Pessoa pessoa = new Pessoa();

        pessoa.setId(id);
        pessoa.setSexo(sexo);
        pessoa.setNome(nome);
        pessoa.setDataNasc(data);
        pessoa.setIdUsuario(idUsuario);
        pessoa.setUsuario(usuario);

        return pessoa;
    }

    // Insere o Obj pessoa na TABELA_PESSOA, pegando os atributos e inserindo

    public long inserirPessoa(Pessoa pessoa){
        feelingsDb = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();

        String colunaNome =  DataBase.PESSOA_NOME;
        String nome = pessoa.getNome();
        values.put(colunaNome, nome);

        String colunaSexo = DataBase.PESSOA_SEXO;
        String sexo = pessoa.getSexo();
        values.put(colunaSexo, sexo);

        String colunaId = DataBase.ID;
        long idPessoa = pessoa.getId();
        values.put(colunaId, idPessoa);

        String colunaUserId = DataBase.PESSOA_USER_ID;
        long idUsuario = pessoa.getIdUsuario();
        values.put(colunaUserId, idUsuario);

        String colunaDataNasc = DataBase.PESSOA_DATANASC;
        String dataNasc = pessoa.getDataNasc();
        values.put(colunaDataNasc, dataNasc);

        String tabela = DataBase.TABELA_PESSOA;

        long id = feelingsDb.insert(tabela, null, values);

        feelingsDb.close();

        return id;
    }

    // Busca um objeto Pessoa na TABELA_PESSOA passando um objeto Usuario

    public Pessoa getPessoa(Usuario usuario){
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DataBase.TABELA_PESSOA +
                " WHERE " + DataBase.PESSOA_USER_ID + " LIKE ?";

        long idUsuario = usuario.getId();
        String idUsuarioString = Long.toString(idUsuario);
        String[] argumentos = {idUsuarioString};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        Pessoa pessoa = null;

        if (cursor.moveToNext()) {

            pessoa = criarPessoa(cursor);
        }
        cursor.close();
        feelingsDb.close();

        return pessoa;
    }

    // Busca obj Pessoa na TABELA_PESSOA passando o Id

    public Pessoa getPessoa(long id){
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DataBase.TABELA_PESSOA +
                " WHERE " + DataBase.ID + " LIKE ?";

        String idString = Long.toString(id);
        String[] argumentos = {idString};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        Pessoa pessoa = null;

        if (cursor.moveToNext()) {

            pessoa = criarPessoa(cursor);

        }
        cursor.close();
        feelingsDb.close();

        return pessoa;

    }
}