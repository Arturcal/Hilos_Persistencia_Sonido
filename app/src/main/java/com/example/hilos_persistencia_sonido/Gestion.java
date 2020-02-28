package com.example.hilos_persistencia_sonido;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;

public class Gestion  extends Activity {


    private Partida partida;

    private int dificultad;

    private int FPS=30; //VELOCIDAD DE CUADROS POR SEGUNDO

    private Handler temporizador;



    public void onCreate(Bundle SavedInstanceState){

        super.onCreate(SavedInstanceState);

        Bundle miBundle=getIntent().getExtras();

        dificultad=miBundle.getInt("DIFICULTAD");

        partida=new Partida(getApplicationContext(),dificultad);

        setContentView(partida);

        temporizador=new Handler();

        temporizador.postDelayed(elHilo,2000);

    }

    //CREAMOS EL METODO PARA GENERAR EL HILO

    private Runnable elHilo=new Runnable() {
        @Override
        public void run() {

            //SI LA BOLA SALE DE LA PANTALLA (POR ARRIBA O POR LOS COSTADOS)
            //LA PELOTA QUEDA INVISIBLE POR LO QUE EL HILO MUERE

            if(partida.movimientoBola()) fin(); //EL MÉTODO movimientoBola() DEVUELVE UN true

            else{

                partida.invalidate(); //ELIMINA EL CONTENIDO DE ImageView
                                      // Y LLAMA DE NUEVO A onDraw() (ACTUALIZA LA PANTALLA)

                temporizador.postDelayed(elHilo,1000/FPS);

            }

        }
    };

    //CAPTURA EL TOQUE EN LA PANTALLA

    public boolean onTouchEvent(MotionEvent evento){

        //DETERMINAMOS DONDE SE PULSO EN LA PANTALLA

        int x=(int)evento.getX();

        int y=(int)evento.getY();

        //PASAMOS AL METODO DONDE HA PULSADO EL USUARIO

        partida.toque(x,y);

        //COMO EL METODO ES boolean, PASAMOS UN FALSE SOLO PARA TERMINAR EL METODO
        //(TAMPOCO IBA A HABER PROBLEMAS SI return true)

        return false;

    }

    //PARA FINALIZAR LA ACTIVIDAD ACTUAL

    public void fin(){

        temporizador.removeCallbacks(elHilo); //LIMPIA LA MEMORIA

        finish();



    }


}
