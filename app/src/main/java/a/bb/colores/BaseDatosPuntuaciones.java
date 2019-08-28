package a.bb.colores;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BaseDatosPuntuaciones extends SQLiteOpenHelper {

    private final static String sqlCreacionTablaPartida = "CREATE TABLE PARTIDA ( id INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT,tiempo TEXT,juego TEXT,record TEXT,sinAvatar TEXT,rotada TEXT,nivel TEXT)";
    private final static String sqlCreacionTablaPartidaRecord = "CREATE TABLE IF NOT EXISTS PARTIDARECORD ( id INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT,tiempo TEXT,juego TEXT,record TEXT,sinAvatar TEXT,rotada TEXT, nivel TEXT)";
    String juegotmp;
    public BaseDatosPuntuaciones(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sqlCreacionTablaPartida);
        sqLiteDatabase.execSQL(sqlCreacionTablaPartidaRecord);


    }

    public void insertarJugada (Puntuacion jugada)
    {
       // database.execSQL("INSERT INTO PERSONA (id, nombre) VALUES ("+ persona.getId()+" , '"+ persona.getNombre()+"')");
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("INSERT INTO PARTIDA (nombre,tiempo,juego,record,sinAvatar,rotada,nivel)  VALUES ( '"+ jugada.getNombre().toString()+"' , '"+ jugada.getTiempo().toString()+"' , '"+ jugada.getJuego().toString()+"','"+ jugada.getRecord().toString()+"','"+ jugada.getSinAvatar().toString()+"','"+jugada.getRotada().toString()+"','"+jugada.getNivel().toString()+"' )");

        //database.execSQL("INSERT INTO PARTIDA  VALUES ("+ jugada.getId()+" , '"+ jugada.getNombre().toString()+"' , '"+ jugada.getTiempo().toString()+"' , '"+ jugada.getJuego().toString()+"','"+ jugada.getRecord().toString()+"' )");
        this.cerrarBaseDatos(database);
    }

    public void insertRecord (Puntuacion jugada)
    {
        // database.execSQL("INSERT INTO PERSONA (id, nombre) VALUES ("+ persona.getId()+" , '"+ persona.getNombre()+"')");
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("INSERT INTO PARTIDARECORD (nombre,tiempo,juego,record,sinAvatar,rotada,nivel)  VALUES ( '"+ jugada.getNombre().toString()+"' , '"+ jugada.getTiempo().toString()+"' , '"+ jugada.getJuego().toString()+"','"+ jugada.getRecord().toString()+"','"+ jugada.getSinAvatar().toString()+"','"+jugada.getRotada().toString()+"','"+jugada.getNivel().toString()+"' )");

        //database.execSQL("INSERT INTO PARTIDA  VALUES ("+ jugada.getId()+" , '"+ jugada.getNombre().toString()+"' , '"+ jugada.getTiempo().toString()+"' , '"+ jugada.getJuego().toString()+"','"+ jugada.getRecord().toString()+"' )");
        this.cerrarBaseDatos(database);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    // buscar el string dado - que puede ser cualquiera de las cuatro columnas de la DB PARTIDA
    //     I.E buscar el nombre del juego en la clase Puntuacion
    //    private final static String sqlCreacionTablaPartida =
    //    "CREATE TABLE PARTIDA ( id INTEGER PRIMARY KEY,nombre TEXT,tiempo TEXT,juego TEXT,record TEXT)";

    public List<Puntuacion> listaconrecord(String nivelabuscar) {
        //List<Puntuacion> juego = new ArrayList<Puntuacion>();
        ArrayList<String> arrayListjuegosbuscados = new ArrayList<>();
        List<Puntuacion> lista_juegos = null;
        Puntuacion juego = null;
        int aux_id = -1;
        String name = "yes";
        String nombre_aux = null;
        String jugador= null;
        String tiempo= null;
        String juego1= null;
        String record = null;
        String sinAvatar = null;
        String rotada = "0";
        String nivel = "0";
        Boolean sinAvatarbolean = false;
        String consulta2 = "SELECT * FROM PARTIDARECORD WHERE record LIKE '%"+name+"%';";
        String consulta3;
        String basico = "1";
        String dificil = "2";
        String mdificil = "3";
   //     if (nivelabuscar.contains("4")){
   //         consulta3 = "SELECT * FROM PARTIDARECORD " +
   //                 "WHERE record LIKE '%"+name+"%' " +
   //                 "OR nivel LIKE '%"+basico+"%'" +
   //                 "OR nivel LIKE '%"+dificil+"%'" +
   //                 "OR nivel LIKE '%"+mdificil+"%'" ;
   //     }else {
            consulta3 = "SELECT * FROM PARTIDARECORD " +
                    "WHERE record LIKE '%"+name+"%' " +
                    "AND nivel LIKE '%"+nivelabuscar+"%'";
   //     }
        Log.i("traceo","BaseDAtosPuntuaciones-listaconrecord-consulta3 es :"+consulta3);
        SQLiteDatabase basedatos = this.getReadableDatabase();
        Cursor cursor = basedatos.rawQuery(consulta3, null);
        int count = 0;
        //String temporaljuego = null;
        int count1 = cursor.getCount();
        if (cursor != null && cursor.getCount()  > 0) {
            cursor.moveToLast();
            lista_juegos = new ArrayList<>(cursor.getCount());
            //juegotmp = cursor.getString(3); // guardo el juego en variable temporal
            for (int i = 0;i< count1;i++) {
                //String p = cursor.getString(4);
                aux_id = cursor.getInt(0); //la posicion primera, el id
                jugador = cursor.getString(1); //la posicion segunda, el id
                tiempo = cursor.getString(2);
                record = cursor.getString(4);
                sinAvatar = cursor.getString(5);
                rotada = cursor.getString(6);
                nivel = cursor.getString(7);
                if (sinAvatar.toString().contains("true")){
                    sinAvatarbolean = true;
                }

                if (juego1==null) {// primera partida encontrada
                        juego1 = cursor.getString(3);
                        int tempjuego1int = Integer.parseInt(juego1);
                        if (tempjuego1int<10){
                            juego1 = "0"+juego1;
                        }
                        // añado juego a la lista de encontrados
                        arrayListjuegosbuscados.add(juego1);
                        juego = (new Puntuacion(aux_id, jugador, tiempo, juego1, record,sinAvatar,rotada,nivel));
                        lista_juegos.add(juego);
                        Log.i("MIAPP", "dentro -buscarjuego- juego es :" + juego);
                        //temporaljuego = juego1;
                        cursor.moveToPrevious();

                }else {
                    // ya hay al menos una partida guardada
                    // busco si esta ya guardado el record para este juego
                    // 1 - SI entonces continuar busqueda
                    // 2-  NO almacenar esta partida
                    juego1 = cursor.getString(3);
                    int tempjuego1int = Integer.parseInt(juego1);
                    if (tempjuego1int<10){
                        juego1 = "0"+juego1;
                    }
                    Boolean recordEncontrado= false;
                    for (int x=0;x<arrayListjuegosbuscados.size();x++){
                        if (arrayListjuegosbuscados.get(x).toString().contains(juego1)){
                            // se ha econtrado uno se aborta la busqueda y se marca como ya almacenado
                            x=arrayListjuegosbuscados.size()-1;
                            recordEncontrado = true;
                        }
                    }
                    juego = (new Puntuacion(aux_id, jugador, tiempo, juego1, record,sinAvatar,rotada,nivel));

                    Log.i("MIAPP", "dentro -buscarjuego- juego es :" + juego);
                    //temporaljuego = juego1;
                    if (!recordEncontrado) {
                        // añado juego a la lista de encontrados si no lo estaba
                        arrayListjuegosbuscados.add(juego1);
                        lista_juegos.add(juego);
                    }
                    cursor.moveToPrevious();
                    /*
                    if (!temporaljuego.equals(juego1)){ // si no es el mismo juego
                        juego = (new Puntuacion(aux_id, jugador, tiempo, juego1, record));
                        lista_juegos.add(juego);
                        Log.i("MIAPP", "dentro -buscarjuego- juego es :" + juego);
                        temporaljuego = juego1;
                        cursor.moveToPrevious();
                    }else {
                        //
                        cursor.moveToPrevious();
                    }
                    */
                }
            }
        }

        //cursor.close();

        cursor.close();
        this.cerrarBaseDatos(basedatos);

        return lista_juegos;
    }


    public List<Puntuacion> listaconrecordPorJugador(String jugadorelegido,String nivelabuscar) {
        //List<Puntuacion> juego = new ArrayList<Puntuacion>();
        ArrayList<String> arrayListjuegosbuscados = new ArrayList<>();
        List<Puntuacion> lista_juegos = null;
        Puntuacion juego = null;
        int aux_id = -1;
        String name = "yes";
        String nombre_aux = null;
        String jugador= null;
        String tiempo= null;
        String juego1= null;
        String record = null;
        String sinAvatar = null;
        String rotada = "0";
        String nivel = "0";
        Boolean sinAvatarbolean = false;
        String consulta2 = "SELECT * FROM PARTIDARECORD WHERE record LIKE '%"+name+"%';";

        String consulta3 = "SELECT * FROM PARTIDARECORD " +
                "WHERE record LIKE '%"+name+"%' " +
                "AND nivel LIKE '%"+nivelabuscar+"%';";

        SQLiteDatabase basedatos = this.getReadableDatabase();
        Cursor cursor = basedatos.rawQuery(consulta3, null);
        int count = 0;
        //String temporaljuego = null;
        int count1 = cursor.getCount();
        if (cursor != null && cursor.getCount()  > 0) {
            cursor.moveToLast();
            lista_juegos = new ArrayList<>(cursor.getCount());
            //juegotmp = cursor.getString(3); // guardo el juego en variable temporal
            for (int i = 0;i< count1;i++) {
                //String p = cursor.getString(4);
                aux_id = cursor.getInt(0); //la posicion primera, el id
                jugador = cursor.getString(1); //la posicion segunda, el id
                tiempo = cursor.getString(2);
                record = cursor.getString(4);
                sinAvatar = cursor.getString(5);
                rotada = cursor.getString(6);
                nivel = cursor.getString(7);
                if (sinAvatar.toString().contains("true")){
                    sinAvatarbolean = true;
                }

                if (juego1==null) {// primera partida encontrada

                    if (jugador.toString().contains(jugadorelegido)) {
                        juego1 = cursor.getString(3);
                        int tempjuego1int = Integer.parseInt(juego1);
                        if (tempjuego1int<10){
                            juego1 = "0"+juego1;
                        }
                        // añado juego a la lista de encontrados
                        arrayListjuegosbuscados.add(juego1);
                        juego = (new Puntuacion(aux_id, jugador, tiempo, juego1, record, sinAvatar,rotada,nivel));
                        lista_juegos.add(juego);
                        Log.i("MIAPP", "dentro -buscarjuego- juego es :" + juego);
                        //temporaljuego = juego1;
                    }
                    cursor.moveToPrevious();

                }else {
                    // ya hay al menos una partida guardada
                    // busco si esta ya guardado el record para este juego
                    // 1 - SI entonces continuar busqueda
                    // 2-  NO almacenar esta partida
                    juego1 = cursor.getString(3);
                    int tempjuego1int = Integer.parseInt(juego1);
                    if (tempjuego1int<10){
                        juego1 = "0"+juego1;
                    }
                    Boolean recordEncontrado= false;
                    if (jugador.toString().contains(jugadorelegido)) {
                        for (int x = 0; x < arrayListjuegosbuscados.size(); x++) {
                            if (arrayListjuegosbuscados.get(x).toString().contains(juego1)) {
                                // se ha econtrado uno se aborta la busqueda y se marca como ya almacenado
                                x = arrayListjuegosbuscados.size() - 1;
                                recordEncontrado = true;
                            }
                        }
                        juego = (new Puntuacion(aux_id, jugador, tiempo, juego1, record, sinAvatar,rotada,nivel));

                        Log.i("MIAPP", "dentro -buscarjuego- juego es :" + juego);
                        //temporaljuego = juego1;
                        if (!recordEncontrado) {
                            // añado juego a la lista de encontrados si no lo estaba
                            arrayListjuegosbuscados.add(juego1);
                            lista_juegos.add(juego);
                        }
                    }
                    cursor.moveToPrevious();
                    /*
                    if (!temporaljuego.equals(juego1)){ // si no es el mismo juego
                        juego = (new Puntuacion(aux_id, jugador, tiempo, juego1, record));
                        lista_juegos.add(juego);
                        Log.i("MIAPP", "dentro -buscarjuego- juego es :" + juego);
                        temporaljuego = juego1;
                        cursor.moveToPrevious();
                    }else {
                        //
                        cursor.moveToPrevious();
                    }
                    */
                }
            }
        }

        //cursor.close();

        cursor.close();
        this.cerrarBaseDatos(basedatos);

        return lista_juegos;
    }



    public List<Puntuacion> listatodaspartidas(String string, String juegoelegido,String nivelabuscar) {
        //List<Puntuacion> juego = new ArrayList<Puntuacion>();
        List<Puntuacion> lista_juegos = null;
        Puntuacion juego = null;

        int aux_id = -1;
        String name = "yes";
        String nombre_aux = null;
        String jugador= null;
        String tiempo= null;
        String juego1= null;
        String record = null;
        String sinAvatar = null;
        String rotada = "0";
        String nivel = "0";

        Boolean sinAvatarbolean = false;
        //String consulta = "SELECT * FROM PARTIDA WHERE record LIKE ('%')ORDER BY juego";
        //String consulta1 = "SELECT * FROM PARTIDA WHERE record LIKE ('%')ORDER BY ('string')";
 //       String consulta2 = "SELECT * FROM PARTIDA ";
 //       String parte2 = "ORDER BY "+string;
 //       if (string.contains("xx")){
            // hay que hacer una busqueda solo por orden de partidas jugadas en order inverso
//            parte2 = "ORDER BY "+"id DESC";
//        }

        String consulta2 = "SELECT * FROM PARTIDA " +
                "WHERE nivel LIKE '%"+nivelabuscar+"%'";

        if (nivelabuscar.contains("4")){
            consulta2 = "SELECT * FROM PARTIDA ";
        }


        String parte2 = "ORDER BY "+string ;
        if (string.contains("xx")){
            // hay que hacer una busqueda solo por orden de partidas jugadas en order inverso
            parte2 = "ORDER BY "+"id DESC";
        }


        String consultafinal = consulta2 + parte2;
        //nombre TEXT,tiempo TEXT,juego TEXT,record TEXT)";

        SQLiteDatabase basedatos = this.getReadableDatabase();
        Cursor cursor = basedatos.rawQuery(consultafinal, null);
        int count1 = cursor.getCount();
        if (cursor != null && cursor.getCount()  > 0) {
            cursor.moveToFirst();
            lista_juegos = new ArrayList<>(cursor.getCount());
            //juegotmp = cursor.getString(3); // guardo el juego en variable temporal
            for (int i = 0;i< count1;i++) {
                aux_id = cursor.getInt(0); //la posicion primera, el id
                jugador = cursor.getString(1); //la posicion segunda, el id
                tiempo = cursor.getString(2);
                juego1 = cursor.getString(3);
                int tempjuego1int = Integer.parseInt(juego1);
                if (tempjuego1int<10){
                    juego1 = "0"+juego1;
                }
                record = cursor.getString(4);
                sinAvatar = cursor.getString(5);
                rotada = cursor.getString(6);
                nivel = cursor.getString(7);
                if (sinAvatar.contains("true")){
                    sinAvatarbolean = true;
                }
                if (juegoelegido != "no"){
                    if (jugador.toString().contains(juegoelegido)){
                        juego = (new Puntuacion(aux_id, jugador, tiempo, juego1, record,sinAvatar,rotada,nivel));
                        lista_juegos.add(juego);
                    }

                }else {
                    juego = (new Puntuacion(aux_id, jugador, tiempo, juego1, record,sinAvatar,rotada,nivel));
                    lista_juegos.add(juego);
                }
                cursor.moveToNext();
            }
        }
      //  Log.i("MIAPP","BaseDatosPuntuaciones-listatodaspartidas- lista_juegos es :"+ lista_juegos.toString());

        //cursor.close();

        cursor.close();
        this.cerrarBaseDatos(basedatos);

        return lista_juegos;
    }

    public Puntuacion buscarJuego (String name,String name1)//"yes",dummy)
    {
        Puntuacion juego = null;
        int aux_id = -1;
        String nombre_aux = null;
        String jugador= null;
        String tiempo= null;
        String juego1= null;
        String record = null;
        String sinAvatar = null;
        String rotada = "0";
        String nivel = "0";
        Boolean sinAvatarbolean = false;
        //("SELECT * FROM TABLENAME WHERE name LIKE'%?%'",(Variable,))
        String consulta = "SELECT * FROM PARTIDA WHERE record LIKE ('%name%')";
        String consulta2 = "SELECT * FROM PARTIDA WHERE record LIKE '%"+name+"%';";
        String consulta2distinct = "SELECT DISTINCT * FROM PARTIDA WHERE record LIKE '%"+name+"%';";
        String consulta3 = "SELECT * FROM (PARTIDA WHERE record LIKE '%"+name+"%'AND WHERE juego LIKE '%"+name1+"%';)";

        SQLiteDatabase basedatos = this.getReadableDatabase();
        Cursor cursor = basedatos.rawQuery(consulta2, null);
        int count = 0;
        int count1 = cursor.getCount();
        if (cursor != null && cursor.getCount()  > 0) {
            cursor.moveToFirst();
            //juegotmp = cursor.getString(3); // guardo el juego en variable temporal
            for (int i = 0;i< count1;i++) {
                String p = cursor.getString(4);
                if (p.equals(name)) {
                    aux_id = cursor.getInt(0); //la posicion primera, el id
                    jugador = cursor.getString(1); //la posicion segunda, el id
                    tiempo = cursor.getString(2);
                    juego1 = cursor.getString(3);
                    int tempjuego1int = Integer.parseInt(juego1);
                    if (tempjuego1int<10){
                        juego1 = "0"+juego1;
                    }
                    record = cursor.getString(4);
                    sinAvatar = cursor.getString(5);
                    rotada = cursor.getString(6);
                    nivel = cursor.getString(7);
                    if (sinAvatar.contains("true")){
                        sinAvatarbolean = true;
                    }

                    juego = new Puntuacion(jugador, tiempo, juego1, record, sinAvatar,rotada,nivel);
                    Log.i("MIAPP", "dentro -buscarjuego- juego es :" + juego);
                    //insertRecord(juego);
                    //break;
                    // solo voy a coger el record final por eso lo he interrumpido
                    //cursor.moveToPrevious();
                } else {
                    cursor.moveToNext();
                }
            }
        }

        //cursor.close();

        cursor.close();
        this.cerrarBaseDatos(basedatos);

        return juego;
    }


    public Puntuacion buscarUltimaPartidaGuardada ()
    {  // nombre,tiempo,juego,record
        // buscar juego y tiempo almacenados y comprobar si es record el actual
        //  Si hay nuevo record poner record = "yes"
        //  Si no hay ninguna partida para este juego almacenarlo y poner "yes"
        Puntuacion juego = null;
        int aux_id = -1;
        String jugador= null;
        String tiempo= null;
        String juego1= null;
        String record = null;
        String sinAvatar = null;
        String rotada = "0";
        String nivel = "0";
        Boolean sinAvatarbolean = false;

        //("SELECT * FROM TABLENAME WHERE name LIKE'%?%'",(Variable,))
        String consulta = "SELECT * FROM PARTIDA WHERE nombre LIKE ('%')";


        SQLiteDatabase basedatos = this.getReadableDatabase();
        Cursor cursor = basedatos.rawQuery(consulta, null);
        //Boolean count = false;
        int count1 = cursor.getCount();
        if (cursor != null && cursor.getCount()  > 0) {
            cursor.moveToLast();
            aux_id = cursor.getInt(0); //la posicion primera, el id
            jugador = cursor.getString(1); //la posicion segunda, el id
            tiempo = cursor.getString(2);
            juego1 = cursor.getString(3);
            int tempjuego1int = Integer.parseInt(juego1);
            if (tempjuego1int<10){
                juego1 = "0"+juego1;
            }
            record = cursor.getString(4);
            sinAvatar = cursor.getString(5);
            rotada = cursor.getString(6);
            nivel = cursor.getString(7);
            if (sinAvatar.contains("true")){
                sinAvatarbolean = true;
            }
            juego = new Puntuacion(jugador, tiempo, juego1, record, sinAvatar,rotada,nivel);
        }else {
        }

        //cursor.close();

        cursor.close();
        this.cerrarBaseDatos(basedatos);

        return juego;
    }


    public Puntuacion buscarBuscarRecordporJuego (Puntuacion jugada,String nivel)
    {  // nombre,tiempo,juego,record
        // buscar juego y tiempo almacenados y comprobar si es record el actual
        //  Si hay nuevo record poner record = "yes"
        //  Si no hay ninguna partida para este juego almacenarlo y poner "yes"
        Puntuacion juego = null;
        int aux_id = -1;
        String jugador= null;
        String tiempo= null;
        String juego1= jugada.getJuego().toString();
        String record = null;
        String sinAvatar = null;
        String rotada = "0";
        //String nivel = "1";
        Boolean sinAvatarbolean = false;

        //("SELECT * FROM TABLENAME WHERE name LIKE'%?%'",(Variable,))
        //String consulta = "SELECT * FROM PARTIDARECORD WHERE nombre LIKE ('%')";
        String consulta2 = "SELECT * FROM PARTIDARECORD WHERE juego LIKE '%"+juego1+"%';";
        String consulta3 = "SELECT * FROM PARTIDARECORD " +
                "WHERE juego LIKE '%"+juego1+"%' " +
                "AND nivel LIKE '%"+nivel+"%';";

        SQLiteDatabase basedatos = this.getReadableDatabase();
        Cursor cursor = basedatos.rawQuery(consulta3, null);
        //Boolean count = false;
        int count1 = cursor.getCount();
        if (cursor != null && cursor.getCount()  > 0) {
            cursor.moveToLast();
            aux_id = cursor.getInt(0); //la posicion primera, el id
            jugador = cursor.getString(1); //la posicion segunda, el id
            tiempo = cursor.getString(2);
            juego1 = cursor.getString(3);
            int tempjuego1int = Integer.parseInt(juego1);
            if (tempjuego1int<10){
                juego1 = "0"+juego1;
            }

            record = cursor.getString(4);
            sinAvatar = cursor.getString(5);
            rotada = cursor.getString(6);
            nivel = cursor.getString(7);
            if (sinAvatar.contains("true")){
                sinAvatarbolean = true;
            }
            juego = new Puntuacion(jugador, tiempo, juego1, record, sinAvatar,rotada,nivel);
        }else {
        }

        //cursor.close();

        cursor.close();
        this.cerrarBaseDatos(basedatos);

        return juego;
    }



    public Puntuacion buscarJuego1 (Puntuacion jugada,String nivelabuscar)
    {  // nombre,tiempo,juego,record
        // buscar juego y tiempo almacenados y comprobar si es record el actual
        //  Si hay nuevo record poner record = "yes"
        //  Si no hay ninguna partida para este juego almacenarlo y poner "yes"

        Puntuacion juego = null;
        int aux_id = -1;
        String nombre_aux = null;
        String nombre2 = jugada.getNombre().toString();
        String tiempo2 = jugada.getTiempo().toString();
        String juego2 = jugada.getJuego().toString();
        String sinAvatar = jugada.getSinAvatar();
        //String record2 = jugada.getRecord().toString();
        String record2 = "no";
        String rotada = jugada.getRotada().toString();
        String nivel = jugada.getNivel().toString();
        //String sinAvatar = null;
        //("SELECT * FROM TABLENAME WHERE name LIKE'%?%'",(Variable,))
        String consulta = "SELECT * FROM PARTIDA WHERE record LIKE ('%name%')";
        String consulta2 = "SELECT * FROM PARTIDA WHERE juego LIKE '"+ juego2 +"';";
        //String consulta2distinct = "SELECT DISTINCT * FROM PARTIDA WHERE record LIKE '%"+name+"%';";
        //String consulta3 = "SELECT * FROM (PARTIDA WHERE record LIKE '%"+name+"%'AND WHERE juego LIKE '%"+name1+"%';)";
        String consulta3 = "SELECT * FROM PARTIDA " +
                "WHERE juego LIKE '"+juego2+"' " +
                "AND nivel LIKE '%"+nivelabuscar+"%';";

        SQLiteDatabase basedatos = this.getReadableDatabase();
        Cursor cursor = basedatos.rawQuery(consulta3, null);
        //Boolean count = false;
        Double temporalmenor = null;
        Double tactual = Double.valueOf(tiempo2);
        int count1 = cursor.getCount();
        if (cursor != null && cursor.getCount()  > 0) {
            cursor.moveToFirst();
            //juegotmp = cursor.getString(3); // guardo el juego en variable temporal
            for (int i = 0;i< count1;i++) {
                // ahora tengo que ver si el tiempo guardado es menor que el actual
                Double t2 = Double.valueOf(cursor.getString(2));

                Log.i("MIAPP","BaseDAtosPuntuaciones-BuscarJuego1-tiempo almacenado es: "+t2);
                Log.i("MIAPP","BaseDAtosPuntuaciones-BuscarJuego1-tiempo actual es: "+tactual);
                String p = cursor.getString(3);

                // el valor actual leido se almacena en temporalmenor
                if (temporalmenor == null){
                    temporalmenor = t2;
                }else{
                    if (t2< temporalmenor) {
                        temporalmenor = t2;
                    }else {
                        // no actualizo el valor
                    }
                }


                Log.i("MIAPP", "dentro -buscarjuego- temporalmenor es :" + temporalmenor );

                cursor.moveToNext();
            }


            if (tactual <= temporalmenor){
                // insertar jugada con record
                record2 = "yes";
                Log.i("MIAPP", "dentro -buscarjuego- hay record :" );

            }else {// no hay record

            }
            juego = new Puntuacion(nombre2, tiempo2, juego2, record2, sinAvatar,rotada,nivel);
            Log.i("MIAPP", "dentro -buscarjuego- juego es :" + juego);
            insertarJugada(juego);
            if (record2.contains("yes")){
                insertRecord(juego);
            }
        }else {
            record2 = "yes";
            juego = new Puntuacion(nombre2, tiempo2, juego2, record2,sinAvatar,rotada,nivel);
            insertarJugada(juego);
            insertRecord(juego);
            Log.i("MIAPP", "dentro -buscarjuego- se ha insertado juego y record :" + juego);
        }

        //cursor.close();

        cursor.close();
        this.cerrarBaseDatos(basedatos);

        return juego;
    }

    public List<Puntuacion> buscaridultimo (Puntuacion persona)
    {
        List<Puntuacion> lista_coches = null;
        Puntuacion coche = null;
        int aux_id = -1;
        String modelo = null;


        String consulta = "SELECT id FROM PARTIDA WHERE id ="+persona.getId();

        SQLiteDatabase basedatos = this.getReadableDatabase();
        Cursor cursor = basedatos.rawQuery(consulta, null);


        if( cursor != null && cursor.getCount() >0)
        {
            cursor.moveToFirst();
            lista_coches = new ArrayList<>(cursor.getCount());

            do
            {

                modelo = cursor.getString(0); //la posicion primera, el id
                coche = new Puntuacion();
                lista_coches.add(coche);

            }while (cursor.moveToNext());

            cursor.close();
        }

        this.cerrarBaseDatos(basedatos);
        return lista_coches;
    }

    private void cerrarBaseDatos (SQLiteDatabase database)
    {
        database.close();
    }
}
