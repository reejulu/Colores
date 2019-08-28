package a.bb.colores;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class SplitViewCopia extends AppCompatActivity {

    private static int var1;
    int i = randomColor();
    private final int COLORES[] = { R.color.negro, i};
    private final int COLORESint[] = {0,1};
    Random rand = new Random();
    int randNum ;

    private int indexColor = 0;
    private static Random r = new Random();
    Menu menureferencia;
    int numerodeCajas = 1;  // valor inicial para ver numero de cajas totales depues de cade click
    int contador1 = 1;  // contador toques
    int contadorColores = 1;
    public static final String SPLIT_VIEW_DIVIDIR = "MIAPP";
    int contardorToques = 2;
    int cajasportoque = 2;
    LinearLayout vista;
    TextView texto;
    Button boton;
    long t1Start;
    long t1Pause;
    double t1Duraciont1Startt1Pause = 0;
    String Texto;
    String Texto2;
    Boolean jugarahora = false;
    // https://github.com/flcarballeda/CajaColores.git
    private static final String RECORD = "RecordPartidaAnterior";
    private static final String CLAVE = "RecordPartidaAnteriorClave";
    private static final String JUGADOR = "Jugadornombre";
    private static final String JUGADORCLAVE = "Jugadornombreclave";

    // news
    boolean inicio = false;
    ArrayList<Integer> ids = new ArrayList<>();
    private int num_veces;
    boolean bloqueclick;
    double recordjuego = 0;
    int record = 0;
    int otrojuego = 0;
    //boolean disableButtonFlagJugarAhora = false;
    Menu myMenu;
    MenuItem logojugarahora;
    EditText nombrejugador;
    String jugadorRecord;
    String jugadoractual;
    ArrayList<String> arrayData = new ArrayList<>();
    ArrayList<String> arrayDataLog = new ArrayList<>();
    MediaPlayer mediaPlayer;
    String dato1 = "sin dato";
    String dato2 = "sin dato";
    String dato3 = "sin dato";
    String dato4 = "sin dato";
    String dato5 = "false";
    String dato6 = "0";
    String dato7 = "0";
    String Texto1;
    String Texto3previo;
    String Texto3;
    String Texto4;
    ArrayList<Puntuacion> top10 = new ArrayList<>();
    int id = 0;
    private CountDownTimer cronometro1;
    String timer;
    private TextView timerText;
    private TextView timerJuego;
    int segundos;
    int minutos = 0;
    boolean empiezajuego = false;
    View resultadosItem;
    View jugarahoraItem;
    MenuItem resultadosItem1;
    MenuItem jugarahoraItem1;
    private Uri photo_uri;//para almacenar la ruta de la imagen
    String ruta_captura_foto;
    Uri uri_destino = null;
    File file;
    String sinAvatar = "false";
    Boolean sinAvatarbolean;
    String nivel;
    int rotada;
    String original_photo_galery_uri;
    String nombreArchivo;
    String juegoactual;
    String tiempoactual;
    String imagenrecordantiguo;
    String rotadarecord;
    String avatatarrecord;
    int continuarpor= 0;
    String pulsado;

    @SuppressLint("NewApi")

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_view_copia);
        // para quitar el titulo de la app en la barra cabecera
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Log.i("MIAPP","onCreate");
        continuarpor = 0;

        //******************************* //
        // Recupero los datos del Intent  //
        //******************************* //
        if (getIntent().getExtras() != null) {
            t1Start = System.currentTimeMillis();
            contardorToques = getIntent().getIntExtra("toques", 2);  // numero de toques
            cajasportoque = getIntent().getIntExtra("cajas", 2);
            jugadoractual = getIntent().getStringExtra("jugador");
            //intent.putExtra("imagen",photo_uri);
            ruta_captura_foto = getIntent().getStringExtra("imagen");
            sinAvatarbolean = getIntent().getBooleanExtra("sinAvatar", false);
            sinAvatar = getIntent().getStringExtra("sinAVatarSTring");
            nivel = getIntent().getStringExtra("nivel");
            rotada = getIntent().getIntExtra("rotada",0);  // por defecto no hay rotacion de imagen
            original_photo_galery_uri =getIntent().getStringExtra("originaluri");
            if (sinAvatarbolean) {
               // sinAvatar = "true"; // ya vine con el string de la imagen seleccionada
            }
            uri_destino = null;
            file = new File(ruta_captura_foto);
            try { //INTENTA ESTO
                if (file.createNewFile()) {
                    Log.d("MIAPP", "FICHERO CREADO");
                } else {
                    Log.d("MIAPP", "FICHERO NO CREADO");
                }
            } catch (IOException e) { // Y SI FALLA SE METE POR AQUÍ
                Log.e("MIAPP", "Error al crear el fichero", e);
            }
            photo_uri = Uri.fromFile(file);
            // estas las voy a dejar sin valor
            record = getIntent().getIntExtra("record", 0);
            numerodeCajas = getIntent().getIntExtra("recordporcajas", 1);
            otrojuego = getIntent().getIntExtra("otrojuego", 0);
        }
        arrayData.add(0, jugadoractual);  // almaceno el primer dato
        arrayData.add(1, "relleno");
        arrayData.add(2, "relleno");
        arrayData.add(3, "no"); // No -Por defecto este jugador no tiene el record
        Log.i("MIAPP", "SplitViewCopia-onCreate-jugadorActual es: " + jugadoractual);

    }
