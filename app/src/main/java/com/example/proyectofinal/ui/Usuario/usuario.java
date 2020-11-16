package com.example.proyectofinal.ui.Usuario;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.proyectofinal.MySingleton;
import com.example.proyectofinal.R;
import com.example.proyectofinal.Setting_var;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class usuario extends Fragment {

    private EditText edit_idUsuario, edit_NombreUsuario, edit_ApellidosUsuario, edit_correoUsuario, edit_usuUsuario, edit_claveUsuario, edit_respuestaUsuario;
    private Spinner tipo_usuario, estado_usuario, pregunta_usuario;
    private Button btn_guardar, btn_nuevo;
    private TextView tv_fechahora;

    String datoSelect1 = "";
    String datoSelect2= "";
    String datoSelect3 = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_usuario, container, false);

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
        tv_fechahora = root.findViewById(R.id.tv_fechahora);
        tv_fechahora.setText(timedate());

        btn_guardar = root.findViewById(R.id.btn_guardar);

        ArrayAdapter<CharSequence> tipo = ArrayAdapter.createFromResource(getContext(),
                R.array.TipoUsuario, android.R.layout.simple_spinner_item);
        tipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipo_usuario.setAdapter(tipo);
        tipo_usuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (tipo_usuario.getSelectedItemPosition() > 0) {
                    datoSelect1 = tipo_usuario.getSelectedItem().toString();
                } else {
                    datoSelect1 = "";
                }

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

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edit_idUsuario.getText().toString();
                String nombre = edit_NombreUsuario.getText().toString();
                String apellido = edit_ApellidosUsuario.getText().toString();
                String correo = edit_correoUsuario.getText().toString();
                String usuario = edit_usuUsuario.getText().toString();
                String clave = edit_claveUsuario.getText().toString();
                String respuesta = edit_respuestaUsuario.getText().toString();
                if (id.length() == 0) {
                    edit_idUsuario.setError("Campo obligatorio");

                } else if (nombre.length() == 0) {
                    edit_NombreUsuario.setError("Campo obligatorio");

                } else if (apellido.length() == 0){
                    edit_ApellidosUsuario.setError("Camapo Obligatorio");

                } else if (correo.length() == 0){
                    edit_correoUsuario.setError("Camapo Obligatorio");

                }else if (usuario.length() == 0){
                    edit_usuUsuario.setError("Camapo Obligatorio");

                }else if (clave.length() == 0){
                    edit_claveUsuario.setError("Camapo Obligatorio");

                }else if (respuesta.length() == 0){
                    edit_respuestaUsuario.setError("Camapo Obligatorio");

                }else if (tipo_usuario.getSelectedItemPosition() > 0) {

                    save_usuarios(getContext(), String.valueOf(id), nombre, apellido, correo, usuario, clave, datoSelect1, datoSelect2, datoSelect3, respuesta);
                    //  Toast.makeText(getContext(), "Debe seleccionar un tipo de usuario.", Toast.LENGTH_SHORT).show();
                }else if (estado_usuario.getSelectedItemPosition() > 0) {

                    save_usuarios(getContext(), id, nombre, apellido, correo, usuario, clave, datoSelect1, datoSelect2, datoSelect3, respuesta);
                    // Toast.makeText(getContext(), "Debe seleccionar un estado.", Toast.LENGTH_SHORT).show();

                }else if (pregunta_usuario.getSelectedItemPosition() > 0) {

                    save_usuarios(getContext(), id, nombre, apellido, correo, usuario, clave, datoSelect1, datoSelect2, datoSelect3, respuesta);
                    //Toast.makeText(getContext(), "Debe seleccionar una pregunta.", Toast.LENGTH_SHORT).show();

                }else {
                    save_usuarios(getContext(), id, nombre, apellido, correo, usuario, clave, datoSelect1, datoSelect2, datoSelect3, respuesta);
                }

            }
        });

        return root;
    }

    private String timedate(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss ");
        String fecha = sdf.format(cal.getTime());
        return fecha;
    }

    private void save_usuarios(final Context context, final String id, final String nombre, final String apellido, final String correo, final String usuario, final String clave, final String tipo, final String estado, final String pregunta, final String respuesta) {
        final StringRequest request = new StringRequest(Request.Method.POST, Setting_var.URL_registrar_usuarios, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject resquestJSON = null;
              //  Log.i(TAG, "response:" + response);
                try {
                    resquestJSON = new JSONObject(response.toString());
                    String estado = resquestJSON.getString("estado");
                    String mensaje = resquestJSON.getString("mensaje");

                    if (estado.equals("1")) {
                        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                        //Log.i(TAG ,"estado" + estado);
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
                map.put("id", id);
                map.put("nombre", nombre);
                map.put("apellido", apellido);
                map.put("correo", correo);
                map.put("usuario", usuario);
                map.put("clave", clave);
                map.put("tipo", tipo);
                map.put("estado" , estado);
                map.put("pregunta", pregunta);
                map.put("respuesta", respuesta);
                return map;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(request);
    }


}