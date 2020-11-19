package com.example.proyectofinal.ui.EditarProducto;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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
import com.example.proyectofinal.ModeloCategoria;
import com.example.proyectofinal.ModeloProducto;
import com.example.proyectofinal.MySingleton;
import com.example.proyectofinal.R;
import com.example.proyectofinal.Setting_var;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static java.lang.Integer.parseInt;

public class EditarProducto extends Fragment {

    private static final String TAG = "EditarProducto";
    ArrayList<String> lista = null;
    ArrayList<ModeloCategoria> listaCategorias;
    private TextInputLayout ti_id, ti_nombre_prod, ti_descripcion, ti_stock,
            ti_precio, ti_unidadmedida;
    private EditText et_id, et_nombre_prod, et_descripcion, et_stock,
            et_precio, et_unidadmedida;
    private Spinner sp_estadoProductos, sp_fk_categoria;
    private TextView tv_fechahora;
    private Button btneditarPro, btneliminarPro;
    ProgressDialog progressDialog;

    String elementos[] = {"Uno", "Dos", "Tres", "Cuatro", "Cinco"};
    final String[] elementos1 = new String[]{
            "Seleccione",
            "1",
            "2",
            "3",
            "4",
            "5"
    };
    String idcategoria = "";
    String nombrecategoria = "";
    int conta = 0;
    String datoStatusProduct = "";

