package com.example.proyectofinal;

import android.content.Context;
import android.nfc.Tag;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorUsuarios extends RecyclerView.Adapter<AdaptadorUsuarios.ViewHolder> {

    private Context mctx;
    private List<ModeloUsuario> UsuariosLista;
    private static final String TAG = "AdaptadorUsuarios";

    public AdaptadorUsuarios(Context mctx ,List<ModeloUsuario> usuariosLista) {
        this.UsuariosLista = usuariosLista;
        this.mctx = mctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_reciclerviewusu, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder usuariosViewHolder, int i) {
        usuariosViewHolder.tv_idUsu.setText(String.valueOf(UsuariosLista.get(i).getId()));
        usuariosViewHolder.tv_NombreUsu.setText(UsuariosLista.get(i).getNombre());
        usuariosViewHolder.tv_ApellidosUsu.setText(UsuariosLista.get(i).getApellido());
        usuariosViewHolder.tv_CorreoUsu.setText(UsuariosLista.get(i).getCorreo());
        usuariosViewHolder.tv_UsuarioUsu.setText(UsuariosLista.get(i).getUsuario());
        usuariosViewHolder.tv_ClaveUsu.setText((UsuariosLista.get(i).getClave()));
        usuariosViewHolder.tv_TipoUsu.setText(String.valueOf(UsuariosLista.get(i).getTipo()));
        usuariosViewHolder.tv_EstadoUsu.setText(String.valueOf(UsuariosLista.get(i).getEstado()));
        usuariosViewHolder.tvPreguntaUsu.setText(UsuariosLista.get(i).getPregunta());
        usuariosViewHolder.tv_RespuestaUsu.setText(UsuariosLista.get(i).getRespuesta());
    }

    @Override
    public int getItemCount() {
        return UsuariosLista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_idUsu, tv_NombreUsu, tv_ApellidosUsu, tv_CorreoUsu, tv_UsuarioUsu, tv_ClaveUsu, tv_TipoUsu, tv_EstadoUsu, tvPreguntaUsu, tv_RespuestaUsu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_idUsu = itemView.findViewById(R.id.tv_idUsu);
            tv_NombreUsu = itemView.findViewById(R.id.tv_NombreUsu);
            tv_ApellidosUsu = itemView.findViewById(R.id.tv_ApellidosUsu);
            tv_CorreoUsu = itemView.findViewById(R.id.tv_CorreoUsu);
            tv_UsuarioUsu = itemView.findViewById(R.id.tv_UsuarioUsu);
            tv_ClaveUsu = itemView.findViewById(R.id.tv_ClaveUsu);
            tv_TipoUsu = itemView.findViewById(R.id.tv_TipoUsu);
            tv_EstadoUsu = itemView.findViewById(R.id.tv_EstadoUsu);
            tvPreguntaUsu = itemView.findViewById(R.id.tv_PreguntaUsu);
            tv_RespuestaUsu = itemView.findViewById(R.id.tv_RespuestaUsu);

        }
    }
}
