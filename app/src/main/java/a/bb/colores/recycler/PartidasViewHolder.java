package a.bb.colores.recycler;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessControlContext;

import a.bb.colores.MainActivity;
import a.bb.colores.R;
import a.bb.colores.Utilidades;

import static a.bb.colores.MainActivity.getCameraPhotoOrientation;
import static a.bb.colores.MainActivity.getFileNameByUri;
import static a.bb.colores.MainActivity.randomColor;
import static java.security.AccessController.getContext;


public class PartidasViewHolder extends RecyclerView.ViewHolder {

    private ImageView avatar_foto;
    private TextView text_view_juego;
    private TextView text_view_jugador;
    private TextView text_view_tiempo;
    private Context getcontext;
    File file;
    String root;
    int rotada;

    public PartidasViewHolder(View itemView) {

        super(itemView);
        avatar_foto = (ImageView)itemView.findViewById(R.id.foto);
        text_view_juego = (TextView)itemView.findViewById(R.id.juego);
        text_view_jugador = (TextView)itemView.findViewById(R.id.jugador);
        text_view_tiempo = (TextView)itemView.findViewById(R.id.tiempo);
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

    public void loadImagefromExternalmemory() {

        Bitmap bitmap = null;
        FileInputStream fileInputStream;
        // Ha este metodo puedo venir como retorno o bien despues de tomar foto o de seleccionar archivo
        // caso - tomar foto : el uri original es el root que fijamos a continuacion
        // caso - de galeria: el uri original hay que rescatarlo de la imagen que fue seleccionada
        //        que fue guardada en original_photo_galery_uriSTring
//        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + "/" + archivo+".JPEG";

//        String root = Environment.getExternalStorageDirectory().toString()+ "/Pictures/COLORES_PIC.JPEG";

        File file = new File(root);
        Uri photo_uri = Uri.fromFile(file);
//        Uri uri = getImageContentUri(this,file);
//        Log.i("MIAPP","path obtenido tras leer el archivo del fichero es en modo content es : "+ uri.getPath());
        //this.photo_uri = data.getData();//obtenemos la uri de la foto seleccionada
        Log.i("MIAPP","path obtenido tras leer el archivo del fichero es : "+ photo_uri.getPath());
        //COPIA AL BITMAP <-- EL URI ANTERIORMENTE FIJADO
        InputStream imageStream = null;
//        try {
//            imageStream = getContentResolver().openInputStream(this.photo_uri);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
        selectedImage = Utilidades.getResizedBitmap(selectedImage, 400);// 400 is for example, replace with desired size
        // RECUPERAMOS LA IMAGEN EN LA VARIABLE bitmap
        bitmap = selectedImage;
        // CARGAMOS LA IMAGEN EN LA VISTA
//        this.avatarJugador.setImageBitmap(selectedImage);
        // OBTENEMOS LA INFORMACION EN LA IMAGEN ORIGINAL(SELECTEDIMAGEN) PARA FIJARLA CON LA ROTACION CORRECTA
//        int rotada = getCameraPhotoOrientation(this,photo_uri,photo_uri.getPath());
        Log.i("MIAPP","path real del archivo es: "+ photo_uri.getPath());

//        avatarJugador.setScaleType(ImageView.ScaleType.FIT_XY);
//        avatarJugador.setRotation(rotada);
        //SI NO HAY IMAGEN DEBEMOS FIJAR LA IMAGEN CON EL AVATAR .PICTURE
        if (bitmap != null) {
            // previamente cargada - no hacer nada
        } else {
//            avatarJugador.setImageResource(R.drawable.picture);
        }
    }

    public void cargarPartidasEnHolder(Partida l) {


        String p = l.getSinAvatar().toString();
        Log.i("MIAPP","PartidasViewHolder-cargarPartidasEnHolder- sinAvatar es :"+p);

        if (p.toString().contains("true")){
            avatar_foto.setImageResource(R.drawable.picture);
        }else {
            String fotoArchivada = l.getJugador().toString() + l.getJuego().toString() + l.getTiempo().toString();
            String rotadaString = l.getRotada().toString();
            Log.i("MIAPP", "PartidasViewHolder-cargarPartidasEnHolder-ruta imagen es : " + fotoArchivada);
            Bitmap recuperaImagen = Utilidades.recuperarImagenMemoriaInterna(avatar_foto.getContext(), fotoArchivada);//dato1 no se usa
            //AHORA TENEMOS LA IMAGEN EN EL BITMAP "recuperaImagen"
            //LA FIJAMOS A LA IMAGEN
            avatar_foto.setImageBitmap(recuperaImagen);
            //OBTENEMOS EL URI DE LA IMAGEN CARGADA
//           Uri uri = getImageUri(avatar_foto.getContext(),recuperaImagen);
            // OBTENEMOS LA URI REAL EN LUGAR DE LA QUE VIENE CON EL CONTENT
            // ejemplo ,,,aqui llega como /external/images/media/42933 a -->/storage/emulated/0/Pictures/1565522764240.jpg
//            String realpath = getRealPathFromURI(avatar_foto.getContext(),uri);
//            String realpath1 = getFileNameByUri(avatar_foto.getContext(),uri);
//            String root = Environment.getExternalStorageDirectory().toString()+ "/" + fotoArchivada+".JPEG";
            String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + "/" + fotoArchivada+".JPEG";
            file = new File(root);
            Uri uri = Uri.fromFile(file);
            //OBTENEMOS LA ROTACION
            rotada = Integer.parseInt(rotadaString);
        //    rotada = getCameraPhotoOrientation(avatar_foto.getContext(),uri,uri.getPath());

            if (uri != null) {
                Log.i("MIAPP","PartidasVeiwHolder-cargarPartidasEnHolder-root es : "+ uri.getPath().toString());
                Log.i("MIAPP","PartidasVeiwHolder-cargarPartidasEnHolder-rotada es : "+ rotada);
//                avatar_foto.setScaleType(ImageView.ScaleType.FIT_XY);
                avatar_foto.setRotation(rotada);
            } else {
                //tv.setTextColor(randomColor());
                //avatar_foto.setImageResource(R.drawable.jugarahora);
   //             int i = randomColor();
                // avatar_foto.setColorFilter(i);
                avatar_foto.setImageResource(R.drawable.picture);

            }
        }
        text_view_juego.setText("Cajas_"+l.getJuego());
        text_view_jugador.setText(l.getJugador());
        text_view_tiempo.setText(l.getTiempo());
    }
}
