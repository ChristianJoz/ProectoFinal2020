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

import static android.content.ContentValues.TAG;

public class AdaptadorProductos extends RecyclerView.Adapter<AdaptadorProductos.ViewHolder>  {

    private Context mctx;
    private List<ModeloProducto> ProductosLista;
    private static final String TAG = "AdaptadorProductos";

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
        productosViewHolder.tv_CategoriaPro.setText(String.valueOf(ProductosLista.get(i).getCategoria()));
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String id = getTextView(v, R.id.tv_idPro).getText().toString();
                    String nombre = getTextView(v, R.id.tv_NombrePro).getText().toString();
                    String descripcion = getTextView(v, R.id.tv_DesPro).getText().toString();
                    String stock = getTextView(v, R.id.tv_StockPro).getText().toString();
                    String precio = getTextView(v, R.id.tv_PrecioPro).getText().toString();
                    String unidad = getTextView(v, R.id.tv_UNIPro).getText().toString();
                    String estado = getTextView(v, R.id.tv_EstadoPro).getText().toString();
                    String categoria = getTextView(v, R.id.tv_CategoriaPro).getText().toString();

                    Log.i(TAG, "Id: " + id + ", Nombre: " + nombre + ", Descripcion: " + descripcion + ", Stock: " + stock + ", Precio: " + precio + ", Unidad_Medida: " + unidad + ", Estado: " + estado + ", Categoria " + categoria);
                    Bundle b = new Bundle();
                    b.putString("id", id);
                    b.putString("nombre", nombre);
                    b.putString("descripcion", descripcion);
                    b.putString("stock", stock);
                    b.putString("precio", precio);
                    b.putString("unidad", unidad);
                    b.putString("estado", estado);
                    b.putString("categoria", categoria);

                    Navigation.findNavController(v).navigate(R.id.nav_editarProducto, b);
                }
            });
        }
    }

    private TextView getTextView(View v, int id){
        return v.findViewById(id);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

}