// **************************************************************************************
// para añadir el APP Bar
// **************************************************************************************
    //  DIBUJO EL MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("MIAPP","onCreateOptionsMenu");
        myMenu = menu;
        getMenuInflater().inflate(R.menu.menu1, menu);
        MenuItem timerItem = menu.findItem(R.id.break_timer);
        MenuItem timerItem1 = menu.findItem(R.id.juego);
        jugarahoraItem1 = menu.findItem(R.id.jugarahora);
        jugarahoraItem1.setVisible(false);
        resultadosItem1 = menu.findItem(R.id.resultados);
        resultadosItem1.setVisible(false);
        timerItem.setVisible(true);
        timerText = (TextView) MenuItemCompat.getActionView(timerItem);
        timerText.setText("Presiona sobre la pantalla");
        timerText = (TextView) MenuItemCompat.getActionView(timerItem);
        timerJuego = (TextView) MenuItemCompat.getActionView(timerItem1);
        timerText.setPadding(10, 0, 10, 0); //Or something like that...
        timerJuego.setPadding(1, 0, 10, 0);
        startTimer(300000, 1000); //One tick every second for 30 seconds
        Log.i("MIAPP", "SplitViewCopia-onCreateOptionsMenu es timer : " + timer);
        return super.onCreateOptionsMenu(menu);
    }


    private void startTimer(long duration, final long interval) {
        CountDownTimer timer = new CountDownTimer(duration, interval) {

            @Override
            public void onFinish() {

            }

            @Override
            public void onTick(long millisecondsLeft) {
                if (empiezajuego) {

                    segundos = segundos + 1;
                    if (segundos == 60) {
                        segundos = 0;
                        minutos = minutos + 1;
                    }
                    int secondsLeft = (int) Math.round((millisecondsLeft / (double) 1000));
                    Log.i("timer", "secondsLeft es : " + secondsLeft);
                    String p = String.valueOf(secondsLeft);
                    String p1 = String.valueOf(segundos);
                    String p2 = String.valueOf(minutos);
                    if (segundos < 10) {
                        p1 = "0" + p1;
                    }
                    if (minutos < 10) {
                        p2 = "0" + p2;
                    }
                    //timerText.setText(secondsToString(secondsLeft));
                    timerText.setText("-> " + p2 + ":" + p1);
                    timerJuego.setText(jugadoractual + ": cajas_" + numerodeCajas);
                } else {
                    // no hace nada aun no ha dado a jugar
                }
            }
        };

        timer.start();
        segundos = 0;
    }



    public static void setVar1(int val){
        var1=val;
        Log.i("traceo","SplitViewCopia-setVar1-var1 is set to :"+var1);
    }


    @Override
    protected void onResume() {
        Log.i("MIAPP", "onResume");

        Log.i("traceo","SplitViewCopia-onResume-lo que vale continuarpor es : "+continuarpor);
            // CUANDO VUELVE DESDE VisualizacionRecord la variable vale 1
        if (continuarpor == 1) {
  //          resultadosItem = findViewById(R.id.resultados);
  //          resultadosItem.setVisibility(View.VISIBLE);
            jugarahoraItem1 = myMenu.findItem(R.id.jugarahora);
            resultadosItem1 = myMenu.findItem(R.id.resultados);

            resultadosItem1.setVisible(false);
            jugarahoraItem1.setVisible(false);
            Reiniciar(vista);


        } else {
            if (continuarpor == 2){
                Log.i("traceo","SplitViewCopia-onResume- lo que vale var1 al leerla es: "+ var1);
                if (var1 == 2){ //2// se ha pulsado ver resultados
                    verresultados();
                }else {// 1// se ha pulsado jugar otra vez
                    jugarahoraItem1 = myMenu.findItem(R.id.jugarahora);
                    resultadosItem1 = myMenu.findItem(R.id.resultados);
                    resultadosItem1.setVisible(false);
                    jugarahoraItem1.setVisible(false);
                    Reiniciar(vista);
                }
            }
        }
        //invalidateOptionsMenu();
        super.onResume();
    }