    ModeloProducto dto = new ModeloProducto();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editar_producto, container, false);

        ti_id = view.findViewById(R.id.ti_id);
        ti_nombre_prod = view.findViewById(R.id.ti_nombre_prod);
        ti_descripcion = view.findViewById(R.id.ti_descripcion);
        ti_stock = view.findViewById(R.id.ti_stock);
        ti_precio = view.findViewById(R.id.ti_precio);
        ti_unidadmedida = view.findViewById(R.id.ti_unidadmedida);

        et_id = view.findViewById(R.id.et_id);
        et_nombre_prod = view.findViewById(R.id.et_nombre_prod);
        et_descripcion = view.findViewById(R.id.et_descripcion);
        et_stock = view.findViewById(R.id.et_stock);
        et_precio = view.findViewById(R.id.et_precio);
        et_unidadmedida = view.findViewById(R.id.et_unidadmedida);
        sp_estadoProductos = view.findViewById(R.id.sp_estadoProductos);
        sp_fk_categoria = view.findViewById(R.id.sp_fk_categoria);

        btneditarPro = view.findViewById(R.id.btneditarPro);
        btneliminarPro = view.findViewById(R.id.btneliminarPro);

        ModeloProducto m = new ModeloProducto();
        Bundle b = getArguments();

        et_id.setText(b.getString("id"));
        et_nombre_prod.setText(b.getString("nombre"));
        et_descripcion.setText(b.getString("descripcion"));
        et_stock.setText(b.getString("stock"));
        et_precio.setText(b.getString("precio"));
        et_unidadmedida.setText(b.getString("unidad"));
        sp_estadoProductos.setSelection(Integer.parseInt(b.getString("estado")));

        fk_categorias(getContext(), b.getString("categoria"));

        sp_estadoProductos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sp_estadoProductos.getSelectedItemPosition() > 0) {
                    datoStatusProduct =  sp_estadoProductos.getSelectedItem().toString();
                } else {
                    datoStatusProduct = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_fk_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (conta >= 1 && sp_fk_categoria.getSelectedItemPosition() > 0) {
                    String item_spinner = sp_fk_categoria.getSelectedItem().toString();
                    String s[] = item_spinner.split("~");
                    idcategoria = s[0].trim();
                    nombrecategoria = s[1];
                  /*  Toast toast = Toast.makeText(getContext(), "Id cat: " + idcategoria + "\n" + "Nombre Categoria: " + nombrecategoria, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();*/
                } else {
                    idcategoria = "";
                    nombrecategoria = "";
                }
                conta++;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btneditarPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = et_id.getText().toString();
                String nombre = et_nombre_prod.getText().toString();
                String descripcion = et_descripcion.getText().toString();
                String stock = et_stock.getText().toString();
                String precio = et_precio.getText().toString();
                String unidad = et_unidadmedida.getText().toString();
                String estado = sp_estadoProductos.getSelectedItem().toString();
                String categoria = sp_fk_categoria.getSelectedItem().toString();

                // Log.i(TAG, "onClick -> id: " + id + ", nombre: " + nombre + ", descripción: " + descripcion + ", stock: " + stock + ", precio: " + precio + ", unidad_medida: " + unidad + ", estado: " + estado + ", categoria: " + categoria);
                editarPro(id, nombre, descripcion, stock, precio, unidad, estado, categoria);
                Navigation.findNavController(v).navigate(R.id.nav_listaProductos);
            }
        });

        btneliminarPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = et_id.getText().toString();

               // Log.i(TAG, "onClick -> id: " + id );

                eliminarPro(Integer.parseInt(id));
                Navigation.findNavController(v).navigate(R.id.nav_listaProductos);

            }
        });

        return view;
    }

    private void editarPro(final String id_producto, final String nom_producto, final String des_producto, final String stock, final String precio, final String unidad_medida, final String estado_producto, final String categoria ) {
        final StringRequest request = new StringRequest(Request.Method.POST, Setting_var.URL_Editar_Producto, new Response.Listener<String>() {
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
                List<ModeloProducto> ListaProducto;
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put("Accept", "application/json");
                map.put("id", id_producto);
                map.put("nombre", nom_producto);
                map.put("descripcion", des_producto);
                map.put("stock", stock);
                map.put("precio", precio);
                map.put("unidad", unidad_medida);
                map.put("estado", estado_producto);
                map.put("categoria", categoria);
                return map;
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    public void fk_categorias(final Context context, final String catId){
        listaCategorias = new ArrayList<ModeloCategoria>();
        lista = new ArrayList<String>();
        lista.add("Seleccione Categoria");
        String url = Setting_var.URL_consultaAllCategorias;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    ModeloCategoria obj_categorias = null;

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject categoriasObject = array.getJSONObject(i);
                        int id_categoria = categoriasObject.getInt("id_categoria");
                        String nombre_categoria = categoriasObject.getString("nom_categoria");
                        int estado_categoria = Integer.parseInt(categoriasObject.getString("estado_categoria"));

                        obj_categorias = new  ModeloCategoria(id_categoria, nombre_categoria, estado_categoria);

                        listaCategorias.add(obj_categorias);

                        lista.add(listaCategorias.get(i).getId_categoria()+" ~ "+listaCategorias.get(i).getNombre_categoria());

//                        Log.i("Id Categoria", String.valueOf(obj_categorias.getId_categoria()));
//                        Log.i("Nombre Categoria", obj_categorias.getNombre_categoria());
//                        Log.i("Estado Categoria", String.valueOf(obj_categorias.getEstado_categoria()));
                    }

                    ArrayAdapter<String> adaptador =new ArrayAdapter<String> (getContext(),android.R.layout.simple_spinner_item, lista);
                    sp_fk_categoria.setAdapter(adaptador);

                    int selectedPosition = 0;
                    for (int i = 0; i < lista.size(); i++) {
                        if (lista.get(i).contains(catId)){
                            selectedPosition = i;
                        }
                    }

                    sp_fk_categoria.setSelection(selectedPosition);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error. Compruebe su acceso a Internet.", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void eliminarPro(final int id_producto) {
        final StringRequest request = new StringRequest(Request.Method.POST, Setting_var.URL_Eliminar_Productos, new Response.Listener<String>() {
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
                Toast.makeText(getContext(), "No se puede eliminar.\n" + "Intentelo más tarde.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put("Accept", "application/json");
                map.put("id", String.valueOf(id_producto));
                return map;
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

}