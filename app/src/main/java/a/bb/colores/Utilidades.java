package a.bb.colores;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;


public class Utilidades {

    //creamos el fichero donde irá la imagen
    public static Uri crearFicheroImagen() {
        Uri uri_destino = null;
        String nombre_fichero = null;
        File file = null;

        //crearNombreArchivo();

        String ruta_captura_foto = crearNombreArchivo();
        Log.i("MIAPP", "RUTA FOTO " + ruta_captura_foto);
        file = new File(ruta_captura_foto);

        try { //INTENTA ESTO

            if (file.createNewFile()) {
                Log.i("MIAPP", "FICHERO CREADO");
            } else {
                Log.i("MIAPP", "FICHERO NO CREADO");
            }
        } catch (IOException e) { // Y SI FALLA SE METE POR AQUÍ
            Log.e("MIAPP", "Error al crear el fichero", e);
        }

        uri_destino = Uri.fromFile(file);
        Log.i("MIAPP", "URI = " + uri_destino.toString());

        return uri_destino;
    }


    // Gracias Vale!!
    public static void desactivarModoEstricto() {
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);

            } catch (Exception e) {
                Log.e("MIAPP", "Error al trucar el método disableDeathOnFileUriExposure", e);
            }
        }
    }


    // Redimensionar Bitmap
    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    public static String crearNombreArchivo() {
        String PREFIJO_FOTOS = "COLORES_PIC";
        String SUFIJO_FOTOS = ".JPEG";
        // Creamos un nombre de fichero para guardar la foto
        String nombre_fichero = PREFIJO_FOTOS + SUFIJO_FOTOS;
        String ruta_captura_foto = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + "/" + nombre_fichero;
      //  String ruta_captura_foto = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + "/" + nombre_fichero;

        return ruta_captura_foto;
    }

    public static void guardarImagenMemoriaInterna(Context context, String archivo, byte[] byteArray) {
        try {
            FileOutputStream outputStream = context.openFileOutput(archivo + ".JPEG", Context.MODE_PRIVATE);
            outputStream.write(byteArray);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public static Bitmap recuperarImagenMemoriaInterna(Context context, String archivo) {
      //  String root = Environment.getExternalStorageDirectory().toString()+ "/" + archivo+".JPEG";
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + "/" + archivo+".JPEG";

      //  String ARCHIVO_IMAGEN_JUGADOR = "ARCHIVOIMAGENJUGADOR";
        Bitmap bitmap = null;

      //  if (archivo == null) {
      //      archivo = ARCHIVO_IMAGEN_JUGADOR;
      //  }
    //    File dataDirectory = Environment.getDataDirectory();
        try {
            FileInputStream fileInputStream =
                  //  new FileInputStream(dataDirectory+"/data/a.bb.colores/files/"+archivo+".JPEG");
                    new FileInputStream(root);
            bitmap = BitmapFactory.decodeStream(fileInputStream);
        } catch (IOException io) {
            io.printStackTrace();
        }

        return bitmap;
    }

    public static void eliminarArchivo(Context context, String archivo) {

        boolean eliminado = false;

        File file = new File(context.getFilesDir().getPath() + "/" + archivo + ".JPEG");
        if (file.exists()) eliminado = file.delete();

    }


    public static byte[] bitmapToArrayBytes(Bitmap bitmap) {

        byte[] arrayBytes = null;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        arrayBytes = stream.toByteArray();

        return arrayBytes;

    }


    public static Drawable getAssetImage(Context context, String filename) {

        AssetManager assets = context.getResources().getAssets();
        InputStream buffer = null;
        try {
            buffer = new BufferedInputStream((assets.open("drawable/" + filename + ".png")));
        } catch (IOException e) {
            e.printStackTrace();
        }



        Bitmap bitmap = BitmapFactory.decodeStream(buffer);
        return new BitmapDrawable(context.getResources(), bitmap);


    }


}
