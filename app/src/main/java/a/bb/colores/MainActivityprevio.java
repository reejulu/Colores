package a.bb.colores;


import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import static a.bb.colores.R.drawable.picture;


public class MainActivityprevio extends AppCompatActivity {
    NumberPicker numberPicker;
    NumberPicker numberPicker1;
    Button continuar;
    EditText editText;
    String jugador = "Desconocido";
    TextView tv;
    View vista;
    private Uri photo_uri;//para almacenar la ruta de la imagen
    private String ruta_foto;//nombre fichero creado
    int CODIGO_PETICION_SELECCIONAR_FOTO = 100;
    int CODIGO_PETICION_HACER_FOTO = 200;
    private ImageView avatarJugador;
    private static final String[] PERMISOS = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int CODIGO_PETICION_PERMISOS = 15;
    Boolean sinAvatar;
    String nivel;
    String imagen="inicial";
    String juego;
    String tiempo;
    String sinAvatarString = "false";
    Bitmap bitmap;
    int rotada;
    Uri original_photo_galery_uri;
    String original_photo_galery_uriString;

    boolean inicio = false;
    Random rand = new Random();
    int randNum ;
    ArrayList<Integer> ids = new ArrayList<Integer>();
    private int num_veces;
    boolean bloqueclick;
    int numerodeCajas = 1;  // valor inicial para ver numero de cajas totales depues de cade click
    int contador = 0;
    private static Random r = new Random();
    ArrayList<Integer>  lista = new ArrayList<Integer>();
    int animUnit1;
    int idseleccionado;
    int x;
    CountDownTimer yourCountDownTimer;
    Boolean listahecha = false;
    int contadorpantallas = 0;
    String root;

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
        // GETINTENT TRAE VALORES CUANDO SE HA JUGADO Y VIENE DESDE RESULTADOS.JAVA
        if (getIntent().getExtras() != null) {
            editText.setVisibility(View.VISIBLE);
            jugador = getIntent().getStringExtra("nombre");
            editText.setText(jugador);
            sinAvatarString = getIntent().getStringExtra("avatardesdeResultados");
            original_photo_galery_uriString = getIntent().getStringExtra("originaluri");
            rotada = getIntent().getIntExtra("rotadevuelta",0);
            if (sinAvatarString.toString().contains("false")){
                loadImagefromExternalmemory();
                Button button = findViewById(R.id.continuarsi);
                button.setVisibility(View.VISIBLE);
            }else {
                // avatar_foto.setColorFilter(i);
                avatarJugador.setImageResource(picture);
            }
        }
        //editText.setText(jugador);
        // startColor = 0(cambiar color texto) y EndColor no se usa en el metodo changeTexColor
        changeTextColor(tv, 0, 0, 100000, 1000);
        //animateIt();
        //startColor 1 - cambiar solo background color y de todas las cajitas
 //       changeTextColor(editText, 1, 0, 100000, 1000);
        ActivityCompat.requestPermissions(this, PERMISOS, CODIGO_PETICION_PERMISOS);
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

