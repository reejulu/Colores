package a.bb.colores;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Puntuacion extends ArrayList<Puntuacion> implements Comparable<Puntuacion>{
    private int id;
    private String nombre;
    private String tiempo;
    private String juego;
    private String record;
    private String sinAvatar;
    private String rotada;
    private String nivel;
    private Integer number;
    private Double distance;

    public Puntuacion(int id, String nombre, String tiempo, String juego, String record, String sinAvatar, String rotada, String nivel) {
        this.id = id;
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.juego = juego;
        this.record = record;
        this.sinAvatar = sinAvatar;
        this.rotada = rotada;
        this.nivel = nivel;
    }

    public String getRotada() {
        return rotada;
    }

    public void setRotada(String rotada) {
        this.rotada = rotada;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getSinAvatar() {
        return sinAvatar;
    }

    public void setSinAvatar(String sinAvatar) {
        this.sinAvatar = sinAvatar;
    }

    private static ArrayList<Puntuacion> top = new ArrayList<Puntuacion>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getJuego() {
        return juego;
    }

    public void setJuego(String juego) {
        this.juego = juego;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public Puntuacion(int id, String nombre, String tiempo, String juego, String record ,String sinAvatar) {
        this.id = id;
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.juego = juego;
        this.record = record;
        this.sinAvatar = sinAvatar;
    }

    public Puntuacion(String nombre, String tiempo, String juego, String record, String sinAvatar) {
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.juego = juego;
        this.record = record;
        this.sinAvatar = sinAvatar;
    }
    public Puntuacion(String nombre, String tiempo, String juego, String record, String sinAvatar,String rotada,String nivel) {
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.juego = juego;
        this.record = record;
        this.sinAvatar = sinAvatar;
        this.rotada = rotada;
        this.nivel = nivel;
    }

    public Puntuacion() {
        this.id = id;
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.juego = juego;
        this.record = record;
        this.sinAvatar = sinAvatar;
        this.rotada = rotada;
        this.nivel = nivel;
    }


    public static ArrayList<Puntuacion> getTop() {
        return top;
    }

    public static void setTop(ArrayList<Puntuacion> top) {
        Puntuacion.top = top;
    }

    @Override
    public String toString() {
        return "Puntuacion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", tiempo='" + tiempo + '\'' +
                ", juego='" + juego + '\'' +
                ", record='" + record + '\'' +
                ", record='" + sinAvatar + '\'' +
                ", rotada='" + rotada + '\'' +
                ", nivel='" + nivel + '\'' +
                '}';
    }


    @Override
    public Stream<Puntuacion> stream() {
        return null;
    }


    @Override
    public int compareTo(@NonNull Puntuacion puntuacion) {
        //return puntuacion.nombre.contains(puntuacion.nombre)? -1 : 1;
        return puntuacion.nombre.toString().compareTo(puntuacion.nombre.toString());
    }


}
