package a.bb.colores;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import a.bb.colores.recycler.AdapterPartidasDetalle;
import a.bb.colores.recycler.Partida;

public class DetalleJugador extends AppCompatActivity {
    String jugadorelegido ;
    String juegoelegido;
    String tiempoelegido;
    String sinAvatar;
    String origen = "nada";
    private ArrayList<Partida> datos;
    String juego;
    private RecyclerView recView;
    private AdapterPartidasDetalle adaptador;
    private ImageView avatar_foto;
    String rotada;
    String nivel;
    ImageView i;
    DisplayMetrics metrics;
    Bitmap recuperaImagen;
    int click = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_jugador);


        // este getItent es solo devuelto cuando viene de haber jugado y se da volver
        if (getIntent().getExtras() != null) {
            jugadorelegido = getIntent().getStringExtra("nombre");
            juegoelegido = getIntent().getStringExtra("juego");
            tiempoelegido = getIntent().getStringExtra("tiempo");
            sinAvatar = getIntent().getStringExtra("avatar");
          //  origen = getIntent().getStringExtra("origen");
            rotada = getIntent().getStringExtra("rotada");
            nivel = getIntent().getStringExtra("nivel");

        }
        leerimagen(jugadorelegido,juegoelegido,tiempoelegido,sinAvatar);
        filtartodaspartidas(juegoelegido,nivel);
        mostrarrecycler("orden",jugadorelegido);
        if (origen.toString().contains("oooo")){

            int height=avatar_foto.getHeight();
            int width=avatar_foto.getWidth();
            avatar_foto.setLayoutParams(new LinearLayout.LayoutParams(height, width));
            /*
            i = avatar_foto;
            i.getMaxWidth();
            i.getMaxHeight();
            ViewTreeObserver vto = i.getViewTreeObserver();

//gets screen dimensions
            metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

//this happens before the layout is visible
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int newWidth;
                    double newHeight;
                    int oldHeight;
                    int oldWidth;

                    //the new width will fit the screen
                    newWidth = metrics.widthPixels;

                    //so we can scale proportionally
                    oldHeight = i.getDrawable().getIntrinsicHeight();
                    oldWidth = i.getDrawable().getIntrinsicWidth();
                    newHeight = Math.floor((oldHeight * newWidth) / oldWidth);
                    i.setLayoutParams(new LinearLayout.LayoutParams(newWidth, (int) newHeight));
                    i.setScaleType(ImageView.ScaleType.CENTER_CROP);

                    //so this only happens once
                    i.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            });
            */
            // viene de AdapterPartidasDetalla y no hay que mostrar recycler
            // solo la foto en grande
        }else {
          //  filtartodaspartidas();
          //  mostrarrecycler("jugador");
        }

    }

    public void filtartodaspartidas( String juegoelegido,String nivelabuscar) {
        // CONSULTA DB PARTIDA PARA OBTENER LA LISTA DE PARTIDAS ordenada por JUEGO
        //private ArrayList<Partida> datos;
        datos = new ArrayList<>();
        Partida partida_aux =null;

        BaseDatosPuntuaciones baseDatosPuntuaciones = new BaseDatosPuntuaciones(this, "MiDB", null, 1);
        ArrayList<Puntuacion> listajuegos = new ArrayList<>();
        // todas las partidas
        // cuando juego es fijado xx - la busqueda se hara por orden de partidas jugadas
        juego = "nombre";
        listajuegos = (ArrayList<Puntuacion>) baseDatosPuntuaciones.listatodaspartidas(juego,jugadorelegido,nivelabuscar);


        if (listajuegos == null){

        }else {
            // convierto los datos de partida para guardarlos en el array datos
            // que tiene solo tres datos
            //    private String jugador;
            //    private String juego;
            //    private String tiempo;
            for (int i = 0; i < listajuegos.size(); i++) {
                String jugador = listajuegos.get(i).getNombre();
                String juego = listajuegos.get(i).getJuego();
                String tiempo = listajuegos.get(i).getTiempo();
                String sinAvatar = listajuegos.get(i).getSinAvatar();
                String rotada = listajuegos.get(i).getRotada();
                String nivel = listajuegos.get(i).getNivel();
                partida_aux = new Partida(jugador,juego,tiempo,sinAvatar,rotada,nivel);
                datos.add(partida_aux);
            }
        }


    }

    public void traduciravatar(String avatar){

        switch (avatar) {
            case "dora1":
                avatar_foto.setImageResource(R.drawable.dora1);
                break;
            case "dora2":
                avatar_foto.setImageResource(R.drawable.dora2);
                break;
            case "dora3":
                avatar_foto.setImageResource(R.drawable.dora3);
                break;
            case "dora4":
                avatar_foto.setImageResource(R.drawable.dora4);
                break;
            case "dora5":
                avatar_foto.setImageResource(R.drawable.dora5);
            case "dora6":
                avatar_foto.setImageResource(R.drawable.dora6);
                break;
            default:
                avatar_foto.setImageResource(R.drawable.picture);
                break;
        }
    }

    public void leerimagen(String jugadorelegido, String juegoelegido, String tiempoelegido, String sinAvatar){
        avatar_foto = (ImageView)findViewById(R.id.foto);
        if (!sinAvatar.toString().contains("false")){
            traduciravatar(sinAvatar);
       //     avatar_foto.setImageResource(R.drawable.picture);
        }else {
            Uri uri = null;
            String fotoArchivada = jugadorelegido.toString() + juegoelegido.toString() + tiempoelegido.toString();
            Log.i("MIAPP", "PartidasViewHolder-cargarPartidasEnHolder-ruta imagen es : " + fotoArchivada);
            recuperaImagen = Utilidades.recuperarImagenMemoriaInterna(this, fotoArchivada);
            if (recuperaImagen != null) {
                avatar_foto.setImageBitmap(recuperaImagen);
                avatar_foto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                //avatar_foto.setScaleType(ImageView.ScaleType.FIT_START);
                int rotadaint = Integer.parseInt(rotada);
                avatar_foto.setRotation(rotadaint);
                origen = "hay foto";
            } else {
                avatar_foto.setImageResource(R.drawable.picture);
            }
        }
    }

    public void mostrarrecycler(String value, String jugadorelegido){
      //  setContentView(R.layout.activity_resultado_recycler);
        TextView t2 = findViewById(R.id.txtfiltrado);
        if (value.contains("record")){
            t2.setText("RECORDS DEL JUGADOR : " + jugadorelegido);
        }else {
            if (value.contains("orden")){
                t2.setText("PARTIDAS ORDENADAS POR FECHA DEL JUGADOR : "+jugadorelegido);
            }
        }


        recView = (RecyclerView) findViewById(R.id.RecView);
        //recView.setHasFixedSize(true);//opcional, si sé que el tamaño no va a cambiar

        adaptador = new AdapterPartidasDetalle(datos);


        recView.setAdapter(adaptador);

        recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    public void ordenar(View view) {
        int i = 0;
        ordenar1(view,i);
    }
    public void ordenar1(View view,int j){
        // CUANDO DATO2 LLEGA CON 1 - MOSTRAR SOLO PARTIDAS CON RECORD(SOLO UNA POR JUEGO TIENE QUE HABER)
        //              DIFERENTE 1 - PUEDE SER POR "juego" , "jugador" o "tiempo"
        //datos = new ArrayList<Partida>();
        // BORRO array datos para volver a generarlo con lo que se haya pedido en el DAT02
        datos.clear();
        leerimagen(jugadorelegido,juegoelegido,tiempoelegido,sinAvatar);
        Partida partida_aux = null;

        //CONSULTO LA TABLA DE RECORD Y OBTENGO LA LISTA JUEGOS - RECORD
        BaseDatosPuntuaciones baseDatosPuntuaciones = new BaseDatosPuntuaciones(this, "MiDB", null, 1);


        ArrayList<Puntuacion> listajuegos = new ArrayList<>();
        // solo partidas con record
        //listajuegos = (ArrayList<Puntuacion>) baseDatosPuntuaciones.listaconrecord();
        // todas las partidas

        if (j == 1) {// mostrar solo record
            listajuegos = (ArrayList<Puntuacion>) baseDatosPuntuaciones.listaconrecordPorJugador(jugadorelegido,nivel);
            if (listajuegos == null){

            }else {
                for (int i = 0; i < listajuegos.size(); i++) {
                    String jugador = listajuegos.get(i).getNombre();
                    String juego = listajuegos.get(i).getJuego();
                    String tiempo = listajuegos.get(i).getTiempo();
                    String sinAvatar = listajuegos.get(i).getSinAvatar();
                    String rotada = listajuegos.get(i).getRotada();
                    String nivel = listajuegos.get(i).getNivel();
                    partida_aux = new Partida(jugador, juego, tiempo,sinAvatar,rotada,nivel);
                    datos.add(partida_aux);
                }
            }


            mostrarrecycler("record",jugadorelegido);
            //setContentView(R.layout.activity_detalle_jugador);
    //        recView = (RecyclerView) findViewById(R.id.RecView);
            //recView.setHasFixedSize(true);//opcional, si sé que el tamaño no va a cambiar

    //        adaptador = new AdapterPartidasDetalle(datos);


    //        recView.setAdapter(adaptador);

    //        recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        } else {
            String ii = view.getTag().toString();
            String string = "juego";
            switch (ii){
                case "juego":
                    // NO CAMBIO NADA
                    mostrarrecycler("orden",jugadorelegido);
                    break;
                case "jugador":
                    string = "nombre";
                    //TODAS LAS PARTIDAS POR EL JUGADOR ELEJIDO
                    filtartodaspartidas(juegoelegido,nivel);
                    mostrarrecycler("orden",jugadorelegido);
                    break;
                case "tiempo":
                    // NO CAMBIO NADA
                    mostrarrecycler("orden",jugadorelegido);
                    break;
                case "foto":
                    filtarparidasrecords();
                   // mostrarrecycler("record",jugadorelegido);
                    break;
                default:break;
            }

        }

    }

    public void filtarparidasrecords(){
        View view = null;
        ordenar1(view,1);
    }

    public void volveramenu(View view) {
        finish();
    }

    public void agrandar(View view) {
        LinearLayout l = findViewById(R.id.principal);
        avatar_foto = findViewById(R.id.foto1);
        if (click == 0) {
            l.setVisibility(View.INVISIBLE);
            avatar_foto.setVisibility(View.VISIBLE);
            if (recuperaImagen != null) {
                avatar_foto.setImageBitmap(recuperaImagen);
                //avatar_foto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                //avatar_foto.setScaleType(ImageView.ScaleType.FIT_START);
                int rotadaint = Integer.parseInt(rotada);
                avatar_foto.setRotation(rotadaint);
                origen = "hay foto";
            } else {
                traduciravatar(sinAvatar);
              //  avatar_foto.setImageResource(R.drawable.picture);
            }
            click = 1;
        }else{
            l.setVisibility(View.VISIBLE);
            avatar_foto.setVisibility(View.INVISIBLE);
            click = 0;
        }

        /*
        int height=avatar_foto.getHeight();
        int width=avatar_foto.getWidth();
        avatar_foto.setLayoutParams(new LinearLayout.LayoutParams(height, width));

            i = avatar_foto;
            i.getMaxWidth();
            i.getMaxHeight();
            ViewTreeObserver vto = i.getViewTreeObserver();

//gets screen dimensions
            metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

//this happens before the layout is visible
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int newWidth;
                    double newHeight;
                    int oldHeight;
                    int oldWidth;

                    //the new width will fit the screen
                    newWidth = metrics.widthPixels;

                    //so we can scale proportionally
                    oldHeight = i.getDrawable().getIntrinsicHeight();
                    oldWidth = i.getDrawable().getIntrinsicWidth();
                    newHeight = Math.floor((oldHeight * newWidth) / oldWidth);
                    i.setLayoutParams(new LinearLayout.LayoutParams(newWidth, (int) newHeight));
                    i.setScaleType(ImageView.ScaleType.CENTER_CROP);

                    //so this only happens once
                    i.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            });
            */
        // viene de AdapterPartidasDetalla y no hay que mostrar recycler
        // solo la foto en grande

    }
}
