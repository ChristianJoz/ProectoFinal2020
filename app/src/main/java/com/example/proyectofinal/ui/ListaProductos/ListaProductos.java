package com.example.proyectofinal.ui.ListaProductos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectofinal.AdaptadorProductos;
import com.example.proyectofinal.ModeloProducto;
import com.example.proyectofinal.R;
import com.example.proyectofinal.Setting_var;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.proyectofinal.Setting_var.URL_buscarProductos;

public class ListaProductos extends Fragment {

    private RecyclerView RV_lista;
    AdaptadorProductos adaptador;
    List<ModeloProducto> ListaProductos;
   private Button btnNuevoPro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_lista_productos, container, false);


        RV_lista = root.findViewById(R.id.RV_lista);
        RV_lista.setHasFixedSize(true);
        RV_lista.setLayoutManager(new LinearLayoutManager(this.getContext()));


        ListaProductos = new ArrayList<>();

        loadProducto();

        btnNuevoPro = root.findViewById(R.id.btnNuevoPro);
        btnNuevoPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_productos);
            }
        });


        return root;
    }

    private void loadProducto(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Setting_var.URL_buscarProductos,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject productos = array.getJSONObject(i);

                                ListaProductos.add(new ModeloProducto(
                                        productos.getInt("id_producto"),
                                        productos.getString("nom_producto"),
                                        productos.getString("des_producto"),
                                        productos.getInt("stock"),
                                        productos.getDouble("precio"),
                                        productos.getString("unidad_medida"),
                                        productos.getInt("estado_producto"),
                                        productos.getInt("categoria")

                                ));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        AdaptadorProductos adaptadorProductos = new AdaptadorProductos( getContext(), ListaProductos );
                        RV_lista.setAdapter(adaptadorProductos);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

}