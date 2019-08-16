package a.bb.colores;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import a.bb.colores.recycler.AdapterPartidas;
import a.bb.colores.recycler.Partida;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Resultados extends AppCompatActivity {
    ArrayList<String> arrayDataLog = new ArrayList<String>();
    ArrayList<String> arrayDataLogtemp = new ArrayList<>();
    String jugador;
    String juego;
    String tiempo;
    String record;
    Double tiempod;
    String NOMBRES [] = {"Elige opcion","Resultados","Records"};
    boolean juegoset = false;
    String oldrecord = null;
    String juegotmp;
    private RecyclerView recView;
    private ArrayList<Partida> datos;
    String JugadorActual = "sin nombre";
    public static String sinAvatar;

    private AdapterPartidas adaptador;
    String original_photo_galery_uri;
    String nivel;
    int rotada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        //DATOS RECIBIDOS DESDE SplitViewCopia
        arrayDataLog = getIntent().getStringArrayListExtra("resultados");
        JugadorActual = getIntent().getStringExtra("nombre");
        nivel = getIntent().getStringExtra("nivel");
        sinAvatar = getIntent().getStringExtra("sinAvatar");
        rotada = getIntent().getIntExtra("rotadevuelta",0);
        original_photo_galery_uri = getIntent().getStringExtra("originaluri");

        Log.i("MIAPP","sinAvatar es: "+sinAvatar);
        // formato del array recibido es:
        // Jugador,tiempo , Juego, record?
        filtartodaspartidas(nivel);
        mostrarrecycler("record",nivel);
        //filtarparidasrecords();
        //presentarEnRecycler("xx");


        /*
        // *****************esta parte la voy a anular de momento***************************
        // PREPARO OTRA NUEVA VISTA PARA PODER ELEGIR ENTRE : TODAS LAS PARTIDAS Y RECORD POR JUEGO
        //  -TODAS LAS PARTIDAS- han sido anteriormente almacenadas en datos
        //  -RECORD POR JUEGO - hay que generarlo


        final Spinner spinner = (Spinner) findViewById(R.id.mispinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, NOMBRES);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("MIAPP","la posicion selecciona es : " +i);
                String juego = (String) spinner.getAdapter().getItem(i);

                    //   String NOMBRES [] = {"Todos","Jugador","Juego-Record", "Juego"};
                    switch (juego) {
                        case "Elige opcion":
                            break;
                        case "Resultados": // Mostrar datos en el recycler
                            mostrarrecycler();
                            break;
                        case "Records": // nuevo para probar records solamente
                            ordenar1(view,1);
                            break;
                        default:    // "Elegir"
                            break;
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        TextView texto = findViewById(R.id.txtresultados);
        //para imprimir un array en un text view

      //  Collections.sort(arrayDataLog, new Sortbyroll());
        /**
        Collections.sort(this.arrayDataLog, new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
               String [] m = s.split(",");
                String [] m1 = t1.split(",");
                Double md = Double.parseDouble(m[1]);
                Double m1d = Double.parseDouble(m1[1]);

                Log.i("MIAPP","muestar s es: "+s);
                return new Integer(md.compareTo(m1d));
            }
        });

        int size = arrayDataLog.size()-1;
        Collections.sort(arrayDataLog);
        int w = arrayDataLog.size()-1;
        for (int x = w; x >-1;x--){
            String data = arrayDataLog.get(x).toString();
            String [] campos = data.split(",");
            jugador = campos[0];
            tiempo = campos[1];
            juego = campos[2];
            record = campos[3];

        }


        //Collections.sort(arrayDataLog,Collections.<String>reverseOrder());

        texto.setText("");

        int z = arrayDataLog.size();
           for(int i = 0; i < z; i++) {
            texto.append(arrayDataLog.get(i));
        }
        */

    }
    private static Boolean getinfo(){
        boolean p = false;
        if (sinAvatar=="true"){
            p= true;
        }
        return p;
    }

    public static String getSinAvatar() {
        String s = "false";
        if (getinfo()==true){
            s = "true";
        }

        return s;
    }

    public void filtarparidasrecords(){
        View view = null;
        ordenar1(view,1);
    }
    public void filtartodaspartidas(String nivelabuscar) {
        // CONSULTA DB PARTIDA PARA OBTENER LA LISTA DE PARTIDAS ordenada por JUEGO
        //private ArrayList<Partida> datos;
        datos = new ArrayList<Partida>();
        Partida partida_aux =null;

        BaseDatosPuntuaciones baseDatosPuntuaciones = new BaseDatosPuntuaciones(this, "MiDB", null, 1);
        ArrayList<Puntuacion> listajuegos = new ArrayList<Puntuacion>();
        // todas las partidas
        // cuando juego es fijado xx - la busqueda se hara por orden de partidas jugadas
        juego = "xx";
        String juegoelegido = "no";
        listajuegos = (ArrayList<Puntuacion>) baseDatosPuntuaciones.listatodaspartidas(juego, juegoelegido,nivel);


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



    public void filtar(int menu){
        // "Todos","Jugador","Juego-Record", "Juego"}
        // menu 0,   1      ,   2           ,   3
        Log.i("MIAPP","arrayDataLog (antes de filtrar) es: " +arrayDataLog);
        int size = arrayDataLog.size()-1;
        //Collections.sort(arrayDataLog);
        int w = arrayDataLog.size()-1;
        TextView texto = findViewById(R.id.txtresultados);
        String menusalida = ""+"\n";
        texto.setText(menusalida);
        juegoset = false;
       // texto.setText(Texto+"\n"+Texto2+"\n"+Texto3+"\n"+Texto4);
        for (int x = w; x >-1;x--){
            String data = arrayDataLog.get(x).toString();
            String [] campos = data.split(",");
            jugador = campos[0];
            tiempo = campos[1];
            juego = campos[2];
            record = campos[3];
            switch (menu) {



                case 0: // nuevo para probar recycler
                    mostrarrecycler("record",nivel);
                    break;
                case 1: // Juego-Record

                    View view = null;
                    ordenar1(view,1);
                    break;
                default:
            }

        }
        // en este momento arrayDataLogtemp tiene la lista de los marcados con yes.
        // hay que dejar solo el primero encontrado de diferente juego .
        int v = arrayDataLogtemp.size();
        if (v > 0) {
            texto.setMovementMethod(new ScrollingMovementMethod());
            texto.append("   Mejores Tiempos por Juego  \n");
            for (w = v-1; w > -1; w--) {
                String data = arrayDataLogtemp.get(w).toString();
                String[] campos = data.split(",");
                jugador = campos[0];
                tiempo = campos[1];
                juego = campos[2];
                record = campos[3];
                menusalida = "Jugador: " + jugador + ",Juego_" + juego + ",tiempo: " + tiempo + " Seg. " + record;
                if (w == v-1) {
                    texto.append("    "+menusalida);
                    juegotmp = juego;
                } else {
                    if (data.toString().contains("," + juegotmp +",")) {
                        // Ya esta el record para este juego no hay que añadirlo
                    } else {
                        // añadir el record para este juego
                        texto.append(menusalida);
                        juegotmp = juego;
                    }
                }
            }
        }
        arrayDataLogtemp.clear();
    }


    public void mostrarrecycler(String value,String nivelapresentar){
        setContentView(R.layout.activity_resultado_recycler);
        TextView t1 = findViewById(R.id.txtlistadodelaspartidas);
        TextView t2 = findViewById(R.id.txtfiltrado);
        TextView tnivel = findViewById(R.id.txtnivel);
        RadioButton rbb = findViewById(R.id.nivelBasico);
        RadioButton rbd = findViewById(R.id.nivelDificil);
        RadioButton rbmd = findViewById(R.id.nivelMuyDificil);
        RadioButton rbtodos = findViewById(R.id.nivelTodos);
        String nivelstring = "BASICO";
        if (nivelapresentar.contains("1")){
            rbb.setChecked(true);

        }else if (nivelapresentar.contains("2")){
            nivelstring = "DIFICIL";
            rbd.setChecked(true);
        }else {
            if (nivelapresentar.contains("3")){
                nivelstring = "MUY DIFICIL";
                rbmd.setChecked(true);
            }else {
                if (nivelapresentar.contains("4")){
                    nivelstring = "TODOS";
                    rbtodos.setChecked(true);
                }
            }
        }

        tnivel.setText("NIVEL "+nivelstring);
        if (value.contains("record")){
            t1.setText("LISTADO DE TODOS LOS RECORDS POR JUEGO");
            t2.setText("Filtrar por records,juego,jugador o tiempo");
        }else {
            if (value.contains("orden")){
                t1.setText("LISTADO DE TODAS LAS PARTIDAS ORDENADAS POR FECHA");
                t2.setText("Filtrar por records,juego,jugador o tiempo");
            }
        }

        rbb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nivel = "1";
                view.setTag("tiempo");
                ordenar1(view,0);
            }
        });
        rbd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nivel = "2";
                view.setTag("tiempo");
                ordenar1(view,0);
            }
        });
        rbmd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nivel = "3";
                view.setTag("tiempo");
                ordenar1(view,0);
            }
        });
        rbtodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nivel = "4";
                view.setTag("tiempo");
                ordenar1(view,0);
            }
        });


        recView = (RecyclerView) findViewById(R.id.RecView);
        //recView.setHasFixedSize(true);//opcional, si sé que el tamaño no va a cambiar

        adaptador = new AdapterPartidas(datos);


        recView.setAdapter(adaptador);

        recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }



    public void volveramenu(View view) {
        //onBackPressed();
        finish();
        Intent List = new Intent(getApplicationContext(), MainActivity.class);
        List.putExtra("nombre",JugadorActual);
        List.putExtra("jugadordesdeResultados",jugador);
        List.putExtra("juegodesdeResultados",juego);
        List.putExtra("tiempodesdeResultados",tiempo);
        List.putExtra("avatardesdeResultados",sinAvatar);
        List.putExtra("rotadevuelta",rotada);
        List.putExtra("originaluri",original_photo_galery_uri);
    //    sinAvatar = getIntent().getStringExtra("sinAvatar");
        Log.i("MIAPP","volveramenu sinAvatar es : "+sinAvatar);
        startActivity(List);
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
        //filtartodaspartidas();
        Partida partida_aux = null;

        //CONSULTO LA TABLA DE RECORD Y OBTENGO LA LISTA JUEGOS - RECORD
        BaseDatosPuntuaciones baseDatosPuntuaciones = new BaseDatosPuntuaciones(this, "MiDB", null, 1);


        ArrayList<Puntuacion> listajuegos = new ArrayList<Puntuacion>();
        ArrayList<Puntuacion> listajuegos1 = new ArrayList<Puntuacion>();
        ArrayList<Puntuacion> listajuegos2 = new ArrayList<Puntuacion>();
        // solo partidas con record
        //listajuegos = (ArrayList<Puntuacion>) baseDatosPuntuaciones.listaconrecord();
        // todas las partidas
        String nivelporbusqueda = nivel;
        Log.i("traceo","Resultados-ordenar1-nivelporbusqueda es : "+ nivelporbusqueda);

        if (j == 1) {// mostrar solo record
            if (nivelporbusqueda.contains("4")){
                listajuegos = (ArrayList<Puntuacion>) baseDatosPuntuaciones.listaconrecord("1");
                listajuegos1 = (ArrayList<Puntuacion>) baseDatosPuntuaciones.listaconrecord("2");
                listajuegos2 = (ArrayList<Puntuacion>) baseDatosPuntuaciones.listaconrecord("3");
                if (listajuegos1!=null){
                    listajuegos.addAll(listajuegos1);
                }
               if (listajuegos2!=null){
                   listajuegos.addAll(listajuegos2);
               }
                if (listajuegos!=null) {
                    Collections.sort(listajuegos, new Comparator<Puntuacion>() {
                        @Override
                        public int compare(Puntuacion puntuacion, Puntuacion t1) {
                            return puntuacion.getJuego().toString().compareTo(t1.getJuego().toString());
                        }

                    });
                }



            }else {
                listajuegos = (ArrayList<Puntuacion>) baseDatosPuntuaciones.listaconrecord(nivelporbusqueda);
            }

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
            setContentView(R.layout.activity_resultado_recycler);
            recView = (RecyclerView) findViewById(R.id.RecView);
            //recView.setHasFixedSize(true);//opcional, si sé que el tamaño no va a cambiar

            adaptador = new AdapterPartidas(datos);


            recView.setAdapter(adaptador);

            recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        } else {
            String ii = view.getTag().toString();
            String string = "juego";
            switch (ii){
                case "juego":
                    //string ="juego";
                    //CONSULTO LA TABLA DE RECORD Y OBTENGO LA LISTA JUEGOS - RECORD
                    presentarEnRecycler(string,nivel);
                    //listajuegos = (ArrayList<Puntuacion>) baseDatosPuntuaciones.listatodaspartidas(string);
                    //mostrarrecycler();
                    break;
                case "jugador":
                    string = "nombre";
                    //CONSULTO LA TABLA DE RECORD Y OBTENGO LA LISTA JUEGOS - RECORD
                    presentarEnRecycler(string,nivel);
                    //listajuegos = (ArrayList<Puntuacion>) baseDatosPuntuaciones.listatodaspartidas(string);
                    //mostrarrecycler();
                    break;
                case "tiempo":
                    string = "tiempo";
                    //OBTENGO TODAS LAS JUGADAS POR ORDEN
                    filtartodaspartidas(nivel);
                    mostrarrecycler("orden",nivel);
                    //listajuegos = (ArrayList<Puntuacion>) baseDatosPuntuaciones.listatodaspartidas(string);
                    //mostrarrecycler();
                    break;
                case "foto":
                    filtarparidasrecords();
                    mostrarrecycler("record",nivel);
                    break;
                default:break;
            }
            /*
            if (ii.contains("juego")) {

            } else {
                if (ii.contains("jugador")) {
                    string = "nombre";
                } else {
                    // ordernar por tiempo
                    string = "tiempo";
                }
            }
            //CONSULTO LA TABLA DE RECORD Y OBTENGO LA LISTA JUEGOS - RECORD
            listajuegos = (ArrayList<Puntuacion>) baseDatosPuntuaciones.listatodaspartidas(string);
            */
        }
        /*

        for (int i = 0; i < listajuegos.size(); i++) {
            String jugador = listajuegos.get(i).getNombre();
            String juego = listajuegos.get(i).getJuego();
            String tiempo = listajuegos.get(i).getTiempo();
            partida_aux = new Partida(jugador, juego, tiempo);
            datos.add(partida_aux);
        }
        setContentView(R.layout.activity_resultado_recycler);
        recView = (RecyclerView) findViewById(R.id.RecView);
        //recView.setHasFixedSize(true);//opcional, si sé que el tamaño no va a cambiar

        adaptador = new AdapterPartidas(datos);


        recView.setAdapter(adaptador);

        recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        */
    }

    /**
     // ejemplo filtering --a Estudiar
     ArrayList<Student> ar = new ArrayList<Student>();
     ar.add(new Student(111, "bbbb", "london"));
     ar.add(new Student(131, "aaaa", "nyc"));
     ar.add(new Student(121, "cccc", "jaipur"));


     for (int i=0; i<ar.size(); i++)
     System.out.println(ar.get(i));
     Log.i("MIAPP","ar es :"+ ar);
     //Collections.sort(ar, new Sortbyroll());

     System.out.println("\nSorted by rollno");
     for (int i=0; i<ar.size(); i++)
     System.out.println(ar.get(i));
     Log.i("MIAPP","ar es :"+ ar);
     */

