package com.example.hilos_persistencia_sonido;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ayuda(View vista) {

        Intent intencion = new Intent(this, AyudaActividad.class);

        startActivity(intencion);


    }

    public void volver(View vista){

        onBackPressed();
    }

    public void dificultad(View vista){

        //RESCATAMOS EL TEXTO DEL BOTON PULSADO

        String dific=(String)((Button)vista).getText();

        int dificultad=1;

        if(dific.equals("Standar"))dificultad=2;

        if(dific.equals("Dificult"))dificultad=3;

        Intent intencion=new Intent(this,Gestion.class);

        intencion.putExtra("DIFICULTAD",dificultad);

        startActivity(intencion);

    }
}
