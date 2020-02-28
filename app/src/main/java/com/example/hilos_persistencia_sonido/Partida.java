package com.example.hilos_persistencia_sonido;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.view.WindowManager;


import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;


public class Partida extends AppCompatImageView {

    private int acel;
    private Bitmap pelota, fondo;
    private int tam_pantX, tam_pantY, posX, posY, velX, velY;
    private int tamPelota;
    boolean pelota_sube;

    public Partida(Context contexto, int nivel_dificultad){

        super(contexto);

        //WindowManager (INTERFAZ) ES UTILIZADA POR LA APP PARA COMUNICARSE CON EL MANEJADOR DE VENTANA,
        //PERMITE RESCATAR DATOS DEL DISPLAY (TAMAÑO, RESOLUCIÓN)  CON LA CLASE DISPLAY

        WindowManager manejador_ventana=(WindowManager) contexto.getSystemService(Context.WINDOW_SERVICE);

        Display pantalla=manejador_ventana.getDefaultDisplay();

        Point maneja_coord=new Point(); //INTEGRA LAS COORDENAS X-Y

        pantalla.getSize(maneja_coord); //DETERMINA EL TAMAÑO DE LA PANTALLA (ANCHO, LARGO)

        tam_pantX=maneja_coord.x;

        tam_pantY=maneja_coord.y;


        //SE CONSTRUYE UNA INTERFAZ GRAFICA MEDIANTE CÓDIGO

        BitmapDrawable dibujo_fondo=(BitmapDrawable) ContextCompat.getDrawable(contexto, R.drawable.paisaje_1);

        fondo=dibujo_fondo.getBitmap();// mirar en api getBitmap en clase BitmapDrawable. esto nos lleva a la siguiente instr.

        fondo=Bitmap.createScaledBitmap(fondo, tam_pantX, tam_pantY, false);//mirar en clase Bitmap


        BitmapDrawable objetoPelota=(BitmapDrawable) ContextCompat.getDrawable(contexto, R.drawable.pelota_1);

        pelota=objetoPelota.getBitmap();

        tamPelota=tam_pantY/3;

        pelota= Bitmap.createScaledBitmap(pelota, tamPelota, tamPelota, false);

        posX=tam_pantX/2-tamPelota/2;

        posY=0-tamPelota;

        acel=nivel_dificultad*(maneja_coord.y/400);



    }

    //SE EJECUTA AL TOCAR LA PANTALLA

    public boolean toque(int x, int y){

        if(y<tam_pantY/3) return false; //INVALIDA EL 1/3 SUPERIOR DE LA PANTALLA, ENVÍA false

        if(velY<=0) return false;

        //SI SE TOCA POR FUERA DE LA PELOTA (ANTES O DESPUES, EN EL EJE X)
        //EL METODO DEVUELVE false

        if(x<posX || x> posX+tamPelota) return false;

        if(y<posY || y>posY+tamPelota) return false;

        //SI SE TOCÓ LA PELOTA, SE INVIERTE EL MOVIMIENTO

        velY=-velY;

        double desplX=x-(posX+tamPelota/2);

        desplX=desplX/(tamPelota/2)*velY/2;

        velX+=(int)desplX;

        return true;
    }


    //DETERMINA EL MOVIMIENTO DE LA PELOTA

    public boolean movimientoBola(){

       //SI movimientoBola() DEVUELVE UN true,
       // LA PARTIDA FINALIZA

        if(posX<0-tamPelota){

            posY=0-tamPelota;

            velY=acel;
        }

        posX+=velX;

        posY+=velY;

        if(posY>=tam_pantY) return true; //SI LA BOLA ESTÁ DEBAJO DE LA PANTALLA

        if(posX+tamPelota<0 || posX>tam_pantX) return true; //SI LA BOLA ESTÁ A LA IZQ O DER
                                                            //DE LA PANTALLA

        if(velY<0) pelota_sube=true;

        if(velY>0 && pelota_sube){

            pelota_sube=false;

        }

        velY+=acel;

        return false;
    }

    //METODO QUE DIBUJA EL JUEGO

    protected void onDraw(Canvas lienzo){

        lienzo.drawBitmap(fondo, 0,0, null);

        lienzo.drawBitmap(pelota, posX, posY, null);


    }
}
