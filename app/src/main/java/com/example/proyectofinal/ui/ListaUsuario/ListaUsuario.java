package com.example.proyectofinal.ui.ListaUsuario;

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
import com.example.proyectofinal.AdaptadorUsuarios;
import com.example.proyectofinal.ModeloProducto;
import com.example.proyectofinal.ModeloUsuario;
import com.example.proyectofinal.R;
import com.example.proyectofinal.Setting_var;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaUsuario extends Fragment {

    private RecyclerView RV_lista;
    AdaptadorUsuarios adaptador;
    List<ModeloUsuario> ListaUsuario;
    private Button btnNueva;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_lista_usuario, container, false);

        RV_lista = root.findViewById(R.id.RV_lista);
        RV_lista.setHasFixedSize(true);
        RV_lista.setLayoutManager(new LinearLayoutManager(this.getContext()));

        btnNueva = root.findViewById(R.id.btnNuevoUsu);
        btnNueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_usuario);
            }
        });

        ListaUsuario = new ArrayList<>();

        loadUsuario();

        return root;
    }

    private void loadUsuario(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Setting_var.URL_buscar_usuarios,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject usuario = array.getJSONObject(i);

                                ListaUsuario.add(new ModeloUsuario(
                                        usuario.getInt("id"),
                                        usuario.getString("nombre"),
                                        usuario.getString("apellidos"),
                                        usuario.getString("correo"),
                                        usuario.getString("usuario"),
                                        usuario.getString("clave"),
                                        usuario.getInt("tipo"),
                                        usuario.getInt("estado"),
                                        usuario.getString("pregunta"),
                                        usuario.getString("respuesta")

                                ));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        AdaptadorUsuarios adaptadorUsuarios = new AdaptadorUsuarios( getContext(), ListaUsuario );
                        RV_lista.setAdapter(adaptadorUsuarios);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }


}