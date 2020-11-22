package com.example.proyectofinal;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {

    private Button ini;
    private EditText ET_USU, ET_CLA;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==event.KEYCODE_BACK){
            AlertDialog.Builder alerta = new AlertDialog.Builder(Login.this);
            alerta.setTitle("Salir");
            alerta.setMessage("¿Desea Salir de la aplicación?")
                    .setCancelable(false)
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                        }
                    })
                    .setNegativeButton("Cancelar", null);
            AlertDialog alertDialog = alerta.create();
            alertDialog.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ini = findViewById(R.id.btninicio);
        ET_USU = findViewById(R.id.ET_USU);
        ET_CLA = findViewById(R.id.ET_CLA);


        ini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread tr = new Thread(){
                    @Override
                    public void run() {
                        final String resultado = Ingresar(ET_USU.getText().toString(), ET_CLA.getText().toString());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int r = obtenerDatosJson(resultado);
                                if (r > 0){
                                    Intent i = new Intent(Login.this , MainActivity.class);
                                    i.putExtra("usuario", ET_USU.getText().toString());
                                    i.putExtra("clave", ET_CLA.getText().toString());
                                    startActivity(i);
                                    finish();

                                }else{

                                    //Toast.makeText(getApplicationContext(), "Las Credenciales son Incorrectas.", Toast.LENGTH_LONG).show();
                                    String user = ET_USU.getText().toString();
                                    String pas = ET_CLA.getText().toString();
                                    if (user.length() == 0){
                                        ET_USU.setError("Rellene este campo");
                                    }else if (pas.length() == 0){
                                        ET_CLA.setError("Rellene este campo");
                                    }else {

                                    }

                                    if (ET_USU.getText().toString().equals("") == false) {
                                        Toast.makeText(getApplicationContext(), "Usuario Incorrecto.", Toast.LENGTH_LONG).show();
                                    }
                                    else if (ET_CLA.getText().toString().equals("") == false){
                                        Toast.makeText(getApplicationContext(), "Contraseña Incorrecta.", Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(getApplicationContext(), "Bienvenido.", Toast.LENGTH_LONG).show();
                                    }


                                }
                            }
                        });
//
                    }
                };
                tr.start();
            }
        });
    }

    public String Ingresar(String usuario, String Clave){

        URL url = null;
        String linea = "";
        int respuesta=0;
        StringBuilder resul=null;

        try {

            url = new URL("http://pinguox.6te.net/AppInventario/validar_usuario.php?usuario="+usuario+"&clave="+Clave);

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            respuesta = connection.getResponseCode();

            resul = new StringBuilder();

            if (respuesta==HttpURLConnection.HTTP_OK){

                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                while ((linea = reader.readLine()) !=null){
                    resul.append(linea);
                }
            }

        }catch (Exception e){

        }

        return resul.toString();
    }

    public int obtenerDatosJson(String response){
        int res = 0;

        try {

            JSONArray json = new JSONArray(response);

            if (json.length() > 0 ){
                res = 1;
            }

        }catch (Exception e){

        }

        return res;
    }

}

