package a.bb.colores;


import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import a.bb.colores.recycler.Partida;


public class MainActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial_colores);
        vista = findViewById(R.id.root2);
        SplitViewCopia splitViewCopia = new SplitViewCopia();
        splitViewCopia.recorrerLayoutsycolorear(vista);
        avatarJugador = findViewById(R.id.foto);
        int i = randomColor();
        //avatarJugador.setColorFilter(i);
        // DEFINO BOTON CONTINUAR
        continuar = findViewById(R.id.continuarsi);
        editText = findViewById(R.id.jugadornombre1);
        TextView tv = (TextView) findViewById(R.id.titulo);
        // este getItent es solo devuelto cuando viene de haber jugado y se da volver
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
                avatarJugador.setImageResource(R.drawable.picture);
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

    //PARA VER LA ORIENTACION DE LA IMAGEN TOMADA POR LA CAMARA
    public float getImageRotation(Uri path, Context context) {
        try {
            String[] projection = {MediaStore.Images.ImageColumns.ORIENTATION};

            Cursor cursor = context.getContentResolver().query(path, projection, null, null, null);

            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
            cursor.close();

        } catch (Exception ex) {
            return 0f;
        }

        return 0f;
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

    public void animateIt() {
        ObjectAnimator a = ObjectAnimator.ofInt(tv, "TextColor", Color.GREEN, Color.RED);
        a.setInterpolator(new LinearInterpolator());
        a.setDuration(1000);
        a.setRepeatCount(ValueAnimator.INFINITE);
        a.setRepeatMode(ValueAnimator.REVERSE);
        a.setEvaluator(new ArgbEvaluator());
        AnimatorSet t = new AnimatorSet();
        t.play(a);
        t.start();
    }

    public void Proseguir(View view) {
        editText = findViewById(R.id.jugadornombre1);
        if (editText.getText().toString().isEmpty()){
            editText.setText(jugador);
        }else {
            jugador = editText.getText().toString();
        }
        setContentView(R.layout.activity_main);
        LinearLayout l1 = findViewById(R.id.root1);
        l1.setVisibility(View.VISIBLE);
        editText = findViewById(R.id.jugadornombre);
        editText.setVisibility(View.INVISIBLE);
        Button button = findViewById(R.id.continuarsi);
        button.setVisibility(View.INVISIBLE);
        // DEFINO NUMBERPICKER - PARA TOQUES Y NUMERO DE CAJAS POR CLICK
        numberPicker = findViewById(R.id.edit_toques);
        numberPicker1 = findViewById(R.id.edit_cajas);
        //      DEFINO RANGO DE VALORES
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(3);
        numberPicker.setDisplayedValues( new String[] { "FACIL", "DIFICIL", "MUY DIFICIL" } );
        numberPicker.setValue(1); // valor por defecto
        numberPicker1.setMinValue(2);
        numberPicker1.setMaxValue(10);
        numberPicker1.setValue(3); // valor por defecto
        //      EJECUTO EL NUMBERPICKER
        numberPicker.setOnValueChangedListener(onValueChangeListener);
        numberPicker1.setOnValueChangedListener(onValueChangeListener1);
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
        Intent intent = new Intent(MainActivity.this, SplitViewCopia.class);
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

        MainActivity.this.finish();
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
                        avatarJugador.setImageResource(R.drawable.picture);
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

        String root = Utilidades.crearNombreArchivo();
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
            avatarJugador.setImageResource(R.drawable.picture);
        }
    }

    public Bitmap getBitmap(String path) {
        try {
            bitmap=null;
            File f= new File(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
            avatarJugador.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
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


       //         String root = Environment.getExternalStorageDirectory().toString()+ "/Pictures/COLORES_PIC.jpg";
       //         Uri photo_uri1 =null;
       //         if (selectedImage != null) {
       //             File file = new File(root);
       //             photo_uri1 = Uri.fromFile(file);
       //             Log.i("MIAPP","photo_uri1 obtenida manualmente "+photo_uri1.toString());
       //             //photo_uri1 obtenida manualmente file:///storage/emulated/0/Pictures/COLORES_PIC.jpg
       //         }

                /* pruebas varias
                int rotada1 = getCameraPhotoOrientation(this,photo_uri1,photo_uri1.getPath());
                photo_uri1 = getImageUri(this,selectedImage);
                Log.i("MIAPP","photo_uri1 obtenida desde getImageUri "+photo_uri1.toString());
                //photo_uri1 obtenida desde getImageUri content://media/external/images/media/42803

                String path = getRealPathFromURI(this,photo_uri1);
                Log.i("MIAPP","photo_uri1 obtenida despues getImageUri "+path);
                //photo_uri1 obtenida desde getImageUri content://media/external/images/media/42803

                String filename = getFileNameByUri(this,photo_uri1);
                Log.i("MIAPP","photo_uri1 obtenida despues getFileNameByUri, es un string : "+filename);

                String path2 = getImageFilePath(photo_uri1);
                Log.i("MIAPP","photo_uri1 obtenida despues getImageFilePath, es un string : "+path2);

                File file = new File(path);
                photo_uri1 = Uri.fromFile(file);

                //COMPROBAR SI LA IMAGEN HA SIDO ROTADA
               // float rotada = getImageRotation(photo_uri, this);
               */
  //              saveImagetoExternalmemory(selectedImage);
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

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        Log.i("MIAPP","photo_uri1-path dentro getImageUri"+path);
        return Uri.parse(path);
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

    public String getImageFilePath(Uri uri) {
        String path = null, image_id = null;

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            image_id = cursor.getString(0);
            image_id = image_id.substring(image_id.lastIndexOf(":") + 1);
            cursor.close();
        }

        cursor = getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
        if (cursor!=null) {
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
        }
        return path;
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
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );
        intent_pide_foto.setType("image/*");//tipo mime
        startActivityForResult(intent_pide_foto, CODIGO_PETICION_SELECCIONAR_FOTO);
    }

    // pruebas
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

    public static int getOrientation(Context context, Uri photoUri) {
        Cursor cursor = context.getContentResolver().query(photoUri, new String[]{MediaStore.Images.ImageColumns.ORIENTATION}, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            } else {
                return -1;
            }
        } finally {
            cursor.close();
        }
    }

    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

};



