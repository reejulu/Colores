package a.bb.colores;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import static a.bb.colores.R.drawable.dora1;
import static a.bb.colores.R.drawable.dora2;
import static a.bb.colores.R.drawable.dora3;
import static a.bb.colores.R.drawable.dora4;
import static a.bb.colores.R.drawable.dora5;
import static a.bb.colores.R.drawable.dora6;


public class MainActivityprevio extends AppCompatActivity {
    Button continuar;
    EditText editText;
    String jugador = "Desconocido";
    TextView tv;
    View vista;
    private Uri photo_uri;//para almacenar la ruta de la imagen
    int CODIGO_PETICION_SELECCIONAR_FOTO = 100;
    int CODIGO_PETICION_HACER_FOTO = 200;
    private ImageView avatarJugador;
    private static final String[] PERMISOS = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int CODIGO_PETICION_PERMISOS = 15;
    Boolean sinAvatar;
    String sinAvatarString = "false";
    int rotada;
    String original_photo_galery_uriString;
    int contador = 0;
    private static Random r = new Random();
    ArrayList<Integer> lista = new ArrayList<>();
    int animUnit1;
    int x;
    CountDownTimer yourCountDownTimer;
    Boolean listahecha = false;
    int contadorpantallas = 0;
    String root;
    LinearLayout kk;
    String randomString;
    CountDownTimer avatartimer;
    CountDownTimer cambiartimertexto;
    Boolean atrapamejuego = false;
    Boolean timerempezado = false;
    long t1Start;
    long t1Pause;
    double t1Duraciont1Startt1Pause = 0;
    double record = 0;
    private static final String RECORD = "RecordPartidaAnterior";
    private static final String CLAVE = "RecordPartidaAnteriorClave";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial_colores);
        vista = findViewById(R.id.root2);
        lista.clear();
        recorrerLayoutsycolorear(vista);
        listahecha = true;
        avatarJugador = findViewById(R.id.foto);
        int i = randomColor();
        //avatarJugador.setColorFilter(i);
        // DEFINO BOTON CONTINUAR
        continuar = findViewById(R.id.continuarsi);
        editText = findViewById(R.id.jugadornombre1);
        TextView tv = (TextView) findViewById(R.id.titulo);
        tv.setText("SELECCIONA");
        //editText.setText(jugador);
        // startColor = 0(cambiar color texto) y EndColor no se usa en el metodo changeTexColor
        changeTextColor(tv, 0, 0, 100000, 1000);
        changeAvatar(avatarJugador, 1, 100000, 1000);
        //animateIt();
        //startColor 1 - cambiar solo background color y de todas las cajitas
        //       changeTextColor(editText, 1, 0, 100000, 1000);
        ActivityCompat.requestPermissions(this, PERMISOS, CODIGO_PETICION_PERMISOS);

        //RECUPERAR RECORD DE TIEMPO DE JUEGO ATRAPAME
        SharedPreferences prefs = getSharedPreferences(RECORD,MODE_PRIVATE);
        //*******************************//
        //Datos de los ficheros "Record" //
        //*******************************//
        double pepe = prefs.getFloat(CLAVE,0);
        record = pepe;
    }


    public static int randomColor() {
        int randomColor1;
        Random rand = new Random();
        int r1 = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        randomColor1 = Color.rgb(r1, g, b);
        return randomColor1;
    }

    public void changeAvatar(final ImageView avatarJugador1, final int startColor,
                             final long animDuration, final long animUnit) {
        avatarJugador = findViewById(R.id.foto);
        if (avatarJugador == null) return;
        avatartimer = new CountDownTimer(animDuration, animUnit) {
            //animDuration is the time in ms over which to run the animation
            //animUnit is the time unit in ms, update color after each animUnit
            @Override
            public void onTick(long l) {
                if (startColor == 1) {
                    // cambiar avatar automatico
                    cambiaravatar();
                    //   avatarJugador.setImageResource(stringint);
                } else {
                    ;
                }
            }

            @Override
            public void onFinish() {
                cambiaravatar();
            }
        }.start();
    }

    public void cambiaravatar() {
        String[] myArray = {"dora1", "dora2", "dora3", "dora4", "dora5", "dora6"};
        randomString = myArray[new Random().nextInt(myArray.length)];
        if (!atrapamejuego) {
            traduciravatar(0);
        } else {
            // de esta forma solo he cambiado el randomString
        }
    }

    public void traduciravatar(int i) {
        if (i == 0) {
            switch (randomString) {
                case "dora1":
                    avatarJugador.setImageResource(R.drawable.dora1);
                    break;
                case "dora2":
                    avatarJugador.setImageResource(R.drawable.dora2);
                    break;
                case "dora3":
                    avatarJugador.setImageResource(R.drawable.dora3);
                    break;
                case "dora4":
                    avatarJugador.setImageResource(R.drawable.dora4);
                    break;
                case "dora5":
                    avatarJugador.setImageResource(R.drawable.dora5);
                case "dora6":
                    avatarJugador.setImageResource(R.drawable.dora6);
                    break;
                default:
                    avatarJugador.setImageResource(R.drawable.dora1);
                    break;
            }
        } else {
            switch (randomString) {
                case "dora1":
                    kk.setBackgroundResource(dora1);
                    break;
                case "dora2":
                    kk.setBackgroundResource(dora2);
                    break;
                case "dora3":
                    kk.setBackgroundResource(dora3);
                    break;
                case "dora4":
                    kk.setBackgroundResource(dora4);
                    break;
                case "dora5":
                    kk.setBackgroundResource(dora5);
                case "dora6":
                    kk.setBackgroundResource(dora6);
                    break;
                default:
                    kk.setBackgroundResource(dora1);
                    break;
            }
        }
    }

    public void changeTextColor(final TextView tv, final int startColor1, final int endColor,
                                final long animDuration, final long animUnit) {
        if (tv == null) return;

        cambiartimertexto = new CountDownTimer(animDuration, animUnit) {
            //animDuration is the time in ms over which to run the animation
            //animUnit is the time unit in ms, update color after each animUnit
            @Override
            public void onTick(long l) {
                Log.i("traceillo1", "startColor1 vale : " + startColor1);
                if (startColor1 == 2) {
                    tv.setText("ATRAPAME Y COMPLETA LA PANTALLA");
                    if (contador == 0) {
                        tv.setText("ATRAPAME Y");
                        tv.setTextColor(Color.BLACK);
                        tv.setTextSize(1, 35);
                        contador = 1;
                    } else {
                        tv.setText("COMPLETA PANTALLA");
                        tv.setTextColor(Color.BLACK);
                        tv.setTextSize(1, 25);
                        contador = 0;
                    }
                } else {
                    if (startColor1 == 1) {
                        // cambiar color fondo
                        tv.setBackgroundColor(randomColor());
                        vista = findViewById(R.id.root2);
                        SplitViewCopia splitViewCopia = new SplitViewCopia();
                        splitViewCopia.recorrerLayoutsycolorear(vista);
                    } else {
                        if (contador == 0) {
                            tv.setText("SELECCIONA Y");
                            contador = 1;
                        } else {
                            tv.setText("ENCUENTRAME");
                            tv.setTextSize(1, 35);
                            contador = 0;
                        }

                        tv.setTextColor(randomColor());
                    }
                }
            }

            @Override
            public void onFinish() {
                tv.setText("COLOR");
                tv.setTextColor(randomColor());
            }
        }.start();
    }

    public void changeTextColor1(final View vista, final int startColor, final int endColor,
                                 final long animDuration, final long animUnit) {
        if (vista == null) return;

        yourCountDownTimer = new CountDownTimer(animDuration, animUnit) {
            //animDuration is the time in ms over which to run the animation
            //animUnit is the time unit in ms, update color after each animUnit
            @Override
            public void onTick(long l) {
                //tv.setText("COLORES");
                if (startColor == 1) {
                    //RECORRE lista-array(son todos los cajitas) y las pone de color variado
                    // vista realmente es el array - llamamos al metodo con vista pero no se usa
                    // en el onClick queda abierta la posibilidad de jugarAtrapame y jugar a cajitas
                    // -Cuando jugar Atrapame : cada vez que se marque una cajita el dibujito se quedara fijo
                    // -Cuando jugar a cajitas: al pinchar por primera vez un dibujito se saltara a jugar.
                    recorrerLayoutsycolorear1(vista);
                    // añado una cajita aleatoria  con una imagen (deberia ser aleatoria-pendiente)
                    Random r = new Random();
                    Log.i("traceillo2", "lista size es : " + lista.size());
                    if (lista.size() > 0) {
                        x = lista.get(r.nextInt(lista.size()));
                        kk = findViewById(x);
                        kk.setTag("si");
                    }
                    // COMPROBAMOS SI HAY IMAGEN O AVATAR
                    //   kk.setBackgroundResource(picture);
                    if (!sinAvatar) {
                        String nombreArchivo = "COLORES_PIC";
                        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + "/" + nombreArchivo + ".JPEG";
                        Bitmap bitmap = BitmapFactory.decodeFile(root);
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
                        kk.setBackground(bitmapDrawable);
                        Uri urifile;
                        File file1 = new File(root);
                        urifile = Uri.fromFile(file1);
                        // UNA VEZ GUARDADO . VEO COMO SE HA GUARDADO PARA ESO VEO EL uri anterior
                        rotada = getCameraPhotoOrientation(getBaseContext(), urifile, urifile.getPath());
                        kk.setRotation(rotada);
                    } else {
                        traduciravatar(1);
                    }
                    //esto fue para pruebas, solo mostrar 1 o 2 veces la panatalla animacion inicio
                    contadorpantallas = contadorpantallas + 1;
                    //       if (contadorpantallas == 2){
                    //           yourCountDownTimer.cancel();
                    //       }

                    //EN ESTE PUNTO TENGO LA PANTALLA COLOREADA Y UNA CASILLA CON LA IMAGEN
                    Log.i("traceillo", "el id seleccionado es :" + x);
                    Log.i("traceillo", "el id seleccionado es kk.getId :" + kk.getId());

                }
                if (startColor == 2) {
                    //RECORRE lista-array(son todos los cajitas) y las pone de color variado
                    // vista realmente es el array - llamamos al metodo con vista pero no se usa
                    // en el onClick queda abierta la posibilidad de jugarAtrapame y jugar a cajitas
                    // -Cuando jugar Atrapame : cada vez que se marque una cajita el dibujito se quedara fijo
                    // -Cuando jugar a cajitas: al pinchar por primera vez un dibujito se saltara a jugar.
                    recorrerLayoutsycolorear1(vista);
                    // añado una cajita aleatoria  con una imagen (deberia ser aleatoria-pendiente)
                    Random r = new Random();
                    Log.i("traceillo2", "lista size es : " + lista.size());
                    if (lista.size() > 0) {
                        x = lista.get(r.nextInt(lista.size()));
                        kk = findViewById(x);
                        kk.setTag("si");
                    }
                    cambiaravatar();
                    traduciravatar(1);
                }
            }

            @Override
            public void onFinish() {
                //CUANDO EXPIRA EL TIEMPO- VUELVO A PEDIR LA GENERACION DE LA PANTALLA + LA CASILLA CON LA IMAGEN
                if (animUnit1 < 2000) {
                    animUnit1 = animUnit1 + 200;
                    Log.i("traceillo", "valor j es :" + animUnit);
                    changeTextColor1(vista, 1, 0, 10000, animUnit1);
                }
            }
        }.start();
    }

    public void recorrerLayoutslistacolorear(View vista) {
        if (vista instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) vista;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                final View vistahija = viewGroup.getChildAt(i);
                vistahija.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        String temporaltag1 = v.getTag().toString();
                        Log.i("traceillo2", "click en atrapada");
                        if (temporaltag1.contains("no")) {
                            // cambio de color
                            Log.i("traceillo2", "click en tag no");
                            vistahija.setBackgroundColor(randomColor());
                        } else {
                            Log.i("traceillo2", "click en tag si");
                            int tempid = v.getId();
                            //                 lista.remove(tempid);
                            // entonces cambio de color sino NO
                        }
                    }
                });
                recorrerLayoutslistacolorear(vistahija);
            }
        }
    }

    public void recorrerLayoutsycolorear(final View vista) {
        if (vista instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) vista;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                final View vistahija = viewGroup.getChildAt(i);
                // ALMACENO TODOS LOS HIJOS EN UN ARRAY-lista
                // solo almaceno una vez la lista en caso listahecha = false
                if (!listahecha) {
                    if (vistahija.getId() != -1) {
                        lista.add(vistahija.getId());
                    }
                }
                //       vistahija.setBackgroundResource(picture);
                vistahija.setBackgroundColor(randomColor());
                vistahija.setTag("no");
                vistahija.setClickable(true);
                vistahija.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (!atrapamejuego) {
                            String tag = v.getTag().toString();
                            Log.i("traceillo", "tag pinchado es : " + tag);
                            Log.i("traceillo", "tag pinchado es (id): " + v.getId());
                            Log.i("traceillo", "el id actual x es :" + x);
                            if (tag.contains("si")) {
                                yourCountDownTimer.cancel();
                                Log.i("traceillo", "he conseguido pillarte");
                                Log.i("traceillo", "el id pillado es v.getId :" + v.getId());
                                editText = findViewById(R.id.jugadornombre1);
                                if (editText.getText().toString().isEmpty()) {
                                    editText.setText(jugador);
                                } else {
                                    jugador = editText.getText().toString();
                                }
                                Intent intent = new Intent(MainActivityprevio.this, MainActivity.class);
                                intent.putExtra("rotadevuelta", rotada);
                                int desdeMainActivityprevio = 1;
                                intent.putExtra("desdeprevio", desdeMainActivityprevio);
                                root = Environment.getExternalStorageDirectory().toString() + "/Pictures/COLORES_PIC.JPEG";
                                intent.putExtra("fotoinicio", root);
                                sinAvatarString = "false";
                                if (sinAvatar) {
                                    sinAvatarString = randomString;// aqui esta el seleccionado
                                    //           sinAvatarString = "picture";
                                }
                                intent.putExtra("avatardesdeResultados", sinAvatarString);
                                intent.putExtra("nombre", jugador);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            for (int x = 0; x < lista.size(); x++) {
                                int idtemporal = lista.get(x);
                                //       ViewGroup viewGroup = (ViewGroup) v;
                                //               Log.i("traceillo2","viewGroup.getChildCount es : "+viewGroup.getChildCount());
                                //               for (int i = 0; i < viewGroup.getChildCount(); i++) {
                                //                   final View vistahija = viewGroup.getChildAt(i);
                                //                    Log.i("traceillo2","id-lista es: "+idtemporal);
                                //                    Log.i("traceillo2","id-vistahija es : "+viewGroup.getId());
                                if (v.getId() == idtemporal) {
                                    Log.i("traceillo2", "id encontrado " + idtemporal);
                                    v.setClickable(false);
                                    lista.remove(x);
                                }
                                //               }

                                //     vistahija.setClickable(false);
                            }
                            // en caso de atrapame juego ejecuta esta parte
                            View vista1;
                            vista1 = findViewById(R.id.root2);
                            Log.i("traceillo2", "pulsado en juego atrapa");
                            recorrerLayoutslistacolorear(vista1);
                        }
                    }
                });
                recorrerLayoutsycolorear(vistahija);
            }
        }
    }

    public void recorrerLayoutsycolorear1(View vista) {
        for (int i = 0; i < lista.size(); i++) {
            int tmpid = lista.get(i);
            vista = findViewById(tmpid);
            vista.setId(tmpid);
            vista.setClickable(true);
            vista.setBackgroundColor(randomColor());
            vista.setTag("no");

            vista.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (atrapamejuego) {
                        if (!timerempezado) {
                            //INICION TIMER DEL JUEGO -ATRAPAME
                            t1Start = System.currentTimeMillis();
                            t1Duraciont1Startt1Pause = 0;
                            timerempezado = true;
                            Toast.makeText(MainActivityprevio.this, "Empieza el tiempo", Toast.LENGTH_SHORT).show();
                            Toast.makeText(MainActivityprevio.this, "Record actual " + record + " segundos", Toast.LENGTH_SHORT).show();

                        }

                        for (int x = 0; x < lista.size(); x++) {
                            int idtemporal = lista.get(x);
                            if (v.getTag() == "si") {
                                if (v.getId() == idtemporal) {
                                    Log.i("traceillo2", "id encontrado " + idtemporal);
                                    v.setClickable(false);
                                    // v.setTag("no");
                                    lista.remove(x);
                                    if (lista.size() == 0) {
                                        Log.i("traceillo3", "hemos terminado");
                                        t1Pause = System.currentTimeMillis();
                                        t1Duraciont1Startt1Pause =((t1Pause - t1Start) / 1000d);  // en segundos

                                        // GUARDAR RECORD DE ATRAPAME EN SHAREPREFERENCE
                                        // JUEGO = "atrapame"
                                        SharedPreferences prefs = getSharedPreferences(RECORD,MODE_PRIVATE);
                                        // Entro en modo Edicion
                                        SharedPreferences.Editor editor = prefs.edit();

                                        int t1 = (int) t1Duraciont1Startt1Pause;
                                        int r = (int) record;
                                        if (r==0){
                                            record = t1Duraciont1Startt1Pause;
                                            Toast.makeText(MainActivityprevio.this, "nuevo record de " + record + " segundos", Toast.LENGTH_SHORT).show();
                                        }else {
                                            if (t1 < r) {
                                                record = t1Duraciont1Startt1Pause;
                                                Toast.makeText(MainActivityprevio.this, "nuevo record de " + record + " segundos", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(MainActivityprevio.this, "LO SIENTO NO HAS SUPERADO EL RECORD", Toast.LENGTH_SHORT).show();
                                                Toast.makeText(MainActivityprevio.this, "TU TIEMPO FUE DE " + t1Duraciont1Startt1Pause + " segundos", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        // Asigno una clave para acceder poner la informacion que quiero
                                        //   CLAVE <-----"atrapameclave"
                                        editor.putFloat(CLAVE, (float) record);
                                        editor.commit();
                                        Log.i("traceillo3", "El tiempo tardado es de : " + t1Duraciont1Startt1Pause + " segundos");
                                        Intent intent = new Intent(MainActivityprevio.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                }
                            }
                        }
                    } else {
                        // caso inial para empezar juego cajitas
                        for (int x = 0; x < lista.size(); x++) {
                            int idtemporal = lista.get(x);
                            if (v.getTag() == "si") {
                                if (v.getId() == idtemporal) {
                                    yourCountDownTimer.cancel();
                                    editText = findViewById(R.id.jugadornombre1);
                                    if (editText.getText().toString().isEmpty()) {
                                        editText.setText(jugador);
                                    } else {
                                        jugador = editText.getText().toString();
                                    }
                                    Intent intent = new Intent(MainActivityprevio.this, MainActivity.class);
                                    intent.putExtra("rotadevuelta", rotada);
                                    int desdeMainActivityprevio = 1;
                                    intent.putExtra("desdeprevio", desdeMainActivityprevio);
                                    root = Environment.getExternalStorageDirectory().toString() + "/Pictures/COLORES_PIC.JPEG";
                                    intent.putExtra("fotoinicio", root);
                                    sinAvatarString = "false";
                                    if (sinAvatar) {
                                        sinAvatarString = randomString;// aqui esta el seleccionado
                                    }
                                    intent.putExtra("avatardesdeResultados", sinAvatarString);
                                    intent.putExtra("nombre", jugador);
                                    startActivity(intent);
                                    finish();

                                }
                            }
                        }
                    }
                }

            });
        }
    }


    public void Proseguir(View view) {
        //  borra pantalla y recorre vista root2 para colorear
        avatarJugador.setVisibility(View.INVISIBLE);
        TextView tv = (TextView) findViewById(R.id.titulo);
        tv.setVisibility(View.INVISIBLE);
        Button button = findViewById(R.id.continuarsi);
        button.setVisibility(View.INVISIBLE);
        editText = findViewById(R.id.jugadornombre1);
        editText.setVisibility(View.INVISIBLE);
        vista = findViewById(R.id.root2);
        animUnit1 = 1100;

        if (sinAvatar) {
            animUnit1 = 500;
            if (atrapamejuego) {
                animUnit1 = 1500;
            }
            Log.i("traceillo", "valor j es :" + animUnit1);
            // recorre la vista -lista y con startColor = 2 hace:
            //RECORRE lista-array(son todos los cajitas) y las pone de color variado
            // (vista) realmente es el array - llamamos al metodo con vista pero no se usa
            // en el onClick queda abierta la posibilidad de jugarAtrapame y jugar a cajitas
            // -Cuando jugar Atrapame : cada vez que se marque una cajita el dibujito se quedara fijo
            // -Cuando jugar a cajitas: al pinchar por primera vez un dibujito se saltara a jugar.
            //         recorrerLayoutsycolorear1(vista);
            // añado una cajita aleatoria  con una imagen (deberia ser aleatoria-pendiente)
            changeTextColor1(vista, 2, 0, 500000, animUnit1);
        } else {
            // recorre la vista -lista y con startColor = 1 hace:
            //RECORRE lista-array(son todos los cajitas) y las pone de color variado
            // (vista) realmente es el array - llamamos al metodo con vista pero no se usa
            // en el onClick : al pinchar por primera vez un dibujito se saltara a jugar.
            //         recorrerLayoutsycolorear1(vista);
            changeTextColor1(vista, 1, 0, 100000, animUnit1);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODIGO_PETICION_PERMISOS) {
            // en 0 corresponde al primer valor del array PERMISOS -camera y 1 a write_external_storage
            if ((grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    && (grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                Log.i("MIAPP", "Me ha concedido Permisos");
            } else {
                Log.i("MIAPP", "NO ME ha concedido Permisos");
                Toast.makeText(this, "NO PUEDES SEGUIR", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        }
    }

    public void personalizarFoto(final View view) {
        avatartimer.cancel();
        PopupMenu popup = new PopupMenu(this, view);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.personaliza_avatar_menu, popup.getMenu());
        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.foto_camara:
                        tomarFoto();
                        sinAvatar = false;
                        return true;
                    case R.id.foto_galeria:
                        seleccionarFoto();
                        sinAvatar = false;
                        return true;
                    case R.id.continuar:
                        escribeNombreJugador();
                        sinAvatar = true;
                        avatarJugador = findViewById(R.id.foto);
                        traduciravatar(0);
                        //avatarJugador.setImageResource(dora1);
                        // esto hace falta si anteriormente se opto por la opcion tomar foto o desde album
                        // en algunos cosos como la imagen fue rotada el drawable.picture aparece rotado.
                        // si asignamos setRotation con el ultimo valor almacenado en rotda se solventa el problema
                        //             avatarJugador.setRotation(rotada);
                        return true;
                    case R.id.jugarAtrapame:
                        //quitar timer en cambiar texto de titulo
                        cambiartimertexto.cancel();
                        contador = 0;
                        TextView tv = (TextView) findViewById(R.id.titulo);
                        // cambiar texto y fijar timer .startColor1 = 2 y:
                        //  cuando contador = 0 -"ATRAPAME Y"
                        //  cuando contador = 0 -"COMPLETA PANTALLA"
                        changeTextColor(tv, 2, 0, 100000, 1000);
                        //  mostrar toda la pantalla con las cajitas de colores y cambiando cada timer una
                        //  casilla por un dibujo
                        //            changeAvatar(avatarJugador,1,100000,1000);
                        sinAvatar = true;
                        atrapamejuego = true;
                        vista = findViewById(R.id.root2);
                        // prepara pantalla borrando titulo e imagen
                        Proseguir(vista);
                        return true;
                }
                return true;
            }
        });
        popup.show();//showing popup menu
    }

    private void escribeNombreJugador() {
        //    setContentView(R.layout.activity_inicial_colores);
        //     ImageView i = findViewById(R.id.foto);
        //     i.setVisibility(View.VISIBLE);

        EditText editText1 = findViewById(R.id.jugadornombre1);
        editText1.setVisibility(View.VISIBLE);
        Button button = findViewById(R.id.continuarsi);
        button.setVisibility(View.VISIBLE);
    }

    public void setearImagenDesdeArchivo(int resultado, Intent data) {
        switch (resultado) {
            case RESULT_OK:
                this.photo_uri = data.getData();//obtenemos la uri de la foto seleccionada
                //  original_photo_galery_uriString = String.valueOf(photo_uri);
                Log.i("MIAPP", "path obtenido tras leer el archivo del fichero es : " + photo_uri.getPath());
                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(this.photo_uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                selectedImage = Utilidades.getResizedBitmap(selectedImage, 400);// 400 is for example, replace with desired size
                //GUARDO EL ARCHIVO EN EL LUGAR CORRECTO
                ///storage/emulated/0/DCIM/COLORES_PIC.jpg
                saveImagetoExternalmemory(selectedImage);
                this.avatarJugador.setImageBitmap(selectedImage);
                //OBTENGO EL PATH REAL Y LA URI CORRESPONDIENTE
                String verpath = getRealPathFromURI(this, photo_uri);
                Uri urifile;
                File file1 = new File(verpath);
                urifile = Uri.fromFile(file1);
                // UNA VEZ GUARDADO . VEO COMO SE HA GUARDADO PARA ESO VEO EL uri anterior
                rotada = getCameraPhotoOrientation(this, urifile, urifile.getPath());
                Log.i("MIAPP", "path real del archivo es: " + urifile.getPath());
                original_photo_galery_uriString = urifile.getPath();
                //            avatarJugador.setScaleType(ImageView.ScaleType.FIT_XY);
                avatarJugador.setRotation(rotada);
                Log.i("traceo", "MainActivity-setearImagenDesdeArchivo- rotada es " + rotada);
                // Guardamos una copia del archivo en el dispositivo para utilizarlo mas tarde
                //               Utilidades.guardarImagenMemoriaInterna(getApplicationContext(), jugador.getJugadorId(), Utilidades.bitmapToArrayBytes(selectedImage));
                escribeNombreJugador();
                break;
            case RESULT_CANCELED:
                Log.i("MIAPP", "La foto NO ha sido seleccionada canceló");
                break;
        }
    }

    public void saveImagetoExternalmemory(Bitmap image) {
        root = Utilidades.crearNombreArchivo();
        //String root = Environment.getExternalStorageDirectory().toString();
        //File file = new File(root + "/Pictures/COLORES_PIC.jpg");
        Log.i("MIAPP", "MainActivity-saveImagetoExternal path es : " + root);
        File file = new File(root);
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

    private void setearImagenDesdeCamara(int resultado, Intent intent) {
        switch (resultado) {
            case RESULT_OK:
                original_photo_galery_uriString = "false";
                Log.i("MIAPP", "Tiró la foto bien");
                Log.i("MIAPP", "setarImagenDesdeCamara-fotoalmacenadaen uri path :" + photo_uri.getPath().toString());
                // en este caso no hay que convertir la photo_uri a path real
                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(this.photo_uri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                selectedImage = Utilidades.getResizedBitmap(selectedImage, 400);// 400 is for example, replace with desired size
                this.avatarJugador.setImageBitmap(selectedImage);
                //COMPROBAR SI LA IMAGEN HA SIDO ROTADA
                rotada = getCameraPhotoOrientation(this, photo_uri, photo_uri.getPath());
                Log.i("traceo", "MainActivity-setearImagenDesdeCamara-rotada es: " + rotada);
                //   avatarJugador.setImageURI(this.photo_uri);
                //              avatarJugador.setScaleType(ImageView.ScaleType.FIT_XY);
                avatarJugador.setRotation(rotada);
                //   avatarJugador.setRotation(90);
                // Actualizamos la galería
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, this.photo_uri));
                escribeNombreJugador();
                break;
            case RESULT_CANCELED:
                Log.i("MIAPP", "Canceló la foto");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);//no llamamos al padre
        if (requestCode == CODIGO_PETICION_SELECCIONAR_FOTO) {
            setearImagenDesdeArchivo(resultCode, data);
        } else if (requestCode == CODIGO_PETICION_HACER_FOTO) {
            setearImagenDesdeCamara(resultCode, data);
        }
    }


    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    public void tomarFoto() {
        Log.i("MIAPP", "Quiere hacer una foto");
        Intent intent_foto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        this.photo_uri = Utilidades.crearFicheroImagen();
        intent_foto.putExtra(MediaStore.EXTRA_OUTPUT, this.photo_uri);
        Utilidades.desactivarModoEstricto();
        // animacionTitulo.cancel(true);
        // mediaPlayer.stop();
        startActivityForResult(intent_foto, CODIGO_PETICION_HACER_FOTO);
    }

    public void seleccionarFoto() {
        Log.i("MIAPP", "Quiere seleccionar una foto");
        Intent intent_pide_foto = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );
        intent_pide_foto.setType("image/*");//tipo mime
        startActivityForResult(intent_pide_foto, CODIGO_PETICION_SELECCIONAR_FOTO);
    }


    public static int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath) {
        //El siguiente método devuelve la orientación de una imagen en grados. Funciona con imágenes de la galería.
        // Valores devueltos para:
        //Paisaje normal: 0
        //Retrato normal: 90
        //Paisaje invertido: 180
        //Retrato al revés: 270
        //Imagen no encontrada: -1
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
            Log.i("MIAPP", "Exif orientation: " + orientation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }
};



