package com.example.proyectofinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorProductos extends RecyclerView.Adapter<AdaptadorProductos.ViewHolder>  {

    private Context mctx;
    private List<ModeloProducto> ProductosLista;

    public AdaptadorProductos(Context mctx ,List<ModeloProducto> productosLista) {
        this.ProductosLista = productosLista;
        this.mctx = mctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_reciclerviewpro, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder productosViewHolder, int i) {

        productosViewHolder.tv_idPro.setText(String.valueOf(ProductosLista.get(i).getId_producto()));
        productosViewHolder.tv_NombrePro.setText(ProductosLista.get(i).getNom_producto());
        productosViewHolder.tv_DesPro.setText(ProductosLista.get(i).getDes_producto());
        productosViewHolder.tv_StockPro.setText(String.valueOf(ProductosLista.get(i).getStock()));
        productosViewHolder.tv_PrecioPro.setText(String.valueOf(ProductosLista.get(i).getPrecio()));
        productosViewHolder.tv_UNIPro.setText(ProductosLista.get(i).getUnidadmedida());
        productosViewHolder.tv_EstadoPro.setText(String.valueOf(ProductosLista.get(i).getEstado_producto()));
        productosViewHolder.tv_EstadoPro.setText(String.valueOf(ProductosLista.get(i).getCategoria()));
    }

    @Override
    public int getItemCount() {
        return ProductosLista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_idPro, tv_NombrePro, tv_DesPro, tv_StockPro, tv_PrecioPro, tv_UNIPro, tv_EstadoPro, tv_CategoriaPro;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_idPro = itemView.findViewById(R.id.tv_idPro);
            tv_NombrePro = itemView.findViewById(R.id.tv_NombrePro);
            tv_DesPro = itemView.findViewById(R.id.tv_DesPro);
            tv_StockPro = itemView.findViewById(R.id.tv_StockPro);
            tv_PrecioPro = itemView.findViewById(R.id.tv_PrecioPro);
            tv_UNIPro = itemView.findViewById(R.id.tv_UNIPro);
            tv_EstadoPro = itemView.findViewById(R.id.tv_EstadoPro);
            tv_CategoriaPro = itemView.findViewById(R.id.tv_CategoriaPro);

        }
    }

}
