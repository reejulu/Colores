package a.bb.colores.recycler;

public class Partida {
    public Partida(String sinAvatar) {
        this.sinAvatar = sinAvatar;

    }

    @Override
    public String toString() {
        return "Partida{" +
                "jugador='" + jugador + '\'' +
                ", juego='" + juego + '\'' +
                ", tiempo='" + tiempo + '\'' +
                ", sinAvatar='" + sinAvatar + '\'' +
                ", rotada='" + rotada + '\'' +
                ", nivel='" + nivel + '\'' +
                '}';
    }

    public String getSinAvatar() {
        return sinAvatar;
    }

    public void setSinAvatar(String sinAvatar) {
        this.sinAvatar = sinAvatar;
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

    private String jugador;
    private String juego;
    private String tiempo;
    private String sinAvatar;
    private String rotada;
    private String nivel;

    public Partida(String jugador, String juego, String tiempo,String sinAvatar) {
        this.jugador = jugador;
        this.juego = juego;
        this.tiempo = tiempo;
        this.sinAvatar = sinAvatar;
    }

    public Partida(String jugador, String juego, String tiempo,String sinAvatar,String rotada,String nivel) {
        this.jugador = jugador;
        this.juego = juego;
        this.tiempo = tiempo;
        this.sinAvatar = sinAvatar;
        this.rotada = rotada;
        this.nivel = nivel;
    }
    public Partida(String jugador, String juego, String tiempo) {
        this.jugador = jugador;
        this.juego = juego;
        this.tiempo = tiempo;
    }
    public Partida() {

    }

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }

    public String getJuego() {
        return juego;
    }

    public void setJuego(String juego) {
        this.juego = juego;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }
}