    public void changeTextColor(final TextView tv, final int startColor, int endColor,
                                final long animDuration, final long animUnit) {
        if (tv == null) return;
        // final int startRed = Color.red(startColor);
        // final int startBlue = Color.blue(startColor);
        // final int startGreen = Color.green(startColor);
        // final int endRed = Color.red(endColor);
        // final int endBlue = Color.blue(endColor);
        // final int endGreen = Color.green(endColor);
        new CountDownTimer(animDuration, animUnit) {
            //animDuration is the time in ms over which to run the animation
            //animUnit is the time unit in ms, update color after each animUnit
            @Override
            public void onTick(long l) {
                //int red = (int) (endRed + (l * (startRed - endRed) / animDuration));
                //int blue = (int) (endBlue + (l * (startBlue - endBlue) / animDuration));
                //int green = (int) (endGreen + (l * (startGreen - endGreen) / animDuration));
                //tv.setText("COLORES");
                if (startColor == 1) {
                    // cambiar color fondo
                    tv.setBackgroundColor(randomColor());
                    vista = findViewById(R.id.root2);
                    SplitViewCopia splitViewCopia = new SplitViewCopia();
                    splitViewCopia.recorrerLayoutsycolorear(vista);
                } else {
                    if (contador== 0) {
                        tv.setText("SELECCIONA Y");
                        contador = 1;
                    }else {
                        tv.setText("ENCUENTRAME");
                        tv.setTextSize(1,35);
                        contador = 0;
                    }

                    tv.setTextColor(randomColor());
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
                    //            lista.clear();

                    recorrerLayoutsycolorear(vista);
                    Log.i("traceillo", "lista size es : " + lista.size());
                    //            Log.i("traceillo", "lista tiene : " + lista.toString());
                    Random r = new Random();
                    x = lista.get(r.nextInt(lista.size()));
                    LinearLayout kk = findViewById(x);
                    kk.setTag("si");
                    // COMPROBAMOS SI HAY IMAGEN O AVATAR
                    //   kk.setBackgroundResource(picture);
                    if (sinAvatar == false) {
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
                    }else {
                        kk.setBackgroundResource(picture);
                    }

                    contadorpantallas = contadorpantallas + 1;
             //       if (contadorpantallas == 2){
             //           yourCountDownTimer.cancel();
             //       }

                    //EN ESTE PUNTO TENGO LA PANTALLA COLOREADA Y UNA CASILLA CON LA IMAGEN
                    Log.i("traceillo", "el id seleccionado es :" + x);
                    Log.i("traceillo", "el id seleccionado es kk.getId :" + kk.getId());

                    //     vista.setId(x);
                    //     vista.getId();
                    //     vista.setBackgroundColor(Color.BLACK);
                    //         vista.setBackgroundResource(picture);

                } else {
                    if (contador == 0) {
                        tv.setText("SELECCIONA Y");
                        contador = 1;
                    } else {
                        tv.setText("ENCUENTRAME");
                        tv.setTextSize(1,35);
                        contador = 0;
                    }

                    tv.setTextColor(randomColor());
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

                //   tv.setText("COLOR");
                //   tv.setTextColor(randomColor());
            }
        }.start();
    }

    public void recorrerLayoutsycolorear(View vista) {
        // Log.d(getClass().getCanonicalName(), vista.getClass().getCanonicalName());
        if (vista instanceof ViewGroup) {

            ViewGroup viewGroup = (ViewGroup) vista;

            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                final View vistahija = viewGroup.getChildAt(i);
                // ALMACENO TODOS LOS HIJOS EN UN ARRAY-lista
                // solo almaceno una vez la lista en caso listahecha = false
                if (listahecha == false) {
                    if (vistahija.getId() != -1) {
                        lista.add(vistahija.getId());
                    }
                }

                //       vistahija.setBackgroundResource(picture);
                vistahija.setBackgroundColor(randomColor());
                vistahija.setTag("no");
                //    vistahija.setBackgroundColor(Color.BLUE);


                vistahija.setClickable(true);

                vistahija.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        String tag = v.getTag().toString();
                        Log.i("traceillo", "tag pinchado es : " + tag);
                        Log.i("traceillo", "tag pinchado es (id): " + v.getId());
                        Log.i("traceillo", "el id actual x es :" + x);
                        if (tag.contains("si")) {
                            yourCountDownTimer.cancel();
                            Log.i("traceillo", "he conseguido pillarte");
                            Log.i("traceillo", "el id pillado es v.getId :" + v.getId());
                            editText = findViewById(R.id.jugadornombre1);
                            if (editText.getText().toString().isEmpty()){
                                editText.setText(jugador);
                            }else {
                                jugador = editText.getText().toString();
                            }
                            Intent intent = new Intent(MainActivityprevio.this, MainActivity.class);
                            intent.putExtra("rotadevuelta",rotada);
                            int desdeMainActivityprevio = 1;
                            intent.putExtra("desdeprevio",desdeMainActivityprevio);
                            root = Environment.getExternalStorageDirectory().toString()+ "/Pictures/COLORES_PIC.JPEG";
                            intent.putExtra("fotoinicio",root);
                            sinAvatarString = "false";
                            if (sinAvatar){
                                sinAvatarString = "true";
                            }
                            intent.putExtra("avatardesdeResultados",sinAvatarString);
                            intent.putExtra("nombre",jugador);
                            startActivity(intent);
                            finish();

                        }
                        //            marcarColor(v);
                    }
                });

