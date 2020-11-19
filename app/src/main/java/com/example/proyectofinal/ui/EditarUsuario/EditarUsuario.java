package com.example.proyectofinal.ui.EditarUsuario;

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
import com.example.proyectofinal.ModeloUsuario;
import com.example.proyectofinal.MySingleton;
import com.example.proyectofinal.R;
import com.example.proyectofinal.Setting_var;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EditarUsuario extends Fragment {

    private Button btn_EditarUsu, btn_EliminarUsu;
    private EditText edit_idUsuario, edit_NombreUsuario, edit_ApellidosUsuario, edit_correoUsuario, edit_usuUsuario, edit_claveUsuario, edit_respuestaUsuario;
    private Spinner tipo_usuario, estado_usuario, pregunta_usuario;
    String datoSelect1 = "";
    String datoSelect2= "";
    String datoSelect3 = "";

    private static final String TAG = "EditarUsuario";
    ArrayList<String> lista = null;
    ArrayList<ModeloUsuario> listaUsuario;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceStat) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_editar_usuario, container, false);

        edit_idUsuario = root.findViewById(R.id.edit_idUsuario);
        edit_NombreUsuario = root.findViewById(R.id.edit_NombreUsuario);
        edit_ApellidosUsuario = root.findViewById(R.id.edit_ApellidosUsuario);
        edit_correoUsuario = root.findViewById(R.id.edit_correoUsuario);
        edit_usuUsuario = root.findViewById(R.id.edit_usuUsuario);
        edit_claveUsuario = root.findViewById(R.id.edit_claveUsuario);
        tipo_usuario = root.findViewById(R.id.tipo_usuario);
        estado_usuario = root.findViewById(R.id.estado_usuario);
        pregunta_usuario = root.findViewById(R.id.pregunta_usuario);
        edit_respuestaUsuario = root.findViewById(R.id.edit_respuestaUsuario);

        //botones
        btn_EditarUsu = root.findViewById(R.id.btn_EditarUsu);

        ArrayAdapter<CharSequence> tipo = ArrayAdapter.createFromResource(getContext(), R.array.TipoUsuario, android.R.layout.simple_spinner_item);
        tipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipo_usuario.setAdapter(tipo);
        tipo_usuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                datoSelect1 = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        ArrayAdapter<CharSequence> estado = ArrayAdapter.createFromResource(getContext(),
                R.array.EstadoUsuario, android.R.layout.simple_spinner_item);
        estado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        estado_usuario.setAdapter(estado);
        estado_usuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (estado_usuario.getSelectedItemPosition() > 0) {
                    datoSelect2 = estado_usuario.getSelectedItem().toString();
                } else {
                    datoSelect2 = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<CharSequence> pregunta = ArrayAdapter.createFromResource(getContext(),
                R.array.PreguntaUsuario, android.R.layout.simple_spinner_item);
        pregunta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pregunta_usuario.setAdapter(pregunta);
        pregunta_usuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (pregunta_usuario.getSelectedItemPosition() > 0) {

                    datoSelect3 = pregunta_usuario.getSelectedItem().toString();

                } else {
                    datoSelect3 = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        Bundle b = getArguments();

        int selectedTipoUsuario = 0;
        int userTypesCount = tipo_usuario.getAdapter().getCount();
        for (int i = 0; i < userTypesCount; i++) {
            if (b.getString("tipo").equals(tipo_usuario.getAdapter().getItem(i))){
                selectedTipoUsuario = i;
            }
        }

        int selectedPregunta = 0;
        final int preguntasCount = pregunta_usuario.getAdapter().getCount();
        for (int i = 0; i < preguntasCount; i++) {
            if (b.getString("pregunta").equals(pregunta_usuario.getAdapter().getItem(i))){
                selectedPregunta = i;
            }
        }

        edit_idUsuario.setText(b.getString("id"));
        edit_NombreUsuario.setText(b.getString("nombre"));
        edit_ApellidosUsuario.setText(b.getString("apellidos"));
        edit_correoUsuario.setText(b.getString("correo"));
        edit_usuUsuario.setText(b.getString("usuario"));
        edit_claveUsuario.setText(b.getString("clave"));
        estado_usuario.setSelection(Integer.parseInt(b.getString("estado")));
        tipo_usuario.setSelection(selectedTipoUsuario);
        pregunta_usuario.setSelection(selectedPregunta);
        edit_respuestaUsuario.setText(b.getString("respuesta"));

        btn_EditarUsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edit_idUsuario.getText().toString();
                String nombre = edit_NombreUsuario.getText().toString();
                String apellidos = edit_ApellidosUsuario.getText().toString();
                String correo = edit_correoUsuario.getText().toString();
                String usuario = edit_usuUsuario.getText().toString();
                String clave = edit_claveUsuario.getText().toString();
                String tipo = tipo_usuario.getSelectedItem().toString();
                String estado = estado_usuario.getSelectedItem().toString();
                String pregunta = pregunta_usuario.getSelectedItem().toString();
                String respuesta = edit_respuestaUsuario.getText().toString();

                editarUsu(Integer.parseInt(id), nombre, apellidos, correo, usuario, clave, tipo, Integer.parseInt(estado), pregunta, respuesta);
                Navigation.findNavController(view).navigate(R.id.nav_listaUsuario);
            }
        });

        return root;
    }


    private void editarUsu(final int id, final String nombre, final String apellidos, final String correo, final String usuario, final String clave, final String tipo, final int estado, final String pregunta, final String respuesta) {
        final StringRequest request = new StringRequest(Request.Method.POST, Setting_var.URL_editar_usuarios, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject resquestJSON = null;
                Log.i(TAG, "response:" + response);
                try {
                    resquestJSON = new JSONObject(response.toString());
                    String estado = resquestJSON.getString("estado");
                    String mensaje = resquestJSON.getString("mensaje");

                    if (estado.equals("1")) {
                        Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
                        Log.i(TAG ,"estado" + estado);
                    } else if (estado.equals("2")) {
                        Toast.makeText(getContext(), "" + mensaje, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "No se puede guardar.\n" + "Intentelo más tarde.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                List<ModeloUsuario> UsuarioLista;
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put("Accept", "application/json");
                map.put("id", String.valueOf(id));
                map.put("nombre", nombre);
                map.put("apellidos", apellidos);
                map.put("correo", correo);
                map.put("usuario", usuario);
                map.put("clave", clave);
                map.put("tipo", tipo);
                map.put("estado", String.valueOf(estado));
                map.put("pregunta", pregunta);
                map.put("respuesta", respuesta);
                return map;
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(request);
    }
}