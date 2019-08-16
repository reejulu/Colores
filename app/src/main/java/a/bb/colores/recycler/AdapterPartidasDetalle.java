package a.bb.colores.recycler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import a.bb.colores.DetalleJugador;
import a.bb.colores.R;

//*****************************
// PROVEE LOS DATOS AL ADAPTER
//*****************************
public class AdapterPartidasDetalle extends RecyclerView.Adapter<PartidasViewHolder>  {

    public AdapterPartidasDetalle(ArrayList<Partida> datos)
    {
        this.datos = datos;
    }
    private ArrayList<Partida> datos;
    Context c;


    //Creo la vista, con el Holder dentro
    // ESTOY CREANDO CADA ELEMENTO DE LA LISTA INFLANDO EL LAYOUT INDICADO
    // QUE EN MI CASO TENGO DOS TEXTVIEW ( RESERVO POR DECIRLO EL HUECO . LA CAJA)
    //  HOLDER  .....ES COMO ..SI FUERA HUECO A RELLENAR
    // DESPUES NECESITO LOS DATOS DE LOS MISMOS ,,Y ESO SE HACE EN EL onBindViewHolder
    @NonNull
    @Override
    public PartidasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PartidasViewHolder partidasViewHolder = null;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View itemView = inflater.inflate(R.layout.layout_partidas_item, parent, false);

        partidasViewHolder = new PartidasViewHolder(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // recupero la posicion previamente marcada con setTag,,(*1)
                int posicion_tocada = (int) view.getTag();
                Partida partida = datos.get(posicion_tocada);
                Log.i("MIAPP", " se ha tocado un elemento de la lista : " + posicion_tocada);
                Log.i("MIAPP", " se ha seleccionado el empleado : " + partida.toString());
                Intent intent = new Intent(view.getContext(), DetalleJugador.class);
                // AÑADO LOS DATOS DEL EMPLEADO EN EL INTENT
                String jugador = partida.getJugador().toString();
                String sinAvatar = partida.getSinAvatar().toString();
                String juego = partida.getJuego().toString();
                String tiempo = partida.getTiempo().toString();
                String rotada = partida.getRotada().toString();
                String nivel = partida.getNivel().toString();
                intent.putExtra("nombre",jugador);
                intent.putExtra("juego",juego);
                intent.putExtra("tiempo",tiempo);
                intent.putExtra("avatar",sinAvatar);
                intent.putExtra("rotada",rotada);
                intent.putExtra("nivel",nivel);
                view.getContext().startActivity(intent);
                ((Activity)view.getContext()).finish();

            }
        });

        return partidasViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PartidasViewHolder partidasViewHolder, int i) {

        Partida partida = datos.get(i);
        partidasViewHolder.cargarPartidasEnHolder(partida);
        partidasViewHolder.itemView.setTag(i);

    }




    @Override
    public int getItemCount() {
        return datos.size();
    }


}
