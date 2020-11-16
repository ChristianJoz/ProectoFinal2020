package com.example.proyectofinal;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorCategorias extends RecyclerView.Adapter<AdaptadorCategorias.ViewHolder> {

    private static final String TAG = "AdaptadorCategorias";
    private Context mctx;
    private List<ModeloCategoria> CategoriaLista;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_idCat, tv_NombreCat, tv_Estado_Cat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_idCat = itemView.findViewById(R.id.tv_idCat);
            tv_NombreCat = itemView.findViewById(R.id.tv_NombreCat);
            tv_Estado_Cat = itemView.findViewById(R.id.tv_EstadoCat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = getTextView(v, R.id.tv_idCat).getText().toString();
                    String nombre = getTextView(v, R.id.tv_NombreCat).getText().toString();
                    String estado = getTextView(v, R.id.tv_EstadoCat).getText().toString();

                    Log.i(TAG, "Id: " + id + ", Nombre: " + nombre + ", Estado: " + estado);

                    Bundle b = new Bundle();
                    b.putString("id", id);
                    b.putString("nombre", nombre);
                    b.putString("estado", estado);

                   Navigation.findNavController(v).navigate(R.id.nav_editarCategoria, b);
                }
            });
        }
    }

    private TextView getTextView(View v, int id){
        return v.findViewById(id);
    }

    public AdaptadorCategorias(Context mctx ,List<ModeloCategoria> categoriaLista) {
        this.CategoriaLista = categoriaLista;
        this.mctx = mctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_list_reciclerview, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder categoriasViewHolder, int i) {

        categoriasViewHolder.tv_idCat.setText(String.valueOf(CategoriaLista.get(i).getId_categoria()));
        categoriasViewHolder.tv_NombreCat.setText(CategoriaLista.get(i).getNombre_categoria());
        categoriasViewHolder.tv_Estado_Cat.setText(String.valueOf(CategoriaLista.get(i).getEstado_categoria()));
    }



    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {

        return CategoriaLista.size();
    }

}