/**
    //pruebas
    // A class to represent a student.
    class Student
    {
        int rollno;
        String name, address;

        // Constructor
        public Student(int rollno, String name,
                       String address)
        {
            this.rollno = rollno;
            this.name = name;
            this.address = address;
        }

        // Used to print student details in main()
        public String toString()
        {
            return this.rollno + " " + this.name +
                    " " + this.address;
        }
    }

    class Sortbyroll implements Comparator<Student>
    {
        // Used for sorting in ascending order of
        // roll number
        public int compare(Student a, Student b)
        {
            return a.rollno - b.rollno;
        }
    }
 * @param string
 */


public void presentarEnRecycler(String string,String nivelamostrar){
    Partida partida_aux = null;
    BaseDatosPuntuaciones baseDatosPuntuaciones = new BaseDatosPuntuaciones(this, "MiDB", null, 1);
    ArrayList<Puntuacion> listajuegos = new ArrayList<Puntuacion>();
    String juegoelegido = "no";
    if (!string.contains("xx")){
        listajuegos = (ArrayList<Puntuacion>) baseDatosPuntuaciones.listatodaspartidas(string, juegoelegido,nivel);
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
    }else {

    }

    setContentView(R.layout.activity_resultado_recycler);
    TextView t1 = findViewById(R.id.txtlistadodelaspartidas);
    TextView t2 = findViewById(R.id.txtfiltrado);
    TextView tnivel = findViewById(R.id.txtnivel);
    RadioButton rbb = findViewById(R.id.nivelBasico);
    RadioButton rbd = findViewById(R.id.nivelDificil);
    RadioButton rbmd = findViewById(R.id.nivelMuyDificil);
    RadioButton rbtodos = findViewById(R.id.nivelTodos);
    String nivelstring = "BASICO";
    if (nivelamostrar.contains("1")){
        rbb.setChecked(true);
    }else if (nivelamostrar.contains("2")){
        rbd.setChecked(true);
        nivelstring = "DIFICIL";
    }else {
        if (nivelamostrar.contains("3")){
            nivelstring = "MUY DIFICIL";
            rbmd.setChecked(true);
        }else {
            if (nivelamostrar.contains("4")){
                nivelstring = "TODOS";
                rbtodos.setChecked(true);
            }
        }

    }

    tnivel.setText("NIVEL "+nivelstring);

    if (string.contains("juego")){
        t1.setText("LISTADO DE TODAS LAS PARTIDAS POR JUEGO");
        t2.setText("Filtrar por records,juego,jugador o tiempo");
    }else {
        if (string.contains("nombre")){
            t1.setText("LISTADO DE TODAS LAS PARTIDAS POR JUGADOR");
            t2.setText("Filtrar por records,juego,jugador o tiempo");
        }
    }

    recView = (RecyclerView) findViewById(R.id.RecView);
    //recView.setHasFixedSize(true);//opcional, si sé que el tamaño no va a cambiar

    adaptador = new AdapterPartidas(datos);


    recView.setAdapter(adaptador);

    recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
}

class Jugada
{

    String jugador, tiempo,juego,record;

    // Constructor
    public Jugada(String jugador, String tiempo,
                   String juego,String record)
    {
        this.jugador = jugador;
        this.tiempo = tiempo;
        this.juego = juego;
        this.record = record;
    }

    // Used to print student details in main()
    public String toString()
    {
        return this.jugador + " " + this.juego +
                " " + this.tiempo;
    }
}

    class Sortbyroll implements Comparator<Jugada>
    {
        // Used for sorting in ascending order of
        // roll number
        public int compare(Jugada a, Jugada b)
        {
            return   a.juego.compareTo(b.juego);
        }
    }


}
