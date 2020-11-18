package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.logging.LogRecord;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
        Animation animacion2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abao);

        ImageView logoimagenview = findViewById(R.id.imageView2);

        TextView n1 = findViewById(R.id.textView2);
        TextView n2 = findViewById(R.id.textView3);
        TextView n3 = findViewById(R.id.textView4);
        TextView n4 = findViewById(R.id.textView5);
        TextView n5 = findViewById(R.id.textView6);
        TextView n6 = findViewById(R.id.textView7);


        n1.setAnimation(animacion2);
        n2.setAnimation(animacion2);
        n3.setAnimation(animacion2);
        n4.setAnimation(animacion2);
        n5.setAnimation(animacion2);
        n6.setAnimation(animacion2);
        logoimagenview.setAnimation(animacion1);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, Login.class);
                startActivity(intent);
                finish();
            }
        },4000);

    }
}