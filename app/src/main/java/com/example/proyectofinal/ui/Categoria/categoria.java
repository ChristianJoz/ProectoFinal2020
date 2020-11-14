package com.example.proyectofinal.ui.Categoria;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.proyectofinal.MySingleton;
import com.example.proyectofinal.R;
import com.example.proyectofinal.Setting_var;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class categoria extends Fragment implements View.OnClickListener {

    private TextInputLayout ti_idcategoria, ti_namecategoria;
    private EditText et_idcategoria, et_namecategoria;
    private Spinner sp_estado;
    private Button btnSave, btnNew, btnEditar;
    String datoSelect = "";

    //ListaCategoria listaCategoria = new ListaCategoria();
    private int list_reciclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_categoria, container, false);

        ti_idcategoria = root.findViewById(R.id.ti_idcategoria);
        ti_namecategoria = root.findViewById(R.id.ti_namecategoria);
        et_idcategoria = root.findViewById(R.id.et_idcategoria);
        et_namecategoria = root.findViewById(R.id.et_namecategoria);
        sp_estado = root.findViewById(R.id.sp_estado);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.EstadoCategoria, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_estado.setAdapter(adapter);
        sp_estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sp_estado.getSelectedItemPosition() > 0) {
                    datoSelect = sp_estado.getSelectedItem().toString();
                } else {
                    datoSelect = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btnSave = root.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                //save_server(getContext(),);
                String id = et_idcategoria.getText().toString();
                String nombre = et_namecategoria.getText().toString();
                if (id.length() == 0) {
                    et_idcategoria.setError("Campo obligatorio");
                } else if (nombre.length() == 0) {
                    et_namecategoria.setError("Campo obligatorio");
                    //}else if(!datoSelect.isEmpty()){
                } else if (sp_estado.getSelectedItemPosition() > 0) {
                    //Acciones para guardar registro en la base de datos.
                    //Toast.makeText(getContext(), "Bien...", Toast.LENGTH_SHORT).show();
                    save_server(getContext(), Integer.parseInt(id), nombre, Integer.parseInt(datoSelect));
                    //Navigation.findNavController(v).navigate();
                } else {
                    Toast.makeText(getContext(), "Debe seleccionar un estado para la categoria",
                            Toast.LENGTH_SHORT).show();
                }


                break;

            default:
        }
    }

    private void save_server(final Context context, final int id_categoria, final String nom_categoria, final int estado_categoria) {
        final StringRequest request = new StringRequest(Request.Method.POST, Setting_var.URL_guardar_categoria, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject resquestJSON = null;
                // Log.i(TAG, "response:" + response);
                try {
                    resquestJSON = new JSONObject(response.toString());
                    String estado = resquestJSON.getString("estado");
                    String mensaje = resquestJSON.getString("mensaje");

                    if (estado.equals("1")) {
                        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                        Log.i(TAG ,"estado" + estado);
                    } else if (estado.equals("2")) {
                        Toast.makeText(context, "" + mensaje, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "No se puede guardar.\n" + "Intentelo más tarde.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put("Accept", "application/json");
                map.put("id", String.valueOf(id_categoria));
                map.put("nombre", nom_categoria);
                map.put("estado", String.valueOf(estado_categoria));
                return map;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
}