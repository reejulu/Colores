package a.bb.colores;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class VisualizacionRecord extends AppCompatActivity {
    static VisualizacionRecord visualizacionRecord;
    static VisualizacionRecord pulsado;
    public static final String PULSADO = "pulsado";
    public static final String PULSADOCLAVE = "pulsadoclave";
    int record;
    String nivel;
    String nombreArchivo;
    String imagenrecordantiguo;
    String jugadorRecord;
    String jugadoractual;
    double recordjuego;

    double t1Duraciont1Startt1Pause;
    String tiempoactual;
    int numerodeCajas;
    String sinAvatar;
    String avatatarrecord;
    int rotada;
    String rotadarecord;
    int rotadarecordint;
    ImageView imagen;
    ImageView imagenrecord;
    int continuarpor;
    String pulsado1;
    private ArrayList<String> datos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizacion_record);
//        Intent intentresultados = new Intent(SplitViewCopia.this, VisualizacionRecord.class);
//        intentresultados.putExtra("record",record);
//        intentresultados.putExtra("nivel", nivel);
//        intentresultados.putExtra("fotojugador",nombreArchivo);
//        intentresultados.putExtra("fotorecord",imagenrecordantiguo);
//        intentresultados.putExtra("jugadorrecord", jugadorRecord);
//        intentresultados.putExtra("jugadoractual",jugadoractual);
//        intentresultados.putExtra("tiemporecord",recordjuego);
//        intentresultados.putExtra("tutiempo",t1Duraciont1Startt1Pause);
//        intentresultados.putExtra("tiempo",tiempoactual);
//        intentresultados.putExtra("juego", numerodeCajas);
//        intentresultados.putExtra("avatar",sinAvatar);
//        intentresultados.putExtra("rotada",rotada);
//        intentresultados.putExtra("rotadarecord",rotadarecord);

        visualizacionRecord = this;
        pulsado = this;

        if (getIntent().getExtras() != null) {
            continuarpor = getIntent().getIntExtra("continuarpor",0);
            record = getIntent().getIntExtra("record", 0);
            nivel = getIntent().getStringExtra("nivel");
            nombreArchivo = getIntent().getStringExtra("fotojugador");
            imagenrecordantiguo = getIntent().getStringExtra("fotorecord");
            jugadorRecord = getIntent().getStringExtra("jugadorrecord");
            jugadoractual = getIntent().getStringExtra("jugadoractual");
            recordjuego = getIntent().getDoubleExtra("tiemporecord", 0);
            t1Duraciont1Startt1Pause = getIntent().getDoubleExtra("tutiempo", 0);
            numerodeCajas = getIntent().getIntExtra("juego", 2);
            sinAvatar = getIntent().getStringExtra("avatar");
            avatatarrecord = getIntent().getStringExtra("avatarrecord");
            rotada = getIntent().getIntExtra("rotada", 0);
            rotadarecord = getIntent().getStringExtra("rotadarecord");
            if (rotadarecord!=null){
                rotadarecordint = Integer.parseInt(rotadarecord);
            }else {
                rotadarecordint = 0;
            }

        }
        if (continuarpor < 2) { // menos que dos es pantalla de record
            if (record == 1) {   //HAY RECORD TENGO QUE MOSTRAR PANTALLA DE NUEVO RECORD
                setContentView(R.layout.activity_visualizacion_record);
                //HAY RECORD TENGO QUE MOSTRAR PANTALLA DE NUEVO RECORD
                // activity_visualizacion_record
                TextView txtnivel = findViewById(R.id.nivel);
                // vamos a escribir - NIVEL + (numero Nivel)nivel
                String textonivelString = "FACIL";
                if (nivel.contains("1")){
                }else {
                    if (nivel.contains("2")){
                        textonivelString = "DIFICIL";
                    }else {
                        if (nivel.contains("3")){
                            textonivelString = "MUY DIFICIL";
                        }
                    }
                }

                String textonivel;
                String textonivel1;
                textonivel1 = textonivelString;
                if (numerodeCajas<10){
                    textonivel = "CAJAS0"+numerodeCajas+" - NIVEL ";
                }else {
                    textonivel = "CAJAS" + numerodeCajas + " - NIVEL ";
                }

                txtnivel.setText(textonivel+"\n" + textonivel1);
                txtnivel.setTextSize(15);
                TextView txtjugador = findViewById(R.id.nombrejugador);
                String textjugador = "JUGADOR : " + jugadorRecord;
                txtjugador.setText(textjugador);

                TextView txttiempo = findViewById(R.id.tiempo);
                String texttiempo = String.format("Tiempo Record  : %1$.3f", t1Duraciont1Startt1Pause);
                txttiempo.setText(texttiempo);
                loadImagen();

            } else { // NO HAY RECORD - HAY QUE MOSTRAR PANTALLA ALTERNATIVA
                // NO HAY RECORD - HAY QUE MOSTRAR PANTALLA ALTERNATIVA
                setContentView(R.layout.activity_visualizacion_sin_record);
                // activity_visualizacion_record
                TextView txtnivel = findViewById(R.id.nivel);
                // vamos a escribir - NIVEL + (numero Nivel)nivel
                String textonivelString = "FACIL";
                if (nivel.contains("1")) {
                }
                if (nivel.contains("2")) {
                    nivel = "DIFICIL";
                }
                if (nivel.contains("3")) {
                    textonivelString = "MUY DIFICIL";
                }
                String textonivel;
                if (numerodeCajas<10){
                    textonivel = "CAJAS0"+numerodeCajas;
                }else {
                    textonivel = "CAJAS" + numerodeCajas;
                }
                txtnivel.setText(textonivel+"\n"+textonivelString);
                txtnivel.setBackgroundColor(Color.RED);

                TextView txtjugadorytiemporecord = findViewById(R.id.tiemporecord);
                String text1 = "Record Actual : " + jugadorRecord + "\n";
                String text2 = String.format("%1$.3f", recordjuego);
                //texto.setText(Texto + "\n" + Texto2 + "\n" + Texto3 + "\n" + Texto4);
                txtjugadorytiemporecord.setText(text1 + text2 + " segundos");
                txtjugadorytiemporecord.setBackgroundColor(Color.BLUE);

                loadImagen();
                TextView txtjugadorytiempotuyo = findViewById(R.id.tiempotuyo);
                String text10 = "Tu tiempo " + jugadoractual + " fué solo de:" + "\n";
                String text12 = String.format("%1$.3f", t1Duraciont1Startt1Pause);
                //texto.setText(Texto + "\n" + Texto2 + "\n" + Texto3 + "\n" + Texto4);
                txtjugadorytiempotuyo.setText(text10 + text12 + " segundos");
                txtjugadorytiempotuyo.setBackgroundColor(Color.BLUE);

            }
        } else {
            // hay que mostrar pantalla de inicio para empezar a jugar
            setContentView(R.layout.activity_visualizacion_record);
            //HAY RECORD TENGO QUE MOSTRAR PANTALLA DE NUEVO RECORD
            TextView titulo = findViewById(R.id.nuevorecord);
            String textonivel;
            if (numerodeCajas<10){
                textonivel = "CAJAS0"+numerodeCajas;
            }else {
                textonivel = "CAJAS" + numerodeCajas;
            }
            titulo.setText("EL RECORD ACTUAL \n"+textonivel);
            // activity_visualizacion_record
            TextView txtnivel = findViewById(R.id.nivel);
            // vamos a escribir - NIVEL + (numero Nivel)nivel
            if (nivel.contains("1")) {
                nivel = "FACIL";
            }
            if (nivel.contains("2")) {
                nivel = "DIFICIL";
            }
            if (nivel.contains("3")) {
                nivel = "MUY DIFICIL";
            }
            String textonivel1;

            textonivel1 = " - NIVEL "+ nivel +" -";


            txtnivel.setText(textonivel1);
        //    txtnivel.setBackgroundColor(R.color.aguamarina);
            TextView txtjugador = findViewById(R.id.nombrejugador);
            String textjugador = "JUGADOR : " + jugadorRecord;
            txtjugador.setText(textjugador);

            TextView txttiempo = findViewById(R.id.tiempo);
            String texttiempo = String.format("Tiempo Record  : %1$.3f", recordjuego);
            txttiempo.setText(texttiempo);
            record = 1;
            nombreArchivo = imagenrecordantiguo;
            rotada = rotadarecordint;
            //jugadorRecord
            //recordjuego
            if (nombreArchivo==null) {
                titulo.setText("ERES EL PRIMERO EN INTENTARLO ");
                txtjugador.setText("SUERTE Y HAZ UN BUEN TIEMPO");
            }else {
                loadImagen();
            }

        }

    }

    public static String getPulsado(){
        return pulsado.toString();
    }

    public static VisualizacionRecord getInstance(){
        return visualizacionRecord;
    }




    public void loadImagen() {


        if (record == 0){ // hay que mostrar imagen record y imagen jugador actual
            imagen = findViewById(R.id.imagen);
            imagenrecord = findViewById(R.id.imagenpuntos);
            //IMAGEN DEL RECORD
            if (!avatatarrecord.contains("false")) {
                traduciravatar1(avatatarrecord);
             //   imagenrecord.setImageResource(R.drawable.picture);
            } else {
                Bitmap recuperaImagen = Utilidades.recuperarImagenMemoriaInterna(imagenrecord.getContext(), imagenrecordantiguo);//dato1 no se usa
                imagenrecord.setImageBitmap(recuperaImagen);
                imagenrecord.setRotation(rotadarecordint);
        //        imagenrecord.setScaleType(ImageView.ScaleType.FIT_START);
        //        imagenrecord.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imagenrecord.setScaleType(ImageView.ScaleType.FIT_XY);
        //        imagenrecord.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


            }
            //IMAGEN DE LA PARTIDA ACTUAL
            if (!sinAvatar.contains("false")) {
                traduciravatar(sinAvatar);
        //        imagen.setImageResource(R.drawable.picture);
            } else {
                Bitmap recuperaImagen = Utilidades.recuperarImagenMemoriaInterna(imagen.getContext(), nombreArchivo);//dato1 no se usa
                imagen.setImageBitmap(recuperaImagen);
                imagen.setRotation(rotada);
                imagen.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imagen.setScaleType(ImageView.ScaleType.FIT_XY);

            }

        }else {  // mostrar solo imagen jugador record
            //jugadorRecord
            //recordjuego
            imagen = findViewById(R.id.imagen);
            //IMAGEN DEL RECORD
            if (!avatatarrecord.contains("false")) {
                traduciravatar(avatatarrecord);
            //    imagen.setImageResource(R.drawable.picture);
            } else {
                Bitmap recuperaImagen = Utilidades.recuperarImagenMemoriaInterna(imagen.getContext(), nombreArchivo);//dato1 no se usa
                imagen.setImageBitmap(recuperaImagen);
                imagen.setRotation(rotada);
                imagen.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imagen.setScaleType(ImageView.ScaleType.FIT_XY);

            }
        }
    }

    public void traduciravatar(String avatar){

        switch (avatar) {
            case "dora1":
                imagen.setImageResource(R.drawable.dora1);
                break;
            case "dora2":
                imagen.setImageResource(R.drawable.dora2);
                break;
            case "dora3":
                imagen.setImageResource(R.drawable.dora3);
                break;
            case "dora4":
                imagen.setImageResource(R.drawable.dora4);
                break;
            case "dora5":
                imagen.setImageResource(R.drawable.dora5);
            case "dora6":
                imagen.setImageResource(R.drawable.dora6);
                break;
            default:
                imagen.setImageResource(R.drawable.dora1);
                break;
        }


    }

    public void traduciravatar1(String avatar){

        switch (avatar) {
            case "dora1":
                imagenrecord.setImageResource(R.drawable.dora1);
                break;
            case "dora2":
                imagenrecord.setImageResource(R.drawable.dora2);
                break;
            case "dora3":
                imagenrecord.setImageResource(R.drawable.dora3);
                break;
            case "dora4":
                imagenrecord.setImageResource(R.drawable.dora4);
                break;
            case "dora5":
                imagenrecord.setImageResource(R.drawable.dora5);
            case "dora6":
                imagenrecord.setImageResource(R.drawable.dora6);
                break;
            default:
                imagenrecord.setImageResource(R.drawable.dora1);
                break;
        }


    }

    public void continuar(View view) {
        SplitViewCopia.setVar1(1);
        finish();
    }
    public void resultados(View view) {
        SplitViewCopia.setVar1(2);
        finish();

    }


}