public void verresultados(){
    Intent intentresultados = new Intent(SplitViewCopia.this, Resultados.class);
    intentresultados.putStringArrayListExtra("resultados", arrayDataLog);
    intentresultados.putExtra("nombre", jugadoractual);
    intentresultados.putExtra("sinAvatar", sinAvatar);
    intentresultados.putExtra("nivel",nivel);
    intentresultados.putExtra("rotadevuelta",rotada);
    intentresultados.putExtra("originaluri",original_photo_galery_uri);
    startActivity(intentresultados);
    finish();
}
    // RECIBE EL EVENTO DEL MENU ELEGIDO
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        Log.i("MIAPP","onOptionsItemSelected");

        switch (item.getItemId()) {
            case R.id.resultados:
                // mostrar en un nuevo layout el resultado
                //this.finish();
                verresultados();
                break;
            case R.id.jugarahora:
                // Ahora tiene que coger la pantalla actual con el numero de cajas que contenga
                // y a partir de aqui se puede jugar a rellenarlas de color negro.
                // si se pulsa 2 veces se pone de color blanco
                // El objetivo es rellenar todas las casillas de negro y mostrara si hay record o no.
                jugarahora = true;
     //           resultadosItem1 = myMenu.findItem(R.id.resultados);
     //           resultadosItem1.setVisible(true);
     //           jugarahoraItem1 = myMenu.findItem(R.id.jugarahora);
     //           jugarahoraItem1.setVisible(false);

                //quitar onclick de todos las cajas
                // recorrer y crear nuevo onclick,,con el nuevo link al metodo
                // empiezo a recorrer desde el primero .--llamado root
                vista = findViewById(R.id.root);
                recorrerLayouts(vista);
                inicio = true;
                arrayData.remove(2);
                arrayData.add(2, (Integer.toString(numerodeCajas)));
                continuarpor = 2;
                leerRecordporJuego();
                cargardatosIntent(continuarpor);
         //       iniciar();
                //marcarColor(vista);
                break;
            default:
                Log.i("MIAPP", "onOptionsItemSelected-Raro...raroooooo");
        }
        return super.onOptionsItemSelected(item);
    }



    private int randomColor() {
        int randomColor1;
        Random rand = new Random();
        int r1 = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        randomColor1 = Color.rgb(r1, g, b);
        return randomColor1;

    }

    public void recorrerLayouts(View vista) {
        // Log.d(getClass().getCanonicalName(), vista.getClass().getCanonicalName());
        if (vista instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) vista;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View vistahija = viewGroup.getChildAt(i);
                vistahija.setClickable(false);
                vistahija.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        marcarColor(v);
                    }
                });
                recorrerLayouts(vistahija);
            }
        }
    }

    public void recorrerLayoutsycolorear(View vista) {
        // Log.d(getClass().getCanonicalName(), vista.getClass().getCanonicalName());
        if (vista instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) vista;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View vistahija = viewGroup.getChildAt(i);
                vistahija.setBackgroundColor(randomColor());
                vistahija.setClickable(false);
                vistahija.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        marcarColor(v);
                    }
                });
                recorrerLayoutsycolorear(vistahija);

            }
        }
    }


    public void dividir(View view) {
        if (!jugarahora) {
            jugarahoraItem1 = myMenu.findItem(R.id.jugarahora);
            resultadosItem1 = myMenu.findItem(R.id.resultados);
            resultadosItem1.setVisible(false);
            jugarahoraItem1.setVisible(true);
            // LA FORMULA ES: (CONTADOR -1)+cajasportoque
            numerodeCajas = (numerodeCajas - 1) + cajasportoque;
            Log.i("MIAPP", "SplitViewCopia-dividir()-Contador de cuadros  es: " + numerodeCajas);
            limite(contador1);
            contador1 = contador1 + 1;
            LinearLayout padre = (LinearLayout) view;
            for (int i = 0; i < cajasportoque; i++) {   //  numero de cuadros por toque
                LinearLayout hijo = crearHijo(padre.getOrientation());
                padre.addView(hijo);
            }
        } else {

            int id = view.getId();
            Log.i("MIAPP", "SplitViewCopia-dividir - getId seleccionado es: " + id);
            inicio = true;
            marcarColor(view);
        }
    }


    public void marcarColor(View view) {
        // Si ya se ha pulsado el Boton Inicio entonces --true
        // eso quiere decir que empezamos a poder cambiar cajas de colores
        //id = view.findViewById(R.id.bloque1);
        LinearLayout x = (LinearLayout) view;
        Log.i("MIAPP", "SplitViewCopia--marcarColor(view) -inicio es : " + inicio);
        if (inicio) {

            // NIVEL DIFICIL o MUY DIFICIL
            int nivelnumero = Integer.parseInt(nivel);
            if (nivelnumero == 3){
                randNum = rand.nextInt(3) + 0; //MUY DIFICIL
            }else {
                if (nivelnumero == 2){
                    randNum = rand.nextInt(2); // DIFICIL
                }else {
                    randNum = 0;    // FACIL
                }
            }
            Log.i("MIAPP","randNum es: "+randNum);
            if (ids.size() == 0) {

                if (randNum == 0) { // Cambiar a NEGRO
                    ids.add(x.getId());
                    num_veces = num_veces + 1;
                    Log.i("MIAPP","randNum (num_veces) es: "+num_veces);
                    bloqueclick = false;    // poner en negro
                }// ha cambiado a color randon
                else {
                    bloqueclick = true;
                }
            } else {
                if (randNum == 0) {
                    int temporal = num_veces;
                    for (int i = 0; i <= ids.size() - 1; i++) {
                        if (ids.get(i) == x.getId()) {
                            num_veces = num_veces - 1;
                            Log.i("MIAPP", "randNum (num_veces) es: " + num_veces);
                            ids.remove(i);
                            bloqueclick = true;  // poner en blanco
                            break;
                        }
                    }
                    if (temporal == num_veces) {
                        ids.add(x.getId());
                        num_veces = num_veces + 1;
                        Log.i("MIAPP", "randNum (num_veces) es: " + num_veces);
                        bloqueclick = false;    // poner en negro
                    } else {
                        //no hay que hacer nada ya estaba y se ha quitado
                    }
                }
            }
            Log.i("MIAPP", "SplitViewCopia--marcarColor(view)-array momentaneo ids es: " + ids);
            Log.i("MIAPP", "SplitViewCopia--marcarColor(view)-el linear pulsado es " + x.getId());
            if (!bloqueclick) {
                //view.setBackgroundColor(Color.red(100000));
                view.setBackgroundColor(getResources().getColor(R.color.negro));
                bloqueclick = true;
                Log.i("MIAPP", "SplitViewCopia--marcarColor(view)-numero de veces " + num_veces);
                if (num_veces == numerodeCajas) {
                    // ahora tiene que preguntar si quieres jugar otra vez y si debe
                    // mostrarte el tiempo actual y el record ( si hay nuevo lo indicara)
                    otraVez();
                }
            } else {
                // NIVEL DIFICIL
                view.setBackgroundColor(randomColor());
                //view.setBackgroundColor(getResources().getColor(R.color.blanco));
                if (nivelnumero == 1){
                    bloqueclick = false;    // FACIL
                }
                Log.i("MIAPP", "SplitViewCopia--marcarColor(view)-numero de veces " + num_veces);
            }

        } else {
            // No hay que hacer nada aun . Esperar a que el boton INICIAR se presione
        }
        //view.setOnClickListener(new View.OnClickListener() {public void onClick(View v) { marcarColor(v); }});
    }


    private LinearLayout crearHijo(int orientacion) {
        LinearLayout nueva_caja = null;
        LinearLayout.LayoutParams parametros = null;
        nueva_caja = new LinearLayout(this);
        nueva_caja.setId(newId());
        int id_creado = nueva_caja.getId();
        Log.i("MIAPP", "SplitViewCopia -crearHijo- , getid creado es :" + id_creado);

        if (orientacion == LinearLayout.VERTICAL) {
            nueva_caja.setOrientation(LinearLayout.HORIZONTAL);
            parametros = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1F);
        } else //padre en horizontal
        {
            nueva_caja.setOrientation(LinearLayout.VERTICAL);
            parametros = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1F);
        }
        nueva_caja.setLayoutParams(parametros);
        nueva_caja.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dividir(v);
            }
        });
        nueva_caja.setBackgroundColor(randomColor());
        nueva_caja.setVisibility(View.VISIBLE);

        return nueva_caja;
    }


    public void limite(int contador) {
        Log.i("MIAPP","contador1 es : "+contador1);
        Log.i("MIAPP","el contador maximo toques es : "+contardorToques);
        if (this.contador1 > contardorToques) {
            Toast.makeText(this, "HAS ALCANZADO EL NUMERO MAXIMO DE TOQUES ***EMPIEZA A JUGAR*** ", Toast.LENGTH_LONG).show();
            jugarahora = true;
            //quitar onclick de todos las cajas
            // recorrer y crear nuevo onclick,,con el nuevo link al metodo
            // empiezo a recorrer desde el primero .--llamado root
            vista = findViewById(R.id.root);
            recorrerLayouts(vista);
            inicio = true;
            arrayData.remove(2);
            arrayData.add(2, (Integer.toString(numerodeCajas)));
            continuarpor = 2;
            leerRecordporJuego();
            cargardatosIntent(continuarpor);
        }
    }


    public void onClick(LinearLayout padre, LinearLayout hijo1, LinearLayout hijo2) {
        padre.addView(hijo1);
        padre.addView(hijo2);
        hijo1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dividir(v);
            }
        });
        hijo2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dividir(v);
            }
        });
    }


    private int newId() {
        int resultado = -1;
        do {
            resultado = r.nextInt(Integer.MAX_VALUE);
        } while (findViewById(resultado) != null);
        return resultado;
    }

    public void Reiniciar(View view) {
        vista = findViewById(R.id.root);
        texto = findViewById(R.id.txtpausar);
        boton = findViewById(R.id.btnContinuar);
        MenuItem timerItem = myMenu.findItem(R.id.break_timer);
        timerItem.setVisible(true);
        // en caso de reniciar la partida y volver a jugar el boton tiene escrito:
        //   --Para volver a jugar : "OTRA PARTIDA?
        //if (texto.getText().toString().contains("Otra Partida")) {
        Log.i("MIAPP", "SplitCopia-Reiniciar-otrojuego: " + otrojuego);
        if (otrojuego == 1) {
            resultadosItem = findViewById(R.id.resultados);
            jugarahoraItem = findViewById(R.id.jugarahora);
//            resultadosItem.setVisibility(View.INVISIBLE);

            //MenuItem jugarItem = myMenu.findItem(R.id.resultados);
            //jugarItem.setVisible(false);
            // tenemos que empezar otra partida con los mismos cuadros

            jugarahora = true;
            //quitar onclick de todos las cajas
            // recorrer y crear nuevo onclick,,con el nuevo link al metodo
            // empiezo a recorrer desde el primero .--llamado root
            vista = findViewById(R.id.root);
            Log.i("MIAPP", "SplitViewCopia-Reiniciar-antes llamar a recorrerLayoutycolorear");
            recorrerLayoutsycolorear(vista);
            inicio = true;
            empiezajuego = true;

            resultadosItem = findViewById(R.id.resultados);
            jugarahoraItem = findViewById(R.id.jugarahora);
//            resultadosItem.setVisibility(View.INVISIBLE);
//            jugarahoraItem.setVisibility(View.INVISIBLE);
            t1Start = System.currentTimeMillis();
            t1Duraciont1Startt1Pause = 0;
            vista = findViewById(R.id.root);
            texto = findViewById(R.id.txtpausar);
            boton = findViewById(R.id.btnContinuar);
            nombrejugador = findViewById(R.id.editnombrejugador);
            nombrejugador.setVisibility(View.INVISIBLE);
            texto.setText("¿ Listo para empezar el Juego ?");
            boton.setText("Iniciar Juego");
            vista.setVisibility(View.VISIBLE);
            texto.setVisibility(View.INVISIBLE);
            boton.setVisibility(View.INVISIBLE);
            ids.clear();
            num_veces = 0;
        } else {
            // continuamos con el procedimiento normal
            // hay que poner el procedimiento de poner invisible text y boton
            // despues se retoma la activity y ya se puede continuar jugando
            // ademas hay que restablecer los contadores de tiempo
            t1Start = System.currentTimeMillis();
            empiezajuego = true;
            resultadosItem = findViewById(R.id.resultados);
            jugarahoraItem = findViewById(R.id.jugarahora);
//            resultadosItem.setVisibility(View.INVISIBLE);
            //         jugarahoraItem.setVisibility(View.INVISIBLE);

            //     contadortiempo();
            Log.i("MIAPP", "SplitViewCopia-Reiniciar- se ha llamadao a contador tiempo");
            vista.setVisibility(View.VISIBLE);
            texto.setVisibility(View.INVISIBLE);
            boton.setVisibility(View.INVISIBLE);
        }
    }

    public void otraVez() {
        // hay que poner el procedimiento de poner invisible text y boton
        // despues se retoma la activity y ya se puede continuar jugando
        // ademas hay que restablecer los contadores de tiempo
        otrojuego = 1;
        empiezajuego = false;
        resultadosItem = findViewById(R.id.resultados);
        jugarahoraItem = findViewById(R.id.jugarahora);
        resultadosItem1 = myMenu.findItem(R.id.resultados);
        resultadosItem1.setVisible(false);
//        resultadosItem.setVisibility(View.VISIBLE);
        //       jugarahoraItem.setVisibility(View.VISIBLE);
        segundos = 0;
        minutos = 0;
        t1Pause = System.currentTimeMillis();
        record = 0;
        if (t1Duraciont1Startt1Pause == 0) {
            t1Duraciont1Startt1Pause = (t1Pause - t1Start) / 1000d;  // en segundos

        } else {
            t1Duraciont1Startt1Pause = ((t1Pause - t1Start) / 1000d) + t1Duraciont1Startt1Pause;
        }
        Log.i("MIAPP", "SplitViewCopia-otraVez()-tiempo de juego : " + t1Duraciont1Startt1Pause);

        //  CALCULA SI HAY O NO RECORD -devuelve "record" 1 si 0 no
        //  TAMBIEN BUSCA PARTIDA EN BASE DATOS
        //  -Si es la primera partida para este juego lo inserto en PARTIDA y PARTIDARECORD
        //  -Si no es la primera partida y tiene record inserto en PARTIDA y PARTIDARECORD
        //  -Si no es la primera partida y no tiene record solo la inserto en PARTIDA
        comprobarSihayRecord(numerodeCajas, jugadoractual, t1Duraciont1Startt1Pause, sinAvatar);
       arrayData.remove(3);
        arrayData.add(3, "no"); // no -indica que este jugador no tiene el record
        if (record == 1) {
            //jugadorRecord = jugadoractual;
            jugadorRecord = jugadoractual;
            avatatarrecord = sinAvatar;
        }
        mostrarRecord(t1Duraciont1Startt1Pause, record, numerodeCajas);
    }

    public void setearImagenDesdeArchivo(int resultado, Uri photo_uri, Puntuacion id) {
        switch (resultado) {
            case RESULT_OK:
                Log.i("MIAPP", "La foto ha sido seleccionada");
                // photo_uri = data.getData();//obtenemos la uri de la foto seleccionada
                Log.i("MIAPP", "SplitViewCopia-setearImagenDesdeArchivo-photo_uri.getPath es : " + photo_uri.getPath());
                Log.i("MIAPP", "SplitViewCopia-setearImagenDesdeArchivo-getID es : " + id);
                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(photo_uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                selectedImage = Utilidades.getResizedBitmap(selectedImage, 400);// 400 is for example, replace with desired size

                //this.avatarJugador.setImageBitmap(selectedImage);


                // De paso guardamos los datos del jugador (Nickname, id, victorias en el Shared Preferences)

                // Guardamos una copia del archivo en el dispositivo para utilizarlo mas tarde
                nombreArchivo = id.getNombre().toString() + id.getJuego() + id.getTiempo();

                saveImage(selectedImage,nombreArchivo);
      //          saveImagetoExternalmemory(selectedImage);

        //        Utilidades.guardarImagenMemoriaInterna(getApplicationContext(), nombreArchivo, Utilidades.bitmapToArrayBytes(selectedImage));

                break;

            case RESULT_CANCELED:
                Log.i("MIAPP", "La foto NO ha sido seleccionada canceló");
                break;
        }
    }



    public void saveImage(Bitmap finalBitmap, String nombreArchivo){
        //String root = Environment.getExternalStorageDirectory().toString();

        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + "/" + nombreArchivo+".JPEG";
        Log.i("MIAPP","SplitViewCopia-saveImage root es :"+root);
        //File myDir = new File(root + "/Movies/");
        //String fname = "Image-" +".jpg";
        //File file = new File (myDir, fname);
        File file = new File(root);
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveImagetoExternalmemory(Bitmap image) {

        String PREFIJO_FOTOS = nombreArchivo;
        String SUFIJO_FOTOS = ".JPEG";
        // Creamos un nombre de fichero para guardar la foto
        String nombre_fichero = PREFIJO_FOTOS + SUFIJO_FOTOS;
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + "/" + nombre_fichero;

 //       String root =nombreArchivo+".JPEG";
 //       String root = Utilidades.crearNombreArchivo();
        //String root = Environment.getExternalStorageDirectory().toString();
        //File file = new File(root + "/Pictures/COLORES_PIC.jpg");
        Log.i("MIAPP","MainActivity-saveImagetoExternal path es : "+root);

        File file = new File(root);
        //   if (file.exists())
        //       file.delete();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            // Use the compress method on the BitMap object to write image to the OutputStream
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void leerRecordporJuego(){
        dato1 = jugadoractual;
        dato2 = String.valueOf(t1Duraciont1Startt1Pause);
        dato3 = String.valueOf(numerodeCajas);
        dato4 = "no";
        dato5 = sinAvatar;
        dato6 = String.valueOf(rotada);  // rotada
        dato7 = nivel;  // nivel
        Puntuacion nuevojuego = new Puntuacion();
        nuevojuego.setNombre(dato1);
        nuevojuego.setTiempo(dato2);
        nuevojuego.setJuego(dato3);
        nuevojuego.setRecord(dato4);
        nuevojuego.setSinAvatar(dato5);
        nuevojuego.setRotada(dato6);
        nuevojuego.setNivel(dato7);
        //creo el objeto de la base de datos
        BaseDatosPuntuaciones baseDatosPuntuaciones = new BaseDatosPuntuaciones(this, "MiDB", null, 1);
        // BUSCO RECORD ACTUAL
        Puntuacion puntuacion = baseDatosPuntuaciones.buscarBuscarRecordporJuego(nuevojuego,nivel);
        if (puntuacion != null) {
            jugadorRecord = puntuacion.getNombre().toString();
            recordjuego = Double.parseDouble(puntuacion.getTiempo().toString());
            imagenrecordantiguo = puntuacion.getNombre().toString()+puntuacion.getJuego().toString()+puntuacion.getTiempo().toString();
            rotadarecord = puntuacion.getRotada().toString();
            avatatarrecord = puntuacion.getSinAvatar();
        }

    }

    private void comprobarSihayRecord(int numerodeCajas, String jugadoractual, double t1Duraciont1Startt1Pause, String sinAvatar) {
        dato1 = jugadoractual;
        dato2 = String.valueOf(t1Duraciont1Startt1Pause);
        dato3 = String.valueOf(numerodeCajas);
        dato4 = "no";
        dato5 = sinAvatar;
        dato6 = String.valueOf(rotada);  // rotada
        dato7 = nivel;  // nivel
        Puntuacion nuevojuego = new Puntuacion();
        nuevojuego.setNombre(dato1);
        nuevojuego.setTiempo(dato2);
        nuevojuego.setJuego(dato3);
        nuevojuego.setRecord(dato4);
        nuevojuego.setSinAvatar(dato5);
        nuevojuego.setRotada(dato6);
        nuevojuego.setNivel(dato7);
        //creo el objeto de la base de datos
        BaseDatosPuntuaciones baseDatosPuntuaciones = new BaseDatosPuntuaciones(this, "MiDB", null, 1);
        // BUSCO RECORD ACTUAL
        Puntuacion puntuacion = baseDatosPuntuaciones.buscarBuscarRecordporJuego(nuevojuego,nivel);
        if (puntuacion != null) {
            jugadorRecord = puntuacion.getNombre().toString();
            recordjuego = Double.parseDouble(puntuacion.getTiempo().toString());
            imagenrecordantiguo = puntuacion.getNombre().toString()+puntuacion.getJuego().toString()+puntuacion.getTiempo().toString();
            rotadarecord = puntuacion.getRotada().toString();
            avatatarrecord = puntuacion.getSinAvatar();
        }


        //nombre,tiempo,juego,record
        //BUSCO PARTIDA EN BASE DATOS
        //  -Si es la primera partida para este juego lo inserto en PARTIDA y PARTIDARECORD
        //  -Si no es la primera partida y tiene record inserto en PARTIDA y PARTIDARECORD
        //  -Si no es la primera partida y no tiene record solo la inserto en PARTIDA
        baseDatosPuntuaciones.buscarJuego1(nuevojuego,nivel);
        // COMPROBAR SI ULTIMA PARTIDA HA SIDO INDICADA CON RECORD O NO
        Puntuacion p = baseDatosPuntuaciones.buscarUltimaPartidaGuardada();
        if (sinAvatar.contains("true")) {
        } else {
            setearImagenDesdeArchivo(RESULT_OK, photo_uri, p);
        }
        if (p.getRecord().toString().contains("yes")) {
            record = 1;
            recordjuego = Double.parseDouble(p.getTiempo().toString());
        }


        Log.i("MIAPP", "SplitViewCopia-Ultima partida guardada-Juego en DB es : " + p.getJuego());
        Log.i("MIAPP", "SplitViewCopia-Ultima partida guardada-Nombre en DB es : " + p.getNombre());
        Log.i("MIAPP", "SplitViewCopia-Ultima partida guardada-Tiempo en DB es : " + p.getTiempo());
        Log.i("MIAPP", "SplitViewCopia-Ultima partida guardada-Id en DB es : " + p.getId());
        Log.i("MIAPP", "SplitViewCopia-Ultima partida guardada-Record en DB es : " + p.getRecord());
        // baseDatosPuntuaciones.insertRecord(nuevojuego);

        //CONSULTO LA TABLA DE RECORD Y OBTENGO LA LISTA JUEGOS - RECORD
        ArrayList<Puntuacion> listajuegos = new ArrayList<>();

        listajuegos = (ArrayList<Puntuacion>) baseDatosPuntuaciones.listaconrecord(nivel);

    }


    private void mostrarRecord(double t1Duraciont1Startt1Pause, int record, int numerodeCajas) {
        //***************************************************//
        //Actualizo texto a ver en layout y preparo locucion //
        //***************************************************//
        String recordString = "no";
        if (record == 1) {
            // nuevo record
            sonido(record);
            Texto1 = String.format("Nuevo Record : %1$.3f", recordjuego);
            recordString = "yes";
        } else {
            sonido(record);
            Texto1 = String.format("Record actual : %1$.3f", recordjuego);
        }
        //*****************************************************************//
        //Convierto tiempo de Float a string para poderlo guardar en array //
        //*****************************************************************//
        String tiempoActualString = Float.toString((float) t1Duraciont1Startt1Pause);
        String numerocajasString = Integer.toString(numerodeCajas);
        //******************************//
        //Actualizo array con el tiempo //
        //******************************//
        arrayData.remove(1);
        arrayData.add(1, tiempoActualString); // Se Guarda el tiempo actual hecho
        // se pasan los datos de la partida actual al array historico de partidas
        // arrayDataLog <---arrayData(0-3)
        // como si fuera un fichero .csv separados por ,
        for (int i = 0; i < arrayData.size(); i = i + 4) {
            dato1 = arrayData.get(i);
            dato2 = arrayData.get(i + 1);
            dato3 = arrayData.get(i + 2);
            dato4 = arrayData.get(i + 3);
            arrayDataLog.add(dato1 + "," + dato2 + "," + dato3 + "," + dato4 + "\n");
        }
        Log.i("MIAPP", "SplitViewCopia-mostrarRecord()-arrayData es :" + arrayData);
        Log.i("MIAPP", "SplitViewCopia-mostrarRecord()-arrayDataLog es :" + arrayDataLog);
        //*************************************//
        // Guardar partida en clase Puntuacion //
        //*************************************//
        // PARA ASIGNAR id - AL CERRAR LA APP Y VOLVERLA A CREAR EL id empezaria desde 0
        // y ese valor se volveria a repetir dando error .
        // hacer leer el ultimo id usado en la tabla
        // primero ver si la tabla ya esta definida y tal caso leer el ultimo usado.
        Puntuacion.setTop(top10);
        Puntuacion nuevojuego = new Puntuacion();
        //nuevojuego.setId(id);
        nuevojuego.setNombre(dato1);
        nuevojuego.setTiempo(dato2);
        nuevojuego.setJuego(dato3);
        nuevojuego.setRecord(dato4);
        nuevojuego.setSinAvatar(dato5);
        nuevojuego.setRotada(dato6);
        nuevojuego.setNivel(dato7);
        top10.add(nuevojuego);
        //id = id + 1;
        //***************************************************//
        // Guardar record actual puntuacion1 en Preferences  //
        //***************************************************//
//        SharedPreferences pref1 = getSharedPreferences("Serializados", MODE_PRIVATE);
//        SharedPreferences.Editor editor1 = pref1.edit();
        // convertir a JSON (hay libreria externa de gooble-GSON)
        // 1-importar la libreria- ir menu/build/editlibriryandDependencies
        //                          + libreryDependecies
        //                       busco gson y descargo la de google
        // declaro objeto gson
//        Gson gson = new Gson();

        // guardo el Json como si fuera un string para poder pasarlo a las Preferences
//        String puntuacion_json1 = gson.toJson(top10);
 //       editor1.putString("valornuevo", puntuacion_json1);
 //       editor1.commit();
        //************************************************************//
        // Guardar array total con todas las partidas en Preferences  //
        //************************************************************//
//        SharedPreferences pref = getSharedPreferences("arraydatalog", MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putInt("size", arrayDataLog.size());
//        for (int i = 0; i < arrayDataLog.size(); i++)
//            editor.putString("Indice " + i, arrayDataLog.get(i));
//        editor.commit();


        // prueba DB //
        // base de datos prueba//
        //private final static String sqlCreacionTablaPartida =
        //   "CREATE TABLE PARTIDA ( id INTEGER PRIMARY KEY,nombre TEXT,tiempo TEXT,juego TEXT,record TEXT)";
        //   "CREATE TABLE PARTIDARECORD ( id INTEGER PRIMARY KEY,nombre TEXT,tiempo TEXT,juego TEXT,record TEXT)";

        //creo el objeto de la base de datos
        BaseDatosPuntuaciones baseDatosPuntuaciones = new BaseDatosPuntuaciones(this, "MiDB", null, 1);
        //nombre,tiempo,juego,record
        //BUSCO PARTIDA EN BASE DATOS
        //  -Si es la primera partida para este juego lo inserto en PARTIDA y PARTIDARECORD
        //  -Si no es la primera partida y tiene record inserto en PARTIDA y PARTIDARECORD
        //  -Si no es la primera partida y no tiene record solo la inserto en PARTIDA
        //     baseDatosPuntuaciones.buscarJuego1(nuevojuego);

        // baseDatosPuntuaciones.insertRecord(nuevojuego);

        //CONSULTO LA TABLA DE RECORD Y OBTENGO LA LISTA JUEGOS - RECORD
        ArrayList<Puntuacion> listajuegos = new ArrayList<>();

        listajuegos = (ArrayList<Puntuacion>) baseDatosPuntuaciones.listaconrecord(nivel);
        Log.i("MIAPP", "listajuegos es: " + listajuegos);


        //consulto los record
        // List<Puntuacion> listacoches = baseDatosPuntuaciones(nuevojuego);

        //*******************************************************//
        //Prepara y muestra texto con datos partida en el layout //
        //*******************************************************//
        prepararTexto();

    }

    private void cargardatosIntent(int continuarpor){
        Intent intentresultados = new Intent(SplitViewCopia.this, VisualizacionRecord.class);
        intentresultados.putExtra("continuarpor",continuarpor);
        intentresultados.putExtra("record", record);
        intentresultados.putExtra("nivel", nivel);
        intentresultados.putExtra("fotojugador", nombreArchivo);
        intentresultados.putExtra("fotorecord", imagenrecordantiguo);
        intentresultados.putExtra("jugadorrecord", jugadorRecord);
        intentresultados.putExtra("jugadoractual", jugadoractual);
        intentresultados.putExtra("tiemporecord", recordjuego);
        intentresultados.putExtra("tutiempo", t1Duraciont1Startt1Pause);
        intentresultados.putExtra("juego", numerodeCajas);
        intentresultados.putExtra("avatar", sinAvatar);
        intentresultados.putExtra("avatarrecord", avatatarrecord);
        intentresultados.putExtra("rotada", rotada);
        intentresultados.putExtra("rotadarecord", rotadarecord);
        startActivity(intentresultados);
    }

    private void prepararTexto() {
        int saltar =0;

       //ugadoractual= jugadorRecord;
      //  tiempoactual = Texto3;

        if (saltar==0) {
            continuarpor = 1;
            cargardatosIntent(continuarpor);
            continuarpor = 2;
        }else {
            nombrejugador = findViewById(R.id.editnombrejugador);
            nombrejugador.setVisibility(View.VISIBLE);
            String textonivel = "NIVEL -" +nivel;
            nombrejugador.setText(textonivel);
            Texto = "Cajas_" + this.numerodeCajas;
            // en Texto2 esta el record , sea el nuevo o el actual
            Texto2 = Texto1 + " Segundos";
            // en Texto3 esta el tiempo actual
            Texto3previo = String.format("Tiempo Actual  : %1$.3f", t1Duraciont1Startt1Pause);
            Texto3 = Texto3previo + " Segundos";
            Log.i("MIAPP", "SplitViewCopia-mostrarRecord(.)- " + Texto2);
            Log.i("MIAPP", "SplitViewCopia-mostrarREcord(.)" + Texto3);
            Texto4 = "El record lo tiene el jugador: " + jugadorRecord;
            vista = findViewById(R.id.root);
            texto = findViewById(R.id.txtpausar);
            boton = findViewById(R.id.btnContinuar);
            vista.setVisibility(View.INVISIBLE);
            resultadosItem = findViewById(R.id.resultados);
            resultadosItem.setVisibility(View.VISIBLE);
            texto.setText(Texto + "\n" + Texto2 + "\n" + Texto3 + "\n" + Texto4);
            boton.setText("Otra Partida?");
            texto.setVisibility(View.VISIBLE);
            boton.setVisibility(View.VISIBLE);
        }
    }
/*
    private void calcularRecordPorJuego(int numerodeCajas, String jugadoractual, double t1Duraciont1Startt1Pause) {
        // Comprobar si hay alguna partida guardada - por seguridad
        int size = arrayDataLog.size();
        Log.i("MIAPP", "SplitViewCopia-calcularRecordporJuego-size es :" + size);
        record = 0;
        boolean buscar = false;
        if (size > 0) {
            // leer el arraydatalog y buscar si hay alguna partida para el juego-numerodeCajas
            //for (int i=0;i<size;i++){
            for (int i = size - 1; i > -1; i--) {
                if (arrayDataLog.get(i).toString().contains("," + Integer.toString(numerodeCajas) + ",")) {
                    if (arrayDataLog.get(i).toString().contains("yes")) {
                        //....leer el tiempo guardado para este index
                        String temporaldata = arrayDataLog.get(i);
                        String[] fields = temporaldata.split(",");
                        String timerecordantiguo = fields[1].toString();
                        // to do
                        //   con el tiempoactual(t1Durationt1Startt1Pause) y el tiempo anterior se verifica si hay
                        //         nuevo record
                        //        nuevo record = yes...entonces se marca variable record = 1
                        if (t1Duraciont1Startt1Pause < Double.parseDouble(timerecordantiguo)) {
                            // nuevo record
                            record = 1;
                            recordjuego = t1Duraciont1Startt1Pause;
                            jugadorRecord = jugadoractual;
                            Log.i("MIAPP", "SplitViewCopia-CalcularRecordporJuego-recordjuego--hay es :" + recordjuego);
                            Log.i("MIAPP", "SplitViewCopia-CalcularRecordporJuego-jugadorRecord-- es :" + jugadorRecord);

                        } else {
                            // no ha superado el record
                            recordjuego = Double.parseDouble((timerecordantiguo));
                            jugadorRecord = fields[0];
                            Log.i("MIAPP", "SplitViewCopia-CalcularRecordporJuego-recordjuego--antiguo es :" + recordjuego);

                        }
                        break;
                    } else {
                        // este caso es muy raro al menos un registro tendria que tener record
                        Log.i("MIAPP", "SplitViewCopia-calcularRecordporJuego-caso raro");
                    }

                } else {
                    // continua buscando en el array
                    // si ha llegado hasta el index 0 y record es 0, entonces hay que fijarlo a 1
                    if (i == 0) {
                        if (record == 0) {
                            record = 1;
                            recordjuego = t1Duraciont1Startt1Pause;
                            String temporaldata = arrayDataLog.get(i);
                            String[] fields = temporaldata.split(",");
                            jugadorRecord = jugadoractual;
                            //jugadorRecord = fields[0].toString();
                            Log.i("MIAPP", "El caso especial-calcularRecordPorJuego" + jugadorRecord);
                        }
                    }

                }

            }


        } else {
            // no hay nada registrado..esto quiere decir que es la primera partida
            record = 1;
            jugadorRecord = jugadoractual;
            recordjuego = t1Duraciont1Startt1Pause;
        }

    }
*/
    private void sonido(int record) {

        if (record == 1) {
            mediaPlayer = MediaPlayer.create(this, R.raw.hechobien);
        } else {
            //mediaPlayer = MediaPlayer.create(this,R.raw.ohno);
            mediaPlayer = MediaPlayer.create(this, R.raw.nolohasconseguido);
        }
        mediaPlayer.setLooping(false);
        mediaPlayer.setVolume(100, 100);
        mediaPlayer.start();

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        Log.i("MIAPP","onSaveInstanceState");
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i("MIAPP","onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MIAPP","onStop");
        Log.i("MIAPP", "SplitViewCopia-onStop-recordjuego :" + recordjuego);
    }
}
