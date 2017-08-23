package com.ufrpe.feelingsbox.infra.adapter.comentario;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.FormataData;
import com.ufrpe.feelingsbox.infra.adapter.post.RecyclerViewOnClickListenerhack;
import com.ufrpe.feelingsbox.redesocial.dominio.Comentario;
import com.ufrpe.feelingsbox.usuario.usuarioservices.UsuarioService;

import java.util.List;

public class ComentarioRecyclerAdapter extends RecyclerView.Adapter<ComentarioRecyclerAdapter.MyViewHolder> {
    private List<Comentario> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerhack mRecyclerViewOnClickListenerhack;
    private UsuarioService usuarioService;
    private static final int DURACAO = 1000;

    //Construtor
    public ComentarioRecyclerAdapter(Context context, List<Comentario> lista) {
        mList = lista;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //Cria os Itens da Lista (até alguns a mais do que a tela comporta)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_comentario_cardview, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setRecyclerViewOnClickListenerhack(RecyclerViewOnClickListenerhack r){
        mRecyclerViewOnClickListenerhack = r;
    }

    public void addListItem(Comentario comentario, int position){
        mList.add(comentario);
        notifyItemInserted(position);
    }

    public void removeListItem(int position){
        mList.remove(position);
        notifyItemRemoved(position);
    }

    //Autaliza os itens da lista (Os que não estão visíveis)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //holder.ivUser.setImageResource( mList.get(position).getFoto() );
        usuarioService = new UsuarioService(mLayoutInflater.getContext());
        long idUsuario = mList.get(position).getIdUsuario();
        String nickUsuario = usuarioService.buscarNick(idUsuario) ;
        holder.txtDonoComentario.setText(nickUsuario);
        holder.txtComentario.setText(mList.get(position).getTexto());
        String data = FormataData.tempoParaMostrarEmPost(mList.get(position).getDataHora());
        holder.txtData.setText(data);

        //Animação
        try {
            YoYo.with(Techniques.ZoomIn)
                    .duration(DURACAO)
                    .repeat(0)
                    .playOn(holder.itemView);
        }catch (Exception e){

        }

    }

    //ViewHolder personalizada
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView ivUser;
        public TextView txtDonoComentario, txtComentario, txtData;
        public Button btnComentar;


        public MyViewHolder(final View itemView) {
            super(itemView);

            ivUser      = (ImageView) itemView.findViewById(R.id.ivUser);
            txtDonoComentario = (TextView) itemView.findViewById(R.id.txtDonoPost);
            txtComentario = (TextView) itemView.findViewById(R.id.txtPostagem);
            txtData     = (TextView) itemView.findViewById(R.id.lblSeguindo);
            btnComentar = (Button) itemView.findViewById(R.id.btnComentar);

            ivUser.setOnClickListener(this);
            btnComentar.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(mRecyclerViewOnClickListenerhack != null){
                mRecyclerViewOnClickListenerhack.onClickListener(v, getPosition());
            }
        }
    }
}