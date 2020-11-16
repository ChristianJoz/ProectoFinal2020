package com.example.proyectofinal.ui.ListaCategoria;

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
import com.example.proyectofinal.AdaptadorCategorias;
import com.example.proyectofinal.ModeloCategoria;
import com.example.proyectofinal.R;
import com.example.proyectofinal.Setting_var;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaCategorias extends Fragment {

    private Button btnNueva;
        private RecyclerView RV_lista;
        AdaptadorCategorias adaptador;
        List<ModeloCategoria> CategoriaLista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_lista_categorias, container, false);
        btnNueva = root.findViewById(R.id.btnNueva);

        RV_lista = root.findViewById(R.id.RV_lista);
        RV_lista.setHasFixedSize(true);
        RV_lista.setLayoutManager(new LinearLayoutManager(this.getContext()));

        CategoriaLista = new ArrayList<>();

        btnNueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_categoria);
            }
        });

        loadcategoria();


        return root;
    }

    private void loadcategoria(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Setting_var.URL_consultaAllCategorias,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject categorias = array.getJSONObject(i);

                                CategoriaLista.add(new ModeloCategoria(
                                        categorias.getInt("id_categoria"),
                                        categorias.getString("nom_categoria"),
                                        categorias.getInt("estado_categoria")
                                ));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        AdaptadorCategorias adaptadorCategorias = new AdaptadorCategorias( getContext(), CategoriaLista );
                        RV_lista.setAdapter(adaptadorCategorias);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

}