                //      crearimagen(x);
                recorrerLayoutsycolorear(vistahija);

            }

        }

    }


    private int newId() {
        int resultado = -1;
        do {
            resultado = r.nextInt(Integer.MAX_VALUE);
        } while (findViewById(resultado) != null);
        return resultado;
    }
    private LinearLayout crearimagen(int orientacion) {
        LinearLayout nueva_caja = null;
        LinearLayout.LayoutParams parametros = null;
        nueva_caja = new LinearLayout(this);
        nueva_caja.setId(newId());
        int id_creado = nueva_caja.getId();
        Log.i("MIAPP", "SplitViewCopia -crearHijo- , getid creado es :" + id_creado);


            nueva_caja.setOrientation(LinearLayout.HORIZONTAL);
        //    parametros = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1F);
            parametros = new LinearLayout.LayoutParams(ImageView.MEASURED_SIZE_MASK, (int) 1F,1F);

        nueva_caja.setLayoutParams(parametros);
        nueva_caja.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
    //            dividir(v);
            }
        });
        nueva_caja.setBackgroundColor(randomColor());
        nueva_caja.setVisibility(View.VISIBLE);

        return nueva_caja;
    }

    public void Proseguir(View view) {
        avatarJugador.setVisibility(View.INVISIBLE);
        TextView tv = (TextView) findViewById(R.id.titulo);
        tv.setVisibility(View.INVISIBLE);
        Button button = findViewById(R.id.continuarsi);
        button.setVisibility(View.INVISIBLE);
        editText = findViewById(R.id.jugadornombre1);
        editText.setVisibility(View.INVISIBLE);

        vista = findViewById(R.id.root2);
 //       recorrerLayouts(vista);
        animUnit1 = 1100;
        if (sinAvatar == true){
            animUnit1 = 500;
        }


            Log.i("traceillo","valor j es :"+animUnit1);
            changeTextColor1(vista, 1, 0, 10000, animUnit1);



 //       setContentView(R.layout.activity_main);
 //       LinearLayout l1 = findViewById(R.id.root1);
 //       l1.setVisibility(View.VISIBLE);


    }

    NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        }
    };

    NumberPicker.OnValueChangeListener onValueChangeListener1 = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker numberPicker1, int i, int i1) {
        }
    };

    public void Proseguir1(View view) {

        Intent intent = new Intent(MainActivityprevio.this, SplitViewCopia.class);
       // intent.putExtra("toques", numberPicker.getValue());
        intent.putExtra("toques",20);
        intent.putExtra("cajas", numberPicker1.getValue());
        intent.putExtra("jugador", jugador);
        String ruta_captura_foto = Utilidades.crearNombreArchivo();
        intent.putExtra("imagen", ruta_captura_foto);
        intent.putExtra("sinAvatar",sinAvatar);
        nivel = String.valueOf(numberPicker.getValue());
        intent.putExtra("rotada",rotada);   // valor int
        Log.i("traceo","MainActivity-Proseguir1-valor rotada añadido en putExtra es : "+ rotada);
        intent.putExtra("nivel",nivel);
        intent.putExtra("originaluri",original_photo_galery_uriString);

        MainActivityprevio.this.finish();
        startActivity(intent);
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

    public void personalizarFoto(View view) {
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
                        avatarJugador.setImageResource(picture);
                        // esto hace falta si anteriormente se opto por la opcion tomar foto o desde album
                        // en algunos cosos como la imagen fue rotada el drawable.picture aparece rotado.
                        // si asignamos setRotation con el ultimo valor almacenado en rotda se solventa el problema
                        avatarJugador.setRotation(rotada);
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
                Log.i("MIAPP","path obtenido tras leer el archivo del fichero es : "+ photo_uri.getPath());
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
                String verpath = getRealPathFromURI(this,photo_uri);
                Uri urifile ;
                File file1 = new File(verpath);
                urifile = Uri.fromFile(file1);
                // UNA VEZ GUARDADO . VEO COMO SE HA GUARDADO PARA ESO VEO EL uri anterior
                rotada = getCameraPhotoOrientation(this,urifile,urifile.getPath());
                Log.i("MIAPP","path real del archivo es: "+ urifile.getPath());
                original_photo_galery_uriString = urifile.getPath();
    //            avatarJugador.setScaleType(ImageView.ScaleType.FIT_XY);
                avatarJugador.setRotation(rotada);
                Log.i("traceo","MainActivity-setearImagenDesdeArchivo- rotada es "+ rotada);
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
        Log.i("MIAPP","MainActivity-saveImagetoExternal path es : "+root);
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


    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    public void loadImagefromExternalmemory() {
        bitmap = null;
        FileInputStream fileInputStream;
        // Ha este metodo puedo venir como retorno o bien despues de tomar foto o de seleccionar archivo
        // caso - tomar foto : el uri original es el root que fijamos a continuacion
        // caso - de galeria: el uri original hay que rescatarlo de la imagen que fue seleccionada
        //        que fue guardada en original_photo_galery_uriSTring
        String root = Environment.getExternalStorageDirectory().toString()+ "/Pictures/COLORES_PIC.JPEG";
        if (original_photo_galery_uriString.contains("false")){
            // diferente de "false" , indica que venimos originalmente de la toma de una foto.
        }else {
            // false , indica que hay que cargar la imagen seleccionada originalmente
            root = original_photo_galery_uriString;
        }
        File file = new File(root);
        photo_uri = Uri.fromFile(file);
        Uri uri = getImageContentUri(this,file);
        Log.i("MIAPP","path obtenido tras leer el archivo del fichero es en modo content es : "+ uri.getPath());
        //this.photo_uri = data.getData();//obtenemos la uri de la foto seleccionada
        Log.i("MIAPP","path obtenido tras leer el archivo del fichero es : "+ photo_uri.getPath());
        //COPIA AL BITMAP <-- EL URI ANTERIORMENTE FIJADO
        InputStream imageStream = null;
        try {
            imageStream = getContentResolver().openInputStream(this.photo_uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
        selectedImage = Utilidades.getResizedBitmap(selectedImage, 400);// 400 is for example, replace with desired size
        // RECUPERAMOS LA IMAGEN EN LA VARIABLE bitmap
        bitmap = selectedImage;
        // CARGAMOS LA IMAGEN EN LA VISTA
        this.avatarJugador.setImageBitmap(selectedImage);
        // OBTENEMOS LA INFORMACION EN LA IMAGEN ORIGINAL(SELECTEDIMAGEN) PARA FIJARLA CON LA ROTACION CORRECTA
        int rotada = getCameraPhotoOrientation(this,photo_uri,photo_uri.getPath());
        Log.i("MIAPP","path real del archivo es: "+ photo_uri.getPath());
        Log.i("traceo","MainActivity-loadImagefromExternalmemory-rotada es : "+ rotada);

 //       avatarJugador.setScaleType(ImageView.ScaleType.FIT_XY);
        avatarJugador.setRotation(rotada);
        //SI NO HAY IMAGEN DEBEMOS FIJAR LA IMAGEN CON EL AVATAR .PICTURE
        if (bitmap != null) {
            // previamente cargada - no hacer nada
        } else {
            avatarJugador.setImageResource(picture);
        }
    }

    private void setearImagenDesdeCamara(int resultado, Intent intent) {
        switch (resultado) {
            case RESULT_OK:
                original_photo_galery_uriString = "false";
                Log.i("MIAPP", "Tiró la foto bien");
                Log.i("MIAPP","setarImagenDesdeCamara-fotoalmacenadaen uri path :"+photo_uri.getPath().toString());
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
                rotada = getCameraPhotoOrientation(this,photo_uri,photo_uri.getPath());
                Log.i("traceo","MainActivity-setearImagenDesdeCamara-rotada es: "+rotada);
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
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static String getFileNameByUri(Context context, Uri uri)
    {
        String fileName="unknown";//default fileName
        Uri filePathUri = uri;
        if (uri.getScheme().toString().compareTo("content")==0)
        {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor.moveToFirst())
            {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);//Instead of "MediaStore.Images.Media.DATA" can be used "_data"
                filePathUri = Uri.parse(cursor.getString(column_index));
                fileName = filePathUri.getLastPathSegment().toString();
            }
        }
        else if (uri.getScheme().compareTo("file")==0)
        {
            fileName = filePathUri.getLastPathSegment().toString();
        }
        else
        {
            fileName = fileName+"_"+filePathUri.getLastPathSegment();
        }
        return fileName;
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

    //El siguiente método devuelve la orientación de una imagen en grados. Funciona con imágenes de la galería.
    // Valores devueltos para:
    //
    //Paisaje normal: 0
    //Retrato normal: 90
    //Paisaje invertido: 180
    //Retrato al revés: 270
    //Imagen no encontrada: -1
    public static int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath) {
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
    public void marcarColor(View view) {
        // Si ya se ha pulsado el Boton Inicio entonces --true
        // eso quiere decir que empezamos a poder cambiar cajas de colores
        //id = view.findViewById(R.id.bloque1);
        LinearLayout x = (LinearLayout) view;
        Log.i("MIAPP", "SplitViewCopia--marcarColor(view) -inicio es : " + inicio);
        if (inicio == true) {

            // NIVEL DIFICIL o MUY DIFICIL
            int nivelnumero = Integer.parseInt(nivel);
            if (nivelnumero == 3){
                randNum = rand.nextInt(3) + 0; //MUY DIFICIL
            }else {
                if (nivelnumero == 2){
                    randNum = rand.nextInt(2) + 0; // DIFICIL
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
            if (bloqueclick == false) {
                //view.setBackgroundColor(Color.red(100000));
                view.setBackgroundColor(getResources().getColor(R.color.negro));
                bloqueclick = true;
                Log.i("MIAPP", "SplitViewCopia--marcarColor(view)-numero de veces " + num_veces);
                if (num_veces == numerodeCajas) {
                    // ahora tiene que preguntar si quieres jugar otra vez y si debe
                    // mostrarte el tiempo actual y el record ( si hay nuevo lo indicara)
   //                 otraVez();
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

};



